using UngDung_ChuKySo_DSA;
using System;
using System.Drawing;
using System.IO;
using System.Windows.Forms;

namespace UngDung_ChuKySo_DSA
{
    public partial class FormMain : Form
    {
        public FormMain()
        {
            InitializeComponent();

            // Ép cấu hình giao diện phẳng hiện đại cho các nút bấm Menu bên trái
            Button[] menuButtons = { btnMenuTaoKhoa, btnMenuKyFile, btnMenuXacMinh };
            foreach (Button btn in menuButtons)
            {
                btn.FlatStyle = FlatStyle.Flat;                      // Chuyển sang giao diện phẳng hiện đại
                btn.FlatAppearance.BorderSize = 0;                   // Xóa bỏ đường viền nổi khối cũ kỹ
                btn.ForeColor = Color.White;                         // Ép màu chữ ĐỒNG LOẠT thành MÀU TRẮNG
                btn.Font = new Font("Segoe UI", 10, FontStyle.Bold); // Cho font chữ to và đậm lên cho đẹp
            }

            // Thiết lập giá trị mặc định cho ComboBox KeySize khi khởi chạy
            if (cbKeySize.Items.Count > 0)
            {
                cbKeySize.SelectedIndex = 1; // Mặc định chọn mức 2048
            }
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            // Cứ để trống ở đây để giữ cấu hình cho Designer
        }

        // =========================================================================
        // ĐIỀU HƯỚNG MENU (CHUYỂN TAB)
        // =========================================================================
        private void btnMenuTaoKhoa_Click(object sender, EventArgs e)
        {
            tabControlMain.SelectedIndex = 0; // Chuyển sang Tab Tạo khóa
            HighlightMenuButton(btnMenuTaoKhoa);
        }

        private void btnMenuKyFile_Click(object sender, EventArgs e)
        {
            tabControlMain.SelectedIndex = 1; // Chuyển sang Tab Ký file
            HighlightMenuButton(btnMenuKyFile);
        }

        private void btnMenuXacMinh_Click(object sender, EventArgs e)
        {
            tabControlMain.SelectedIndex = 2; // Chuyển sang Tab Xác minh
            HighlightMenuButton(btnMenuXacMinh);
        }

        // Hiệu ứng đổi màu nền nút khi click (Tạo cảm giác Active chuyên nghiệp)
        private void HighlightMenuButton(Button activeButton)
        {
            Color activeColor = Color.FromArgb(46, 51, 73);   // Màu xanh sáng khi chọn
            Color defaultColor = Color.FromArgb(24, 30, 54);  // Màu xanh tối ban đầu

            btnMenuTaoKhoa.BackColor = defaultColor;
            btnMenuKyFile.BackColor = defaultColor;
            btnMenuXacMinh.BackColor = defaultColor;

            activeButton.BackColor = activeColor;
        }

