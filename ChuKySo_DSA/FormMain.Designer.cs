namespace UngDung_ChuKySo_DSA
{
    partial class FormMain
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
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
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            panelMenu = new Panel();
            btnMenuXacMinh = new Button();
            btnMenuKyFile = new Button();
            btnMenuTaoKhoa = new Button();
            tabControlMain = new TabControl();
            tabTaoKhoa = new TabPage();
            label7 = new Label();
            label6 = new Label();
            label5 = new Label();
            btnXoaKetQua = new Button();
            btnLuuPrivateKey = new Button();
            btnLuuPublicKey = new Button();
            txtPrivateKey = new TextBox();
            txtPublicKey = new TextBox();
            btnTaoKhoaNgauNhien = new Button();
            cbKeySize = new ComboBox();
            tabKyFile = new TabPage();
            btnLuuChuKy = new Button();
            label4 = new Label();
            label3 = new Label();
            label2 = new Label();
            label1 = new Label();
            btnThucHienKy = new Button();
            txtKetQuaChuKy = new TextBox();
            btnChonNoiLuu = new Button();
            btnChonPrivateKey = new Button();
            btnChonFileCanKy = new Button();
            txtFileLuuChuKy = new TextBox();
            txtPrivateKeyPath = new TextBox();
            txtFileCanKy = new TextBox();
            tabXacMinh = new TabPage();
            label10 = new Label();
            label9 = new Label();
            label8 = new Label();
            btnThucHienXacMinh = new Button();
            lblKetQuaXacMinh = new Label();
            btnChonPublicKey = new Button();
            btnChonFileChuKy = new Button();
            btnChonFileGoc = new Button();
            txtPublicKeyPath = new TextBox();
            txtFileChuKy = new TextBox();
            txtFileGoc = new TextBox();
            panelMenu.SuspendLayout();
            tabControlMain.SuspendLayout();
            tabTaoKhoa.SuspendLayout();
            tabKyFile.SuspendLayout();
            tabXacMinh.SuspendLayout();
            SuspendLayout();
            // 
            // panelMenu
            // 
            panelMenu.Controls.Add(btnMenuXacMinh);
            panelMenu.Controls.Add(btnMenuKyFile);
            panelMenu.Controls.Add(btnMenuTaoKhoa);
            panelMenu.Dock = DockStyle.Left;
            panelMenu.Location = new Point(0, 0);
            panelMenu.Name = "panelMenu";
            panelMenu.Size = new Size(250, 530);
            panelMenu.TabIndex = 0;
            // 
            // btnMenuXacMinh
            // 
            btnMenuXacMinh.ForeColor = SystemColors.ControlText;
            btnMenuXacMinh.Location = new Point(35, 333);
            btnMenuXacMinh.Name = "btnMenuXacMinh";
            btnMenuXacMinh.Size = new Size(171, 71);
            btnMenuXacMinh.TabIndex = 2;
            btnMenuXacMinh.Text = "Xác minh";
            btnMenuXacMinh.UseVisualStyleBackColor = true;
            btnMenuXacMinh.Click += btnMenuXacMinh_Click;
            // 
            // btnMenuKyFile
            // 
            btnMenuKyFile.ForeColor = SystemColors.ControlText;
            btnMenuKyFile.Location = new Point(35, 190);
            btnMenuKyFile.Name = "btnMenuKyFile";
            btnMenuKyFile.Size = new Size(171, 80);
            btnMenuKyFile.TabIndex = 1;
            btnMenuKyFile.Text = "Ký file";
            btnMenuKyFile.UseVisualStyleBackColor = true;
            btnMenuKyFile.Click += btnMenuKyFile_Click;
            // 
            // btnMenuTaoKhoa
            // 
            btnMenuTaoKhoa.ForeColor = SystemColors.ControlText;
            btnMenuTaoKhoa.Location = new Point(35, 57);
            btnMenuTaoKhoa.Name = "btnMenuTaoKhoa";
            btnMenuTaoKhoa.Size = new Size(171, 79);
            btnMenuTaoKhoa.TabIndex = 0;
            btnMenuTaoKhoa.Text = "Tạo khóa";
            btnMenuTaoKhoa.UseVisualStyleBackColor = true;
            btnMenuTaoKhoa.Click += btnMenuTaoKhoa_Click;
            // 
            // tabControlMain
            // 
            tabControlMain.Appearance = TabAppearance.FlatButtons;
            tabControlMain.Controls.Add(tabTaoKhoa);
            tabControlMain.Controls.Add(tabKyFile);
            tabControlMain.Controls.Add(tabXacMinh);
            tabControlMain.Dock = DockStyle.Fill;
            tabControlMain.ItemSize = new Size(0, 1);
            tabControlMain.Location = new Point(250, 0);
            tabControlMain.Name = "tabControlMain";
            tabControlMain.SelectedIndex = 0;
            tabControlMain.Size = new Size(775, 530);
            tabControlMain.SizeMode = TabSizeMode.Fixed;
            tabControlMain.TabIndex = 1;
            // 
            // tabTaoKhoa
            // 
            tabTaoKhoa.BackColor = Color.White;
            tabTaoKhoa.Controls.Add(label7);
            tabTaoKhoa.Controls.Add(label6);
            tabTaoKhoa.Controls.Add(label5);
            tabTaoKhoa.Controls.Add(btnXoaKetQua);
            tabTaoKhoa.Controls.Add(btnLuuPrivateKey);
            tabTaoKhoa.Controls.Add(btnLuuPublicKey);
            tabTaoKhoa.Controls.Add(txtPrivateKey);
            tabTaoKhoa.Controls.Add(txtPublicKey);
            tabTaoKhoa.Controls.Add(btnTaoKhoaNgauNhien);
            tabTaoKhoa.Controls.Add(cbKeySize);
            tabTaoKhoa.Location = new Point(4, 5);
            tabTaoKhoa.Name = "tabTaoKhoa";
            tabTaoKhoa.Padding = new Padding(3);
            tabTaoKhoa.Size = new Size(767, 521);
            tabTaoKhoa.TabIndex = 0;
            tabTaoKhoa.Text = "tabTaoKhoa";
            // 
            // label7
            // 
            label7.AutoSize = true;
            label7.Location = new Point(29, 275);
            label7.Name = "label7";
            label7.Size = new Size(177, 20);
            label7.TabIndex = 9;
            label7.Text = "Khóa bí mật (Private Key)";
            label7.Click += label7_Click;
            // 
            // label6
            // 
            label6.AutoSize = true;
            label6.Location = new Point(29, 130);
            label6.Name = "label6";
            label6.Size = new Size(193, 20);
            label6.TabIndex = 8;
            label6.Text = "Khóa công khai (Public Key)";
            label6.Click += label6_Click;
            // 
            // label5
            // 
            label5.AutoSize = true;
            label5.Location = new Point(29, 15);
            label5.Name = "label5";
            label5.Size = new Size(106, 20);
            label5.TabIndex = 7;
            label5.Text = "Thông số khóa";
            // 
            // btnXoaKetQua
            // 
            btnXoaKetQua.BackColor = Color.IndianRed;
            btnXoaKetQua.ForeColor = Color.White;
            btnXoaKetQua.Location = new Point(546, 420);
            btnXoaKetQua.Name = "btnXoaKetQua";
            btnXoaKetQua.Size = new Size(124, 52);
            btnXoaKetQua.TabIndex = 6;
            btnXoaKetQua.Text = "❌ Xóa kết quả";
            btnXoaKetQua.UseVisualStyleBackColor = false;
            btnXoaKetQua.Click += btnXoaKetQua_Click;
            // 
            // btnLuuPrivateKey
            // 
            btnLuuPrivateKey.ForeColor = Color.Lime;
            btnLuuPrivateKey.Location = new Point(337, 420);
            btnLuuPrivateKey.Name = "btnLuuPrivateKey";
            btnLuuPrivateKey.Size = new Size(158, 52);
            btnLuuPrivateKey.TabIndex = 5;
            btnLuuPrivateKey.Text = "💾 Lưu khóa bí mật";
            btnLuuPrivateKey.UseVisualStyleBackColor = true;
            btnLuuPrivateKey.Click += btnLuuPrivateKey_Click;
            // 
            // btnLuuPublicKey
            // 
            btnLuuPublicKey.ForeColor = Color.DodgerBlue;
            btnLuuPublicKey.Location = new Point(96, 420);
            btnLuuPublicKey.Name = "btnLuuPublicKey";
            btnLuuPublicKey.Size = new Size(178, 52);
            btnLuuPublicKey.TabIndex = 4;
            btnLuuPublicKey.Text = "💾 Lưu khóa công khai";
            btnLuuPublicKey.UseVisualStyleBackColor = true;
            btnLuuPublicKey.Click += btnLuuPublicKey_Click;
            // 
            // txtPrivateKey
            // 
            txtPrivateKey.Location = new Point(26, 308);
            txtPrivateKey.Multiline = true;
            txtPrivateKey.Name = "txtPrivateKey";
            txtPrivateKey.ScrollBars = ScrollBars.Vertical;
            txtPrivateKey.Size = new Size(707, 85);
            txtPrivateKey.TabIndex = 3;
            txtPrivateKey.TextChanged += txtPrivateKey_TextChanged;
            // 
            // txtPublicKey
            // 
            txtPublicKey.Location = new Point(26, 161);
            txtPublicKey.Multiline = true;
            txtPublicKey.Name = "txtPublicKey";
            txtPublicKey.ScrollBars = ScrollBars.Vertical;
            txtPublicKey.Size = new Size(707, 90);
            txtPublicKey.TabIndex = 2;
            txtPublicKey.TextChanged += txtPublicKey_TextChanged;
            // 
            // btnTaoKhoaNgauNhien
            // 
            btnTaoKhoaNgauNhien.BackColor = Color.DodgerBlue;
            btnTaoKhoaNgauNhien.FlatStyle = FlatStyle.Flat;
            btnTaoKhoaNgauNhien.ForeColor = Color.White;
            btnTaoKhoaNgauNhien.Location = new Point(258, 79);
            btnTaoKhoaNgauNhien.Name = "btnTaoKhoaNgauNhien";
            btnTaoKhoaNgauNhien.Size = new Size(217, 32);
            btnTaoKhoaNgauNhien.TabIndex = 1;
            btnTaoKhoaNgauNhien.Text = "🔑 TẠO KHÓA NGẪU NHIÊN";
            btnTaoKhoaNgauNhien.UseVisualStyleBackColor = false;
            btnTaoKhoaNgauNhien.Click += btnTaoKhoaNgauNhien_Click;
            // 
            // cbKeySize
            // 
            cbKeySize.FormattingEnabled = true;
            cbKeySize.Items.AddRange(new object[] { "1024", "2048", "3072" });
            cbKeySize.Location = new Point(26, 41);
            cbKeySize.Name = "cbKeySize";
            cbKeySize.Size = new Size(707, 28);
            cbKeySize.TabIndex = 0;
            cbKeySize.SelectedIndexChanged += cbKeySize_SelectedIndexChanged;
            // 
            // tabKyFile
            // 
            tabKyFile.BackColor = Color.White;
            tabKyFile.Controls.Add(btnLuuChuKy);
            tabKyFile.Controls.Add(label4);
            tabKyFile.Controls.Add(label3);
            tabKyFile.Controls.Add(label2);
            tabKyFile.Controls.Add(label1);
            tabKyFile.Controls.Add(btnThucHienKy);
            tabKyFile.Controls.Add(txtKetQuaChuKy);
            tabKyFile.Controls.Add(btnChonNoiLuu);
            tabKyFile.Controls.Add(btnChonPrivateKey);
            tabKyFile.Controls.Add(btnChonFileCanKy);
            tabKyFile.Controls.Add(txtFileLuuChuKy);
            tabKyFile.Controls.Add(txtPrivateKeyPath);
            tabKyFile.Controls.Add(txtFileCanKy);
            tabKyFile.ForeColor = SystemColors.ControlText;
            tabKyFile.Location = new Point(4, 5);
            tabKyFile.Name = "tabKyFile";
            tabKyFile.Padding = new Padding(3);
            tabKyFile.Size = new Size(761, 521);
            tabKyFile.TabIndex = 1;
            tabKyFile.Text = "tabKyFile";
            tabKyFile.Click += tabKyFile_Click;
            // 
            // btnLuuChuKy
            // 
            btnLuuChuKy.BackColor = Color.ForestGreen;
            btnLuuChuKy.ForeColor = Color.White;
            btnLuuChuKy.Location = new Point(209, 460);
            btnLuuChuKy.Name = "btnLuuChuKy";
            btnLuuChuKy.Size = new Size(362, 53);
            btnLuuChuKy.TabIndex = 12;
            btnLuuChuKy.Text = "💾 LƯU CHỮ KÝ";
            btnLuuChuKy.UseVisualStyleBackColor = false;
            btnLuuChuKy.Click += btnLuuChuKy_Click;
            // 
            // label4
            // 
            label4.AutoSize = true;
            label4.Location = new Point(13, 388);
            label4.Name = "label4";
            label4.Size = new Size(135, 20);
            label4.TabIndex = 11;
            label4.Text = "Lưu chữ ký vào file:";
            label4.Click += label4_Click;
            // 
            // label3
            // 
            label3.AutoSize = true;
            label3.Location = new Point(13, 293);
            label3.Name = "label3";
            label3.Size = new Size(186, 20);
            label3.TabIndex = 10;
            label3.Text = "Kết quả chữ ký (Signature):";
            label3.Click += label3_Click;
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Location = new Point(13, 100);
            label2.Name = "label2";
            label2.Size = new Size(216, 20);
            label2.TabIndex = 9;
            label2.Text = "Chọn khóa bí mật (Private Key):";
            label2.Click += label2_Click;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Location = new Point(13, 31);
            label1.Name = "label1";
            label1.Size = new Size(116, 20);
            label1.TabIndex = 8;
            label1.Text = "Chọn file cần ký:";
            label1.Click += label1_Click_1;
            // 
            // btnThucHienKy
            // 
            btnThucHienKy.BackColor = Color.Orange;
            btnThucHienKy.FlatStyle = FlatStyle.Flat;
            btnThucHienKy.ForeColor = Color.White;
            btnThucHienKy.Location = new Point(209, 185);
            btnThucHienKy.Name = "btnThucHienKy";
            btnThucHienKy.Size = new Size(362, 66);
            btnThucHienKy.TabIndex = 7;
            btnThucHienKy.Text = "✒️ THỰC HIỆN KÝ";
            btnThucHienKy.UseVisualStyleBackColor = false;
            btnThucHienKy.Click += btnThucHienKy_Click;
            // 
            // txtKetQuaChuKy
            // 
            txtKetQuaChuKy.Location = new Point(13, 328);
            txtKetQuaChuKy.Name = "txtKetQuaChuKy";
            txtKetQuaChuKy.Size = new Size(558, 27);
            txtKetQuaChuKy.TabIndex = 6;
            txtKetQuaChuKy.TextChanged += txtKetQuaChuKy_TextChanged;
            // 
            // btnChonNoiLuu
            // 
            btnChonNoiLuu.Location = new Point(586, 425);
            btnChonNoiLuu.Name = "btnChonNoiLuu";
            btnChonNoiLuu.Size = new Size(153, 29);
            btnChonNoiLuu.TabIndex = 5;
            btnChonNoiLuu.Text = "Chọn nơi lưu";
            btnChonNoiLuu.UseVisualStyleBackColor = true;
            btnChonNoiLuu.Click += btnChonNoiLuu_Click;
            // 
            // btnChonPrivateKey
            // 
            btnChonPrivateKey.Location = new Point(586, 130);
            btnChonPrivateKey.Name = "btnChonPrivateKey";
            btnChonPrivateKey.Size = new Size(158, 29);
            btnChonPrivateKey.TabIndex = 4;
            btnChonPrivateKey.Text = "Chọn file";
            btnChonPrivateKey.UseVisualStyleBackColor = true;
            btnChonPrivateKey.Click += btnChonPrivateKey_Click;
            // 
            // btnChonFileCanKy
            // 
            btnChonFileCanKy.Location = new Point(586, 52);
            btnChonFileCanKy.Name = "btnChonFileCanKy";
            btnChonFileCanKy.Size = new Size(158, 29);
            btnChonFileCanKy.TabIndex = 3;
            btnChonFileCanKy.Text = "Chọn file";
            btnChonFileCanKy.UseVisualStyleBackColor = true;
            btnChonFileCanKy.Click += btnChonFileCanKy_Click;
            // 
            // txtFileLuuChuKy
            // 
            txtFileLuuChuKy.Location = new Point(13, 427);
            txtFileLuuChuKy.Name = "txtFileLuuChuKy";
            txtFileLuuChuKy.Size = new Size(558, 27);
            txtFileLuuChuKy.TabIndex = 2;
            txtFileLuuChuKy.TextChanged += textBox3_TextChanged;
            // 
            // txtPrivateKeyPath
            // 
            txtPrivateKeyPath.Location = new Point(13, 132);
            txtPrivateKeyPath.Name = "txtPrivateKeyPath";
            txtPrivateKeyPath.Size = new Size(558, 27);
            txtPrivateKeyPath.TabIndex = 1;
            txtPrivateKeyPath.TextChanged += txtPrivateKeyPath_TextChanged;
            // 
            // txtFileCanKy
            // 
            txtFileCanKy.Location = new Point(13, 54);
            txtFileCanKy.Name = "txtFileCanKy";
            txtFileCanKy.Size = new Size(558, 27);
            txtFileCanKy.TabIndex = 0;
            txtFileCanKy.TextChanged += txtFileCanKy_TextChanged;
            // 
            // tabXacMinh
            // 
            tabXacMinh.BackColor = Color.White;
            tabXacMinh.Controls.Add(label10);
            tabXacMinh.Controls.Add(label9);
            tabXacMinh.Controls.Add(label8);
            tabXacMinh.Controls.Add(btnThucHienXacMinh);
            tabXacMinh.Controls.Add(lblKetQuaXacMinh);
            tabXacMinh.Controls.Add(btnChonPublicKey);
            tabXacMinh.Controls.Add(btnChonFileChuKy);
            tabXacMinh.Controls.Add(btnChonFileGoc);
            tabXacMinh.Controls.Add(txtPublicKeyPath);
            tabXacMinh.Controls.Add(txtFileChuKy);
            tabXacMinh.Controls.Add(txtFileGoc);
            tabXacMinh.Location = new Point(4, 5);
            tabXacMinh.Name = "tabXacMinh";
            tabXacMinh.Padding = new Padding(3);
            tabXacMinh.Size = new Size(761, 521);
            tabXacMinh.TabIndex = 2;
            tabXacMinh.Text = "tabXacMinh";
            // 
            // label10
            // 
            label10.AutoSize = true;
            label10.Location = new Point(25, 156);
            label10.Name = "label10";
            label10.Size = new Size(257, 20);
            label10.TabIndex = 10;
            label10.Text = "Chọn file khóa công khai (Public Key):";
            label10.Click += btnChonPublicKey_Click;
            // 
            // label9
            // 
            label9.AutoSize = true;
            label9.Location = new Point(25, 94);
            label9.Name = "label9";
            label9.Size = new Size(114, 20);
            label9.TabIndex = 9;
            label9.Text = "Chọn file chữ ký";
            label9.Click += btnChonFileChuKy_Click;
            // 
            // label8
            // 
            label8.AutoSize = true;
            label8.Location = new Point(25, 30);
            label8.Name = "label8";
            label8.Size = new Size(185, 20);
            label8.TabIndex = 8;
            label8.Text = "Chọn file gốc cần kiểm tra:";
            label8.Click += btnChonFileGoc_Click;
            // 
            // btnThucHienXacMinh
            // 
            btnThucHienXacMinh.ForeColor = Color.Red;
            btnThucHienXacMinh.Location = new Point(25, 286);
            btnThucHienXacMinh.Name = "btnThucHienXacMinh";
            btnThucHienXacMinh.Size = new Size(184, 100);
            btnThucHienXacMinh.TabIndex = 7;
            btnThucHienXacMinh.Text = "🔍 XÁC MINH CHỮ KÝ";
            btnThucHienXacMinh.UseVisualStyleBackColor = true;
            btnThucHienXacMinh.Click += btnThucHienXacMinh_Click;
            // 
            // lblKetQuaXacMinh
            // 
            lblKetQuaXacMinh.AutoSize = true;
            lblKetQuaXacMinh.Font = new Font("Segoe UI", 12F, FontStyle.Regular, GraphicsUnit.Point, 0);
            lblKetQuaXacMinh.ForeColor = Color.LimeGreen;
            lblKetQuaXacMinh.Location = new Point(351, 328);
            lblKetQuaXacMinh.Name = "lblKetQuaXacMinh";
            lblKetQuaXacMinh.Size = new Size(318, 28);
            lblKetQuaXacMinh.TabIndex = 6;
            lblKetQuaXacMinh.Text = "Kết quả xác minh sẽ hiển thị tại đây";
            lblKetQuaXacMinh.Click += label1_Click;
            // 
            // btnChonPublicKey
            // 
            btnChonPublicKey.Location = new Point(575, 190);
            btnChonPublicKey.Name = "btnChonPublicKey";
            btnChonPublicKey.Size = new Size(133, 29);
            btnChonPublicKey.TabIndex = 5;
            btnChonPublicKey.Text = "Chọn file";
            btnChonPublicKey.UseVisualStyleBackColor = true;
            btnChonPublicKey.Click += btnChonPublicKey_Click;
            // 
            // btnChonFileChuKy
            // 
            btnChonFileChuKy.Location = new Point(575, 115);
            btnChonFileChuKy.Name = "btnChonFileChuKy";
            btnChonFileChuKy.Size = new Size(133, 29);
            btnChonFileChuKy.TabIndex = 4;
            btnChonFileChuKy.Text = "Chọn file";
            btnChonFileChuKy.UseVisualStyleBackColor = true;
            btnChonFileChuKy.Click += btnChonFileChuKy_Click;
            // 
            // btnChonFileGoc
            // 
            btnChonFileGoc.Location = new Point(575, 52);
            btnChonFileGoc.Name = "btnChonFileGoc";
            btnChonFileGoc.Size = new Size(133, 29);
            btnChonFileGoc.TabIndex = 3;
            btnChonFileGoc.Text = "Chọn file";
            btnChonFileGoc.UseVisualStyleBackColor = true;
            btnChonFileGoc.Click += btnChonFileGoc_Click;
            // 
            // txtPublicKeyPath
            // 
            txtPublicKeyPath.Location = new Point(25, 190);
            txtPublicKeyPath.Name = "txtPublicKeyPath";
            txtPublicKeyPath.Size = new Size(520, 27);
            txtPublicKeyPath.TabIndex = 2;
            txtPublicKeyPath.TextChanged += txtPublicKeyPath_TextChanged;
            // 
            // txtFileChuKy
            // 
            txtFileChuKy.Location = new Point(25, 117);
            txtFileChuKy.Name = "txtFileChuKy";
            txtFileChuKy.Size = new Size(520, 27);
            txtFileChuKy.TabIndex = 1;
            txtFileChuKy.TextChanged += txtFileChuKy_TextChanged;
            // 
            // txtFileGoc
            // 
            txtFileGoc.Location = new Point(25, 54);
            txtFileGoc.Name = "txtFileGoc";
            txtFileGoc.Size = new Size(520, 27);
            txtFileGoc.TabIndex = 0;
            txtFileGoc.TextChanged += txtFileGoc_TextChanged;
            // 
            // FormMain
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(1025, 530);
            Controls.Add(tabControlMain);
            Controls.Add(panelMenu);
            Name = "FormMain";
            Text = "CHƯƠNG TRÌNH KÝ SỐ VÀ XÁC MINH CHỮ KÝ ĐIỆN TỬ DSA ";
            Load += Form1_Load;
            panelMenu.ResumeLayout(false);
            tabControlMain.ResumeLayout(false);
            tabTaoKhoa.ResumeLayout(false);
            tabTaoKhoa.PerformLayout();
            tabKyFile.ResumeLayout(false);
            tabKyFile.PerformLayout();
            tabXacMinh.ResumeLayout(false);
            tabXacMinh.PerformLayout();
            ResumeLayout(false);
        }

        #endregion

        private Panel panelMenu;
        private Button btnMenuXacMinh;
        private Button btnMenuKyFile;
        private Button btnMenuTaoKhoa;
        private TabControl tabControlMain;
        private TabPage tabTaoKhoa;
        private TabPage tabKyFile;
        private TabPage tabXacMinh;
        private ComboBox cbKeySize;
        private Button btnLuuPrivateKey;
        private Button btnLuuPublicKey;
        private TextBox txtPrivateKey;
        private TextBox txtPublicKey;
        private Button btnTaoKhoaNgauNhien;
        private TextBox txtPrivateKeyPath;
        private TextBox txtFileCanKy;
        private TextBox txtFileLuuChuKy;
        private TextBox txtKetQuaChuKy;
        private Button btnChonNoiLuu;
        private Button btnChonPrivateKey;
        private Button btnChonFileCanKy;
        private Button btnChonPublicKey;
        private Button btnChonFileChuKy;
        private Button btnChonFileGoc;
        private TextBox txtPublicKeyPath;
        private TextBox txtFileChuKy;
        private TextBox txtFileGoc;
        private Label lblKetQuaXacMinh;
        private Button btnThucHienXacMinh;
        private Button btnThucHienKy;
        private Button btnXoaKetQua;
        private Label label4;
        private Label label3;
        private Label label2;
        private Label label1;
        private Button btnLuuChuKy;
        private Label label6;
        private Label label5;
        private Label label7;
        private Label label10;
        private Label label9;
        private Label label8;
    }
}
