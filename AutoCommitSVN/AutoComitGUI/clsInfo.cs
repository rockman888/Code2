using System;
using System.Collections.Generic;
using System.Text;

namespace AutoComitGUI
{
    public class clsInfo
    {
        private string _author;
        private string _email;
        private string _localPath;
        private int _hour;
        private int _minute;
        private int _second;

        public clsInfo()
        {
            Author = "ViLH";
            Email = "huuvi168@gmail.com";
        }

        public string Email
        {
            get { return _email; }
            set { _email = value; }
        }

        public string Author
        {
            get { return _author; }
            set { _author = value; }
        }

        public int Hour
        {
            get { return _hour; }
            set { _hour = value; }
        }

        public int Minute
        {
            get { return _minute; }
            set { _minute = value; }
        }

        public int Second
        {
            get { return _second; }
            set { _second = value; }
        }

        public string LocalPath
        {
            get { return _localPath; }
            set { _localPath = value; }
        }
    }
}