        // =========================================================================
        // CHỨC NĂNG: TẠO KHÓA
        // =========================================================================
        private void btnTaoKhoaNgauNhien_Click(object sender, EventArgs e)
        {
            try
            {
                int keySize = 2048;
                if (cbKeySize.SelectedItem != null)
                {
                    int.TryParse(cbKeySize.SelectedItem.ToString(), out keySize);
                }

                // Gọi Helper thuật toán xử lý dữ liệu
                DSACryptoHelper.GenerateKeys(keySize, out string publicKey, out string privateKey);

                // Đổ dữ liệu chuỗi Base64 ra màn hình
                txtPublicKey.Text = publicKey;
                txtPrivateKey.Text = privateKey;

                MessageBox.Show("Tạo cặp khóa DSA thành công!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
            catch (Exception ex)
            {
                MessageBox.Show("Lỗi tạo khóa: " + ex.Message, "Lỗi", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void btnLuuPublicKey_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrWhiteSpace(txtPublicKey.Text))
            {
                MessageBox.Show("Vui lòng tạo khóa trước khi lưu khóa công khai!", "Cảnh báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return;
            }

            using (SaveFileDialog sfd = new SaveFileDialog() { Filter = "Key Files|*.key|All Files|*.*", FileName = "public.key" })
            {
                if (sfd.ShowDialog() == DialogResult.OK)
                {
                    File.WriteAllText(sfd.FileName, txtPublicKey.Text);
                    MessageBox.Show("Đã lưu khóa công khai (public.key) thành công!", "Thành công", MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
            }
        }

        private void btnLuuPrivateKey_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrWhiteSpace(txtPrivateKey.Text))
            {
                MessageBox.Show("Vui lòng tạo khóa trước khi lưu khóa bí mật!", "Cảnh báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return;
            }

            using (SaveFileDialog sfd = new SaveFileDialog() { Filter = "Key Files|*.key|All Files|*.*", FileName = "private.key" })
            {
                if (sfd.ShowDialog() == DialogResult.OK)
                {
                    File.WriteAllText(sfd.FileName, txtPrivateKey.Text);
                    MessageBox.Show("Đã lưu khóa bí mật (private.key) thành công!", "Thành công", MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
            }
        }

        private void btnXoaKetQua_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(txtPublicKey.Text) && string.IsNullOrEmpty(txtPrivateKey.Text))
            {
                return;
            }

            DialogResult result = MessageBox.Show("Bạn có chắc chắn muốn xóa cặp khóa hiện tại không?",
                "Xác nhận", MessageBoxButtons.YesNo, MessageBoxIcon.Question);

            if (result == DialogResult.Yes)
            {
                txtPublicKey.Clear();
                txtPrivateKey.Clear();
                MessageBox.Show("Đã xóa sạch khóa hiển thị trên màn hình!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
        }

        // =========================================================================
        // CHỨC NĂNG: KÝ FILE
        // =========================================================================
        private void btnChonFileCanKy_Click(object sender, EventArgs e)
        {
            txtFileCanKy.Text = ShowOpenFileDialog("All Files|*.*");
        }

        private void btnChonPrivateKey_Click(object sender, EventArgs e)
        {
            txtPrivateKeyPath.Text = ShowOpenFileDialog("Key Files|*.key|All Files|*.*");
        }

        private void btnChonNoiLuu_Click(object sender, EventArgs e)
        {
            using (SaveFileDialog sfd = new SaveFileDialog() { Filter = "Signature Files|*.sig", FileName = "signature.sig" })
            {
                if (sfd.ShowDialog() == DialogResult.OK)
                {
                    txtFileLuuChuKy.Text = sfd.FileName;
                }
            }
        }

        private void btnThucHienKy_Click(object sender, EventArgs e)
        {
            try
            {
                if (string.IsNullOrEmpty(txtFileCanKy.Text) || string.IsNullOrEmpty(txtPrivateKeyPath.Text))
                {
                    MessageBox.Show("Vui lòng chọn đầy đủ File cần ký và Khóa bí mật!", "Nhắc nhở", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return;
                }

                string privateKeyBase64 = File.ReadAllText(txtPrivateKeyPath.Text).Trim();
                byte[] signatureBytes = DSACryptoHelper.SignFile(txtFileCanKy.Text, privateKeyBase64);

                // Đổ chuỗi Hex ký số ra màn hình để xem trực quan
                txtKetQuaChuKy.Text = DSACryptoHelper.ByteArrayToHexString(signatureBytes);

                MessageBox.Show("Ký số lên tệp tin thành công! Bạn hãy nhấn 'Lưu chữ ký' để xuất file.", "Thành công", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
            catch (Exception ex)
            {
                MessageBox.Show("Quá trình ký thất bại: " + ex.Message, "Lỗi", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void btnLuuChuKy_Click(object sender, EventArgs e)
        {
            try
            {
                if (string.IsNullOrEmpty(txtKetQuaChuKy.Text))
                {
                    MessageBox.Show("Chưa có dữ liệu chữ ký! Vui lòng thực hiện ký file trước.", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return;
                }

                if (string.IsNullOrEmpty(txtFileLuuChuKy.Text))
                {
                    MessageBox.Show("Vui lòng chọn đường dẫn nơi lưu file chữ ký (.sig) trước!", "Thông báo", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return;
                }

                byte[] signatureBytes = DSACryptoHelper.HexStringToByteArray(txtKetQuaChuKy.Text);
                File.WriteAllBytes(txtFileLuuChuKy.Text, signatureBytes);

                MessageBox.Show("Đã xuất và lưu file chữ ký (.sig) thành công!", "Thành công", MessageBoxButtons.OK, MessageBoxIcon.Information);
            }
            catch (Exception ex)
            {
                MessageBox.Show("Lỗi khi lưu file chữ ký: " + ex.Message, "Lỗi", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        // =========================================================================
        // CHỨC NĂNG: XÁC MINH CHỮ KÝ
        // =========================================================================
        private void btnChonFileGoc_Click(object sender, EventArgs e)
        {
            txtFileGoc.Text = ShowOpenFileDialog("All Files|*.*");
        }

        private void btnChonFileChuKy_Click(object sender, EventArgs e)
        {
            txtFileChuKy.Text = ShowOpenFileDialog("Signature Files|*.sig|All Files|*.*");
        }

        private void btnChonPublicKey_Click(object sender, EventArgs e)
        {
            txtPublicKeyPath.Text = ShowOpenFileDialog("Key Files|*.key|All Files|*.*");
        }

        private void btnThucHienXacMinh_Click(object sender, EventArgs e)
        {
            try
            {
                if (string.IsNullOrEmpty(txtFileGoc.Text) || string.IsNullOrEmpty(txtFileChuKy.Text) || string.IsNullOrEmpty(txtPublicKeyPath.Text))
                {
                    MessageBox.Show("Vui lòng điền đủ: File gốc, File chữ ký (.sig) và Khóa công khai!", "Nhắc nhở", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                    return;
                }

                string publicKeyBase64 = File.ReadAllText(txtPublicKeyPath.Text).Trim();
                byte[] signatureBytes = File.ReadAllBytes(txtFileChuKy.Text);

                bool matches = DSACryptoHelper.VerifySignature(txtFileGoc.Text, signatureBytes, publicKeyBase64);

                if (matches)
                {
                    lblKetQuaXacMinh.Text = "✅ XÁC MINH THÀNH CÔNG:\nChữ ký hoàn toàn hợp lệ, dữ liệu toàn vẹn!";
                    lblKetQuaXacMinh.ForeColor = Color.Green; // Chữ xanh lá cây

                    MessageBox.Show("XÁC MINH THÀNH CÔNG: Chữ ký hoàn toàn hợp lệ, toàn vẹn dữ liệu!", "Kết quả", MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
                else
                {
                    lblKetQuaXacMinh.Text = "❌ CẢNH BÁO GIẢ MẠO:\nChữ ký KHÔNG hợp lệ! File đã bị sửa đổi hoặc sai khóa.";
                    lblKetQuaXacMinh.ForeColor = Color.Red; // Chữ đỏ cảnh báo

                    MessageBox.Show("CẢNH BÁO: Chữ ký KHÔNG hợp lệ! File gốc có thể đã bị can thiệp chỉnh sửa hoặc sai khóa.", "Kết quả", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Quá trình xác minh xảy ra lỗi: " + ex.Message, "Lỗi", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        // =========================================================================
        // HÀM TIỆN ÍCH DÙNG CHUNG
        // =========================================================================
        private string ShowOpenFileDialog(string filter)
        {
            using (OpenFileDialog ofd = new OpenFileDialog() { Filter = filter })
            {
                return ofd.ShowDialog() == DialogResult.OK ? ofd.FileName : "";
            }
        }

        // =========================================================================
        // CÁC SỰ KIỆN TRỐNG ĐƯỢC GIỮ LẠI ĐỂ TRÁNH LỖI DESIGNER LINKING
        // =========================================================================
        private void button6_Click(object sender, EventArgs e) { }
        private void textBox3_TextChanged(object sender, EventArgs e) { }
        private void label1_Click(object sender, EventArgs e) { }
        private void cbKeySize_SelectedIndexChanged(object sender, EventArgs e) { }
        private void txtPublicKey_TextChanged(object sender, EventArgs e) { }
        private void txtPrivateKey_TextChanged(object sender, EventArgs e) { }
        private void txtFileCanKy_TextChanged(object sender, EventArgs e) { }
        private void txtPrivateKeyPath_TextChanged(object sender, EventArgs e) { }
        private void txtKetQuaChuKy_TextChanged(object sender, EventArgs e) { }
        private void txtFileGoc_TextChanged(object sender, EventArgs e) { }
        private void txtFileChuKy_TextChanged(object sender, EventArgs e) { }
        private void txtPublicKeyPath_TextChanged(object sender, EventArgs e) { }
        private void label3_Click(object sender, EventArgs e) { }
        private void label4_Click(object sender, EventArgs e) { }
        private void label1_Click_1(object sender, EventArgs e) { }
        private void label2_Click(object sender, EventArgs e) { }
        private void label6_Click(object sender, EventArgs e) { }
        private void label7_Click(object sender, EventArgs e) { }
        private void tabKyFile_Click(object sender, EventArgs e) { }
    }
}