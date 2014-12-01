
/* 
 * *****************************************************************
 * Author:          VịLH
 * Date Created:    29/04/2014
 * Description:     Auto gen Table (FS Tool)
 * 
 * *****************************************************************
 * Last Updated:    10/09/2014 
 * Feature: 
 *  + Read file with Aysnc Technology (Many Sheet)
 *  + Gen Code Table -> Lua
 *  + Display ID, Image text, LogID in sheet
 *  + Inser, update, delete Rows on Table
 *  + AutoSave when completed insert 5 rows!
 *  + Display Image when click into comboBox choose item! 
 *  + Auto reload when click Choose Group
 *  + Display Path of game structure
 *  + Add Custom Item into Custom.lua
 *  + Progressbar for Information loading
 *      - Optimize code: remove _arrIbitem , _arrArmor, _arrMagicscript,...
 *  + Display log JSON with data from server
 *  + Add Exception WriteLog
 * 
 * *****************************************************************
 * v2.3 Fix don't need access GIM Tool get ID log for Special Item
 * 
 * *****************************************************************
 * v2.2
 *  Fix Settings 
 * 
 * *****************************************************************
 * v1.9; v2.0; v2.1
 *  Bổ sung log -> tìm các máy bị thiếu lib (System.Web.Helpers)
 * 
 * *****************************************************************
 * v1.7; v1.8
 *  + Add Write Log clsMain (CommonLib)
 *  + Add Write Log in try catch (Function frmMain)
 *  
 * *****************************************************************
 * v1.6 
 *  JSON get data from server
 *  
 * *****************************************************************
 * v1.3
 *  + Display Image when click into comboBox choose item!  
 *
 * ****************************************************************** 
 * v1.2
 *  + Read file with Aysnc Technology (Many Sheet)
 *  + Gen Code Table -> Lua
 *  + Display ID, Image text, LogID in sheet
 *  + update Rows on Table
 *  + AutoSave when completed insert 5 rows!
 *  + Add Custom Item into Custom.lua
 *  
 * *****************************************************************
 * v1.1
 *  + Display Path of game structure
 *  + Insert, delete Rows on Table
 *  + Auto reload when click Choose Group 
 *  + Progressbar for Information loading
 *      - Optimize code: remove _arrIbitem , _arrArmor, _arrMagicscript,... => tiết kiệm bộ nhớ, xử lý trực tiếp trên datatable, lúc load trong comboBox cũng không cần check != null
 *
 * * *****************************************************************
 */

/*
 * Đang giải quyết: nothing  
 */

/*
 * có một đống dữ liệu const, data gồm string, number, làm sao lưu lại tất cả các string, number -> Dictionary<string, int>
 */

/*
 * ***************************** Luồng dữ liệu ***************************** 
 * Giao diện vừa load lên -> 
 *    ReadDataUseAsync: đọc tất cả dữ liệu trong file excel (Aysnc): dùng bất đồng bộ
 *
 * -------------------------------------------------------------------------
 * User choose radioButtonHelm -> 
 *  B1. rbHelm_CheckedChanged: Kiểm tra có check ko? -> 
 *      nếu có:  xem B2
 *      nếu kô: del cái Thread đi -> giải phóng tài nguyên
 *
 *  B2. AddItemToCB: gọi 1 Thread khác lên (Thread này sẽ load dữ liệu từ databaseHelm vào comboBox)
 * 
 * -------------------------------------------------------------------------
 * User choose comboBox -> để xem ID bên cạnh
 *  B1. cbItemName_SelectedIndexChanged -> dùng iPosStart cho biết dòng bắt đầu của item đầu tiên khi load 
 *  
 * -------------------------------------------------------------------------
 */

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using CommonLib;
using System.Diagnostics;
using System.Threading;
using System.IO;
using System.Net;
using System.Web.Helpers;


namespace FSSupport
{
    public partial class FrmGenItems : Form
    {
        #region "Variables"
        private static DataTable _dtMaterial, _dtMagicScript, _dtibitem, _dtArmor, _dtRing, _dtAmulet, _dtBoot, _dtBelt, _dtHelm, _dtCuff, _dtPendant, _dtHorse;
        private static Dictionary<string, int> _dicData;

        private static string _szFileItems;     // đường dẫn chứa file item
        private static string _szFileImage;     // đường dẫn chứa file image
        private static string _szLogFile;       // đường dẫn chứa file Log Error
        

        private List<int> _lstColor;            // trạng thái màu sắc: vàng, xanh lá cây
        private List<clsListTable> _lstCustom;  // Danh sách các item theo kiểu khác trong custom file! (custom.lua)
        
        private string _szOutput;               // đường dẫn chứa file output        
        private string _szCustom;               // đường dẫn chứa file custom template;
        private Thread _thread = null;          // dùng để chạy Thread khi load dữ liệu 
        private int _iCurrentIndex;				// cho biết hiện người dùng đang chọn dòng nào!
                
        #endregion     

        public FrmGenItems()
        {
            InitializeComponent();            
            _dicData = new Dictionary<string, int>();
            _lstColor = new List<int>();
            _szLogFile = Application.StartupPath + "\\info.log";
             
        }        

        #region "Common"

        /*
            {
               "1": {
                   "result":
                    {  
                        "ITEM_ID":"CNSK",
                        "NAME":"C\u1ea9m nang s\u1ef1 ki\u1ec7n",
                        "PRODUCT_CODE":"FS",
                        "ID_INGAME":"6,1,5572",
                        "TYPE":"1",
                        "START_TIME":null,
                        "END_TIME":null}
                    },
                "0":1
            }
             * 
             -> json["1"].result.ITEM_ID = "CNSK" ;
             -> json["1"].result.NAME = "Cẩm nang sự kiện";
             -> json["1"].START_TIME = "";
             * 
             * 
             * http://gim.tool.vng.vn/mda/api/get_itemsIDbyName?NAME=C%E1%BA%A9m%20nang%20s%E1%BB%B1%20ki%E1%BB%87nc
             */


        private clsItem GetInfoItemByName(string name)
        {
            // phải có try catch vì khi trả dữ liệu về json[0]            
            try
            {
                name = clsMain.ConvertToProperName(name);

                clsItem item = new clsItem();

                if (name.ToString() == "")
                    return null;

                string strURL = "http://gim.tool.vng.vn/mda/api/get_itemsIDbyName?NAME=" + name + "&PRODUCT_CODE=FS";
                                
                var json = GetJSonFromServer(strURL);                

                if (json == null)
                    return null;


                if (json["0"] == 0)
                    return null;

                item.szItemID = json["1"].result.ITEM_ID;
                item.szName = json["1"].result.NAME;
                item.szIdIngame = json["1"].result.ID_INGAME;
                item.szType = json["1"].result.TYPE;
                item.szStartTime = json["1"].result.START_TIME;
                item.szEndTime = json["1"].result.END_TIME;
                item.szProductCode = json["1"].result.PRODUCT_CODE;

                return item;
            } catch (Exception ex)
            {             
                clsMain.WriteLog(_szLogFile, ex);
                return null;
            }
        }

        private String GetWebServices(string url)
        {
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);

            try
            {
                WebResponse response = request.GetResponse();
                using (Stream responseStream = response.GetResponseStream())
                {
                    StreamReader reader = new StreamReader(responseStream, Encoding.UTF8);
                    return reader.ReadToEnd();
                }
            }
            catch (WebException ex)
            {
                WebResponse errorResponse = ex.Response;
                using (Stream responStream = errorResponse.GetResponseStream())
                {
                    StreamReader sr = new StreamReader(responStream, Encoding.GetEncoding("utf-8"));
                    String errorText = sr.ReadToEnd();
                }
                throw;
            }
        }       

        private dynamic GetJSonFromServer(string strUrl)
        {
            try
            {                
                WebClient wb = new WebClient();             
                var data = wb.DownloadString(strUrl);

                clsMain.WriteLog(_szLogFile, "data = " + data.ToString());
                var json = System.Web.Helpers.Json.Decode(data);                                

                return json;
            }
            catch (Exception ex)
            {                               
                clsMain.WriteLog(_szLogFile, ex);
                return null;
            }
        }
     

        private void DeleteRows()
        {
            try
            {
                lvTable.SelectedItems[0].Remove();
            }
            catch (Exception ex)
            {
                clsMain.WriteLog(_szLogFile, ex);
            }
        }

