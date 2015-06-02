using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace WjxTool
{
    public partial class frmMain : DevComponents.DotNetBar.Office2007Form
    {
        public frmMain()
        {
            InitializeComponent();
        }

        private void buttonItem1_Click(object sender, EventArgs e)
        {

        }

        private void expandablePanel3_Click(object sender, EventArgs e)
        {

        }

        private void itemPanel2_ItemClick(object sender, EventArgs e)
        {

        }

        private void btnCheckLevel_Click(object sender, EventArgs e)
        {
            frmPara frm = new frmPara("CheckLevel");
            frm.ShowDialog();
        }

        private void btnCheckAboveLevel_Click(object sender, EventArgs e)
        {
            frmPara frm = new frmPara("CheckAboveLevel");
            frm.ShowDialog();
        }


        private void frmMain_FormClosing(object sender, FormClosingEventArgs e)
        {
            
        }
    }
}
