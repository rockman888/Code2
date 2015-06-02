using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Xml;

namespace WjxTool
{
    public class clsCommons
    {
        public static void readXMLFiles(string path)
        {            
            var doc = new XmlDocument();
            doc.Load(path);
            var items = doc.GetElementsByTagName("GameFunction");
            string str;

            for (int k = 0; k < items.Count; k++)   // 2 Game Function
            {
                var count = items[k].ChildNodes.Count;
                var item = items[k].ChildNodes;
                var collections = items[k].Attributes;

                if (collections != null)
                {
                    var key = collections["Key"];
                    str = key.Value;        // CheckLevel - CheckAboveLevel
                }
                for (int i = 0; i < count; i++)
                {
                    var xmlAttributeCollection = item[i].Attributes;

                    if (xmlAttributeCollection != null)
                    {
                        var action = xmlAttributeCollection["Name"];
                        str = action.Value; // Cấp Yêu cầu

                        var fileName = xmlAttributeCollection["ErrorMessage"];
                        str = fileName.Value; // Thông tin nhắc nhở
                    }
                }
            }
        }
    }
}
