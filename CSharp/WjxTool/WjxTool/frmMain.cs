using DevComponents.DotNetBar;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Text;
using System.Windows.Forms;

// làm tiếp tại đây ---------------------------
// Mã Lỗi dt-50002 - hết lis

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

        private void buttonItem1_Click(object sender, EventArgs e)
        {
            
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
            {
                szTemp = "<!-- REWARD -->\n";
                szTemp += "<Reward FunctionKey = \"" + szKey + "\">\n";
            }
            else if (type == clsCommons.CONDITION)
            {
                szTemp = "<!-- CONDITION -->\n";
                szTemp += "<Condition FunctionKey = \"" + szKey + "\">\n";
            }

            szTemp += "\t<Params>\n";

            for (int i = 0; i < lstParam.Count; i++)
                szTemp += "\t\t<Param>" + lstParam[i] + "</Param>\n";

            szTemp += "\t</Params>\n";

            if (type == clsCommons.REWARD)
                szTemp += "</Reward>\n";
            else if (type == clsCommons.CONDITION)
                szTemp += "</Condition>\n";
            
            rtbContent.Text += szTemp;
            
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
            rtbContent.Text = "";
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


                string szTemp = "";
                if (type == clsCommons.REWARD)
                {
                    szTemp = "<!-- REWARD -->\n";
                    szTemp += "<Reward FunctionKey = \"" + szKey + "\">\n";
                }
                else if (type == clsCommons.CONDITION)
                {
                    szTemp = "<!-- CONDITION -->\n";
                    szTemp += "<Condition FunctionKey = \"" + szKey + "\">\n";
                }

                szTemp += "\t<Params>\n";

                for (int i = 0; i < arrParas.Length; i++)
                    szTemp += "\t\t<Param>" + arrParas[i] + "</Param>\n";

                szTemp += "\t</Params>\n";

                if (type == clsCommons.REWARD)
                    szTemp += "</Reward>\n";
                else if (type == clsCommons.CONDITION)
                    szTemp += "</Condition>\n";
                

                rtbContent.Text += szTemp;
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


        public void HighlightText(string word, Color color)
        {
            int s_start = rtbContent.SelectionStart, startIndex = 0, index;

            while ((index = rtbContent.Text.IndexOf(word, startIndex)) != -1)
            {
                rtbContent.Select(index, word.Length);
                rtbContent.SelectionColor = color;

                startIndex = index + word.Length;
            }

            rtbContent.SelectionStart = s_start;
            rtbContent.SelectionLength = 0;
            rtbContent.SelectionColor = Color.Black;
        }


        private void lbFunction_SelectedIndexChanged(object sender, EventArgs e)
        {
               
            
        }
    }
}
