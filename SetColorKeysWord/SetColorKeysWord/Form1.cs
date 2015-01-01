// -----------------------------------------------------------------------------
// Author: zidane
// Email: huuvi168@gmail.com
// highlight 
// -----------------------------------------------------------------------------

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace SetColorKeysWord
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            List<string> list = new List<string>();
            list.Add("if");
            list.Add("else");
            list.Add("then");
            
            string source = "#include \"stdio.h\"\n#include \"conio.h\"\n void main ()\n { \n if (anh la ai) print (ifsd";
            rtbResult.Text = source;
            HighlightPhrase(rtbResult, list, System.Drawing.Color.Blue, System.Drawing.FontStyle.Italic, source);
            
            rtbResult.Refresh();
        }
        
           // Thiết lập màu sắc cho RichTextBox
        public void HighlightPhrase(RichTextBox box, string phrase, 
                            System.Drawing.Color color, System.Drawing.FontStyle fs,
                            string source)
        {
            int pos = box.SelectionStart;
            string s = box.Text;
            for (int ix = 0; ; )
            {
                int jx = s.IndexOf(phrase, ix, StringComparison.CurrentCultureIgnoreCase);
                if (jx < 0)
                    break;

                //if (source[jx - 1].ToString() != "" && source[jx + phrase.Length + 1].ToString() != "")
                //    break;
                box.SelectionStart = jx;
                box.SelectionLength = phrase.Length;
                box.SelectionColor = color;
                box.SelectionFont = new System.Drawing.Font("Courier New", 11, fs);
                ix = jx + 1;
            }
            box.SelectionStart = pos;
            box.SelectionLength = 0;
        }

        public void HighlightPhrase(RichTextBox box, List<string> lstArray, System.Drawing.Color color, System.Drawing.FontStyle fs, string source)
        {
            foreach (string str in lstArray)
            {
                HighlightPhrase(box, str, color, fs, source);
            }
        }

    }
}
