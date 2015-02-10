namespace PostAndGetResponseFromPHPServer
{
    partial class Form1
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
            this.btnPostData = new System.Windows.Forms.Button();
            this.txtUser = new System.Windows.Forms.TextBox();
            this.txtPass = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.btnExit = new System.Windows.Forms.Button();
            this.label3 = new System.Windows.Forms.Label();
            this.textBox3 = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // btnPostData
            // 
            this.btnPostData.Location = new System.Drawing.Point(172, 189);
            this.btnPostData.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.btnPostData.Name = "btnPostData";
            this.btnPostData.Size = new System.Drawing.Size(112, 32);
            this.btnPostData.TabIndex = 0;
            this.btnPostData.Text = "Post Data";
            this.btnPostData.UseVisualStyleBackColor = true;
            this.btnPostData.Click += new System.EventHandler(this.btnPostData_Click);
            // 
            // txtUser
            // 
            this.txtUser.Location = new System.Drawing.Point(86, 40);
            this.txtUser.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.txtUser.Name = "txtUser";
            this.txtUser.Size = new System.Drawing.Size(331, 26);
            this.txtUser.TabIndex = 1;
            // 
            // txtPass
            // 
            this.txtPass.Location = new System.Drawing.Point(86, 71);
            this.txtPass.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.txtPass.Name = "txtPass";
            this.txtPass.Size = new System.Drawing.Size(331, 26);
            this.txtPass.TabIndex = 2;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(33, 40);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(49, 18);
            this.label1.TabIndex = 3;
            this.label1.Text = "User:";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(33, 71);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(49, 18);
            this.label2.TabIndex = 4;
            this.label2.Text = "Pass:";
            // 
            // btnExit
            // 
            this.btnExit.Location = new System.Drawing.Point(302, 189);
            this.btnExit.Margin = new System.Windows.Forms.Padding(4);
            this.btnExit.Name = "btnExit";
            this.btnExit.Size = new System.Drawing.Size(112, 32);
            this.btnExit.TabIndex = 5;
            this.btnExit.Text = "Exit";
            this.btnExit.UseVisualStyleBackColor = true;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(33, 117);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(220, 18);
            this.label3.TabIndex = 6;
            this.label3.Text = "Response From PHP Server:";
            // 
            // textBox3
            // 
            this.textBox3.Location = new System.Drawing.Point(36, 143);
            this.textBox3.Name = "textBox3";
            this.textBox3.Size = new System.Drawing.Size(381, 26);
            this.textBox3.TabIndex = 7;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 18F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(447, 244);
            this.Controls.Add(this.textBox3);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.btnExit);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.txtPass);
            this.Controls.Add(this.txtUser);
            this.Controls.Add(this.btnPostData);
            this.Font = new System.Drawing.Font("Verdana", 11.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.MaximizeBox = false;
            this.MaximumSize = new System.Drawing.Size(463, 282);
            this.MinimizeBox = false;
            this.MinimumSize = new System.Drawing.Size(463, 282);
            this.Name = "Form1";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Post And Get Response From PHP Server";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btnPostData;
        private System.Windows.Forms.TextBox txtUser;
        private System.Windows.Forms.TextBox txtPass;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Button btnExit;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox textBox3;
    }
}

