namespace ProjectFileChange
{
    partial class FormMain
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.txtFileWatched = new System.Windows.Forms.TextBox();
            this.txtPlaceBackup = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.btnFileWatched = new System.Windows.Forms.Button();
            this.btnBrowseBackup = new System.Windows.Forms.Button();
            this.ckbStartWithWindow = new System.Windows.Forms.CheckBox();
            this.ckbActivate = new System.Windows.Forms.CheckBox();
            this.notifyIcon1 = new System.Windows.Forms.NotifyIcon(this.components);
            this.contextMenuStrip1 = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.openToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.exitToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.timer1 = new System.Windows.Forms.Timer(this.components);
            this.contextMenuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // txtFileWatched
            // 
            this.txtFileWatched.Location = new System.Drawing.Point(107, 17);
            this.txtFileWatched.Name = "txtFileWatched";
            this.txtFileWatched.Size = new System.Drawing.Size(468, 20);
            this.txtFileWatched.TabIndex = 2;
            // 
            // txtPlaceBackup
            // 
            this.txtPlaceBackup.Location = new System.Drawing.Point(107, 41);
            this.txtPlaceBackup.Name = "txtPlaceBackup";
            this.txtPlaceBackup.Size = new System.Drawing.Size(468, 20);
            this.txtPlaceBackup.TabIndex = 3;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(31, 20);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(70, 13);
            this.label1.TabIndex = 4;
            this.label1.Text = "File watched:";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(24, 41);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(77, 13);
            this.label2.TabIndex = 5;
            this.label2.Text = "Place Backup:";
            // 
            // btnFileWatched
            // 
            this.btnFileWatched.Location = new System.Drawing.Point(580, 15);
            this.btnFileWatched.Name = "btnFileWatched";
            this.btnFileWatched.Size = new System.Drawing.Size(32, 23);
            this.btnFileWatched.TabIndex = 6;
            this.btnFileWatched.Text = "...";
            this.btnFileWatched.UseVisualStyleBackColor = true;
            // 
            // btnBrowseBackup
            // 
            this.btnBrowseBackup.Location = new System.Drawing.Point(580, 39);
            this.btnBrowseBackup.Name = "btnBrowseBackup";
            this.btnBrowseBackup.Size = new System.Drawing.Size(32, 23);
            this.btnBrowseBackup.TabIndex = 7;
            this.btnBrowseBackup.Text = "...";
            this.btnBrowseBackup.UseVisualStyleBackColor = true;
            // 
            // ckbStartWithWindow
            // 
            this.ckbStartWithWindow.AutoSize = true;
            this.ckbStartWithWindow.Checked = true;
            this.ckbStartWithWindow.CheckState = System.Windows.Forms.CheckState.Checked;
            this.ckbStartWithWindow.Location = new System.Drawing.Point(213, 76);
            this.ckbStartWithWindow.Name = "ckbStartWithWindow";
            this.ckbStartWithWindow.Size = new System.Drawing.Size(149, 17);
            this.ckbStartWithWindow.TabIndex = 8;
            this.ckbStartWithWindow.Text = "Start when windows starts";
            this.ckbStartWithWindow.UseVisualStyleBackColor = true;
            // 
            // ckbActivate
            // 
            this.ckbActivate.AutoSize = true;
            this.ckbActivate.Checked = true;
            this.ckbActivate.CheckState = System.Windows.Forms.CheckState.Checked;
            this.ckbActivate.Location = new System.Drawing.Point(213, 99);
            this.ckbActivate.Name = "ckbActivate";
            this.ckbActivate.Size = new System.Drawing.Size(198, 17);
            this.ckbActivate.TabIndex = 8;
            this.ckbActivate.Text = "Activate SuperBackup when it starts";
            this.ckbActivate.UseVisualStyleBackColor = true;
            // 
            // notifyIcon1
            // 
            this.notifyIcon1.BalloonTipIcon = System.Windows.Forms.ToolTipIcon.Info;
            this.notifyIcon1.BalloonTipText = "SuperBackup Program\r\nBuilt by VịLH - On April 23th 2011 17h22m\r\nVersion 1.0.0.0";
            this.notifyIcon1.BalloonTipTitle = "SuperBackup";
            this.notifyIcon1.ContextMenuStrip = this.contextMenuStrip1;
            this.notifyIcon1.Text = "notifyIcon1";
            this.notifyIcon1.Visible = true;
            // 
            // contextMenuStrip1
            // 
            this.contextMenuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.openToolStripMenuItem,
            this.exitToolStripMenuItem});
            this.contextMenuStrip1.Name = "contextMenuStrip1";
            this.contextMenuStrip1.Size = new System.Drawing.Size(104, 48);
            // 
            // openToolStripMenuItem
            // 
            this.openToolStripMenuItem.Name = "openToolStripMenuItem";
            this.openToolStripMenuItem.Size = new System.Drawing.Size(103, 22);
            this.openToolStripMenuItem.Text = "Open";
            this.openToolStripMenuItem.Click += new System.EventHandler(this.openToolStripMenuItem_Click);
            // 
            // exitToolStripMenuItem
            // 
            this.exitToolStripMenuItem.Name = "exitToolStripMenuItem";
            this.exitToolStripMenuItem.Size = new System.Drawing.Size(103, 22);
            this.exitToolStripMenuItem.Text = "Exit";
            this.exitToolStripMenuItem.Click += new System.EventHandler(this.exitToolStripMenuItem_Click);
            // 
            // timer1
            // 
            this.timer1.Enabled = true;
            this.timer1.Interval = 500;
            this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
            // 
            // FormMain
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(625, 135);
            this.Controls.Add(this.ckbActivate);
            this.Controls.Add(this.ckbStartWithWindow);
            this.Controls.Add(this.btnBrowseBackup);
            this.Controls.Add(this.btnFileWatched);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.txtPlaceBackup);
            this.Controls.Add(this.txtFileWatched);
            this.MaximizeBox = false;
            this.Name = "FormMain";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "SuperBackup";
            this.Load += new System.EventHandler(this.FormMain_Load);
            this.Resize += new System.EventHandler(this.FormMain_Resize);
            this.contextMenuStrip1.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox txtFileWatched;
        private System.Windows.Forms.TextBox txtPlaceBackup;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Button btnFileWatched;
        private System.Windows.Forms.Button btnBrowseBackup;
        private System.Windows.Forms.CheckBox ckbStartWithWindow;
        private System.Windows.Forms.CheckBox ckbActivate;
        private System.Windows.Forms.NotifyIcon notifyIcon1;
        private System.Windows.Forms.ContextMenuStrip contextMenuStrip1;
        private System.Windows.Forms.ToolStripMenuItem openToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem exitToolStripMenuItem;
        private System.Windows.Forms.Timer timer1;
    }
}

