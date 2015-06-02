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
        private string szTitle;

        public frmPara()
        {
            InitializeComponent();
        }

        public frmPara(string title)
        {
            InitializeComponent();

            szTitle = title;
            createForm();
        }

        private void createForm()
        {
            if (szTitle == "CheckLevel")
            {
                labelX1.Text = "Cấp Yêu Cầu";
                labelX2Desc.Text = "Cấm nhập ký tự '|'";
                labelX2.Text = "Thông Tin Nhắc Nhở";
                labelX2Desc.Text = "Cấm nhập ký tự '|'";
            }

            else if (szTitle == "CheckAboveLevel")
            {
                labelX1.Text = "Cấp Yêu Cầu";
                labelX1Desc.Text = "Phải nhập số nguyên'";
                labelX2.Text = "Thông Tin Nhắc Nhở";
                labelX2Desc.Text = "Cấm nhập ký tự '|'";
            }

            else if (szTitle == "CheckWeiWang")
            {
                labelX1.Text = "Điểm Uy danh giang hồ";
                labelX2.Text = "Thông Tin Nhắc Nhở";
            }

            else if (szTitle == "CheckFreeBag")
            {
                labelX1.Text = "Số ô trống trong túi";
                labelX2.Text = "Thông tin nhắc nhở";
            }

            else if (szTitle == "CheckMonthPay")
            {
                labelX1.Text = "Mức tích lũy nạp thẻ tháng này";
                labelX2.Text = "Thông tin nhắc nhở";
            }

            else if (szTitle == "CheckPayIsAction")
            {
                labelX1.Text = "Bit kích hoạt";
                labelX2.Text = "Thông tin nhắc nhở";
            }

        }


        private void textBoxX1_TextChanged(object sender, EventArgs e)
        {

          
        }

        private void btnThoat_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void btnDongY_Click(object sender, EventArgs e)
        {
            clsCommons.readXMLFiles2();
        }

        private void frmPara_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyData == Keys.Escape)
                this.Close();
        }
    }
}
