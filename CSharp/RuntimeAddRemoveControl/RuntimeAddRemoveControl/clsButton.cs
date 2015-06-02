using System;
using System.Collections.Generic;
using System.Text;

namespace RuntimeAddRemoveControl
{
    public class clsButton
    {
        private int top;
        private int left;
        private string text;
        private EventHandler click;

        public EventHandler Click
        {
            get { return click; }
            set { click = value; }
        }

        public string Text
        {
            get { return text; }
            set { text = value; }
        }
        


        public int Top
        {
            get { return top; }
            set { top = value; }
        }
        

        public int Left
        {
            get { return left; }
            set { left = value; }
        }
        




         //System.Windows.Forms.Button btn = new Button();
         //   this.Controls.Add(btn);
         //   btn.Top = iTop * 28;
         //   btn.Left = 15;
         //   btn.Text = "Button Exp" + this.iTop.ToString();
         //   iTop++;
         //   btn.Click += btn_Click;
    }
}
