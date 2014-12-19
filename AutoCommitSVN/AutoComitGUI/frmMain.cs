/* ************************************************************************
 * Author: VịLH - zidane (huuvi168@gmail.com)
 * Last Modified: 20141215 
 * ************************************************************************
 */

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace AutoComitGUI
{
    public partial class frmAutoCommit : Form
    {
        private static string _fileName;
        private static string _path;
        private static string _section;
        private static string _keyLocalPatch;
        private static string _keyHour;
        private static string _keyMinute;
        private static string _keySecond;
        private static string _keyAuthor;
        private static string _keyEmail;

        private static clsIniFile _iniFile;

        public frmAutoCommit()
        {
            InitializeComponent();

            _fileName = "info.ini";
            _path = Application.StartupPath + "\\" + _fileName;
            _iniFile = new clsIniFile(_path);

            _section = "AutoCommit";
            _keyLocalPatch = "LocalPatch";
            _keyHour = "Hour";
            _keyMinute = "Minute";
            _keySecond = "Second";
            _keyAuthor = "Author";
            _keyEmail = "Email";



        }

        private void Form1_Load(object sender, EventArgs e)
        {
            myNotifyIcon.ShowBalloonTip(10, clsCommons.AppTitle, "Auto commit file on SVN", ToolTipIcon.Info);

            // init comboBox
            Init();

            // init file
            loadInfo();
        }

        private void Init()
        {
            for (int i = 0; i <= 23; i++)
                cmbHour.Items.Add(i.ToString());

            for (int i = 0; i <= 59; i++)
            {
                cmbMinute.Items.Add(i.ToString());
                cmbSecond.Items.Add(i.ToString());
            }
        }

        private void WriteInfo(clsInfo clsInfo)
        {

            clsIniFile iniFile = new clsIniFile(_path);

            iniFile.Write(_section, _keyAuthor, clsInfo.Author);
            iniFile.Write(_section, _keyEmail, clsInfo.Email);
            iniFile.Write(_section, _keyLocalPatch, clsInfo.LocalPath);
            iniFile.Write(_section, _keyHour, clsInfo.Hour.ToString());
            iniFile.Write(_section, _keyMinute, clsInfo.Minute.ToString());
            iniFile.Write(_section, _keySecond, clsInfo.Second.ToString());

        }

        private void loadInfo()
        {


            if (!File.Exists(_path))  // ko tìm thấy
            {
                // create file
                clsInfo info = new clsInfo();
                info.Hour = 8;
                info.Minute = 10;
                info.Second = 10;
                info.LocalPath = @"E:\GitHub\AutoCommitSVN";

                WriteInfo(info);
            }

            txtLocalPath.Text = _iniFile.Read(_section, _keyLocalPatch);
            cmbHour.Text = _iniFile.Read(_section, _keyHour);
            cmbMinute.Text = _iniFile.Read(_section, _keyMinute);
            cmbSecond.Text = _iniFile.Read(_section, _keySecond);
        }

        private void notifyIcon1_MouseDoubleClick(object sender, MouseEventArgs e)
        {

            if (WindowState == FormWindowState.Normal)
            {
                myTimer.Enabled = true;
                this.Hide();
                myNotifyIcon.Visible = true;
                WindowState = FormWindowState.Minimized;
            }
            else if (WindowState == FormWindowState.Minimized)
            {
                myTimer.Enabled = false;
                this.Show();
                myNotifyIcon.Visible = false;
                WindowState = FormWindowState.Normal;
            }
        }

        private void Form1_Resize(object sender, EventArgs e)
        {
            if (WindowState == FormWindowState.Minimized)
            {
                // Do some stuff
                myNotifyIcon.Visible = true;

                // Chọn ẩn
                this.Hide();
                this.ShowInTaskbar = false;

                string szLocalPath = _iniFile.Read(_section, _keyLocalPatch);
                string szHour = _iniFile.Read(_section, _keyHour);
                string szMinute = _iniFile.Read(_section, _keyMinute);
                string szSecond = _iniFile.Read(_section, _keySecond);

                string szMessage = "On: " + szHour + ":" + szMinute + " everyday, auto commit to svn from this repos:\n" + szLocalPath;
                myNotifyIcon.ShowBalloonTip(10, clsCommons.AppTitle, szMessage, ToolTipIcon.Info);
                myTimer.Enabled = true;
            }
        }

        private void btnUpdate_Click(object sender, EventArgs e)
        {
            clsInfo info = new AutoComitGUI.clsInfo();

            info.LocalPath = txtLocalPath.Text;
            info.Hour = cmbHour.SelectedIndex;
            info.Minute = cmbMinute.SelectedIndex;
            info.Second = cmbSecond.SelectedIndex;

            WriteInfo(info);

            MessageBox.Show("Settings updated!", clsCommons.AppTitle, MessageBoxButtons.OK, MessageBoxIcon.Information);


            myTimer.Enabled = true;
        }

        private void btnExit_Click(object sender, EventArgs e)
        {
            myNotifyIcon.Dispose(); // delete icon
            this.Close();
        }

        private void frmAutoCommit_FormClosed(object sender, FormClosedEventArgs e)
        {
            clsInfo info = new AutoComitGUI.clsInfo();

            loadInfo();
            info.Hour = cmbHour.SelectedIndex;
            info.Minute = cmbMinute.SelectedIndex;
            info.Second = cmbSecond.SelectedIndex;
            info.LocalPath = txtLocalPath.Text;

            WriteInfo(info);
        }

        private void btnBrowser_Click(object sender, EventArgs e)
        {
            // Show the FolderBrowserDialog.

            FolderBrowserDialog folderBrowserDialog = new FolderBrowserDialog();
            DialogResult result = folderBrowserDialog.ShowDialog();
            folderBrowserDialog.ShowNewFolderButton = false;
            if (result == DialogResult.OK)
            {
                txtLocalPath.Text = folderBrowserDialog.SelectedPath;
            }
        }

        private void myTimer_Tick(object sender, EventArgs e)
        {
            int hour = DateTime.Now.Hour;
            int minute = DateTime.Now.Minute;
            int second = DateTime.Now.Second;

            string szLocalPatch = _iniFile.Read(_section, _keyLocalPatch);
            string szHour = _iniFile.Read(_section, _keyHour);
            string szMinute = _iniFile.Read(_section, _keyMinute);
            string szSecond = _iniFile.Read(_section, _keySecond);
                        
            int Year = DateTime.Now.Year;
            int Month = DateTime.Now.Month;
            int Day = DateTime.Now.Day;

            string logFolder = Application.StartupPath + "\\Log";
            string logFile = Application.StartupPath + "\\Log\\" + Year + Month + Day + ".txt";

            try
            {

                if (hour == Convert.ToInt16(szHour))
                    if (Convert.ToInt16(szMinute) - 1 <= minute && minute <= Convert.ToInt16(szMinute) + 1)
                    {
                     
                       
                        if (!Directory.Exists(logFolder))
                            Directory.CreateDirectory(logFolder);

                        bool bFlag = clsCommons.AddFileToSVN(logFile, szLocalPatch, myTimer);

                        if (bFlag == true)
                        {
                            myNotifyIcon.ShowBalloonTip(5, clsCommons.AppTitle, "Add Files To SVN finished!", ToolTipIcon.Info);
                            string szMessage = "<Commit By AutoCommit Tool> Update Final Version To SVN, Last Update: " + Year + Month + Day;
                            bFlag = clsCommons.CommitToSVN(logFile, szLocalPatch, szMessage, myTimer);

                            if (bFlag == true)
                                myNotifyIcon.ShowBalloonTip(5, clsCommons.AppTitle, "Commit Succeed!", ToolTipIcon.Info);

                            else
                                myNotifyIcon.ShowBalloonTip(5, clsCommons.AppTitle, "Commit Fail, Please Check Log File!", ToolTipIcon.Info);
                        }

                        else
                            myNotifyIcon.ShowBalloonTip(5, clsCommons.AppTitle, "Add Files Fail, Please Check Log File!", ToolTipIcon.Info);

                    }
            }
            catch (Exception ex)
            {
                clsCommons.WriteLog(logFile, ex);
            }
        }
    }
}

