using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FSSupport
{
    public class clsItem
    {
        private string _szItemID;
        private string _szName;
        private string _szProductCode;
        private string _szidIngame;
        private string _sztype;
        private string _szStartTime;
        private string _szEndTime;

        public string szEndTime
        {
            get { return _szEndTime; }
            set { _szEndTime = value; }
        }

        public string szStartTime
        {
            get { return _szStartTime; }
            set { _szStartTime = value; }
        }

        public string szType
        {
            get { return _sztype; }
            set { _sztype = value; }
        }

        public string szIdIngame
        {
            get { return _szidIngame; }
            set { _szidIngame = value; }
        }

        public string szProductCode
        {
            get { return _szProductCode; }
            set { _szProductCode = value; }
        }

        public string szName
        {
            get { return _szName; }
            set { _szName = value; }
        }
       
        public string SzItemID
        {
            get { return _szItemID; }
            set { _szItemID = value; }
        }
    }
}
