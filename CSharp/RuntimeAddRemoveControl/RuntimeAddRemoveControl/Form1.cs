using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace RuntimeAddRemoveControl
{
    public partial class Form1 : Form
    {
        private List<clsButton> lstButton;
        private Queue<int> arrNum;
        
        public Form1()
        {
            InitializeComponent();
            lstButton = new List<clsButton>();
            arrNum = new Queue<int>();
        }

        int index = 0;
        private void addButtnExp_Click(object sender, EventArgs e)
        {
            int iTabTop = 0;
            System.Windows.Forms.Button btn = new Button();
            this.Controls.Add(btn);

            if (arrNum.Count > 0)
            {
                int iPos = arrNum.Dequeue();
                btn.Top = iPos * 28;
                iTabTop = iPos + 1; // tăng lên 1
            }
            else
            {
                btn.Top = index * 28;
                index++;
                iTabTop = index;
            }

            btn.Left = 15;
            btn.Text = "Button Exp" + iTabTop.ToString();
            btn.Name = "Button Exp" + iTabTop.ToString();
            btn.Width = 100;            
            btn.Click += btn_Click;
        }

        void btn_Click(object sender, EventArgs e)
        {
            this.Controls.Remove((System.Windows.Forms.Button)sender);
            string szText = ((Button)sender).Text;
            int iNum = Convert.ToInt32(szText.Substring(10, szText.Length - 10));
            arrNum.Enqueue(iNum-1);            // bỏ vào queue
        }

        private void btnCalc_Click(object sender, EventArgs e)
        {
            int d = 0;
            foreach (Control c in this.Controls)
               if (c.GetType() == typeof(Button))
               {
                   string szName = ((Button)c).Name;
                   if (szName.Contains("Button Exp"))
                       d++;
               }
            MessageBox.Show("tổng cộng có: " + d.ToString());
        }
    }
}
