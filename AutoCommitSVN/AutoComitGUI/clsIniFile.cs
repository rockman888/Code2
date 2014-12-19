/* ************************************************************************
 * Author: VịLH - zidane (huuvi168@gmail.com)
 * Last Modified: 20141215 
 * ************************************************************************
 */

using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;
using System.Text;

/* 
 * How to use: clsIniFile
 * 
 * --> Write file 
 * clsIniFile inif = new clsIniFile("D:\\config.ini");
 * inif.Write("Database", "Devs", "sa");
 * 
 * => [Database]
 *    Devs=sa;
 * 
 * --> Read file
 * clsIniFile inif = new clsIniFile("D:\\config.ini");
 * Console.WriteLine("The Value is:" + inif.Read("Database", "Devs"));
 * => sa
 * 
 */

namespace AutoComitGUI
{
    public class clsIniFile
    {
        private string filePath;
         
        [DllImport("kernel32")]
        private static extern long WritePrivateProfileString(string section,
                                    string key,
                                    string val,
                                    string filePath);
 
        [DllImport("kernel32")]
        private static extern int GetPrivateProfileString(string section,
                                    string key,
                                    string def,
                                    StringBuilder retVal,
                                    int size,
                                    string filePath);

        public clsIniFile(string filePath)
        {
            this.filePath = filePath;
        }
 
        public void Write(string section, string key, string value)
        {
            WritePrivateProfileString(section, key, value.ToLower(), this.filePath);
        }
 
        public string Read(string section, string key)
        {
            StringBuilder SB = new StringBuilder(255);
            int i = GetPrivateProfileString(section, key, "", SB, 255, this.filePath);
            return SB.ToString();
        }
         
        public string FilePath
        {
            get { return this.filePath; }
            set { this.filePath = value; }
        }
    }
}
