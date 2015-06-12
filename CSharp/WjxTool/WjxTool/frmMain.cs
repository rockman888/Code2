using DevComponents.DotNetBar;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Text;
using System.Windows.Forms;

// làm tiếp tại đây ---------------------------
// Mã Lỗi dt-10068 - hết liscense
// Mã Lỗi dt-10048 - cố gắng chỉnh time cheat liscense

namespace WjxTool
{
    public partial class frmMain : DevComponents.DotNetBar.Office2007Form
    {
        private List<clsGameFunction> _lstGameFunctions;

        // receive value from another form
        public static string _szKey;            
        public static int _itype;       
        public static List<string> _lstparams;

        // log
        private static string _szFullLogPatch ;
        

        public frmMain()
        {
            InitializeComponent();
            _lstparams = new List<string>();
        }

        private void checkLiscence()
        {
            DateTime dt = DateTime.Now;
            if (dt.Year >= 2016 && dt.Month >= 3)
            {
                MessageBox.Show("Mã Lỗi dt-10068! Liên hệ với vilh@vng.com.vn để biết thêm thông tin", clsCommons.TITLE, MessageBoxButtons.OK, MessageBoxIcon.Error);
                clsCommons.createRegeditKey(_szFullLogPatch);
                this.Close();
                return;
            }

            if (clsCommons.checkRegeditKeyExists(_szFullLogPatch) == true)
            {
                MessageBox.Show("Mã Lỗi dt-10048! Liên hệ với vilh@vng.com.vn để biết thêm thông tin", clsCommons.TITLE, MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.Close();
                return;
            }

        }

        private void createLogFolder()
        {
            DateTime dt = DateTime.Now;           

            string szFileLog = dt.Year + "-" + dt.Month + "-" + dt.Day + ".bin";
            string szPath = Application.StartupPath + "\\Log";
            _szFullLogPatch = szPath + "\\" + szFileLog;
            
            if (!Directory.Exists(szPath))
                Directory.CreateDirectory(szPath);    // tạo mới folder nếu không tồn tại      
        }

        private void checkVersion()
        {
            clsFTPCommons cls = new clsFTPCommons("", "", "");
            createLogFolder();

            bool flag = cls.downloadAndCheckVerision("wjxversion.txt", _szFullLogPatch);
            if (flag == true)
            {
                MessageBox.Show("Mã Lỗi dt-50002! Liên hệ với vilh@vng.com.vn để biết thêm thông tin", clsCommons.TITLE, MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.Close();
            }
        }


        private void frmMain_Load(object sender, EventArgs e)
        {
            checkLiscence();
                                  
            // load tab 1

            string path = Application.StartupPath + "\\product.xml";
            _lstGameFunctions = clsCommons.readXMLFiles(path);

            ePMain.TitleText = "Total:" + _lstGameFunctions.Count.ToString();

            for (int i = 0; i < _lstGameFunctions.Count; i++)
            {
                string szName = _lstGameFunctions[i].Key;
                ButtonItem btn = new ButtonItem("btn" + szName, szName);
                btn.Tooltip = _lstGameFunctions[i].Name;                
                btn.Click += new System.EventHandler(this.btn_Click);

                if (_lstGameFunctions[i].Type.Equals("Condition"))
                    itemPConditons.Items.Add(btn);

                else if (_lstGameFunctions[i].Type.Equals("Reward"))
                    itemPReward.Items.Add(btn);
            }           


            
        }

        private void btn_Click(object sender, EventArgs e)
        {
            checkLiscence();
            for (int i=0; i<_lstGameFunctions.Count; i++)
                if ( ((ButtonItem)sender).Name == "btn" + _lstGameFunctions[i].Key )
                {
                    frmPara frm = new frmPara(_lstGameFunctions[i]);
                    frm.ShowDialog();
                    break;
                }
        }

    
        
        // làm tiếp tại đây ---------------------------
        private void frmMain_Activated(object sender, EventArgs e)
        {
            // show activated form
            if (_szKey != null)
            {
                string temp = _szKey + "(";
                for (int i = 0; i < _lstparams.Count; i++)
                {
                    if (i >= 1)
                        temp += ", ";
                    temp += _lstparams[i];

                }

                temp += ")-" + _itype.ToString();
                                

                lbFunction.Items.Add(temp);

                // read
                showOnRichBox(_itype, _szKey, _lstparams);
                HighLight();
                

                // free resources
                _szKey = null;
                _lstparams = new List<string>();                
            }
        }

        private void showOnRichBox(int type, string szKey, List<string>lstParam)
        {
            //"<Reward FunctionKey=""AddItem"">
            //  <Params>
            //    <Param>18,1,25005,9</Param>
            //    <Param>50</Param>
            //    <Param>1</Param>
            //    <Param>0</Param>
            //    <Param>0</Param>
            //    <Param>0</Param>
            //  </Params>
            //</Reward>"

            string szTemp = "";
            if (type == clsCommons.REWARD)
                szTemp += "<Reward FunctionKey = \"" + szKey + "\">\n";

            else if (type == clsCommons.CONDITION)
                szTemp += "<Condition FunctionKey = \"" + szKey + "\">\n";

            szTemp += "\t<Params>\n";

            for (int i = 0; i < lstParam.Count; i++)
                szTemp += "\t\t<Param>" + lstParam[i] + "</Param>\n";

            szTemp += "\t</Params>\n";

            if (type == clsCommons.REWARD)
            {
                szTemp += "</Reward>\n";
                rtbReward.Text += szTemp;
            }
            else if (type == clsCommons.CONDITION)
            {
                szTemp += "</Condition>\n";
                rtbCondition.Text += szTemp;
            }
        }

        private void lbFunction_KeyDown(object sender, KeyEventArgs e)
        {
            int iCurr = lbFunction.SelectedIndex;
            if (iCurr >= 0)
            {
                DialogResult dr = MessageBox.Show("Có muốn xóa dòng " + lbFunction.Items[iCurr].ToString() + " không?", clsCommons.TITLE, MessageBoxButtons.YesNo, MessageBoxIcon.Question);

                if (dr == DialogResult.Yes)
                {
                    lbFunction.Items.RemoveAt(lbFunction.SelectedIndex);
                    reloadSourceCode();
                }
            }
        }

        private void reloadSourceCode()
        {
            rtbReward.Text = "";
            // reload lai source code
            // CheckKey (1,2,3)-1

            for (int k = 0; k < lbFunction.Items.Count; k++)
            {
                string str = lbFunction.Items[k].ToString();

                int index = str.LastIndexOf('(');
                string szKey = str.Substring(0, index);

                int length = str.Length;
                string szNum = str.Substring(index + 1, length-3 - (index + 1)); // (3: ")-1" )

                int type = Convert.ToInt32(str.Substring(length - 1));

                // list parameters 
                string[] arrParas = szNum.Split(',');
                List<string> lstParam = new List<string>();
                for (int i = 0; i < arrParas.Length; i++)
                    lstParam.Add(arrParas[i]);


                showOnRichBox(type, szKey, lstParam);              

            }
            HighLight();

           
        }

        public void HighLight()
        {
            HighlightText("FunctionKey", Color.Red);
            HighlightText("Param", Color.Blue);
            HighlightText("Params", Color.Black);
            HighlightText("Reward", Color.Blue);
            HighlightText("Condition", Color.Blue);
        }
        
        private void FindTextRichTextBox(RichTextBox rtb, string word, Color color)
        {
            int s_start = rtb.SelectionStart, startIndex = 0, index;
            

            while ((index = rtb.Text.IndexOf(word, startIndex)) != -1)
            {
                rtb.Select(index, word.Length);
                rtb.SelectionColor = color;

                startIndex = index + word.Length;
            }

            rtb.SelectionStart = s_start;
            rtb.SelectionLength = 0;
            rtb.SelectionColor = Color.Black;
        }

        public void HighlightText(string word, Color color)
        {
            FindTextRichTextBox(rtbReward, word, color);
            FindTextRichTextBox(rtbCondition, word, color);
            FindTextRichTextBox(rtbCode, word, color);
        }

    

        // gen code from tab 1
        private void btnGen_Click(object sender, EventArgs e)
        {
            //<Option Name="Rương Thiên Nhai" Description="" WaitTime="0">
            //      <Conditions />
            //      <Rewards />
            //      <Others />
            //</Option>

            string sztemp = "<Option Name=\"Rương Thiên Nhai\" Description=\"\" WaitTime=\"0\">\n";

            if (rtbCondition.Text.Equals(""))
                sztemp += "\t<Conditions />\n";
            else
                sztemp += "\t" + rtbCondition.Text.Replace("\n","\n\t") + "\n";

            if (rtbReward.Text.Equals(""))
                sztemp += "\t<Rewards />\n";
            else
                sztemp += "\t" + rtbReward.Text.Replace("\n", "\n\t") + "\n";

            sztemp += "\t<Others />\n";

            sztemp += "</Option>";
            string szPath = Application.StartupPath + "\\codeWJX.xml";
            clsCommons.WriteFile(szPath, sztemp, 1, Encoding.Unicode);

            DialogResult result =  MessageBox.Show("File code đã gen xong. Có muốn mở file CodeWJX.xml không?", clsCommons.TITLE, MessageBoxButtons.YesNo, MessageBoxIcon.Information);
            if (result == DialogResult.Yes)
            {
                Process notePad = new Process();
                notePad.StartInfo.FileName = "notepad++.exe";
                notePad.StartInfo.Arguments = szPath;
                notePad.Start();
            }
        }


        private void autogen(int type, string szKey, List<string>lstParam)
        {
            checkLiscence();

            string szTemp = "";
            if (type == clsCommons.REWARD)
                szTemp += "<Reward FunctionKey = \"" + szKey + "\">\n";

            else if (type == clsCommons.CONDITION)
                szTemp += "<Condition FunctionKey = \"" + szKey + "\">\n";

            szTemp += "\t<Params>\n";

            for (int i = 0; i < lstParam.Count; i++)
                szTemp += "\t\t<Param>" + lstParam[i] + "</Param>\n";

            szTemp += "\t</Params>\n";

            if (type == clsCommons.REWARD)
            {
                szTemp += "</Reward>\n";
                rtbCode.Text += szTemp;
            }
            else if (type == clsCommons.CONDITION)
            {
                szTemp += "</Condition>\n";
                rtbCode.Text += szTemp;
            }
        }

        private void rtbRawData_TextChanged(object sender, EventArgs e)
        {
            rtbCode.Text = "";
            string szTemp = rtbRawData.Text;

            string[] arrTemp = szTemp.Split('\n');
            string[] arrKeyFunction;
            int type = -1;

            for (int k = 0; k < _lstGameFunctions.Count; k++)
                for (int i = 0; i < arrTemp.Length; i++)
                {
                    // arrKeyFunction: addLevel     100     Giới hạn nhận thưởng
                    arrKeyFunction = arrTemp[i].Split('\t');

                    List<string> lstparam = new List<string>();
                    for (int iCur = 1; iCur < arrKeyFunction.Length; iCur++)
                        lstparam.Add(arrKeyFunction[iCur]);




                    if (arrKeyFunction[0].Equals(_lstGameFunctions[k].Key))
                    {
                        if (_lstGameFunctions[k].Type.Equals("Condition"))
                            type = clsCommons.CONDITION;

                        else if (_lstGameFunctions[k].Type.Equals("Reward"))
                            type = clsCommons.REWARD;


                        autogen(type, _lstGameFunctions[k].Key, lstparam);

                    } 

                }

            HighLight();
        }

  
    }
}
