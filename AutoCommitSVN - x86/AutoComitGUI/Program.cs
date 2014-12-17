using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace AutoComitGUI
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new frmAutoCommit());

            // notifyIcon1.ShowBalloonTip(10, "Auto Commit Application", "Auto commit file on SVN", ToolTipIcon.Info);
        }
    }
}
