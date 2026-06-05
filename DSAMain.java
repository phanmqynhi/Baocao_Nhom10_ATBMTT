import java.awt.GraphicsEnvironment;
import javax.swing.*;

/**
 * =========================================================================
 * DSA DIGITAL SIGNATURE ALGORITHM - MAIN ENTRY POINT
 * =========================================================================
 *
 * Ứng dụng: Mô phỏng thuật toán Chữ ký số DSA (Digital Signature Algorithm)
 * Trường: HAUI (Hanoi University of Industry)
 * Môn học: An toàn Bảo mật Thông tin
 *
 * Tác giả: Sinh viên Lớp [Tên lớp]
 * Ngày: 2026
 *
 * =========================================================================
 * CHỨC NĂNG:
 * =========================================================================
 *
 * GIAI ĐOẠN 1: Phát sinh khóa (Key Generation)
 * - Sinh tham số hệ thống (p, q, g) chuẩn FIPS 186-4 (1024/160-bit)
 * - Tạo cặp khóa: bí mật x và công khai y = g^x mod p
 *
 * GIAI ĐOẠN 2: Tạo chữ ký (Signing)
 * - Ký thông điệp sử dụng khóa bí mật (x)
 * - Kết quả: Chữ ký (r, s)
 *
 * GIAI ĐOẠN 3: Xác minh chữ ký (Verification)
 * - Kiểm tra tính hợp lệ của chữ ký
 * - Hiển thị kết quả: Hợp lệ (Xanh) / Không hợp lệ (Đỏ)
 *
 * =========================================================================
 * CÔNG NGHỆ SỬ DỤNG:
 * =========================================================================
 *
 * - Ngôn ngữ: Java
 * - GUI Framework: Java Swing
 * - Toán học: java.math.BigInteger
 * - Hash: SHA-256 (java.security.MessageDigest)
 * - Tham số DSA: java.security.AlgorithmParameterGenerator
 * - Random: java.security.SecureRandom
 * - Mục tiêu: Giáo dục, Demo, Học tập
 *
 * =========================================================================
 * KIẾN TRÚC:
 * =========================================================================
 *
 * DSASignatureParams.java     → Tham số hệ thống (p, q, g) – FIPS 186-4
 * DSAKeyPair.java             → Cặp khóa (khóa bí mật x, khóa công khai y)
 * DSASignature.java           → Chữ ký (r, s) + serialize/deserialize
 * DSASignatureAlgorithm.java  → Triển khai thuật toán 3 giai đoạn
 * DSAGUI.java                 → Giao diện Swing
 * DSAMain.java                → Entry point chính (FILE NÀY)
 *
 * =========================================================================
 * SO SÁNH DSA VÀ SCHNORR:
 * =========================================================================
 *
 *   Thuộc tính          DSA                      Schnorr
 *   ──────────────────────────────────────────────────────────
 *   Chuẩn hóa          FIPS 186-4 (NIST)        Không chính thức
 *   Tham số p          1024-bit                 256+ bit (linh hoạt)
 *   Tham số q          160-bit                  256-bit
 *   Chữ ký             (r, s)                   (s, e)
 *   Công thức ký       s = k⁻¹(H(M)+xr) mod q  s = k + xe mod q
 *   Công thức xác minh v = (g^u1·y^u2 mod p)    r' = g^s·y^{-e} mod p
 *                       mod q == r               → H(M||r') == e
 *   Bảo mật            Dựa trên DLP             Dựa trên DLP
 *   Tốc độ ký          Tương đương              Tương đương
 *   Tốc độ xác minh    Chậm hơn (~2 exp)        Nhanh hơn (~2 exp)
 *
 * =========================================================================
 */
public class DSAMain {

