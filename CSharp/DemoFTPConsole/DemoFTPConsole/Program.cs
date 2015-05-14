using System;
using System.Collections.Generic;
using System.Text;
using System.Net;
using System.IO;

namespace DemoFTPConsole
{
    class Program
    {
        // static string sourcefilepath = "C:/www/localhost_3-day.png";        // e.g. “d:/test.docx”


        static string sourcefilepath = "E:/a.txt";          // e.g. “d:/test.docx”        
        static string ftpurl = "ftp://pg1.net78.net/public_html/ftp";               // e.g. ftp://serverip/foldername/foldername
        static string ftpusername = "a4347279";             // e.g. username
        static string ftppassword = "fedoravi888";          // e.g. password

        private static void UploadFileToFTP(string source)
        {
            try
            {
                string filename = Path.GetFileName(source);
                string ftpfullpath = ftpurl;
                FtpWebRequest ftp = (FtpWebRequest)FtpWebRequest.Create(String.Format(@"ftp://{0}/{1}/{2}", "pg1.net78.net", "public_html", "ftp"));
                ftp.Credentials = new NetworkCredential(ftpusername, ftppassword);

                ftp.KeepAlive = true;
                ftp.UseBinary = true;
                ftp.Method = WebRequestMethods.Ftp.UploadFile;

                FileStream fs = File.OpenRead(source);
                byte[] buffer = new byte[fs.Length];
                fs.Read(buffer, 0, buffer.Length);
                fs.Close();

                Stream ftpstream = ftp.GetRequestStream();
                ftpstream.Write(buffer, 0, buffer.Length);
                ftpstream.Close();

                Console.WriteLine("update file succeed!");
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message.ToString());                
                throw ex;
            }
        }

        static void Main(string[] args)
        {
            //UploadFileToFTP(sourcefilepath);
            clsFTP cls = new clsFTP("ftp://pg1.net78.net", "a4347279", "fedoravi888");
            cls.upload("public_html/ftp", sourcefilepath);
            Console.WriteLine("update file succeed!");
            Console.ReadLine();
        }
    }
}
