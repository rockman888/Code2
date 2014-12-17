/* ************************************************************************
 * Author: VịLH - zidane (huuvi168@gmail.com)
 * Last Modified: 20141215 
 * ************************************************************************
 */


using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace AutoComitGUI
{
    public class clsCommons
    {
        public static string AppTitle = "Auto Commit";

        // AddFileToSVN
        public static bool AddFileToSVN(string strLogFile, string path, Timer myTimer)
        {
            try
            {
                using (SharpSvn.SvnClient client = new SharpSvn.SvnClient())
                {
                    Collection<SharpSvn.SvnStatusEventArgs> changeFiles = new Collection<SharpSvn.SvnStatusEventArgs>();
                    client.GetStatus(path, out changeFiles);

                    foreach (SharpSvn.SvnStatusEventArgs changeFile in changeFiles)
                    {
                        if (changeFile.LocalContentStatus == SharpSvn.SvnStatus.Missing)
                        {
                            client.Delete(changeFile.Path);
                            WriteLog(strLogFile, changeFile.Path + " Removed - Missing files (that are not in filesystem)");

                            myTimer.Enabled = false;
                        }

                        if (changeFile.LocalContentStatus == SharpSvn.SvnStatus.NotVersioned)
                        {
                            client.Add(changeFile.Path);
                            WriteLog(strLogFile, changeFile.Path + " Added - This file is new in filesystem!");

                            myTimer.Enabled = false;
                        }
                    }
                    return true;
                }
            }
            catch (Exception ex)
            {
                myTimer.Enabled = true;
                WriteLog(strLogFile, ex);
                return false;
            }
        }

        // CommitToSVN
        public static bool CommitToSVN(string strLogFile, string workingcopy, string message, Timer myTimer)
        {
            using (SharpSvn.SvnClient client = new SharpSvn.SvnClient())
            {
                SharpSvn.SvnCommitArgs args = new SharpSvn.SvnCommitArgs();

                args.LogMessage = message;
                args.ThrowOnError = true;
                args.ThrowOnCancel = true;

                try
                {
                    myTimer.Enabled = true;
                    return client.Commit(workingcopy, args);
                }
                catch (Exception ex)
                {
                    myTimer.Enabled = true;
                    WriteLog(strLogFile, ex);
                    return false;
                }
            }
        }

        /// <summary>
        /// Ghi Noi Dung strContent vao duong dan strPath
        /// \nMode=1: CreateNew (co se ghi de, chua co tao moi) / 2:Append
        /// </summary>
        /// <param name="strPath">Duong Dan file</param>
        /// <param name="strContent">Noi Dung file</param>
        /// <param name="Mode">1: CreateNew (co se ghi de, chua co tao moi) / 2:Append</param>
        /// <param name="Encoding">Unicode/ANSCII</param>
        /// <returns>true -> succeed</returns>
        public static bool WriteFile(string strPath, List<string> lstContent, int iMode, Encoding en)
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

                // Encoding en = Encoding.Unicode!

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
        public static bool WriteFile(string szPath, string szContent, int iMode, Encoding en)
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


        /// <summary>
        /// Ghi Noi Dung strContent vao duong dan strPath
        /// \nMode=1: CreateNew (co se ghi de, chua co tao moi) / 2:Append
        /// </summary>
        /// <param name="strPath">Duong Dan file</param>
        /// <param name="strContent">Noi Dung file</param>
        /// <param name="Mode">1: CreateNew (co se ghi de, chua co tao moi) / 2:Append</param>
        /// <returns>true -> succeed</returns>
        public static bool WriteFile(string strPath, List<string> lstContent, int iMode)
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

      
    }
}
