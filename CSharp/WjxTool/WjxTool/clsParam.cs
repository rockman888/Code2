using System;
using System.Collections.Generic;
using System.Text;

namespace WjxTool
{
    public class clsParam
    {
        private string pName;
        private string pDescription;
        private string pErrorMessage;

        public string PErrorMessage
        {
            get { return pErrorMessage; }
            set { pErrorMessage = value; }
        }

        public string PDescription
        {
            get { return pDescription; }
            set { pDescription = value; }
        }

        public string PName
        {
            get { return pName; }
            set { pName = value; }
        }
    }
}
