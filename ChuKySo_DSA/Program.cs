using System;
using System.Windows.Forms;

namespace UngDung_ChuKySo_DSA
{
    internal static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            // Gọi đúng FormMain đã được đổi tên sạch sẽ của bạn để khởi chạy
            Application.Run(new FormMain());
        }
    }
}