    private static final String APP_VERSION = "1.0";
    /**
     * Điểm vào chính của ứng dụng.
     * Kiểm tra điều kiện tiên quyết rồi khởi chạy GUI.
     *
     * @param args Tham số dòng lệnh (không sử dụng)
     */
    public static void main(String[] args) {
        try {
            printWelcomeBanner();
            checkSystemRequirements();
            configureUI();
            launchGUI();
        } catch (Exception e) {
            handleFatalError(e);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    private static void printWelcomeBanner() {
        System.out.println("\n");
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                ║");
        System.out.println("║         🔏  DSA DIGITAL SIGNATURE SIMULATOR  🔏                ║");
        System.out.println("║                          v" + APP_VERSION + "                                  ║");
        System.out.println("║                                                                ║");
        System.out.println("║  HAUI - An toàn Bảo mật Thông tin                             ║");
        System.out.println("║  Hanoi University of Industry                                  ║");
        System.out.println("║                                                                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("📋 Ứng dụng triển khai 3 giai đoạn của DSA (FIPS 186-4):");
        System.out.println("   1️⃣  Phát sinh khóa (Key Generation)  –  p/q/g chuẩn 1024/160-bit");
        System.out.println("   2️⃣  Tạo chữ ký    (Signing)          –  r = (g^k mod p) mod q");
        System.out.println("                                              s = k⁻¹·(H(M)+xr) mod q");
        System.out.println("   3️⃣  Xác minh chữ ký (Verification)   –  v = (g^u1·y^u2 mod p) mod q");
        System.out.println();
        System.out.println("⏳ Khởi chạy giao diện... Vui lòng chờ!");
        System.out.println();
    }

    // ─────────────────────────────────────────────────────────────────────────
    private static void checkSystemRequirements() {
        System.out.println("✓ Kiểm tra yêu cầu hệ thống:");

        // 1. Phiên bản Java
        String javaVersion = System.getProperty("java.version");
        System.out.println("  ✓ Java Version: " + javaVersion);
        try {
            String vStr = javaVersion.split("\\.")[0];
            if (vStr.equals("1")) vStr = javaVersion.split("\\.")[1];
            if (Integer.parseInt(vStr) < 8)
                throw new RuntimeException("Java 8 hoặc mới hơn được yêu cầu!");
        } catch (NumberFormatException e) {
            System.out.println("  ⚠ Không thể xác định phiên bản Java chính xác");
        }

        // 2. Bộ nhớ
        Runtime rt = Runtime.getRuntime();
        long maxMB  = rt.maxMemory()  / (1024 * 1024);
        long freeMB = rt.freeMemory() / (1024 * 1024);
        System.out.println("  ✓ Bộ nhớ: " + maxMB + " MB (Khả dụng: " + freeMB + " MB)");
        if (maxMB < 256)
            System.out.println("  ⚠ Bộ nhớ có thể không đủ. Gợi ý: java -Xmx512m DSAMain");

        // 3. Hệ điều hành
        System.out.println("  ✓ Hệ điều hành: "
                + System.getProperty("os.name") + " " + System.getProperty("os.version"));

        // 4. Giao diện đồ họa
        if (GraphicsEnvironment.isHeadless())
            throw new RuntimeException("Không tìm thấy giao diện đồ họa (headless mode)!");
        System.out.println("  ✓ Giao diện đồ họa: Có sẵn");

        // 5. Kiểm tra các lớp cần thiết
        try {
            Class.forName("DSASignatureParams");
            Class.forName("DSAKeyPair");
            Class.forName("DSASignature");
            Class.forName("DSASignatureAlgorithm");
            Class.forName("DSAGUI");
            System.out.println("  ✓ Tất cả các lớp cần thiết: OK");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Không tìm thấy lớp: " + e.getMessage());
        }

        // 6. Kiểm tra DSA provider
        try {
            java.security.AlgorithmParameterGenerator.getInstance("DSA");
            System.out.println("  ✓ DSA AlgorithmParameterGenerator: Có sẵn");
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException("JVM không hỗ trợ DSA AlgorithmParameterGenerator!");
        }

        System.out.println();
    }

    // ─────────────────────────────────────────────────────────────────────────
    private static void configureUI() {
        System.out.println("⚙️  Cấu hình giao diện...");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            System.out.println("  ✓ Look & Feel: " + UIManager.getLookAndFeel().getName());
        } catch (Exception e) {
            System.out.println("  ⚠ Sử dụng Look & Feel mặc định");
        }
        // Bật font anti-alias
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        System.out.println();
    }

    // ─────────────────────────────────────────────────────────────────────────
    private static void launchGUI() {
        System.out.println("🚀 Khởi chạy giao diện DSA...");
        System.out.println();
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("[GUI] Giao diện DSA đã được khởi chạy thành công!");
            } catch (Exception e) {
                System.err.println("[LỖI] Không thể khởi chạy giao diện:");
                e.printStackTrace();
                System.exit(1);
            }
        });
    }

    // ─────────────────────────────────────────────────────────────────────────
    private static void handleFatalError(Exception e) {
        System.err.println();
        System.err.println("╔════════════════════════════════════════════════════════════════╗");
        System.err.println("║                        ❌  LỖI FATAL  ❌                       ║");
        System.err.println("╚════════════════════════════════════════════════════════════════╝");
        System.err.println();
        System.err.println("Thông báo lỗi: " + e.getMessage());
        System.err.println();
        e.printStackTrace();
        System.err.println();
        System.err.println("Gợi ý:");
        System.err.println("  1. Đảm bảo tất cả 6 file Java đã được biên dịch thành công");
        System.err.println("  2. Kiểm tra phiên bản Java (cần Java 8 hoặc mới hơn)");
        System.err.println("  3. Đảm bảo bạn đang ở thư mục chính xác");
        System.err.println("  4. Xóa tất cả file .class và biên dịch lại");
        System.err.println();
        System.err.println("Lệnh biên dịch:");
        System.err.println("  javac DSASignatureParams.java DSAKeyPair.java \\");
        System.err.println("        DSASignature.java DSASignatureAlgorithm.java \\");
        System.err.println("        DSAGUI.java DSAMain.java");
        System.err.println();
        System.err.println("Lệnh chạy:");
        System.err.println("  java DSAMain");
        System.err.println();
        System.exit(1);
    }
}
