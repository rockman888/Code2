using Microsoft.Win32;
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

        public static int CONDITION = 1;
        public static int REWARD = 2;



        public static void createRegeditKey(string szLogFolder)
        {
            try
            {
                // Opening the registry key
                RegistryKey rk = Registry.LocalMachine;

                // Open a subKey as read-only
                RegistryKey key = rk.CreateSubKey("SOFTWARE\\wjxtool");

                key.SetValue("Author", "vilh");
                key.Close();
                clsCommons.WriteLog(szLogFolder, "Succeed registry");
            }
            catch(Exception ex)
            {
                clsCommons.WriteLog(szLogFolder, ex);
            }
        }

        public static bool checkRegeditKeyExists(string szLogFolder)
        {
            // Opening the registry key
            RegistryKey rk = Registry.LocalMachine;

            // Open a subKey as read-only
            RegistryKey key = rk.OpenSubKey("SOFTWARE\\wjxtool");

            // If the RegistrySubKey doesn't exist -> (null)
            if ( key == null )
                return false;
            
            else
            {
                try 
                {
                    // If the RegistryKey exists I get its value or null is returned.
                    string szAuthor = (string)key.GetValue("Author");
                    if (szAuthor.Equals("vilh"))
                        return true;
                }
                catch(Exception ex)
                {
                    clsCommons.WriteLog(szLogFolder, ex);
                    return false;
                }
            }
            return false;
        }

        /// <summary>
        /// Ghi Noi Dung strContent vao duong dan strPath
        /// \nMode=1: CreateNew (co se ghi de, chua co tao moi) / 2:Append
        /// </summary>
        /// <param name="strPath">Duong Dan file</param>
        /// <param name="strContent">Noi Dung file</param>
        /// <param name="Mode">1: CreateNew (co se ghi de, chua co tao moi) / 2:Append</param>
        /// <returns>true -> succeed</returns>
        static public bool WriteFile(string strPath, List<string> lstContent, int iMode)
        {
            bool bFlag = false;
            FileStream fs = null;

            try
            {
                switch (iMode)
                {
                    case 1:
                        fs = new FileStream(strPath, FileMode.Create);
                        break;

                    case 2:
                        fs = new FileStream(strPath, FileMode.Append);
                        break;

                    default:
                        fs = new FileStream(strPath, FileMode.Append);
                        break;
                }

                using (StreamWriter sw = new StreamWriter(fs, Encoding.Unicode))
                {
                    for (int i = 0; i < lstContent.Count; i++)
                        sw.WriteLine(lstContent[i]);

                    sw.Close();
                }
                fs.Close();

                bFlag = true;
            }
            catch
            {

                return bFlag;
            }
            return bFlag;
        }

        /// <summary>
        /// Write lstContent on file strPath        
        /// </summary>
        /// <param name="strPath">Path of file</param>
        /// <param name="lstContent">Content file with multi row</param>
        /// <param name="Mode">1: CreateNew / 2:Append</param>
        /// <param name="Encoding">Unicode/ANSCII</param>
        /// <returns>true -> succeed</returns>
        static public bool WriteFile(string strPath, List<string> lstContent, int iMode, Encoding en)
        {
            bool bFlag = false;
            FileStream fs = null;

            try
            {
                switch (iMode)
                {
                    case 1:
                        fs = new FileStream(strPath, FileMode.Create);
                        break;

                    case 2:
                        fs = new FileStream(strPath, FileMode.Append);
                        break;

                    default:
                        fs = new FileStream(strPath, FileMode.Append);
                        break;
                }
                using (StreamWriter sw = new StreamWriter(fs, en))
                {
                    for (int i = 0; i < lstContent.Count; i++)
                        sw.WriteLine(lstContent[i]);

                    sw.Close();
                }
                fs.Close();

                bFlag = true;
            }
            catch
            {
                return bFlag;
            }
            return bFlag;
        }

        /// <summary>
        /// Ghi Noi Dung strContent vao duong dan strPath
        /// \nMode=1: CreateNew (co se ghi de, chua co tao moi) / 2:Append
        /// </summary>
        /// <param name="strPath">Duong Dan file</param>
        /// <param name="strContent">Noi Dung file</param>
        /// <param name="Mode">1: CreateNew (co se ghi de, chua co tao moi) / 2:Append</param>
        /// <returns>true -> succeed</returns>
        static public bool WriteFile(string szPath, string szContent, int iMode, Encoding en)
        {
            bool bFlag = false;
            FileStream fs = null;

            try
            {
                switch (iMode)
                {
                    case 1:
                        fs = new FileStream(szPath, FileMode.Create);
                        break;

                    case 2:
                        fs = new FileStream(szPath, FileMode.Append);
                        break;

                    default:
                        fs = new FileStream(szPath, FileMode.Append);
                        break;
                }

                using (StreamWriter sw = new StreamWriter(fs, en))
                {
                    sw.WriteLine(szContent);
                    sw.Close();
                }
                fs.Close();

                bFlag = true;
            }
            catch
            {
                return bFlag;
            }
            return bFlag;
        }

        public static void WriteLog(string strLogFolder, System.Exception ex)
        {
            try
            {
                // if (!Directory.Exists(strLogFolder))
                //    Directory.CreateDirectory(strLogFolder);    // tạo mới folder nếu không tồn tại                

                string strDate = "[" +
                                        DateTime.Today.Year + "-" + DateTime.Today.Month + "-" + DateTime.Today.Day + " " +
                                        DateTime.Now.Hour + ":" + DateTime.Now.Minute + ":" + DateTime.Now.Second +
                                 "]: ";

                string strLog = " * " + strDate + "Message = " + ex.Message.ToString() + "; TargetSite = " + ex.TargetSite + "; StackTrace = " + ex.StackTrace;

                WriteFile(strLogFolder, strLog, 2, Encoding.UTF8);
            }
            catch { }
        }

        public static void WriteLog(string strLogFolder, string strLog)
        {
            try
            {
                string strDate = "[" +
                                        DateTime.Today.Year + "-" + DateTime.Today.Month + "-" + DateTime.Today.Day + " " +
                                        DateTime.Now.Hour + ":" + DateTime.Now.Minute + ":" + DateTime.Now.Second +
                                 "]: ";

                WriteFile(strLogFolder, " * " + strDate + "\t" + strLog, 2, Encoding.UTF8);
            }
            catch { }
        }

        public static List<string> readTextFile(string szPath)
        {
            FileStream fs = new FileStream(szPath, FileMode.Open);

            if (fs == null)
                return null;

            List<string> ls = new List<string>();

            using (StreamReader sr = new StreamReader(fs, Encoding.ASCII))
            {
                string str = sr.ReadLine();
                while (str != null)
                {
                    ls.Add(str);
                    str = sr.ReadLine();
                }

                sr.BaseStream.Close();
            }

            return ls;
        }


        
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


                            var desc = xmlAttributeCollection["Description"];
                            // str = fileName.Value; // Thông tin nhắc nhở
                            if (desc != null)
                                cParam.PDescription = desc.Value;

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