        // tự động lưu trữ nội dung vào file tableAward.lua!
        private void autoSave()
        {
            // các dòng nằm trong table này mới save
            int[] iArrRow = {5,10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100};

            for (int i = 0; i < iArrRow.Length; i++)
            {
                if (lvTable.Items.Count == iArrRow[i])  // save
                    break;

                if (i == iArrRow.Length - 1)    // ko cần save
                {
                    toolStripStatusLabel.Text = "";
                    return;
                }
            }
            
            List<string> lstContent = new List<string>();

            lstContent.Add("tbAward = {");
            string strColor = "yellow";

            for (int i = 0; i < lvTable.Items.Count; i++)
            {
                string[] strTemp = new string[6];

                if (_lstColor[i] == 0)
                    strColor = "yellow";
                else
                    strColor = "green";

                strTemp[0] = "szName = \"<c=" + strColor + ">" + lvTable.Items[i].SubItems[0].Text.ToString() + "<c>\",\t";
                strTemp[1] = "tbProp = {" + lvTable.Items[i].SubItems[1].Text.ToString() + "},\t";

                if (lvTable.Items[i].SubItems[2].Text != "")
                    strTemp[2] = "nRate = " + lvTable.Items[i].SubItems[2].Text.ToString() + ",\t";

                strTemp[3] = "nType = " + lvTable.Items[i].SubItems[3].Text.ToString() + ",\t";
                strTemp[4] = "nCount = " + lvTable.Items[i].SubItems[4].Text.ToString();

                if (lvTable.Items[i].SubItems[5].Text != "")
                    strTemp[5] = ",\tszItemID = \"" + lvTable.Items[i].SubItems[5].Text.ToString() + "\"";

                lstContent.Add("\t[" + (i + 1).ToString() + "] = {" + strTemp[0] + strTemp[1] + strTemp[2] + strTemp[3] + strTemp[4] + strTemp[5] + "},");
            }

            lstContent.Add("}");

            CommonLib.clsMain.WriteFile(_szOutput, lstContent, 1, Encoding.Unicode);
            toolStripStatusLabel.Text = "Auto Save Succeed";
        }


        private double CheckRate()
        {         
            // nếu bằng 100 => bFlag = true
            double nRate = 0;
            for (int i = 0; i < lvTable.Items.Count; i++)
                if (lvTable.Items[i].SubItems[2].Text != "")
                    nRate = nRate + Convert.ToDouble(lvTable.Items[i].SubItems[2].Text.ToString());

            return Convert.ToDouble(nRate);
        }

        private static string ReplaceString(string str)
        {
            str = str.Replace("#", "");
            str = str.Replace("<c=green>", "");
            str = str.Replace("<c=yellow>", "");
            str = str.Replace("<c=red>", "");
            str = str.Replace("<c=pk>", "");
            str = str.Replace("<c=cyan>", "");
            str = str.Replace("<c=yel>", "");
            str = str.Replace("<c>", "").Trim();
            return str;
        }

        private void InitItemName()
        {
            _dicData = new Dictionary<string, int>();
            _dicData.Add("material", 2);        // sheetname = 'material' ;     số dòng bắt đầu = 2 (bỏ title)
            _dicData.Add("magicscript", 2);     // sheetname = 'magicscript' ;  số dòng bắt đầu = 2 (bỏ title)
            _dicData.Add("ibitem", 2);          // sheetname = 'ibitem' ;       số dòng bắt đầu = 2 (bỏ title)
            _dicData.Add("armor", 2);           // ...
            _dicData.Add("ring", 2);
            _dicData.Add("amulet", 2);
            _dicData.Add("boot", 1);
            _dicData.Add("belt", 2);
            _dicData.Add("helm", 2);
            _dicData.Add("cuff", 2);
            _dicData.Add("pendant", 2);
            _dicData.Add("horse", 2);

            // MessageBox.Show(_dic["material"].ToString()); // 2
        }

        private void InitProgressBar()
        {
            toolStripProgressBar.Minimum = 0;
            toolStripProgressBar.Maximum = 100;
        }

        private void InitData()
        {
            txtCount.Text = "1";
            txtItemID.Text = "3,1280,0,0,0,0";
            txtItemIDLog.Text = "Get Item From GIM Tool";
            txtItemName.Text = "Túi Tân Thủ";
            txtRate.Text = "0.12";
            txtType.Text = "1";
        }
        #endregion 

        #region "Read Data use Aysnc"

        /* sử dụng kỹ thuật bất đồng bộ */
        private delegate void AsyncGetString(); // hàm xử lý bất đồng bộ kiểu void

        /* sử dụng kỹ thuật bất đồng bộ */
        // private delegate void AsyncGetItem (DataTable dt, string sheetName); // hàm xử lý bất đồng bộ kiểu void with parameters
        
        //private static void ReadFileLog()
        //{
            // Đọc file log format
            // _dtLogFormat = CommonLib.clsMain.ReadExcelFile(_szFileLogFormat, "Item");
        //}        

        private static void ReadDataUseAsync()
        {
            //AsyncGetItem getItem = new AsyncGetItem(ReadData_Sheet_Item);            
            //IAsyncResult resultItemM = getItem.BeginInvoke(_dtMaterial, "material", null, null); // bị lỗi nên tách ra từng hàm
            //IAsyncResult resultItemMS = getItem.BeginInvoke(_dtMagicScript , "magicscript", null, null);
            
            // load dữ liệu sheet material
            AsyncGetString getM = new AsyncGetString(ReadData_Sheet_Material);
            IAsyncResult resultM = getM.BeginInvoke(null, null);

            // load dữ liệu sheet magicscript
            AsyncGetString getMS = new AsyncGetString(ReadData_Sheet_Magicscript);
            IAsyncResult resultMS = getMS.BeginInvoke(null, null);

            // load dữ liệu sheet ibItem
            AsyncGetString getIB = new AsyncGetString(ReadData_Sheet_IBItem);
            IAsyncResult resultIB = getIB.BeginInvoke(null, null);

            // load dữ liệu sheet armor
            AsyncGetString getA = new AsyncGetString(ReadData_Sheet_Armor);
            IAsyncResult resultA = getA.BeginInvoke(null, null);

            // load dữ liệu sheet ring
            AsyncGetString getR = new AsyncGetString(ReadData_Sheet_Ring);
            IAsyncResult resultR = getR.BeginInvoke(null, null);

            // load dữ liệu sheet amulet
            AsyncGetString getAmu = new AsyncGetString(ReadData_Sheet_Amulet);
            IAsyncResult resultAmu = getAmu.BeginInvoke(null, null);                        

            // load dữ liệu sheet boot
            AsyncGetString getB = new AsyncGetString(ReadData_Sheet_Boot);
            IAsyncResult resultB = getB.BeginInvoke(null, null);

            // load dữ liệu sheet belt
            AsyncGetString getBe = new AsyncGetString(ReadData_Sheet_Belt);
            IAsyncResult resultBe = getBe.BeginInvoke(null, null);

            // load dữ liệu sheet helm
            AsyncGetString getH = new AsyncGetString(ReadData_Sheet_Helm);
            IAsyncResult resultH = getH.BeginInvoke(null, null);

            // load dữ liệu sheet horse
            AsyncGetString getHorse = new AsyncGetString(ReadData_Sheet_Horse);
            IAsyncResult resultHorse = getHorse.BeginInvoke(null, null);

            // load dữ liệu sheet pendant
            AsyncGetString getPendant = new AsyncGetString(ReadData_Sheet_Pendant);
            IAsyncResult resultPendant = getPendant.BeginInvoke(null, null);

            // load dữ liệu sheet cuff
            AsyncGetString getCuff = new AsyncGetString(ReadData_Sheet_Cuff);
            IAsyncResult resultCuff = getCuff.BeginInvoke(null, null);

            // load dữ liệu file log
            //AsyncGetString getL = new AsyncGetString(ReadFileLog);
           // IAsyncResult resultL = getL.BeginInvoke(null, null);
            
            getM.EndInvoke(resultM);
            getMS.EndInvoke(resultMS);
            getIB.EndInvoke(resultIB);
            getA.EndInvoke(resultA);

            getR.EndInvoke(resultR);
            getAmu.EndInvoke(resultAmu);
            getB.EndInvoke(resultB);
            getBe.EndInvoke(resultBe);

            getH.EndInvoke(resultH);
            getHorse.EndInvoke(resultHorse);
            getPendant.EndInvoke(resultPendant);
            getCuff.EndInvoke(resultCuff);
            
           // getL.EndInvoke(resultL);            
        }

        #endregion

        #region "Control ListView"
        private void LoadlItemToListView()
        {
            _szOutput = Application.StartupPath + "\\tableAward.lua";

            if (File.Exists(_szOutput) == true) // tồn tại thì mới đọc file
            {
                // đọc file tableAward
                List<string> lstAward = CommonLib.clsMain.ReadTextFile(_szOutput);
                List<clsListTable> lstTable = new List<clsListTable>();
                lstTable = LoadFileIntoTable(lstAward); // xử lý lstAward vào lstTable
                LoadListTableIntoListView(lstTable);    // load content on listView
            }
        }

        // Load list table vào listView
        private void LoadListTableIntoListView(List<clsListTable> lst)
        {
            ListViewItem lvItem = null;
            _lstColor = new List<int>();    // khởi tạo lại trạng thái màu sắc

            string[] strTemp = new string[6];

            for (int i = 0; i < lst.Count; i++)
            {
                strTemp[0] = lst[i].Name;
                strTemp[1] = lst[i].Prop;
                strTemp[2] = lst[i].Rate;

                strTemp[3] = lst[i].Type;
                strTemp[4] = lst[i].Count;
                strTemp[5] = lst[i].ItemID;

                if (lst[i].Color == (int)COLOR.GREEN)   // 1
                {
                    lvItem = new ListViewItem(strTemp, (int)COLOR.GREEN);
                    _lstColor.Add((int)COLOR.GREEN);
                }

                else if (lst[i].Color == (int)COLOR.YELLOW) // 0
                {
                    lvItem = new ListViewItem(strTemp, (int)COLOR.YELLOW);
                    _lstColor.Add((int)COLOR.YELLOW);
                }

                lvTable.Items.Add(lvItem);
            }
        }

