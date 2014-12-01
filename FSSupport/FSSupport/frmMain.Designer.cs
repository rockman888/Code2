namespace FSSupport
{
    partial class FrmGenItems
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FrmGenItems));
            this.cmbItemName = new System.Windows.Forms.ComboBox();
            this.label1 = new System.Windows.Forms.Label();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.rbCustom = new System.Windows.Forms.RadioButton();
            this.rbShowAll = new System.Windows.Forms.RadioButton();
            this.rbRing = new System.Windows.Forms.RadioButton();
            this.rbHorse = new System.Windows.Forms.RadioButton();
            this.rbHelm = new System.Windows.Forms.RadioButton();
            this.rbPendant = new System.Windows.Forms.RadioButton();
            this.rbBoot = new System.Windows.Forms.RadioButton();
            this.rbBelt = new System.Windows.Forms.RadioButton();
            this.rbCuff = new System.Windows.Forms.RadioButton();
            this.rbArmor = new System.Windows.Forms.RadioButton();
            this.rbAmulet = new System.Windows.Forms.RadioButton();
            this.rbIBItem = new System.Windows.Forms.RadioButton();
            this.rbMagicScript = new System.Windows.Forms.RadioButton();
            this.rbMaterial = new System.Windows.Forms.RadioButton();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.txtCount = new System.Windows.Forms.TextBox();
            this.txtRate = new System.Windows.Forms.TextBox();
            this.lvTable = new System.Windows.Forms.ListView();
            this.nType = new System.Windows.Forms.Label();
            this.txtType = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.txtItemName = new System.Windows.Forms.TextBox();
            this.txtItemID = new System.Windows.Forms.TextBox();
            this.txtItemIDLog = new System.Windows.Forms.TextBox();
            this.cmbColor = new System.Windows.Forms.ComboBox();
            this.imglst = new System.Windows.Forms.ImageList(this.components);
            this.statusStrip1 = new System.Windows.Forms.StatusStrip();
            this.toolStripProgressBar = new System.Windows.Forms.ToolStripProgressBar();
            this.toolStripStatusLabelProcessbar = new System.Windows.Forms.ToolStripStatusLabel();
            this.toolStripStatusLabelLoadingText = new System.Windows.Forms.ToolStripStatusLabel();
            this.toolStripStatusLabel = new System.Windows.Forms.ToolStripStatusLabel();
            this.label4 = new System.Windows.Forms.Label();
            this.txtImage = new System.Windows.Forms.TextBox();
            this.txtItemID1 = new System.Windows.Forms.TextBox();
            this.txtItemID2 = new System.Windows.Forms.TextBox();
            this.txtItemID3 = new System.Windows.Forms.TextBox();
            this.txtItemID4 = new System.Windows.Forms.TextBox();
            this.txtItemID5 = new System.Windows.Forms.TextBox();
            this.txtItemID6 = new System.Windows.Forms.TextBox();
            this.btnEquipOptions = new System.Windows.Forms.Button();
            this.imgItem = new System.Windows.Forms.PictureBox();
            this.btnAddCustom = new System.Windows.Forms.Button();
            this.btnUpdate = new System.Windows.Forms.Button();
            this.btnPath = new System.Windows.Forms.Button();
            this.btnClose = new System.Windows.Forms.Button();
            this.btnOutput = new System.Windows.Forms.Button();
            this.btnDelete = new System.Windows.Forms.Button();
            this.btnInsert = new System.Windows.Forms.Button();
            this.groupBox1.SuspendLayout();
            this.statusStrip1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.imgItem)).BeginInit();
            this.SuspendLayout();
            // 
            // cmbItemName
            // 
            this.cmbItemName.AutoCompleteMode = System.Windows.Forms.AutoCompleteMode.Suggest;
            this.cmbItemName.AutoCompleteSource = System.Windows.Forms.AutoCompleteSource.ListItems;
            this.cmbItemName.FormattingEnabled = true;
            this.cmbItemName.Location = new System.Drawing.Point(107, 21);
            this.cmbItemName.Margin = new System.Windows.Forms.Padding(4);
            this.cmbItemName.Name = "cmbItemName";
            this.cmbItemName.Size = new System.Drawing.Size(366, 24);
            this.cmbItemName.TabIndex = 1;
            this.cmbItemName.SelectedIndexChanged += new System.EventHandler(this.cbItemName_SelectedIndexChanged);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(64)))), ((int)(((byte)(0)))));
            this.label1.Location = new System.Drawing.Point(9, 25);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(94, 16);
            this.label1.TabIndex = 1;
            this.label1.Text = "szItemName:";
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.rbCustom);
            this.groupBox1.Controls.Add(this.rbShowAll);
            this.groupBox1.Controls.Add(this.rbRing);
            this.groupBox1.Controls.Add(this.rbHorse);
            this.groupBox1.Controls.Add(this.rbHelm);
            this.groupBox1.Controls.Add(this.rbPendant);
            this.groupBox1.Controls.Add(this.rbBoot);
            this.groupBox1.Controls.Add(this.rbBelt);
            this.groupBox1.Controls.Add(this.rbCuff);
            this.groupBox1.Controls.Add(this.rbArmor);
            this.groupBox1.Controls.Add(this.rbAmulet);
            this.groupBox1.Controls.Add(this.rbIBItem);
            this.groupBox1.Controls.Add(this.rbMagicScript);
            this.groupBox1.Controls.Add(this.rbMaterial);
            this.groupBox1.ForeColor = System.Drawing.Color.Black;
            this.groupBox1.Location = new System.Drawing.Point(12, 78);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(462, 189);
            this.groupBox1.TabIndex = 0;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Item";
            // 
            // rbCustom
            // 
            this.rbCustom.AutoSize = true;
            this.rbCustom.Location = new System.Drawing.Point(19, 156);
            this.rbCustom.Name = "rbCustom";
            this.rbCustom.Size = new System.Drawing.Size(93, 20);
            this.rbCustom.TabIndex = 14;
            this.rbCustom.TabStop = true;
            this.rbCustom.Text = "Customize";
            this.rbCustom.UseVisualStyleBackColor = true;
            this.rbCustom.CheckedChanged += new System.EventHandler(this.rbCustom_CheckedChanged);
            // 
            // rbShowAll
            // 
            this.rbShowAll.AutoSize = true;
            this.rbShowAll.Checked = true;
            this.rbShowAll.Location = new System.Drawing.Point(19, 22);
            this.rbShowAll.Name = "rbShowAll";
            this.rbShowAll.Size = new System.Drawing.Size(309, 20);
            this.rbShowAll.TabIndex = 13;
            this.rbShowAll.TabStop = true;
            this.rbShowAll.Text = "Show All (Material + MagicScript + IbItem)";
            this.rbShowAll.UseVisualStyleBackColor = true;
            this.rbShowAll.CheckedChanged += new System.EventHandler(this.rbShowAll_CheckedChanged);
            // 
            // rbRing
            // 
            this.rbRing.AutoSize = true;
            this.rbRing.Location = new System.Drawing.Point(181, 49);
            this.rbRing.Name = "rbRing";
            this.rbRing.Size = new System.Drawing.Size(111, 20);
            this.rbRing.TabIndex = 4;
            this.rbRing.Text = "Ring (0,3,...)";
            this.rbRing.UseVisualStyleBackColor = true;
            this.rbRing.CheckedChanged += new System.EventHandler(this.rbRing_CheckedChanged);
            // 
            // rbHorse
            // 
            this.rbHorse.AutoSize = true;
            this.rbHorse.Location = new System.Drawing.Point(320, 130);
            this.rbHorse.Name = "rbHorse";
            this.rbHorse.Size = new System.Drawing.Size(129, 20);
            this.rbHorse.TabIndex = 11;
            this.rbHorse.Text = "Horse (0,10,...)";
            this.rbHorse.UseVisualStyleBackColor = true;
            this.rbHorse.CheckedChanged += new System.EventHandler(this.rbHorse_CheckedChanged);
            // 
            // rbHelm
            // 
            this.rbHelm.AutoSize = true;
            this.rbHelm.Location = new System.Drawing.Point(320, 49);
            this.rbHelm.Name = "rbHelm";
            this.rbHelm.Size = new System.Drawing.Size(115, 20);
            this.rbHelm.TabIndex = 8;
            this.rbHelm.Text = "Helm (0,7,...)";
            this.rbHelm.UseVisualStyleBackColor = true;
            this.rbHelm.CheckedChanged += new System.EventHandler(this.rbHelm_CheckedChanged);
            // 
            // rbPendant
            // 
            this.rbPendant.AutoSize = true;
            this.rbPendant.Location = new System.Drawing.Point(320, 103);
            this.rbPendant.Name = "rbPendant";
            this.rbPendant.Size = new System.Drawing.Size(138, 20);
            this.rbPendant.TabIndex = 10;
            this.rbPendant.Text = "Pendant (0,9,...)";
            this.rbPendant.UseVisualStyleBackColor = true;
            this.rbPendant.CheckedChanged += new System.EventHandler(this.rbPendant_CheckedChanged);
            // 
            // rbBoot
            // 
            this.rbBoot.AutoSize = true;
            this.rbBoot.Location = new System.Drawing.Point(181, 103);
            this.rbBoot.Name = "rbBoot";
            this.rbBoot.Size = new System.Drawing.Size(114, 20);
            this.rbBoot.TabIndex = 6;
            this.rbBoot.Text = "Boot (0,5,...)";
            this.rbBoot.UseVisualStyleBackColor = true;
            this.rbBoot.CheckedChanged += new System.EventHandler(this.rbBoot_CheckedChanged);
            // 
            // rbBelt
            // 
            this.rbBelt.AutoSize = true;
            this.rbBelt.Location = new System.Drawing.Point(181, 130);
            this.rbBelt.Name = "rbBelt";
            this.rbBelt.Size = new System.Drawing.Size(109, 20);
            this.rbBelt.TabIndex = 7;
            this.rbBelt.Text = "Belt (0,6,...)";
            this.rbBelt.UseVisualStyleBackColor = true;
            this.rbBelt.CheckedChanged += new System.EventHandler(this.rbBelt_CheckedChanged);
            // 
            // rbCuff
            // 
            this.rbCuff.AutoSize = true;
            this.rbCuff.Location = new System.Drawing.Point(320, 76);
            this.rbCuff.Name = "rbCuff";
            this.rbCuff.Size = new System.Drawing.Size(111, 20);
            this.rbCuff.TabIndex = 9;
            this.rbCuff.Text = "Cuff (0,8,...)";
            this.rbCuff.UseVisualStyleBackColor = true;
            this.rbCuff.CheckedChanged += new System.EventHandler(this.rbCuff_CheckedChanged);
            // 
            // rbArmor
            // 
            this.rbArmor.AutoSize = true;
            this.rbArmor.Location = new System.Drawing.Point(19, 130);
            this.rbArmor.Name = "rbArmor";
            this.rbArmor.Size = new System.Drawing.Size(122, 20);
            this.rbArmor.TabIndex = 3;
            this.rbArmor.Text = "Armor (0,2,...)";
            this.rbArmor.UseVisualStyleBackColor = true;
            this.rbArmor.CheckedChanged += new System.EventHandler(this.rbArmor_CheckedChanged);
            // 
            // rbAmulet
            // 
            this.rbAmulet.AutoSize = true;
            this.rbAmulet.Location = new System.Drawing.Point(181, 76);
            this.rbAmulet.Name = "rbAmulet";
            this.rbAmulet.Size = new System.Drawing.Size(129, 20);
            this.rbAmulet.TabIndex = 5;
            this.rbAmulet.Text = "Amulet (0,4,...)";
            this.rbAmulet.UseVisualStyleBackColor = true;
            this.rbAmulet.CheckedChanged += new System.EventHandler(this.rbAmulet_CheckedChanged);
            // 
            // rbIBItem
            // 
            this.rbIBItem.AutoSize = true;
            this.rbIBItem.Location = new System.Drawing.Point(19, 103);
            this.rbIBItem.Name = "rbIBItem";
            this.rbIBItem.Size = new System.Drawing.Size(114, 20);
            this.rbIBItem.TabIndex = 2;
            this.rbIBItem.Text = "IbItem (8,...)";
            this.rbIBItem.UseVisualStyleBackColor = true;
            this.rbIBItem.CheckedChanged += new System.EventHandler(this.rbIBItem_CheckedChanged);
            // 
            // rbMagicScript
            // 
            this.rbMagicScript.AutoSize = true;
            this.rbMagicScript.Location = new System.Drawing.Point(19, 76);
            this.rbMagicScript.Name = "rbMagicScript";
            this.rbMagicScript.Size = new System.Drawing.Size(161, 20);
            this.rbMagicScript.TabIndex = 1;
            this.rbMagicScript.Text = "MagicScript (6,1,...)";
            this.rbMagicScript.UseVisualStyleBackColor = true;
            this.rbMagicScript.CheckedChanged += new System.EventHandler(this.rbMagicScript_CheckedChanged);
            // 
            // rbMaterial
            // 
            this.rbMaterial.AutoSize = true;
            this.rbMaterial.Location = new System.Drawing.Point(19, 49);
            this.rbMaterial.Name = "rbMaterial";
            this.rbMaterial.Size = new System.Drawing.Size(128, 20);
            this.rbMaterial.TabIndex = 0;
            this.rbMaterial.Text = "Material (3, ...)";
            this.rbMaterial.UseVisualStyleBackColor = true;
            this.rbMaterial.CheckedChanged += new System.EventHandler(this.rbMaterial_CheckedChanged);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(64)))), ((int)(((byte)(0)))));
            this.label2.Location = new System.Drawing.Point(508, 138);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(61, 16);
            this.label2.TabIndex = 12;
            this.label2.Text = "nCount:";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(64)))), ((int)(((byte)(0)))));
            this.label3.Location = new System.Drawing.Point(517, 81);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(52, 16);
            this.label3.TabIndex = 13;
            this.label3.Text = "nRate:";
            // 
            // txtCount
            // 
            this.txtCount.Font = new System.Drawing.Font("Verdana", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtCount.ForeColor = System.Drawing.Color.Blue;
            this.txtCount.Location = new System.Drawing.Point(570, 135);
            this.txtCount.Name = "txtCount";
            this.txtCount.Size = new System.Drawing.Size(233, 23);
            this.txtCount.TabIndex = 13;
            // 
            // txtRate
            // 
            this.txtRate.Font = new System.Drawing.Font("Verdana", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtRate.ForeColor = System.Drawing.Color.Blue;
            this.txtRate.Location = new System.Drawing.Point(570, 78);
            this.txtRate.Name = "txtRate";
            this.txtRate.Size = new System.Drawing.Size(233, 23);
            this.txtRate.TabIndex = 11;
            // 
            // lvTable
            // 
            this.lvTable.AutoArrange = false;
            this.lvTable.FullRowSelect = true;
            this.lvTable.Location = new System.Drawing.Point(12, 303);
            this.lvTable.MultiSelect = false;
            this.lvTable.Name = "lvTable";
            this.lvTable.Size = new System.Drawing.Size(956, 356);
            this.lvTable.TabIndex = 20;
            this.lvTable.UseCompatibleStateImageBehavior = false;
            this.lvTable.View = System.Windows.Forms.View.Details;
            this.lvTable.SelectedIndexChanged += new System.EventHandler(this.lvTable_SelectedIndexChanged);
            this.lvTable.KeyDown += new System.Windows.Forms.KeyEventHandler(this.lvTable_KeyDown);
            // 
            // nType
            // 
            this.nType.AutoSize = true;
            this.nType.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(64)))), ((int)(((byte)(0)))));
            this.nType.Location = new System.Drawing.Point(514, 110);
            this.nType.Name = "nType";
            this.nType.Size = new System.Drawing.Size(55, 16);
            this.nType.TabIndex = 18;
            this.nType.Text = "nType:";
            // 
            // txtType
            // 
            this.txtType.Location = new System.Drawing.Point(570, 106);
            this.txtType.Name = "txtType";
            this.txtType.Size = new System.Drawing.Size(233, 23);
            this.txtType.TabIndex = 12;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(64)))), ((int)(((byte)(0)))));
            this.label5.Location = new System.Drawing.Point(506, 26);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(66, 16);
            this.label5.TabIndex = 24;
            this.label5.Text = "nItemID:";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(64)))), ((int)(((byte)(0)))));
            this.label6.Location = new System.Drawing.Point(474, 166);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(95, 16);
            this.label6.TabIndex = 25;
            this.label6.Text = "szItemIDLog:";
            // 
            // txtItemName
            // 
            this.txtItemName.Font = new System.Drawing.Font("Verdana", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtItemName.ForeColor = System.Drawing.Color.Red;
            this.txtItemName.Location = new System.Drawing.Point(107, 50);
            this.txtItemName.Name = "txtItemName";
            this.txtItemName.Size = new System.Drawing.Size(291, 23);
            this.txtItemName.TabIndex = 2;
            // 
            // txtItemID
            // 
            this.txtItemID.Location = new System.Drawing.Point(570, 22);
            this.txtItemID.Name = "txtItemID";
            this.txtItemID.Size = new System.Drawing.Size(398, 23);
            this.txtItemID.TabIndex = 4;
            // 
            // txtItemIDLog
            // 
            this.txtItemIDLog.Font = new System.Drawing.Font("Verdana", 9.75F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtItemIDLog.ForeColor = System.Drawing.Color.Red;
            this.txtItemIDLog.Location = new System.Drawing.Point(570, 163);
            this.txtItemIDLog.Name = "txtItemIDLog";
            this.txtItemIDLog.Size = new System.Drawing.Size(233, 23);
            this.txtItemIDLog.TabIndex = 14;
            this.txtItemIDLog.TextChanged += new System.EventHandler(this.txtItemIDLog_TextChanged);
            // 
            // cmbColor
            // 
            this.cmbColor.FormattingEnabled = true;
            this.cmbColor.Location = new System.Drawing.Point(404, 49);
            this.cmbColor.Name = "cmbColor";
            this.cmbColor.Size = new System.Drawing.Size(69, 24);
            this.cmbColor.TabIndex = 3;
            // 
            // imglst
            // 
            this.imglst.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imglst.ImageStream")));
            this.imglst.TransparentColor = System.Drawing.Color.Transparent;
            this.imglst.Images.SetKeyName(0, "yellow.png");
            this.imglst.Images.SetKeyName(1, "green.png");
            // 
            // statusStrip1
            // 
            this.statusStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.toolStripProgressBar,
            this.toolStripStatusLabelProcessbar,
            this.toolStripStatusLabelLoadingText,
            this.toolStripStatusLabel});
            this.statusStrip1.Location = new System.Drawing.Point(0, 667);
            this.statusStrip1.Name = "statusStrip1";
            this.statusStrip1.Size = new System.Drawing.Size(977, 22);
            this.statusStrip1.TabIndex = 27;
            this.statusStrip1.Text = "statusStrip1";
            // 
            // toolStripProgressBar
            // 
            this.toolStripProgressBar.Name = "toolStripProgressBar";
            this.toolStripProgressBar.Size = new System.Drawing.Size(100, 16);
            // 
            // toolStripStatusLabelProcessbar
            // 
            this.toolStripStatusLabelProcessbar.ForeColor = System.Drawing.Color.Blue;
            this.toolStripStatusLabelProcessbar.Name = "toolStripStatusLabelProcessbar";
            this.toolStripStatusLabelProcessbar.Size = new System.Drawing.Size(23, 17);
            this.toolStripStatusLabelProcessbar.Text = "0%";
            // 
            // toolStripStatusLabelLoadingText
            // 
            this.toolStripStatusLabelLoadingText.ForeColor = System.Drawing.Color.Blue;
            this.toolStripStatusLabelLoadingText.Image = global::FSSupport.Properties.Resources.output;
            this.toolStripStatusLabelLoadingText.Name = "toolStripStatusLabelLoadingText";
            this.toolStripStatusLabelLoadingText.Size = new System.Drawing.Size(52, 17);
            this.toolStripStatusLabelLoadingText.Text = "Items";
            // 
            // toolStripStatusLabel
            // 
            this.toolStripStatusLabel.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.toolStripStatusLabel.Enabled = false;
            this.toolStripStatusLabel.Name = "toolStripStatusLabel";
            this.toolStripStatusLabel.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
            this.toolStripStatusLabel.Size = new System.Drawing.Size(785, 17);
            this.toolStripStatusLabel.Spring = true;
            this.toolStripStatusLabel.Text = "Status";
            this.toolStripStatusLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(192)))), ((int)(((byte)(64)))), ((int)(((byte)(0)))));
            this.label4.Location = new System.Drawing.Point(501, 194);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(68, 16);
            this.label4.TabIndex = 28;
            this.label4.Text = "szImage:";
            // 
            // txtImage
            // 
            this.txtImage.Location = new System.Drawing.Point(570, 191);
            this.txtImage.Name = "txtImage";
            this.txtImage.Size = new System.Drawing.Size(398, 23);
            this.txtImage.TabIndex = 15;
            // 
            // txtItemID1
            // 
            this.txtItemID1.Font = new System.Drawing.Font("Verdana", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtItemID1.Location = new System.Drawing.Point(570, 49);
            this.txtItemID1.Name = "txtItemID1";
            this.txtItemID1.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
            this.txtItemID1.Size = new System.Drawing.Size(66, 23);
            this.txtItemID1.TabIndex = 5;
            this.txtItemID1.TextChanged += new System.EventHandler(this.txtItemID1_TextChanged);
            // 
            // txtItemID2
            // 
            this.txtItemID2.Font = new System.Drawing.Font("Verdana", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtItemID2.Location = new System.Drawing.Point(636, 49);
            this.txtItemID2.Name = "txtItemID2";
            this.txtItemID2.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
            this.txtItemID2.Size = new System.Drawing.Size(66, 23);
            this.txtItemID2.TabIndex = 6;
            this.txtItemID2.TextChanged += new System.EventHandler(this.txtItemID2_TextChanged);
            // 
            // txtItemID3
            // 
            this.txtItemID3.Font = new System.Drawing.Font("Verdana", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtItemID3.Location = new System.Drawing.Point(703, 49);
            this.txtItemID3.Name = "txtItemID3";
            this.txtItemID3.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
            this.txtItemID3.Size = new System.Drawing.Size(66, 23);
            this.txtItemID3.TabIndex = 7;
            // 
            // txtItemID4
            // 
            this.txtItemID4.Font = new System.Drawing.Font("Verdana", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtItemID4.Location = new System.Drawing.Point(769, 49);
            this.txtItemID4.Name = "txtItemID4";
            this.txtItemID4.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
            this.txtItemID4.Size = new System.Drawing.Size(66, 23);
            this.txtItemID4.TabIndex = 8;
            // 
            // txtItemID5
            // 
            this.txtItemID5.Font = new System.Drawing.Font("Verdana", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtItemID5.Location = new System.Drawing.Point(835, 49);
            this.txtItemID5.Name = "txtItemID5";
            this.txtItemID5.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
            this.txtItemID5.Size = new System.Drawing.Size(66, 23);
            this.txtItemID5.TabIndex = 9;
            // 
            // txtItemID6
            // 
            this.txtItemID6.Font = new System.Drawing.Font("Verdana", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtItemID6.Location = new System.Drawing.Point(902, 49);
            this.txtItemID6.Name = "txtItemID6";
            this.txtItemID6.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
            this.txtItemID6.Size = new System.Drawing.Size(66, 23);
            this.txtItemID6.TabIndex = 10;
            // 
            // btnEquipOptions
            // 
            this.btnEquipOptions.Image = global::FSSupport.Properties.Resources.Tool2;
            this.btnEquipOptions.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnEquipOptions.Location = new System.Drawing.Point(546, 224);
            this.btnEquipOptions.Name = "btnEquipOptions";
            this.btnEquipOptions.Size = new System.Drawing.Size(82, 73);
            this.btnEquipOptions.TabIndex = 35;
            this.btnEquipOptions.Text = "Equip Options";
            this.btnEquipOptions.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.btnEquipOptions.UseVisualStyleBackColor = true;
            this.btnEquipOptions.Click += new System.EventHandler(this.btnEquipOptions_Click);
            // 
            // imgItem
            // 
            this.imgItem.Location = new System.Drawing.Point(828, 105);
            this.imgItem.Name = "imgItem";
            this.imgItem.Size = new System.Drawing.Size(75, 74);
            this.imgItem.TabIndex = 34;
            this.imgItem.TabStop = false;
            // 
            // btnAddCustom
            // 
            this.btnAddCustom.Image = global::FSSupport.Properties.Resources.add;
            this.btnAddCustom.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnAddCustom.Location = new System.Drawing.Point(632, 224);
            this.btnAddCustom.Name = "btnAddCustom";
            this.btnAddCustom.Size = new System.Drawing.Size(83, 34);
            this.btnAddCustom.TabIndex = 33;
            this.btnAddCustom.Text = "AddCus";
            this.btnAddCustom.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.btnAddCustom.UseVisualStyleBackColor = true;
            this.btnAddCustom.Click += new System.EventHandler(this.btnAddCustom_Click);
            // 
            // btnUpdate
            // 
            this.btnUpdate.ForeColor = System.Drawing.Color.Blue;
            this.btnUpdate.Image = global::FSSupport.Properties.Resources.edit;
            this.btnUpdate.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnUpdate.Location = new System.Drawing.Point(718, 263);
            this.btnUpdate.Name = "btnUpdate";
            this.btnUpdate.Size = new System.Drawing.Size(83, 34);
            this.btnUpdate.TabIndex = 32;
            this.btnUpdate.Text = "Update";
            this.btnUpdate.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.btnUpdate.UseVisualStyleBackColor = true;
            this.btnUpdate.Click += new System.EventHandler(this.btnUpdate_Click);
            // 
            // btnPath
            // 
            this.btnPath.Image = global::FSSupport.Properties.Resources.images;
            this.btnPath.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnPath.Location = new System.Drawing.Point(718, 224);
            this.btnPath.Name = "btnPath";
            this.btnPath.Size = new System.Drawing.Size(83, 34);
            this.btnPath.TabIndex = 31;
            this.btnPath.Text = "Path";
            this.btnPath.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.btnPath.UseVisualStyleBackColor = true;
            this.btnPath.Click += new System.EventHandler(this.btnPath_Click);
            // 
            // btnClose
            // 
            this.btnClose.Image = global::FSSupport.Properties.Resources.Close;
            this.btnClose.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnClose.Location = new System.Drawing.Point(890, 224);
            this.btnClose.Name = "btnClose";
            this.btnClose.Size = new System.Drawing.Size(78, 73);
            this.btnClose.TabIndex = 19;
            this.btnClose.Text = "Close";
            this.btnClose.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.btnClose.UseVisualStyleBackColor = true;
            this.btnClose.Click += new System.EventHandler(this.btnClose_Click);
            // 
            // btnOutput
            // 
            this.btnOutput.Image = global::FSSupport.Properties.Resources.output;
            this.btnOutput.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnOutput.Location = new System.Drawing.Point(804, 224);
            this.btnOutput.Name = "btnOutput";
            this.btnOutput.Size = new System.Drawing.Size(83, 34);
            this.btnOutput.TabIndex = 18;
            this.btnOutput.Text = "Output";
            this.btnOutput.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.btnOutput.UseVisualStyleBackColor = true;
            this.btnOutput.Click += new System.EventHandler(this.btnOutput_Click);
            // 
            // btnDelete
            // 
            this.btnDelete.ForeColor = System.Drawing.Color.Blue;
            this.btnDelete.Image = global::FSSupport.Properties.Resources.delete;
            this.btnDelete.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnDelete.Location = new System.Drawing.Point(803, 263);
            this.btnDelete.Name = "btnDelete";
            this.btnDelete.Size = new System.Drawing.Size(83, 34);
            this.btnDelete.TabIndex = 17;
            this.btnDelete.Text = "Delete";
            this.btnDelete.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.btnDelete.UseVisualStyleBackColor = true;
            this.btnDelete.Click += new System.EventHandler(this.btnDelete_Click);
            // 
            // btnInsert
            // 
            this.btnInsert.ForeColor = System.Drawing.Color.Blue;
            this.btnInsert.Image = global::FSSupport.Properties.Resources.insert;
            this.btnInsert.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.btnInsert.Location = new System.Drawing.Point(632, 263);
            this.btnInsert.Name = "btnInsert";
            this.btnInsert.Size = new System.Drawing.Size(83, 34);
            this.btnInsert.TabIndex = 16;
            this.btnInsert.Text = "Insert";
            this.btnInsert.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.btnInsert.UseVisualStyleBackColor = true;
            this.btnInsert.Click += new System.EventHandler(this.btnInsert_Click);
            // 
            // FrmGenItems
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(977, 689);
            this.Controls.Add(this.btnEquipOptions);
            this.Controls.Add(this.imgItem);
            this.Controls.Add(this.btnAddCustom);
            this.Controls.Add(this.btnUpdate);
            this.Controls.Add(this.btnPath);
            this.Controls.Add(this.txtItemID6);
            this.Controls.Add(this.txtItemID5);
            this.Controls.Add(this.txtItemID4);
            this.Controls.Add(this.txtItemID3);
            this.Controls.Add(this.txtItemID2);
            this.Controls.Add(this.txtItemID1);
            this.Controls.Add(this.txtImage);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.statusStrip1);
            this.Controls.Add(this.cmbColor);
            this.Controls.Add(this.txtItemIDLog);
            this.Controls.Add(this.txtItemID);
            this.Controls.Add(this.txtItemName);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.btnClose);
            this.Controls.Add(this.btnOutput);
            this.Controls.Add(this.btnDelete);
            this.Controls.Add(this.btnInsert);
            this.Controls.Add(this.txtType);
            this.Controls.Add(this.nType);
            this.Controls.Add(this.lvTable);
            this.Controls.Add(this.txtRate);
            this.Controls.Add(this.txtCount);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.cmbItemName);
            this.Font = new System.Drawing.Font("Verdana", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Margin = new System.Windows.Forms.Padding(4);
            this.MaximizeBox = false;
            this.MaximumSize = new System.Drawing.Size(993, 727);
            this.MinimumSize = new System.Drawing.Size(993, 727);
            this.Name = "FrmGenItems";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "FS - Table - huuvi168@gmail.com - v2.3";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.FrmGenItems_FormClosing);
            this.Load += new System.EventHandler(this.Form1_Load);
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.statusStrip1.ResumeLayout(false);
            this.statusStrip1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.imgItem)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ComboBox cmbItemName;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox txtCount;
        private System.Windows.Forms.TextBox txtRate;
        private System.Windows.Forms.ListView lvTable;
        private System.Windows.Forms.Label nType;
        private System.Windows.Forms.TextBox txtType;
        private System.Windows.Forms.Button btnInsert;
        private System.Windows.Forms.Button btnDelete;
        private System.Windows.Forms.Button btnOutput;
        private System.Windows.Forms.Button btnClose;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.TextBox txtItemName;
        private System.Windows.Forms.TextBox txtItemID;
        private System.Windows.Forms.TextBox txtItemIDLog;
        private System.Windows.Forms.RadioButton rbAmulet;
        private System.Windows.Forms.RadioButton rbIBItem;
        private System.Windows.Forms.RadioButton rbMagicScript;
        private System.Windows.Forms.RadioButton rbMaterial;
        private System.Windows.Forms.RadioButton rbHorse;
        private System.Windows.Forms.RadioButton rbHelm;
        private System.Windows.Forms.RadioButton rbPendant;
        private System.Windows.Forms.RadioButton rbCuff;
        private System.Windows.Forms.RadioButton rbBoot;
        private System.Windows.Forms.RadioButton rbBelt;
        private System.Windows.Forms.RadioButton rbArmor;
        private System.Windows.Forms.RadioButton rbRing;
        private System.Windows.Forms.ComboBox cmbColor;
        private System.Windows.Forms.ImageList imglst;
        private System.Windows.Forms.RadioButton rbShowAll;
        private System.Windows.Forms.StatusStrip statusStrip1;
        private System.Windows.Forms.ToolStripProgressBar toolStripProgressBar;
        private System.Windows.Forms.ToolStripStatusLabel toolStripStatusLabelProcessbar;
        private System.Windows.Forms.ToolStripStatusLabel toolStripStatusLabelLoadingText;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox txtImage;
        private System.Windows.Forms.TextBox txtItemID1;
        private System.Windows.Forms.TextBox txtItemID2;
        private System.Windows.Forms.TextBox txtItemID3;
        private System.Windows.Forms.TextBox txtItemID4;
        private System.Windows.Forms.TextBox txtItemID5;
        private System.Windows.Forms.TextBox txtItemID6;
        private System.Windows.Forms.Button btnPath;
        private System.Windows.Forms.Button btnUpdate;
        private System.Windows.Forms.ToolStripStatusLabel toolStripStatusLabel;
        private System.Windows.Forms.Button btnAddCustom;
        private System.Windows.Forms.RadioButton rbCustom;
        private System.Windows.Forms.PictureBox imgItem;
        private System.Windows.Forms.Button btnEquipOptions;
    }
}

