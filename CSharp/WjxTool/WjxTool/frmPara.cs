using DevComponents.DotNetBar;
using DevComponents.DotNetBar.Controls;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace WjxTool
{
    public partial class frmPara : DevComponents.DotNetBar.Office2007Form
    {        
        private clsGameFunction _clsGameFunction;

    


        public frmPara()
        {
            InitializeComponent();
        }

        public frmPara(clsGameFunction cls)
        {
            
            
            InitializeComponent();
            createForm(cls);
        }

        private void createForm(clsGameFunction cls)
        {
          

            displayTitleFrm(cls);

            int index = 0; // tabindex
            int count = cls.CParma.Count;
            _clsGameFunction = cls;
            string temp;

            for (int i = 0; i < count; i++)
            {
                LabelX label = new DevComponents.DotNetBar.LabelX();
                label.BackgroundStyle.CornerType = DevComponents.DotNetBar.eCornerType.Square;
                label.Location = new System.Drawing.Point(30, 25 * (i+1));  // vị trí
                label.Name = "labelX" + (i+1).ToString() ;
                label.TabIndex = index;
                label.Size = new System.Drawing.Size(230, 28);
                label.Text = cls.CParma[i].PName;                             
                label.TextAlignment = System.Drawing.StringAlignment.Far;
                Controls.Add(label);

                index++;

                TextBoxX textBox = new DevComponents.DotNetBar.Controls.TextBoxX();
                textBox.Border.Class = "TextBoxBorder";
                textBox.Border.CornerType = DevComponents.DotNetBar.eCornerType.Square;
                textBox.Location = new System.Drawing.Point(266, 25 * (i+1));   // vị trí
                textBox.Margin = new System.Windows.Forms.Padding(4);
                textBox.Name = "textBoxX" + (i + 1).ToString();
                textBox.Size = new System.Drawing.Size(450, 25);
                textBox.TabIndex = index;
                

                if (cls.CParma[i].PErrorMessage.Contains("số"))
                    textBox.Text = "0";

                if (cls.Name.Equals("AddRandomAward"))
                    if (i == 3 || i == 4 || i == 5)
                        textBox.Text = "0,0,\"0,0,0,0,0,0,0,0,0\",\"\",0,0,0";

                
                temp = "Tham số thứ: " + i.ToString() + " - " + cls.CParma[i].PName ;
                this.superTooltip1.SetSuperTooltip(textBox, new DevComponents.DotNetBar.SuperTooltipInfo(temp, cls.CParma[i].PDescription, null, null, null, DevComponents.DotNetBar.eTooltipColor.System));
                                
                Controls.Add(textBox);
                index++;
            }           


            // create autobutton
            ButtonX btnYes = new DevComponents.DotNetBar.ButtonX();
            btnYes.AccessibleRole = System.Windows.Forms.AccessibleRole.PushButton;
            btnYes.ColorTable = DevComponents.DotNetBar.eButtonColor.OrangeWithBackground;
            btnYes.Location = new System.Drawing.Point(500, 25* (count + 1) + 10);
            btnYes.Margin = new System.Windows.Forms.Padding(4);            
            btnYes.Size = new System.Drawing.Size(100, 28);
            btnYes.Style = DevComponents.DotNetBar.eDotNetBarStyle.StyleManagerControlled;
            btnYes.TabIndex = index;
            btnYes.Name = "btnDongY";
            btnYes.Text = "Đồng Ý";
            btnYes.Click += new System.EventHandler(btnYes_Click);
            

            index++;

            ButtonX btnNo = new DevComponents.DotNetBar.ButtonX();
            btnNo.AccessibleRole = System.Windows.Forms.AccessibleRole.PushButton;
            btnNo.ColorTable = DevComponents.DotNetBar.eButtonColor.OrangeWithBackground;
            btnNo.Location = new System.Drawing.Point(610, 25 *(count + 1) + 10);
            btnNo.Margin = new System.Windows.Forms.Padding(4);
            btnNo.Name = "btnThoat";
            btnNo.Size = new System.Drawing.Size(100, 28);
            btnNo.Style = DevComponents.DotNetBar.eDotNetBarStyle.StyleManagerControlled;
            btnNo.TabIndex = index;
            btnNo.Text = "Thoát";
            btnNo.Click += new System.EventHandler(btnNo_Click);

            Controls.Add(btnYes);
            Controls.Add(btnNo);

            // resize frm           

            if (count > 3)
            {
                this.Height = 30 * (count + 2) + 20;
                this.Size = new Size(this.Width, this.Height);
            }
        }

        private void displayTitleFrm(clsGameFunction cls)
        {
            if (cls.Type.Equals("Condition"))
                this.Text = clsCommons.TITLE + " => " + cls.Key + "/" + cls.Name + " - Condition";

            
            else if (cls.Type.Equals("Reward"))
                this.Text = clsCommons.TITLE + " => " + cls.Key + "/" + cls.Name + " - Reward";

            else
                this.Text = clsCommons.TITLE + " => " + cls.Key + "/" + cls.Name + " - vilh@vng.com.vn";
        }

        private void btnYes_Click(object sender, EventArgs e)
        {
            
            foreach (Control c in this.Controls)
                if (c.GetType().Name.Equals("TextBoxX"))
                    frmMain._lstparams.Add(c.Text);

            
            if (_clsGameFunction.Type.Equals("Condition"))
                frmMain._itype = clsCommons.CONDITION;

            else if (_clsGameFunction.Type.Equals("Reward"))
                frmMain._itype = clsCommons.REWARD;
                        
                        
            frmMain._szKey = _clsGameFunction.Key;
            this.Close();
        }



        private void btnNo_Click(object sender, EventArgs e)
        {
            this.Close();
        }

      
        private void frmPara_KeyDown(object sender, KeyEventArgs e)
        {
            
            if (e.KeyData == Keys.Escape)
                this.Close();
        }

        private void frmPara_Load(object sender, EventArgs e)
        {

        }
    }
}
