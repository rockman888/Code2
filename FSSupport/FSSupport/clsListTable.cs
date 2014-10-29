/*
 * Author: vilh (zidane)
 * last modified: 28/10/2014
 */

using System;
using System.Collections.Generic;
using System.Text;

namespace FSSupport
{
    public enum COLOR
    {
        YELLOW = 0,
        GREEN = 1
    };

    public class clsListTable
    {
        private int color;
        private string name;
        private string prop;
        private string rate;
        private string type;
        private string count;
        private string itemID;

        public int Color
        {
            get { return color; }
            set { color = value; }
        }
        

        public string Name
        {
            get { return name; }
            set { name = value; }
        }
        

        public string Prop
        {
            get { return prop; }
            set { prop = value; }
        }
        

        public string Rate
        {
            get { return rate; }
            set { rate = value; }
        }

        
        public string Type
        {
            get { return type; }
            set { type = value; }
        }

        public string Count
        {
            get { return count; }
            set { count = value; }
        }

        public string ItemID
        {
            get { return itemID; }
            set { itemID = value; }
        }
    }
}
