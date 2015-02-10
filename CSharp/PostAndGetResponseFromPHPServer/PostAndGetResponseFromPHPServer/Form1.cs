using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Net;
using System.Text;
using System.Windows.Forms;

namespace PostAndGetResponseFromPHPServer
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void btnPostData_Click(object sender, EventArgs e)
        {
            submitDataToServer();
        }

        private void submitDataToServer()
        {
            try
            {
                string user = txtUser.Text;
                string pass = txtPass.Text;

                ASCIIEncoding encoding = new ASCIIEncoding();
                string postData = "user=" + user + "&pass=" + pass;

                byte[] data = encoding.GetBytes(postData);

                WebRequest request = WebRequest.Create("http://localhost/cdkey.php");
                request.Method = "POST";
                request.ContentType = "application/x-www-form-urlencoded";
                request.ContentLength = data.Length;

                Stream stream = request.GetRequestStream();
                stream.Write(data, 0, data.Length);
                stream.Close();


                // response from PHP Server
                WebResponse response = request.GetResponse();
                stream = response.GetResponseStream();

                StreamReader sr = new StreamReader(stream);
                textBox3.Text = sr.ReadToEnd();

                sr.Close();
                stream.Close();


            }
            catch(Exception ex)
            {
                MessageBox.Show("Error: " + ex.Message);
            }
        }
    }
}