        // Đọc file output sau đó load vào table -> show ra để xem các items đã add 
        private List<clsListTable> LoadFileIntoTable(List<string> lst)
        {
            string[] szTemp;
            int iPosStart = -1;
            int iStartIndex = -1;
            int iLastIndex = -1;

            List<clsListTable> lstTable = new List<clsListTable>();
            clsListTable table;

            for (int i = 1; i < lst.Count; i++)
            {
                szTemp = lst[i].Split('\t');

                if (szTemp.Length < 5)
                    continue;

                table = new clsListTable();     // reset lại giá trị clsListTable();
                
                for (int k = 1; k < szTemp.Length; k++) // 5 đoạn phân khúc
                {
                    switch (k)
                    {
                        case 1:         // [1] = {szName = "<c=yellow>Túi Quà Tài Lộc<c>",
                            iPosStart = szTemp[k].LastIndexOf("<c=yellow>");
                            if (iPosStart == -1)
                            {
                                iPosStart = szTemp[k].LastIndexOf("<c=green>");

                                if (iPosStart == -1)    // ko tìm thấy yellow và green -> duyệt phần tử kế tiếp!
                                    break;
                                else    // tìm thấy chuỗi <c=green>
                                {
                                    table.Color = (int)COLOR.GREEN;


                                    iStartIndex = iPosStart + 9;    //<c=green>
                                    iLastIndex = szTemp[k].Length - 5 - iStartIndex;    // trừ các ký tự <c>", (5 ký tự)
                                    table.Name = szTemp[k].Substring(iStartIndex, iLastIndex);
                                }
                            }
                            else  // tìm thấy chuỗi <c=yellow>
                            {
                                table.Color = (int)COLOR.YELLOW;

                                iStartIndex = iPosStart + 10;    //<c=yellow>
                                iLastIndex = szTemp[k].Length - 5 - iStartIndex;        // trừ các ký tự <c>", (5 ký tự)
                                table.Name = szTemp[k].Substring(iStartIndex, iLastIndex);
                            }
                            break;

                        case 2: // tbProp = {6,1,5913,0,0,0},
                            iPosStart = szTemp[k].LastIndexOf("{");
                            if (iPosStart == -1)
                                break;

                            iStartIndex = iPosStart + 1;
                            iLastIndex = szTemp[k].Length - 2 - iStartIndex;    // trừ các ký tự }, (2 ký tự)
                            table.Prop = szTemp[k].Substring(iStartIndex, iLastIndex);
                            break;

                        case 3: // nRate = 0.12,
                            iPosStart = szTemp[k].LastIndexOf("= ");
                            if (iPosStart == -1)
                                break;

                            iStartIndex = iPosStart + 2;
                            iLastIndex = szTemp[k].Length - 1 - iStartIndex;    // trừ các ký tự , (1 ký tự)
                            table.Rate = szTemp[k].Substring(iStartIndex, iLastIndex);
                            break;

                        case 4: // nType = 1,
                            iPosStart = szTemp[k].LastIndexOf("= ");
                            if (iPosStart == -1)
                                break;

                            iStartIndex = iPosStart + 2;
                            iLastIndex = szTemp[k].Length - 1 - iStartIndex;    // trừ các ký tự , (1 ký tự)
                            table.Type = szTemp[k].Substring(iStartIndex, iLastIndex);
                            break;

                        case 5:
                            // TH1: nCount = 1,    có szItemLog
                            // TH2: nCount = 1},   không có szItemLog

                            iPosStart = szTemp[k].LastIndexOf("= ");
                            if (iPosStart == -1)
                                break;

                            iStartIndex = iPosStart + 2;

                            // tìm dấu } trong trường hợp kết thúc một phần tử trong table!
                            int index = szTemp[k].LastIndexOf("}");
                            if (index == -1)    // không tìm thấy dấu }
                                iLastIndex = szTemp[k].Length - 1 - iStartIndex;    // trừ các ký tự , (1 ký tự)

                            else    // tìm thấy dấu }
                                iLastIndex = szTemp[k].Length - 2 - iStartIndex;    // trừ các ký tự }, (2 ký tự)

                            table.Count = szTemp[k].Substring(iStartIndex, iLastIndex);
                            break;


                        case 6: //szItemID = "VNG_527"},
                            iPosStart = szTemp[k].LastIndexOf("= \"");
                            if (iPosStart == -1)
                                break;

                            iStartIndex = iPosStart + 3;
                            iLastIndex = szTemp[k].Length - 3 - iStartIndex;        // trừ các ký tự "},  (3 ký tự)
                            table.ItemID = szTemp[k].Substring(iStartIndex, iLastIndex);
                            break;
                    }

                }
                lstTable.Add(table);
            }
            return lstTable;
        }

        private void InitListView()
        {
            lvTable.View = View.Details;
            lvTable.GridLines = true;
            lvTable.FullRowSelect = true;

            string[] strItemName = { "szName", "tbProp", "nRate", "nType", "nCount", "szItemID" };
            int[] iItemSize = { 230, 450, 60, 60, 60, 100 };

            // Add Column Header
            for (int i = 0; i < strItemName.Length; i++)
                lvTable.Columns.Add(strItemName[i], iItemSize[i]);
        }

