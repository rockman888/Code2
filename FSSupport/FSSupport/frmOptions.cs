using CommonLib;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace FSSupport
{
    public partial class frmOptions : Form
    {
        private static string _szFileItems;
        private static DataTable _dtData;

        public frmOptions()
        {
            InitializeComponent();
        }

        private void frmOptions_Load(object sender, EventArgs e)
        {
            List<string> lst = CommonLib.clsMain.ReadTextFile(Application.StartupPath + "\\settings.txt");
            if (lst == null)
            {
                MessageBox.Show("Cannot find settings.txt file!", "Info", MessageBoxButtons.OK, MessageBoxIcon.Question);
                return;
            }

            // có giá trị mới lưu vào
            if (lst[0] != null)
                _szFileItems = lst[0];

            LoadDataToListView();            
        }

        private void LoadDataToListView()
        {
            InitListView();

            _dtData = clsMain.ReadExcelFile(_szFileItems, "Opts");
            if (_dtData == null)
            {
                MessageBox.Show("Cannot find Opts Sheet!", "Info", MessageBoxButtons.OK, MessageBoxIcon.Information);
                return;
            }

            ListViewItem lvItem = null;
            string[] strTemp = new string[6];

            for (int i = 0; i < _dtData.Rows.Count; i++)
            {
                strTemp[0] = _dtData.Rows[i][0].ToString();
                strTemp[1] = _dtData.Rows[i][1].ToString();
                lvItem = new ListViewItem(strTemp);               

                lvOptions.Items.Add(lvItem);
                // lvOptions.Items[i].SubItems[0].ForeColor = Color.Blue;
            }
        }

        private void InitListView()
        {
            lvOptions.View = View.Details;
            lvOptions.GridLines = true;
            lvOptions.FullRowSelect = true;

            string[] strItemName = { "ID", "Name" };
            int[] iItemSize = { 100, 350 };

            // Add Column Header
            for (int i = 0; i < strItemName.Length; i++)
                lvOptions.Columns.Add(strItemName[i], iItemSize[i]);
        }

        private void txtSearch_TextChanged(object sender, EventArgs e)
        {
            lvOptions.Items.Clear();
            string[] strTemp = new string[6];

            for (int i = 0; i < _dtData.Rows.Count; i++)
            {
                if (_dtData.Rows[i]["ID"].ToString().Contains(txtSearch.Text) == true)
                {
                    ListViewItem lvItem = null;

                    strTemp[0] = _dtData.Rows[i][0].ToString();
                    strTemp[1] = _dtData.Rows[i][1].ToString();
                    lvItem = new ListViewItem(strTemp);

                    lvOptions.Items.Add(lvItem);
                }
            }

            for (int i = 0; i < _dtData.Rows.Count; i++)
            {
                if (_dtData.Rows[i]["Mô tả"].ToString().Contains(txtSearch.Text) == true)
                {
                    ListViewItem lvItem = null;

                    strTemp[0] = _dtData.Rows[i][0].ToString();
                    strTemp[1] = _dtData.Rows[i][1].ToString();
                    lvItem = new ListViewItem(strTemp);

                    lvOptions.Items.Add(lvItem);
                }
            }
        }
    }
}
