using DevComponents.DotNetBar;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

// làm tiếp tại đây ---------------------------
// dòng 87

namespace WjxTool
{
    public partial class frmMain : DevComponents.DotNetBar.Office2007Form
    {
        private List<clsGameFunction> _lstGameFunctions;
        public static string _szKey;            // receive value from another form
        public static List<string> _lstparams;   // receive value from another form

        public frmMain()
        {
            InitializeComponent();
            _lstparams = new List<string>();
        }

        private void checkTime()
        {
            DateTime dt = DateTime.Now;
            if (dt.Year == 2016 && dt.Month == 3)
            {
                MessageBox.Show("Tool bị lỗi dt! Liên hệ với vilh@vng.com.vn để biết thêm chi tiết");
                this.Close();
            }
        }


        private void frmMain_Load(object sender, EventArgs e)
        {
            checkTime();
            string path = Application.StartupPath + "\\product.xml";
            _lstGameFunctions = clsCommons.readXMLFiles(path);

            ePMain.TitleText = "Tổng Cộng:" + _lstGameFunctions.Count.ToString();

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
            checkTime();
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
        

        // làm tiếp tại đây ---------------------------
        private void frmMain_Activated(object sender, EventArgs e)
        {
            if (_szKey != null)
            {
                string temp = _szKey + "(";
                for (int i = 0; i < _lstparams.Count; i++)
                {
                    if (i >= 1)
                        temp += ", ";
                    temp += _lstparams[i];

                }

                temp += ")";

                lbFunction.Items.Add(temp);
                _szKey = null;
                _lstparams = new List<string>();
            }
        }

        private void lbFunction_KeyDown(object sender, KeyEventArgs e)
        {
            int iCurr = lbFunction.SelectedIndex;
            if (iCurr >= 0)
            {
                DialogResult dr = MessageBox.Show("Có muốn xóa dòng " + lbFunction.Items[iCurr].ToString() + " không?", clsCommons.TITLE, MessageBoxButtons.YesNo, MessageBoxIcon.Question);

                if (dr == DialogResult.Yes)
                    lbFunction.Items.RemoveAt(lbFunction.SelectedIndex);
            }
        }

        private void lbFunction_SelectedIndexChanged(object sender, EventArgs e)
        {
            //MessageBox.Show (lbFunction.SelectedIndex.ToString());
        }      
    }
}
