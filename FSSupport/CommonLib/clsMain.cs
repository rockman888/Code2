using System;
using System.Collections.Generic;
using System.Data;
using System.Data.OleDb;
using System.IO;
using System.Text;

// vilh

namespace CommonLib
{
    static public class clsMain
    {

        public static List<string> ReadTextFile(string szPath)
        {
            FileStream fs = new FileStream(szPath, FileMode.Open);

            if (fs == null)
                return null;

            List<string> ls = new List<string>();

            using (StreamReader sr = new StreamReader(fs, Encoding.ASCII ))
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


        /// <summary>
        /// Read excel file 
        /// </summary>
        /// <param name="path">path of xac suat file</param>
        /// <param name="strSheetName">ten sheet n</param>
        /// <returns>DataTable: content of file Excel in Sheet one</returns>
        public static DataTable ReadExcelFile(string path, string strSheetName)
        {
            string connectionString = @"Provider=Microsoft.ACE.OLEDB.12.0;";
            connectionString += "Data Source=" + path + ";";
            connectionString += "Extended Properties=" + "\"Excel 12.0;HDR=YES;\"";

            // string connectionString = "provider=Microsoft.Jet.OLEDB.4.0;Data Source=" + path + ";Extended Properties=Excel 8.0;HDR=NO;"; ko duoc
            // YES : ko doc header
            // NO: Doc header
            // if you don't want to show the header row (first row)
            // use 'HDR=NO' in the string

            // string strSQL = "SELECT * FROM [Sheet1$]";
            string strSQL = "SELECT * FROM [" + strSheetName + "$]";

            OleDbConnection excelConnection = new OleDbConnection(connectionString);
            excelConnection.Open();             // This code will open excel file.

            OleDbCommand dbCommand = new OleDbCommand(strSQL, excelConnection);
            OleDbDataAdapter dataAdapter = new OleDbDataAdapter(dbCommand);

            // create data table
            DataTable dTable = new DataTable();
            dataAdapter.Fill(dTable);

            dTable.Dispose();
            dataAdapter.Dispose();
            dbCommand.Dispose();

            excelConnection.Close();
            excelConnection.Dispose();

            return dTable;
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
    }
}
