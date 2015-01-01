// ----------------------------------------------------
// Author: zidane
// Email: huuvi168@gmail.com
// auto copy from store if file from store have any changed (update, delete, create)!
// ----------------------------------------------------
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.IO;

namespace ProjectFileChange
{
    public partial class FormMain : Form
    {
        private Icon[] images;
        private int offset = 0;

        public FormMain()
        {
            InitializeComponent();
        }

        private void btnExec_Click(object sender, EventArgs e)
        {
            //    FileSystemWatcher watch = new FileSystemWatcher();
            //    watch.Path = txtFileWatched.Text;
            //    watch.Filter = "*.txt";
            //    watch.IncludeSubdirectories = true;

            //    watch.Created += new FileSystemEventHandler(watch_Update);
            //    watch.Deleted += new FileSystemEventHandler(watch_Update);
            //    watch.Changed += new FileSystemEventHandler(watch_Update);

            //watch.EnableRaisingEvents = true;
        }

        void watch_Update(object sender, FileSystemEventArgs e)
        {
            File.Copy(txtFileWatched.Text + "NewItem.txt", txtPlaceBackup.Text + "NewItem.txt", true);
        }

        private void FormMain_Load(object sender, EventArgs e)
        {
            txtFileWatched.Text = @"D:\ZT Product - Private\Silver Server\ZT_Application 18-04-2011\ZT_Application\bin\Debug\";
            txtPlaceBackup.Text = @"D:\ZT Product - Private\Silver Server\ZT_Application 18-04-2011\ZT_Application\bin\";

            images = new Icon[2];

            images[0] = new Icon(Application.StartupPath + "\\icon\\red.ico");
            images[1] = new Icon(Application.StartupPath + "\\icon\\green.ico");

            FileSystemWatcher watch = new FileSystemWatcher();
            watch.Path = txtFileWatched.Text;
            watch.Filter = "*.txt";
            watch.IncludeSubdirectories = true;

            watch.Created += new FileSystemEventHandler(watch_Update);
            watch.Deleted += new FileSystemEventHandler(watch_Update);
            watch.Changed += new FileSystemEventHandler(watch_Update);

            watch.EnableRaisingEvents = true;
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            notifyIcon1.Icon = images[offset];
            offset++;
            if (offset > 1)
                offset = 0;
        }

        private void FormMain_Resize(object sender, EventArgs e)
        {
            if (WindowState == FormWindowState.Minimized)
                this.Hide();
            else if (WindowState == FormWindowState.Normal)
                this.Activate();
        }

        private void openToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.ShowDialog();
            this.Focus();
        }

        private void exitToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void btnExit_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
