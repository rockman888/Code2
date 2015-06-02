using DevComponents.DotNetBar;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace demoDotNetBar
{
    public partial class Form1 : DevComponents.DotNetBar.Office2007Form
    {

        private bool isChoose = false;
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

     
        private void btnCheckLevel_Click(object sender, EventArgs e)
        {            
            //itemPanel.AddItem("Check Level (para 1, para 2)");

            // ButtonItem btnItem = new ButtonItem("Check Level (para1, para2)");
            
            //this.itemPanel.Items.AddRange(new DevComponents.DotNetBar.BaseItem[] {
            //    btnItem
            // });
            //itemPanel.Refresh();
            lbFunction.Items.Add("AddCheckLevel (para 1, para 2)");
            
        }

        

        private void btnCheckAboveLevel_Click(object sender, EventArgs e)
        {

           
        }


        private void lbFunction_SelectedIndexChanged(object sender, EventArgs e)
        {
            isChoose = true;
        }

        private void lbFunction_KeyDown(object sender, KeyEventArgs e)
        {
            if (isChoose == true)
                if (lbFunction.SelectedIndex >= 0)
                    if (e.KeyCode == Keys.Delete)
                        lbFunction.Items.RemoveAt(lbFunction.SelectedIndex);
        }     
    }
}
