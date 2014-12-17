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


namespace AutoCommitSVN
{
    class Program
    {
        static void AddFileToSVN(string path, string workingcopy)
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
                            Console.WriteLine(changeFile.Path + " Removed - Missing files (that are not in filesystem)");
                        }

                        if (changeFile.LocalContentStatus == SharpSvn.SvnStatus.NotVersioned)
                        {
                            client.Add(changeFile.Path);
                            Console.WriteLine(changeFile.Path + " Added - This file is new in filesystem!");
                        }
                      
                }

                //SharpSvn.SvnAddArgs args = new SharpSvn.SvnAddArgs();
                //args.Depth = SharpSvn.SvnDepth.Empty;
                //args.AddParents = true;
                //args.Force = true;
                //Console.WriteLine(path);                

                //try
                //{
                //    return client.Add(path, args);
                //}
                //catch (Exception ex)
                //{
                //    Console.WriteLine(ex.Message);
                //    return false;
                //}
            }
        }

        static bool CommitToSVN(string workingcopy, string message)
        {
            using (SharpSvn.SvnClient client = new SharpSvn.SvnClient())
            {
                SharpSvn.SvnCommitArgs args = new SharpSvn.SvnCommitArgs();

                args.LogMessage = message;                
                args.ThrowOnError = true;
                args.ThrowOnCancel = true;

                try
                {
                    
                    return client.Commit(workingcopy, args);
                }
                catch(Exception ex)
                {
                    // if (ex.InnerException != null)                    
                    //    throw new Exception(ex.InnerException.Message, ex);

                    throw ex;
                }
            }
        }

        static void Main(string[] args)
        {
            String[] folders;
            string path = "E:\\Script - FS - PHONG THAN\\FS_Task";
            folders = Directory.GetDirectories(path, "*.*", SearchOption.AllDirectories);
            string workingcopy = "E:\\Script - FS - PHONG THAN\\FS_Task";


            AddFileToSVN(path, workingcopy);

            //foreach (String folder in folders)
            //{
            //    String[] files;
            //    files = Directory.GetFiles(folder);
                
            //    foreach (String file in files)
            //    {
            //        AddFileToSVN(file, workingcopy);
            //    }
            //}

            Console.WriteLine("Add Files To SVN finished!");
            CommitToSVN(workingcopy, "change");
            Console.WriteLine("commit Succeed!");
            Console.ReadLine();
        }
    }
}