        private void lvTable_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Delete)
            {
                try
                {
                    lvTable.SelectedItems[0].Remove();
                }
                catch(Exception ex)
                {
                    clsMain.WriteLog(_szLogFile, ex);
                }
            }
        }
        #endregion

        #region "ComboBox Color"
        private void InitColorComboBox()
        {
            cmbColor.Items.Add("yellow");
            cmbColor.Items.Add("green");
            cmbColor.SelectedIndex = 0;
        }

        #endregion

        #region "ComboBox ItemName"
        /// <summary>
        /// Mo lai textbox, ko an textbox
        /// </summary>
        private void RestoreCB()
        {
            cmbItemName.Invoke((MethodInvoker)delegate
            {
                cmbItemName.Text = "";
                cmbItemName.Enabled = true;                                
                cmbItemName.SelectedIndex = 0;
                cmbItemName.Focus();     // set focus tại comboBox ItemName

                btnInsert.Enabled = true;
            });
        }

        /// <summary>
        /// Reset lai gia tri cua Progressbar = 0, xoa resource trong comboBox
        /// </summary>
        private void ResetSettings()
        {
            cmbItemName.Invoke((MethodInvoker)delegate
            {
                toolStripProgressBar.Value = 0;
                cmbItemName.Items.Clear();
                cmbItemName.Enabled = false;
                btnInsert.Enabled = false;
                
            });
        }
        
        // chọn items trong comboBox
        private void cbItemName_SelectedIndexChanged(object sender, EventArgs e)
        {
            // hint for Item ID Log
            txtItemIDLog.Clear();
            txtItemIDLog.ForeColor = Color.Red;

            btnInsert.Enabled = true;

            string strID1 = "0";
            string strID2 = "0";
            string strID3 = "0";
            string strID4 = "0";
            string strID5 = "0";
            string strID6 = "0";
            string strImage = "";            

            int iChoice = cmbItemName.SelectedIndex;

            // ------------- KHI CHECK VÀO ALL --------------
            if (rbShowAll.Checked)  
            {
                int iPosStartM = _dicData["material"];  // lấy số dòng bắt đầu của từng sheet
                int iPosStartMS = _dicData["magicscript"];
                int iPosStartIB = _dicData["ibitem"];
                              
                int lengthM = _dtMaterial.Rows.Count - iPosStartM;
                int lengthM_MS = _dtMaterial.Rows.Count + _dtMagicScript.Rows.Count - iPosStartM - iPosStartMS;
                int lengthM_MS_IB = _dtMaterial.Rows.Count + _dtMagicScript.Rows.Count + _dtibitem.Rows.Count - iPosStartM - iPosStartMS - iPosStartIB;

                /* lengthM phải trừ cho iPosStartM: vì lý do sau:
                 * giả sử _dtMaterial.length có 1332 items 
                 * nhưng iChoice thuộc 1331
                 * Đáng lẽ iChoice sẽ nằm trong _dtMagicScript nhưng chưa trừ nên nó vẫn nằm trong _dtMaterial
                 * => truy xuất trong _dtMaterial, nên nó bị vượt quá index => sai
                 */

                if (0 <= iChoice && iChoice < lengthM)
                {
                    strID1 = _dtMaterial.Rows[iChoice + iPosStartM][1].ToString();
                    strID2 = _dtMaterial.Rows[iChoice + iPosStartM][2].ToString();

                    strImage = _dtMaterial.Rows[iChoice + iPosStartM][3].ToString();
                }
                else if (lengthM <= iChoice && iChoice < lengthM_MS)
                {
                    strID1 = _dtMagicScript.Rows[iChoice - lengthM + iPosStartMS][1].ToString();
                    strID2 = _dtMagicScript.Rows[iChoice - lengthM + iPosStartMS][2].ToString();
                    strID3 = _dtMagicScript.Rows[iChoice - lengthM + iPosStartMS][3].ToString();

                    strImage = _dtMagicScript.Rows[iChoice - lengthM + iPosStartMS][4].ToString();
                    
                }
                else if (lengthM_MS <= iChoice && iChoice < lengthM_MS_IB)
                {
                    strID1 = _dtibitem.Rows[iChoice - lengthM_MS + iPosStartIB][1].ToString();
                    strID2 = _dtibitem.Rows[iChoice - lengthM_MS + iPosStartIB][2].ToString();
                    strID3 = _dtibitem.Rows[iChoice - lengthM_MS + iPosStartIB][3].ToString();

                    strImage = _dtibitem.Rows[iChoice - lengthM_MS + iPosStartIB][4].ToString();
                }
            }

            // ------------- KHI CHECK VÀO MATERIAL --------------
            else if (rbMaterial.Checked) 
            {
                int iPosStart = _dicData["material"];
                strID1 = _dtMaterial.Rows[iChoice + iPosStart][1].ToString();
                strID2 = _dtMaterial.Rows[iChoice + iPosStart][2].ToString();

                strImage = _dtMaterial.Rows[iChoice + iPosStart][3].ToString(); // image cột số 4
            }

            // ------------- KHI CHECK VÀO MAGIC SCRIPT --------------
            else if (rbMagicScript.Checked) 
            {
                int iPosStart = _dicData["magicscript"];
                strID1 = _dtMagicScript.Rows[iChoice + iPosStart][1].ToString();
                strID2 = _dtMagicScript.Rows[iChoice + iPosStart][2].ToString();
                strID3 = _dtMagicScript.Rows[iChoice + iPosStart][3].ToString();
                
                strImage = _dtMagicScript.Rows[iChoice + iPosStart][4].ToString();
            }

            // ------------- KHI CHECK VÀO IBITEM --------------
            else if (rbIBItem.Checked)
            {
                int iPosStart = _dicData["ibitem"];
                strID1 = _dtibitem.Rows[iChoice + iPosStart][1].ToString();
                strID2 = _dtibitem.Rows[iChoice + iPosStart][2].ToString();
                strID3 = _dtibitem.Rows[iChoice + iPosStart][3].ToString();
                strImage = _dtibitem.Rows[iChoice + iPosStart][3].ToString();
            }

            // ------------- KHI CHECK VÀO ARMOR --------------
            else if (rbArmor.Checked)
            {
                int iPosStart = _dicData["armor"];
                strID1 = _dtArmor.Rows[iChoice + iPosStart][1].ToString();
                strID2 = _dtArmor.Rows[iChoice + iPosStart][2].ToString();
                strID3 = _dtArmor.Rows[iChoice + iPosStart][3].ToString();
                strID4 = _dtArmor.Rows[iChoice + iPosStart][11].ToString();

                strImage = _dtArmor.Rows[iChoice + iPosStart][4].ToString();
            }

            // ------------- KHI CHECK VÀO RING --------------
            else if (rbRing.Checked)    // nếu đang check Ring
            {
                int iPosStart = _dicData["ring"];
                strID1 = _dtRing.Rows[iChoice + iPosStart][1].ToString();
                strID2 = _dtRing.Rows[iChoice + iPosStart][2].ToString();
                strID3 = _dtRing.Rows[iChoice + iPosStart][3].ToString();
                strID4 = _dtRing.Rows[iChoice + iPosStart][11].ToString();

                strImage = _dtArmor.Rows[iChoice + iPosStart][4].ToString();
            }

            // ------------- KHI CHECK VÀO AMULET --------------
            else if (rbAmulet.Checked)
            {
                int iPosStart = _dicData["amulet"];
                strID1 = _dtAmulet.Rows[iChoice + iPosStart][1].ToString();
                strID2 = _dtAmulet.Rows[iChoice + iPosStart][2].ToString();
                strID3 = _dtAmulet.Rows[iChoice + iPosStart][3].ToString();
                strID4 = _dtAmulet.Rows[iChoice + iPosStart][11].ToString();

                strImage = _dtAmulet.Rows[iChoice + iPosStart][4].ToString();
            }

            // ------------- KHI CHECK VÀO BOOT --------------
            else if (rbBoot.Checked)
            {
                int iPosStart = _dicData["boot"];
                strID1 = _dtBoot.Rows[iChoice + iPosStart][1].ToString();
                strID2 = _dtBoot.Rows[iChoice + iPosStart][2].ToString();
                strID3 = _dtBoot.Rows[iChoice + iPosStart][3].ToString();
                strID4 = _dtBoot.Rows[iChoice + iPosStart][11].ToString();  // cột level

                strImage = _dtBoot.Rows[iChoice + iPosStart][4].ToString();
            }

            // ------------- KHI CHECK VÀO BELT --------------
            else if (rbBelt.Checked)
            {
                int iPosStart = _dicData["belt"];
                strID1 = _dtBelt.Rows[iChoice + iPosStart][1].ToString();
                strID2 = _dtBelt.Rows[iChoice + iPosStart][2].ToString();
                strID3 = _dtBelt.Rows[iChoice + iPosStart][3].ToString();
                strID4 = _dtBelt.Rows[iChoice + iPosStart][11].ToString();  // cột level

                strImage = _dtBelt.Rows[iChoice + iPosStart][4].ToString();
            }

            // ------------- KHI CHECK VÀO HELM --------------
            else if (rbHelm.Checked)
            {
                int iPosStart = _dicData["helm"];
                strID1 = _dtHelm.Rows[iChoice + iPosStart][1].ToString();
                strID2 = _dtHelm.Rows[iChoice + iPosStart][2].ToString();
                strID3 = _dtHelm.Rows[iChoice + iPosStart][3].ToString();
                strID4 = _dtHelm.Rows[iChoice + iPosStart][11].ToString();  // cột level

                strImage = _dtHelm.Rows[iChoice + iPosStart][4].ToString();
            }

            // ------------- KHI CHECK VÀO CUFF --------------
            else if (rbCuff.Checked)
            {
                int iPosStart = _dicData["cuff"];
                strID1 = _dtCuff.Rows[iChoice + iPosStart][1].ToString();
                strID2 = _dtCuff.Rows[iChoice + iPosStart][2].ToString();
                strID3 = _dtCuff.Rows[iChoice + iPosStart][3].ToString();
                strID4 = _dtCuff.Rows[iChoice + iPosStart][11].ToString();  // cột level

                strImage = _dtCuff.Rows[iChoice + iPosStart][4].ToString();
            }

            // ------------- KHI CHECK VÀO PENDANT --------------
            else if (rbPendant.Checked)
            {
                int iPosStart = _dicData["pendant"];
                strID1 = _dtPendant.Rows[iChoice + iPosStart][1].ToString();
                strID2 = _dtPendant.Rows[iChoice + iPosStart][2].ToString();
                strID3 = _dtPendant.Rows[iChoice + iPosStart][3].ToString();
                strID4 = _dtPendant.Rows[iChoice + iPosStart][11].ToString();  // cột level

                strImage = _dtPendant.Rows[iChoice + iPosStart][4].ToString();
            }

            // ------------- KHI CHECK VÀO HORSE --------------
            else if (rbHorse.Checked)
            {
                int iPosStart = _dicData["horse"];
                strID1 = _dtHorse.Rows[iChoice + iPosStart][1].ToString();
                strID2 = _dtHorse.Rows[iChoice + iPosStart][2].ToString();
                strID3 = _dtHorse.Rows[iChoice + iPosStart][3].ToString();
                strID4 = _dtHorse.Rows[iChoice + iPosStart][11].ToString();  // cột level

                strImage = _dtHorse.Rows[iChoice + iPosStart][4].ToString();
            }

            // ------------- KHI CHECK VÀO CUSTOM --------------
            else if (rbCustom.Checked)
            {
                txtItemID.Text = _lstCustom[iChoice].Prop.ToString();
            }            

            if (!rbCustom.Checked)  // rbCustom  == false, chỉ có checked tại radio button custom
                txtItemID.Text = strID1 + "," + strID2 + "," + strID3 + "," + strID4 + "," + strID5 + "," + strID6;
            
            txtItemName.Text = cmbItemName.Text.ToString();
            txtImage.Text = strImage;
            ShowItemID(strID1, strID2, strID3, strID4, strID5, strID6);

            // hiển thị hình ảnh
            DisplayImage(txtItemID1.Text, txtItemID2.Text, txtItemID3.Text, txtItemID4.Text, txtItemID5.Text, txtItemID6.Text);

            if (rbCustom.Checked == true)   // nếu rbCustom checked thì 
                return;

            ShowLogItemID();    // skip item in here           
            
        }

        #endregion

        #region "ComboBox ItemID Use Invoke"

        //private void AddRingToCB()
        //{
        //    ResetSettings();
        //    int iPosStart = _dicData["ring"];
        //    int iLength = _dtRing.Rows.Count - iPosStart;
        //    for (int i = iPosStart; i < _dtRing.Rows.Count; i++)
        //    {
        //        RunProcessBar(iLength, i, _dtRing.Rows[i][0].ToString());
        //        cbItemName.Invoke((MethodInvoker)delegate
        //        {
        //            cbItemName.Items.Add(_dtRing.Rows[i][0].ToString());
        //        });                
        //    }
        //    RestoreCB();
        //}

        private void AddCustomeToCB()
        {
            ResetSettings();

            for (int i = 0; i < _lstCustom.Count; i++)
            {
                RunProcessBar(_lstCustom.Count, i + 1, _lstCustom[i].Name);

                cmbItemName.Invoke((MethodInvoker)delegate   // dùng delegate và Thread để load dữ liệu nhanh hơn, không gom chung lại chạy rất chậm
                {                    
                    cmbItemName.Items.Add(_lstCustom[i].Name);
                });
            }

            RestoreCB();
        }



        private void AddItemToCB(DataTable dt, string sheetName)
        {
            ResetSettings();

            int iPosStart = _dicData[sheetName];
            int iLength = dt.Rows.Count - iPosStart;   // tổng chiều dài

            for (int i = iPosStart; i < dt.Rows.Count; i++)
            {
                RunProcessBar(iLength, i, dt.Rows[i][0].ToString());

                cmbItemName.Invoke((MethodInvoker)delegate   // dùng delegate và Thread để load dữ liệu nhanh hơn, không gom chung lại chạy rất chậm
                {
                    
                    cmbItemName.Items.Add(dt.Rows[i][0].ToString());
                });
            }

            RestoreCB();
        }

        //private void AddMaterialToCB()
        //{
        //    ResetSettings();
            
        //    int iPosStart = _dicData["material"];            
        //    int iLength = _dtMaterial.Rows.Count - iPosStart;   // tổng chiều dài

        //    for (int i = iPosStart; i < _dtMaterial.Rows.Count; i++)
        //    {
        //        RunProcessBar(iLength, i, _dtMaterial.Rows[i][0].ToString());

        //        cbItemName.Invoke((MethodInvoker)delegate   // dùng delegate và Thread để load dữ liệu nhanh hơn, không gom chung lại chạy rất chậm
        //        {
        //            cbItemName.Items.Add(_dtMaterial.Rows[i][0].ToString());
        //        });                
        //    }

        //    RestoreCB();
        //}

        //private void AddMagicScriptToCB()
        //{
        //    ResetSettings();

        //    int iPosStart = _dicData["magicscript"];
        //    int iLength = _dtMagicScript.Rows.Count - iPosStart;

        //    for (int i = iPosStart; i < _dtMagicScript.Rows.Count; i++)
        //    {
        //        RunProcessBar(iLength, i, _dtMagicScript.Rows[i][0].ToString());
        //        cbItemName.Invoke((MethodInvoker)delegate
        //        {
        //            cbItemName.Items.Add(_dtMagicScript.Rows[i][0].ToString());
        //        });                
        //    }

        //    RestoreCB();
        //}

        //private void AddBeltToCB()
        //{
        //    ResetSettings();

        //    int iPosStart = _dicData["belt"];
        //    int iLength = _dtBelt.Rows.Count - iPosStart; // tổng độ dài chạy progressbar

        //    for (int i = iPosStart; i < _dtBelt.Rows.Count; i++)
        //    {
        //        RunProcessBar(iLength, i, _dtBelt.Rows[i][0].ToString()); // chạy thanh progress và hiển thị phần tử thứ i trên statusbar
        //        cbItemName.Invoke((MethodInvoker)delegate
        //        {
        //            cbItemName.Items.Add(_dtBelt.Rows[i][0].ToString());
        //        });

        //    }

        //    RestoreCB();
        //}

        //private void AddBootToCB()
        //{
        //    ResetSettings();

        //    int iPosStart = _dicData["boot"];
        //    int iLength = _dtBoot.Rows.Count - iPosStart; // tổng độ dài chạy progressbar

        //    for (int i = iPosStart; i < _dtBoot.Rows.Count; i++)
        //    {
        //        RunProcessBar(iLength, i, _dtBoot.Rows[i][0].ToString()); // chạy thanh progress và hiển thị phần tử thứ i trên statusbar
        //        cbItemName.Invoke((MethodInvoker)delegate
        //        {
        //            cbItemName.Items.Add(_dtBoot.Rows[i][0].ToString());
        //        });

        //    }
        //    RestoreCB();
        //}

        //private void AddIbItemToCB()
        //{
        //    ResetSettings();

        //    int iPosStart = _dicData["ibitem"];
        //    int iLength = _dtibitem.Rows.Count - iPosStart; // tổng độ dài chạy progressbar

        //    for (int i = iPosStart; i < _dtibitem.Rows.Count; i++)
        //    {
                    
        //        RunProcessBar(iLength, i, _dtibitem.Rows[i][0].ToString()); // chạy thanh progress và hiển thị phần tử thứ i trên statusbar
        //        cbItemName.Invoke((MethodInvoker)delegate
        //        {
        //            cbItemName.Items.Add(_dtibitem.Rows[i][0].ToString());
        //        });
                
        //    }
        //    RestoreCB();
        //}
            

        //private void AddAmuletToCB()
        //{
        //    ResetSettings();

        //    int iPosStart = _dicData["amulet"];
        //    int iLength = _dtAmulet.Rows.Count - iPosStart;

        //    for (int i = iPosStart; i < _dtAmulet.Rows.Count; i++)
        //    {
                    
        //        RunProcessBar(iLength, i, _dtAmulet.Rows[i][0].ToString());
        //        cbItemName.Invoke((MethodInvoker)delegate
        //        {
        //            cbItemName.Items.Add(_dtAmulet.Rows[i][0].ToString());
        //        });
        //    }
        //    RestoreCB();
        //}

        /// <summary>
        /// Thêm item (material, magicscript, ibitem) vào comboBox
        /// </summary>
        private void AddAllToCB()
        {
            ResetSettings();

            //int iLengthM = _arrMaterial.Length - 1;
            //int iLengthMS = _arrMagicscript.Length - 1;
            //int iLengthIB = _arrIbitem.Length - 1;

            int iStartM = _dicData["material"];
            int iStartMS = _dicData["magicscript"];
            int iStartIB = _dicData["ibitem"];
            
            // Chiều dài thực sự để chạy thanh progress bar
            int iLengthM = _dtMaterial.Rows.Count - iStartM;
            int iLengthMS = _dtMagicScript.Rows.Count - iStartMS;
            int iLengthIB = _dtibitem.Rows.Count - iStartIB;

            int iLength = iLengthM + iLengthMS + iLengthIB;

            for (int i = iStartM; i < _dtMaterial.Rows.Count; i++)
            {
                RunProcessBar(iLength, i, _dtMaterial.Rows[i][0].ToString());
                cmbItemName.Invoke((MethodInvoker)delegate   // dùng delegate và Thread để load dữ liệu nhanh hơn
                {                    
                    cmbItemName.Items.Add(_dtMaterial.Rows[i][0].ToString());
                });
            }

            for (int j = iStartMS; j < _dtMagicScript.Rows.Count; j++)
            {
                RunProcessBar(iLength, j + iLengthM, _dtMagicScript.Rows[j][0].ToString());
                cmbItemName.Invoke((MethodInvoker)delegate   // dùng delegate và Thread để load dữ liệu nhanh hơn
                {
                    cmbItemName.Items.Add(_dtMagicScript.Rows[j][0].ToString());
                });                
            }

            for (int k = iStartIB; k < _dtibitem.Rows.Count; k++)
            {
                RunProcessBar(iLength, k + iLengthM + iLengthMS, _dtibitem.Rows[k][0].ToString());
                cmbItemName.Invoke((MethodInvoker)delegate   // dùng delegate và Thread để load dữ liệu nhanh hơn
                {
                    cmbItemName.Items.Add(_dtibitem.Rows[k][0].ToString());
                });                
            }

            RestoreCB();
        }

        #endregion

        #region "Textbox ItemID LogItem"
        private void ShowItemID(string strID1, string strID2, string strID3, string strID4, string strID5, string strID6)
        {
            txtItemID1.Text = strID1;
            txtItemID2.Text = strID2;
            txtItemID3.Text = strID3;
            txtItemID4.Text = strID4;
            txtItemID5.Text = strID5;
            txtItemID6.Text = strID6;
        }


        private void ShowLogItemID()
        {
            clsItem item = new clsItem();
            item = GetInfoItemByName(txtItemName.Text);

            if (item == null)
                return;
            
            txtItemIDLog.Text = item.szItemID;
        }

        //private void ShowLogItemName()
        //{
        //    txtItemIDLog.Text = "";

        //    // tìm ID theo item name
        //    for (int i = 0; i < _dtLogFormat.Rows.Count; i++)
        //    {
        //        string szItemLogName = _dtLogFormat.Rows[i][1].ToString().ToLower();
        //        if (txtItemName.Text.ToLower().Equals(szItemLogName))
        //            txtItemIDLog.Text = _dtLogFormat.Rows[i][0].ToString();
        //    }
        //}
        
        private void DisplayImage(string strID1, string strID2, string strID3, string strID4, string strID5, string strID6)
        {
            if (_szFileImage == null)   // không có hình thì không xử lý
                return;

            string strPath = "";

            if (strID1 == "3")  // material
                strPath = _szFileImage + "material\\" + strID2 + ".png";

            else if (strID1 == "6" && strID2 == "1")
                strPath = _szFileImage + "magicscript\\" + strID3 + ".png";

            else if (strID1 == "8")
                strPath = _szFileImage + "ibitem\\" + strID2 + ".png";

            else if (strID1 == "0" && strID2 == "2")
                strPath = _szFileImage + "armor\\" + strID3 + ".png";

            else if (strID1 == "0" && strID2 == "3")
                strPath = _szFileImage + "ring\\" + strID3 + ".png";

            else if (strID1 == "0" && strID2 == "4")
                strPath = _szFileImage + "amulet\\" + strID3 + ".png";

            else if (strID1 == "0" && strID2 == "5")
                strPath = _szFileImage + "boot\\" + strID3 + ".png";

            else if (strID1 == "0" && strID2 == "6")
                strPath = _szFileImage + "belt\\" + strID3 + ".png";

            else if (strID1 == "0" && strID2 == "7")
                strPath = _szFileImage + "helm\\" + strID3 + ".png";

            else if (strID1 == "0" && strID2 == "8")
                strPath = _szFileImage + "cuff\\" + strID3 + ".png";

            else if (strID1 == "0" && strID2 == "9")
                strPath = _szFileImage + "pendant\\" + strID3 + ".png";

            else if (strID1 == "0" && strID2 == "10")
                strPath = _szFileImage + "horse\\" + strID3 + ".png";

            imgItem.ImageLocation = strPath;  // hiển thị hình ảnh, dùng imageLocation, không dùng imgItem.Load -> sẽ bị lỗi
        }

        #endregion

        #region "Khi thay đổi nội dung vào các textbox"
        private void txtItemID1_TextChanged(object sender, EventArgs e)
        {


        }

        private void txtItemID2_TextChanged(object sender, EventArgs e)
        {
            //if (txtItemID1.Text == "3")
            //{
            //    if (txtItemID2.Text == "")
            //        return;

            //    if (Int32.Parse(txtItemID2.Text) > _dtMaterial.Rows.Count)
            //        return;

            //    for (int i = 0; i < _dtMaterial.Rows.Count; i++)
            //        if (_dtMaterial.Rows[i][2].ToString() == txtItemID2.Text)
            //        {
            //            cbItemName.SelectedIndex = i;
            //            break;
            //        }
            //}
        }
        #endregion
        
        #region "Radio Button - Khi Chọn các radio Button"
        private void rbBoot_CheckedChanged(object sender, EventArgs e)
        {
            if (!rbBoot.Checked)
            {
                if (_thread != null)
                {
                    if (_thread.IsAlive == true)
                        _thread.Abort();
                }

                return;
            }

            _thread = new Thread(() => AddItemToCB(_dtBoot, "boot"));
            _thread.Start();
        }

        private void rbBelt_CheckedChanged(object sender, EventArgs e)
        {
            if (!rbBelt.Checked)
            {
                if (_thread != null)
                {
                    if (_thread.IsAlive == true)
                        _thread.Abort();
                }

                return;
            }
                        
            _thread = new Thread(() => AddItemToCB(_dtBelt, "belt"));
            _thread.Start();
        }

        private void rbIBItem_CheckedChanged(object sender, EventArgs e)
        {
            if (!rbIBItem.Checked)
            {
                if (_thread != null)
                {
                    if (_thread.IsAlive == true)
                        _thread.Abort();
                }

                return;
            }

            
            _thread = new Thread(() => AddItemToCB(_dtibitem, "ibitem"));
            _thread.Start();
        }

        private void rbArmor_CheckedChanged(object sender, EventArgs e)
        {
            if (!rbArmor.Checked)
            {
                if (_thread != null)
                {
                    if (_thread.IsAlive == true)
                        _thread.Abort();
                }

                return;
            }

            _thread = new Thread(() => AddItemToCB(_dtArmor, "armor"));
            _thread.Start();
        }

        private void rbRing_CheckedChanged(object sender, EventArgs e)
        {
            if (!rbRing.Checked)
            {
                if (_thread != null)
                {
                    if (_thread.IsAlive == true)
                        _thread.Abort();
                }

                return;
            }

            _thread = new Thread(() => AddItemToCB(_dtRing, "ring"));
            _thread.Start();

        }

        private void rbAmulet_CheckedChanged(object sender, EventArgs e)
        {
            if (!rbAmulet.Checked)
            {
                if (_thread != null)
                {
                    if (_thread.IsAlive == true)
                        _thread.Abort();
                }
                return;
            }

            _thread = new Thread(() => AddItemToCB(_dtAmulet, "amulet"));
            _thread.Start();            
        }

        private void rbShowAll_CheckedChanged(object sender, EventArgs e)
        {
            if (!rbShowAll.Checked)
            {
                if (_thread != null)
                {
                    if (_thread.IsAlive == true)
                        _thread.Abort();
                }
                return;
            }
                        
            _thread = new Thread(new ThreadStart(AddAllToCB));  // không có tham số
            _thread.Start();
        }

        private void rbMaterial_CheckedChanged(object sender, EventArgs e)
        {
            if (!rbMaterial.Checked)
            {
                if (_thread != null)
                {
                    if (_thread.IsAlive == true)
                        _thread.Abort();
                }
                return;
            }

            _thread = new Thread(() => AddItemToCB(_dtMaterial, "material"));
            _thread.Start();
        }

        private void rbHelm_CheckedChanged(object sender, EventArgs e)
        {
            if (!rbHelm.Checked)
            {
                if (_thread != null)
                {
                    if (_thread.IsAlive == true)
                        _thread.Abort();
                }

                return;
            }

            _thread = new Thread(() => AddItemToCB(_dtHelm, "helm"));
            _thread.Start();
        }

        private void rbCuff_CheckedChanged(object sender, EventArgs e)
        {
            if (!rbCuff.Checked)
            {
                if (_thread != null)
                {
                    if (_thread.IsAlive == true)
                        _thread.Abort();
                }

                return;
            }

            _thread = new Thread(() => AddItemToCB(_dtCuff, "cuff"));
            _thread.Start();
        }

        private void rbPendant_CheckedChanged(object sender, EventArgs e)
        {
            if (!rbPendant.Checked)
            {
                if (_thread != null)
                {
                    if (_thread.IsAlive == true)
                        _thread.Abort();
                }
                return;
            }

            _thread = new Thread(() => AddItemToCB(_dtPendant, "pendant"));
            _thread.Start();
        }

        private void rbCustom_CheckedChanged(object sender, EventArgs e)
        {
            // load from file!
            _szCustom = Application.StartupPath + "\\custom.lua";

            if (!File.Exists(_szCustom))    // không tìm thấy file
            {
                MessageBox.Show("Không tìm thấy file custom template!", "Fs Tools", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }
            
            // Read file custom.lua => get List<clsListTable> with follow below row
            // {szName = "Thủy tinh",	tbProp = {3,0,0,0,0,0}

            LoadItemFromTemplateFile();
            AddCustomeToCB();
        }

        /// <summary>
        /// Đọc từ file Custom.lua => lấy ra Table chứa thông tin tên vật phẩm, tên item!
        /// {szName = "Thủy tinh",	tbProp = {3,0,0,0,0,0}
        /// </summary>
        /// <returns>Table</returns>

        private void LoadItemFromTemplateFile()
        {
            _lstCustom = new List<clsListTable>();
            List<string> lst = clsMain.ReadTextFile(_szCustom);
            clsListTable table = new clsListTable();
            string[] szTemp;

            int iPosStart = -1;
            int iPosEnd = -1;
            string tmpBegin, tmpEnd;

            for (int i = 1; i < lst.Count; i++)
            {
                if (lst[i] == null || lst[i] == "\t" || lst[i] == string.Empty || lst[i] == "\n")
                    continue;

                szTemp = lst[i].Split('\t');

                table = new clsListTable();             // khởi tạo lại
                for (int k = 0; k < szTemp.Length; k++) // 5 đoạn phân khúc
                {
                    switch (k)
                    {
                        case 0:         // {szName = "Túi Quà Tài Lộc",

                            tmpBegin = "szName = \"";
                            tmpEnd = "\"";

                            iPosStart = szTemp[k].LastIndexOf(tmpBegin);
                            iPosEnd = szTemp[k].LastIndexOf(tmpEnd);

                            if (iPosStart == -1 || iPosEnd == -1)    // không tìm thấy
                                break;

                            table.Name = szTemp[k].Substring(iPosStart + tmpBegin.Length, iPosEnd - iPosStart - tmpBegin.Length);
                            break;

                        case 1:         // tbProp = {6,1,5913,0,0,0},

                            tmpBegin = "tbProp = {";
                            tmpEnd = "}";

                            iPosStart = szTemp[k].LastIndexOf(tmpBegin);
                            iPosEnd = szTemp[k].LastIndexOf(tmpEnd);

                            if (iPosStart == -1 || iPosEnd == -1)
                                break;

                            table.Prop = szTemp[k].Substring(iPosStart + tmpBegin.Length, iPosEnd - iPosStart - tmpBegin.Length);
                            break;
                    }
                }
                _lstCustom.Add(table);
            }
            
        }

        private void rbHorse_CheckedChanged(object sender, EventArgs e)
        {
            if (!rbHorse.Checked)
            {
                if (_thread != null)
                {
                    if (_thread.IsAlive == true)
                        _thread.Abort();
                }

                return;
            }

            _thread = new Thread(() => AddItemToCB(_dtHorse, "horse"));
            _thread.Start();
        }     

        private void rbMagicScript_CheckedChanged(object sender, EventArgs e)
        {
            if (!rbMagicScript.Checked)
            {
                if (_thread != null)
                {
                    if (_thread.IsAlive == true)
                        _thread.Abort();
                }

                return;
            }

            // _thread = new Thread(new ThreadStart(AddMagicScriptToCB));   // Thread ko có tham số
            _thread = new Thread(() => AddItemToCB(_dtMagicScript, "magicscript")); // Thread có tham số
            _thread.Start();
        }

        #endregion

        #region "Progress bar"
        /* Start, Load dữ liệu vào comboBox */
        /* Chạy progressBar */
        private void RunProcessBar(int max, int i, string szMessage)
        {
            cmbItemName.Invoke((MethodInvoker)delegate   // dùng delegate và Thread để load dữ liệu nhanh hơn
            {
                if (i > max)  // trường hợp i vượt quá maximun thì sẽ báo lỗi 
                    i = max;
                toolStripProgressBar.Value = (int)(i * 100 / max);
                toolStripStatusLabelLoadingText.Text = szMessage;
                toolStripStatusLabelProcessbar.Text = toolStripProgressBar.Value.ToString() + "%";

                if (toolStripProgressBar.Value >= 100)
                    toolStripStatusLabelLoadingText.Text = "Total: " + max.ToString() + " Items!";
            });
        }
        #endregion

        #region "Button Insert Output Close Delete"

        //private void JSONDemo()
        //{
        //    WebClient webClient = new WebClient();
        //    dynamic result = JsonValue.Parse(webClient.DownloadString("https://api.foursquare.com/v2/users/self?oauth_token=XXXXXXX")); 
        //}


        private void btnPath_Click(object sender, EventArgs e)
        {
            List<int> lst = clsMain.SplitIDItem("6, 1, 256");
            for (int i = 0; i < lst.Count; i++)
                MessageBox.Show(lst[i].ToString());
            
            //frmTree frm = new frmTree();
            //frm.ShowDialog();
            
        }

        private void btnOutput_Click(object sender, EventArgs e)
        {
            double nRate = CheckRate();
            if (nRate != Convert.ToDouble(100))
            {
                DialogResult dialog = MessageBox.Show("Total Rate = " + nRate.ToString() + "%; NOT MATCH 100%;\nClick [Yes] to gen file, Click [No] to recheck your rate", "Warning!!!", MessageBoxButtons.YesNo, MessageBoxIcon.Warning);
                if (dialog == DialogResult.No)
                    return;     
            }
            
            List<string> lstContent = new List<string>();

            lstContent.Add("tbAward = {");
            string strColor = "yellow";
            
            for (int i = 0; i < lvTable.Items.Count; i++)
            {
                string[] strTemp = new string[6];
                
                if (_lstColor[i] == 0)
                    strColor = "yellow";
                else
                    strColor = "green";

                strTemp[0] = "szName = \"<c=" + strColor + ">" + lvTable.Items[i].SubItems[0].Text.ToString() + "<c>\",\t";
                strTemp[1] = "tbProp = {" + lvTable.Items[i].SubItems[1].Text.ToString() + "},\t";

                if (lvTable.Items[i].SubItems[2].Text != "")
                    strTemp[2] = "nRate = " + lvTable.Items[i].SubItems[2].Text.ToString() + ",\t";

                strTemp[3] = "nType = " + lvTable.Items[i].SubItems[3].Text.ToString() + ",\t";
                strTemp[4] = "nCount = " + lvTable.Items[i].SubItems[4].Text.ToString();

                if (lvTable.Items[i].SubItems[5].Text != "")
                    strTemp[5] = ",\tszItemID = \"" + lvTable.Items[i].SubItems[5].Text.ToString() + "\"";

                lstContent.Add("\t[" + (i+1).ToString() + "] = {" +  strTemp[0] + strTemp[1] + strTemp[2] + strTemp[3] + strTemp[4] + strTemp[5] + "},");
            }

            lstContent.Add("}");

            //bool bFlag = CommonLib.clsMain.WriteFile(_szOutput, lstContent, 1);
            bool bFlag = CommonLib.clsMain.WriteFile(_szOutput, lstContent, 1, Encoding.Unicode);
            if (bFlag == true)
            {
                DialogResult dialog = MessageBox.Show("Succeed Write File, Do you want to open file Items", "Info", MessageBoxButtons.YesNo, MessageBoxIcon.Information);

                if (dialog == DialogResult.Yes)
                    Process.Start(_szOutput);
            }
            else
                MessageBox.Show("Error", "Error", MessageBoxButtons.OK, MessageBoxIcon.Warning);
        }

        private void btnInsert_Click(object sender, EventArgs e)
        {
            ReadData_Sheet_Amulet();

            string[] strTemp = new string[6];
            strTemp[0] = txtItemName.Text;
            strTemp[1] = txtItemID.Text;    // output sẽ xử lý
            strTemp[2] = txtRate.Text;
            strTemp[3] = txtType.Text;
            strTemp[4] = txtCount.Text;
            strTemp[5] = txtItemIDLog.Text;

            ListViewItem lvItem = null;

            if (cmbColor.SelectedIndex == 0)
            {
                lvItem = new ListViewItem(strTemp, (int)COLOR.YELLOW);
                _lstColor.Add((int)COLOR.YELLOW);
            }

            else if (cmbColor.SelectedIndex == 1)
            {
                lvItem = new ListViewItem(strTemp, (int)COLOR.GREEN);
                _lstColor.Add((int)COLOR.GREEN);
            }

            lvTable.Items.Add(lvItem);

            // tự động lưu trữ khi nhập vào listTable
            autoSave();
        }
        
        private void btnClose_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void btnDelete_Click(object sender, EventArgs e)
        {
            DeleteRows();
        }

        private void btnUpdate_Click(object sender, EventArgs e)
        {
            // update vị trí chọn lựa!
            ListViewItem item = lvTable.Items[_iCurrentIndex];
            item.SubItems[0].Text = txtItemName.Text;
            item.SubItems[1].Text = txtItemID.Text;
            item.SubItems[2].Text = txtRate.Text;
            item.SubItems[3].Text = txtType.Text;
            item.SubItems[4].Text = txtCount.Text;
            item.SubItems[5].Text = txtItemIDLog.Text;

            toolStripStatusLabel.Text = "Row = " + (_iCurrentIndex + 1).ToString() + " Update Succeed";
        }

        private void btnAddCustom_Click(object sender, EventArgs e)
        {
            List<string> lst = new List<string>();
            lst.Add("{szName = \"" + txtItemName.Text + "\",\ttbProp = {" +  txtItemID.Text + "}");

            bool bFlag=  clsMain.WriteFile(_szCustom, lst, 2);

            if (bFlag == true)
                MessageBox.Show("Update template file succeed!", "FS Tool", MessageBoxButtons.OK, MessageBoxIcon.Information);
        }  

        #endregion
        
        #region "Event Main Form"
        private void Form1_Load(object sender, EventArgs e)
        {
            try
            {
                // show hint in Item ID Log TextBox for first time login
                txtItemIDLog.ForeColor = Color.Gray;
                txtItemIDLog.Text = "Get ItemID Log from GIM Tool!";                
                txtItemIDLog.Select(txtItemIDLog.TextLength, 0);


                List<string> lst = CommonLib.clsMain.ReadTextFile(Application.StartupPath + "\\settings.txt");
                if (lst == null)
                {
                    MessageBox.Show("Cannot find settings.txt file!", "Info", MessageBoxButtons.OK, MessageBoxIcon.Question);
                    return;
                }

                
                // có giá trị mới lưu vào
                if (lst[0] != null)
                    _szFileItems = lst[0];

                //if (lst[1] != null)
                    //_szFileLogFormat = lst[1];

                if (lst[1] != null)
                {
                    _szFileImage = lst[1];
                    imgItem.ImageLocation = _szFileImage + "magicscript\\5680.png"; // default
                }


                // Load items to ListView
                LoadlItemToListView();
                               
                // khởi tạo các comboBox, ItemName, Progress bar
                InitListView();
                InitData();
                                
                InitItemName();
                InitColorComboBox();
                InitProgressBar();

                // Đọc dữ liệu các sheet material, magicsript, ... vào comboBox                
                ReadDataUseAsync();                   

                // gắn Image list vào Small Image List
                lvTable.SmallImageList = imglst;

                // status FS Tool!
                toolStripStatusLabel.Text = "Welcome to FS Tool";
            }
            catch(Exception ex)
            {                
                MessageBox.Show(ex.Message.ToString());
            }
        }

        // phải remove thread khi thoát vì khi người dùng đang load items mà không remove sẽ xảy ra lỗi!
        private void FrmGenItems_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (_thread != null)
            {
                if (_thread.IsAlive == true)
                    _thread.Abort();
            }
        }
        #endregion

        #region "Read Data from file"
     
        private static void ReadData_Sheet_Item(DataTable dt, string sheetName)
        {
            dt = CommonLib.clsMain.ReadExcelFile(_szFileItems, sheetName);

            int iStart = _dicData[sheetName];
            for (int i = iStart; i < dt.Rows.Count; i++)
            {
                dt.Rows[i][0] = clsConverter.TCVN3ToUnicode(dt.Rows[i][0].ToString());
                dt.Rows[i][0] = ReplaceString(dt.Rows[i][0].ToString());
            }
        }

        /// <summary>
        /// Đọc từ file excel, đổi kiểu chữ TCVN3 sang Unicode rồi lưu trữ vào DataTable
        /// Hàm này ko return ra DataTable, do truy xuất trong file excel, đọc cùng lúc bị lỗi! 
        ///     + AsyncGetString getA = new AsyncGetString(ReadData_Sheet_Armor);
        /// </summary>
     
        private static void ReadData_Sheet_Armor()
        {
            _dtArmor = CommonLib.clsMain.ReadExcelFile(_szFileItems, "armor");

            int iStart = _dicData["armor"];  
            for (int i = iStart; i < _dtArmor.Rows.Count; i++)
            {
                _dtArmor.Rows[i][0] = clsConverter.TCVN3ToUnicode(_dtArmor.Rows[i][0].ToString());
                _dtArmor.Rows[i][0] = ReplaceString(_dtArmor.Rows[i][0].ToString());
            }
        }

        private static void ReadData_Sheet_IBItem()
        {            
            _dtibitem = CommonLib.clsMain.ReadExcelFile(_szFileItems, "ibitem");

            int iStart = _dicData["ibitem"];
            for (int i = iStart; i < _dtibitem.Rows.Count; i++)
            {
                _dtibitem.Rows[i][0] = clsConverter.TCVN3ToUnicode(_dtibitem.Rows[i][0].ToString());
                _dtibitem.Rows[i][0] = ReplaceString(_dtibitem.Rows[i][0].ToString());
            }
        }

        private static void ReadData_Sheet_Magicscript()
        {         
            _dtMagicScript = CommonLib.clsMain.ReadExcelFile(_szFileItems, "magicscript");            

            int iStart = _dicData["magicscript"];
            for (int i = iStart; i < _dtMagicScript.Rows.Count; i++)
            {
                _dtMagicScript.Rows[i][0] = clsConverter.TCVN3ToUnicode(_dtMagicScript.Rows[i][0].ToString());
                _dtMagicScript.Rows[i][0] = ReplaceString(_dtMagicScript.Rows[i][0].ToString());
            }
        }

        private static void ReadData_Sheet_Material()
        {            
            _dtMaterial = CommonLib.clsMain.ReadExcelFile(_szFileItems, "material");            

            int iStart = _dicData["material"];
            for (int i = iStart; i < _dtMaterial.Rows.Count; i++)
            {
                _dtMaterial.Rows[i][0] = clsConverter.TCVN3ToUnicode(_dtMaterial.Rows[i][0].ToString());
                _dtMaterial.Rows[i][0] = ReplaceString(_dtMaterial.Rows[i][0].ToString());
            }
        }

        private static void ReadData_Sheet_Ring()
        {
            _dtRing = CommonLib.clsMain.ReadExcelFile(_szFileItems, "ring");            

            int iStart = _dicData["ring"];            
            for (int i = iStart; i < _dtRing.Rows.Count; i++)
            {
                _dtRing.Rows[i][0] = clsConverter.TCVN3ToUnicode(_dtRing.Rows[i][0].ToString());
                _dtRing.Rows[i][0] = ReplaceString(_dtRing.Rows[i][0].ToString());
            }
        }

        private static void ReadData_Sheet_Boot()
        {
            _dtBoot = CommonLib.clsMain.ReadExcelFile(_szFileItems, "boot");

            int iStart = _dicData["boot"];
            for (int i = iStart; i < _dtBoot.Rows.Count; i++)
            {
                _dtBoot.Rows[i][0] = clsConverter.TCVN3ToUnicode(_dtBoot.Rows[i][0].ToString());    // thay đổi trên dataTable
                _dtBoot.Rows[i][0] = ReplaceString(_dtBoot.Rows[i][0].ToString());
            }
        }

        private static void ReadData_Sheet_Helm()
        {
            _dtHelm = CommonLib.clsMain.ReadExcelFile(_szFileItems, "helm");

            int iStart = _dicData["helm"];
            for (int i = iStart; i < _dtHelm.Rows.Count; i++)
            {
                _dtHelm.Rows[i][0] = clsConverter.TCVN3ToUnicode(_dtHelm.Rows[i][0].ToString());    // thay đổi trên dataTable
                _dtHelm.Rows[i][0] = ReplaceString(_dtHelm.Rows[i][0].ToString());
            }
        }

        private static void ReadData_Sheet_Belt()
        {
            _dtBelt = CommonLib.clsMain.ReadExcelFile(_szFileItems, "belt");

            int iStart = _dicData["belt"];
            for (int i = iStart; i < _dtBelt.Rows.Count; i++)
            {
                _dtBelt.Rows[i][0] = clsConverter.TCVN3ToUnicode(_dtBelt.Rows[i][0].ToString());    // thay đổi trên dataTable
                _dtBelt.Rows[i][0] = ReplaceString(_dtBelt.Rows[i][0].ToString());
            }
        }

        private static void ReadData_Sheet_Amulet()
        {
            _dtAmulet = CommonLib.clsMain.ReadExcelFile(_szFileItems, "amulet");            

            int iStart = _dicData["amulet"];
            for (int i = iStart; i < _dtAmulet.Rows.Count; i++)
            {
                _dtAmulet.Rows[i][0] = clsConverter.TCVN3ToUnicode(_dtAmulet.Rows[i][0].ToString());    // thay đổi trên dataTable
                _dtAmulet.Rows[i][0] = ReplaceString(_dtAmulet.Rows[i][0].ToString());
            }
        }

        private static void ReadData_Sheet_Cuff()
        {
            _dtCuff = CommonLib.clsMain.ReadExcelFile(_szFileItems, "cuff");

            int iStart = _dicData["cuff"];
            for (int i = iStart; i < _dtCuff.Rows.Count; i++)
            {
                _dtCuff.Rows[i][0] = clsConverter.TCVN3ToUnicode(_dtCuff.Rows[i][0].ToString());    // thay đổi trên dataTable
                _dtCuff.Rows[i][0] = ReplaceString(_dtCuff.Rows[i][0].ToString());
            }
        }

        private static void ReadData_Sheet_Pendant()
        {
            _dtPendant = CommonLib.clsMain.ReadExcelFile(_szFileItems, "pendant");

            int iStart = _dicData["pendant"];
            for (int i = iStart; i < _dtPendant.Rows.Count; i++)
            {
                _dtPendant.Rows[i][0] = clsConverter.TCVN3ToUnicode(_dtPendant.Rows[i][0].ToString());    // thay đổi trên dataTable
                _dtPendant.Rows[i][0] = ReplaceString(_dtPendant.Rows[i][0].ToString());
            }
        }

        private static void ReadData_Sheet_Horse()
        {
            _dtHorse = CommonLib.clsMain.ReadExcelFile(_szFileItems, "horse");

            int iStart = _dicData["horse"];
            for (int i = iStart; i < _dtHorse.Rows.Count; i++)
            {
                _dtHorse.Rows[i][0] = clsConverter.TCVN3ToUnicode(_dtHorse.Rows[i][0].ToString());    // thay đổi trên dataTable
                _dtHorse.Rows[i][0] = ReplaceString(_dtHorse.Rows[i][0].ToString());
            }
        }

        #endregion 
      
        private void txtRateMask_CheckedChanged(object sender, EventArgs e)
        {
        }
        
        private void lvTable_SelectedIndexChanged(object sender, EventArgs e)
        {
            // không cho nhấn Insert khi chọn
            btnInsert.Enabled = false;

            if (lvTable.SelectedItems.Count > 0)
            {
                ListViewItem item = lvTable.SelectedItems[0];

                // lấy current index
                _iCurrentIndex = lvTable.SelectedItems[0].Index;

                // show on TextBox
                txtItemName.Text = item.SubItems[0].Text;
                txtItemID.Text = item.SubItems[1].Text;
                txtRate.Text = item.SubItems[2].Text;
                txtType.Text = item.SubItems[3].Text;
                txtCount.Text = item.SubItems[4].Text;
                txtItemIDLog.Text = item.SubItems[5].Text;

                cmbItemName.Text = "";
                txtItemID1.Text = txtItemID2.Text = txtItemID3.Text = txtItemID4.Text = txtItemID5.Text = txtItemID6.Text = "";
            }

            toolStripStatusLabel.Text = "Row = " + (_iCurrentIndex+1).ToString();
        }

        private void txtItemIDLog_TextChanged(object sender, EventArgs e)
        {

        }

        private void btnEquipOptions_Click(object sender, EventArgs e)
        {
            frmOptions frm = new frmOptions();
            frm.ShowDialog();
        }  
    }
}
