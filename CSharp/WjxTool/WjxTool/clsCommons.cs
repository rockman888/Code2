using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Xml;

namespace WjxTool
{
    public class clsCommons
    {
        public static string TITLE = "WJX Tool";

        public static List<clsGameFunction> readXMLFiles(string path)
        {
            List<clsGameFunction> lst = new List<clsGameFunction>();
            try
            {
                var doc = new XmlDocument();
                doc.Load(path);
                

                var items = doc.GetElementsByTagName("GameFunction");
                // string str;

                for (int k = 0; k < items.Count; k++)   // 2 Game Function
                {
                    clsGameFunction clsGameFunction = new clsGameFunction();

                    var count = items[k].ChildNodes.Count;
                    var item = items[k].ChildNodes;
                    var collections = items[k].Attributes;

                    if (collections != null)
                    {
                        var name = collections["Name"];
                        clsGameFunction.Name = name.Value;

                        var key = collections["Key"];
                        // str = key.Value;        // CheckLevel - CheckAboveLevel
                        clsGameFunction.Key = key.Value;

                        var type = collections["Type"];
                        clsGameFunction.Type = type.Value;


                    }
                    List<clsParam> lstParam = new List<clsParam>();
                    for (int i = 0; i < count; i++)
                    {
                        var xmlAttributeCollection = item[i].Attributes;


                        if (xmlAttributeCollection != null)
                        {
                            clsParam cParam = new clsParam();

                            var name = xmlAttributeCollection["Name"];
                            // str = action.Value; // Cấp Yêu cầu
                            cParam.PName = name.Value;


                            var errorMsg = xmlAttributeCollection["ErrorMessage"];
                            // str = fileName.Value; // Thông tin nhắc nhở
                            if (errorMsg != null)
                                cParam.PErrorMessage = errorMsg.Value;

                            lstParam.Add(cParam);
                        }
                    }
                    clsGameFunction.CParma = lstParam;
                    lst.Add(clsGameFunction);
                }
            }
            catch
            {
                
            }
            return lst;
        }
    
    }
}
