import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * Giao diện GUI cho thuật toán DSA – thiết kế theo phong cách Security-Tech.
 * Gương chiếu với SchorrGUI nhưng điều chỉnh cho DSA (r, s thay vì s, e).
 */
public class DSAGUI extends JFrame {

    // ─── Core references ────────────────────────────────────────────────────────
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JTabbedPane tabbedPane;
    private ToggleSwitch themeToggle;

    private JLabel headerTitleLabel;
    private JLabel headerSubtitleLabel;
    private JLabel keyStatusLabel;
    private JLabel signStatusLabel;
    private JLabel verifyStatusLabel;
    private JLabel verificationResultLabel;

    private JTextArea pDisplay, qDisplay, gDisplay;
    private JTextArea privateKeyDisplay, publicKeyDisplay, keySummaryDisplay;
    private JTextArea messageInput, hashDisplay, signatureDisplay;
    private JTextArea verifyMessageInput, verifySignatureInput, verifyPublicKeyDisplay;
    private JTextArea instructionArea;

    private JButton generateKeysButton, randomKeysButton, saveKeyButton;
    private JButton signMessageButton;
    private JButton loadMessageButton, saveMessageButton, saveSignatureButton;
    private JButton verifyButton;
    private JButton loadVerifyMessageButton, loadVerifySignatureButton, loadPublicKeyButton;

    private DSASignatureAlgorithm algorithm;
    private DSAKeyPair currentKeyPair;
    private boolean darkMode;

    // ─── Typography ──────────────────────────────────────────────────────────────
    private final Font TITLE_FONT    = new Font("Serif",     Font.BOLD,   22);
    private final Font SUBTITLE_FONT = new Font("Serif",     Font.ITALIC, 13);
    private final Font LABEL_FONT    = new Font("Dialog",    Font.BOLD,   12);
    private final Font BODY_FONT     = new Font("Dialog",    Font.PLAIN,  13);
    private final Font CODE_FONT     = new Font("Monospaced",Font.PLAIN,  12);
    private final Font TAB_FONT      = new Font("Dialog",    Font.BOLD,   13);
    private final Font BTN_FONT      = new Font("Dialog",    Font.BOLD,   13);
    private final Font STATUS_FONT   = new Font("Dialog",    Font.ITALIC, 12);

    // ─── Fixed accent colours ────────────────────────────────────────────────────
    private final Color BLUE        = new Color( 82, 130, 255);
    private final Color BLUE_LIGHT  = new Color(120, 165, 255);
    private final Color TEAL        = new Color( 32, 201, 151);
    private final Color TEAL_DARK   = new Color( 22, 160, 115);
    private final Color DANGER      = new Color(220,  75,  75);
    private final Color NEUTRAL     = new Color(100, 116, 139);
    private final Color NEUTRAL_DARK= new Color( 71,  85, 105);

    // ─── Theme-dependent colours ─────────────────────────────────────────────────
    private Color APP_BG, PANEL_BG, CARD_BG, CARD_BORDER;
    private Color TEXT, MUTED, HEADER_BG1, HEADER_BG2;
    private Color TITLE_CLR, SUBTITLE_CLR;

    // ════════════════════════════════════════════════════════════════════════════
    public DSAGUI() {
        algorithm = new DSASignatureAlgorithm();
        applyTheme(false);
        setTitle("Phần Mềm Chữ Ký Điện Tử DSA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 800);
        setLocationRelativeTo(null);
        setContentPane(buildMainPanel());
        setVisible(true);
    }

    // ─── Theme ───────────────────────────────────────────────────────────────────
    private void applyTheme(boolean dark) {
        this.darkMode = dark;
        if (dark) {
            APP_BG      = new Color( 15,  18,  30);
            PANEL_BG    = new Color( 22,  28,  48);
            CARD_BG     = new Color( 28,  36,  60);
            CARD_BORDER = new Color( 45,  58,  90);
            TEXT        = new Color(210, 220, 255);
            MUTED       = new Color(110, 125, 165);
            HEADER_BG1  = new Color( 18,  24,  44);
            HEADER_BG2  = new Color( 30,  40,  70);
            TITLE_CLR   = new Color(200, 215, 255);
            SUBTITLE_CLR= new Color(130, 148, 195);
        } else {
            APP_BG      = new Color(242, 244, 250);
            PANEL_BG    = new Color(252, 253, 255);
            CARD_BG     = new Color(255, 255, 255);
            CARD_BORDER = new Color(210, 218, 235);
            TEXT        = new Color( 28,  36,  60);
            MUTED       = new Color(100, 116, 145);
            HEADER_BG1  = new Color(230, 238, 255);
            HEADER_BG2  = new Color(215, 228, 255);
            TITLE_CLR   = new Color( 25,  40,  90);
            SUBTITLE_CLR= new Color( 70,  95, 150);
        }
    }

    // ─── Root panel ──────────────────────────────────────────────────────────────
    private JPanel buildMainPanel() {
        mainPanel = new JPanel(new BorderLayout(0, 14)) {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(APP_BG);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(14, 18, 18, 18));
        headerPanel = buildHeaderPanel();
        tabbedPane  = buildTabbedPane();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane,  BorderLayout.CENTER);
        return mainPanel;
    }

    // ─── Header ──────────────────────────────────────────────────────────────────
    private JPanel buildHeaderPanel() {
        JPanel panel = new GradientPanel(HEADER_BG1, HEADER_BG2);
        panel.setLayout(new BorderLayout(12, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
                new RoundBorder(CARD_BORDER, 14, 1),
                BorderFactory.createEmptyBorder(16, 22, 16, 22)));

        JPanel titleBlock = new JPanel();
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));
        titleBlock.setOpaque(false);

        JPanel accentRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        accentRow.setOpaque(false);

        JLabel bar = new JLabel("▐");
        bar.setFont(new Font("Dialog", Font.BOLD, 26));
        bar.setForeground(BLUE);

        headerTitleLabel = new JLabel("Chữ Ký Điện Tử DSA");
        headerTitleLabel.setFont(TITLE_FONT);
        headerTitleLabel.setForeground(TITLE_CLR);

        accentRow.add(bar);
        accentRow.add(headerTitleLabel);

        headerSubtitleLabel = new JLabel(
                "  Hệ thống mô phỏng DSA – sinh khóa · ký số · xác minh (FIPS 186-4 / 1024-160)");
        headerSubtitleLabel.setFont(SUBTITLE_FONT);
        headerSubtitleLabel.setForeground(SUBTITLE_CLR);

        titleBlock.add(accentRow);
        titleBlock.add(Box.createVerticalStrut(4));
        titleBlock.add(headerSubtitleLabel);

        JPanel rightBox = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 8));
        rightBox.setOpaque(false);

        JLabel badge = new JLabel("  🔏  DSA v1.0  ");
        badge.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
        badge.setForeground(BLUE);
        badge.setOpaque(true);
        badge.setBackground(darkMode ? new Color(20, 30, 60) : new Color(230, 240, 255));
        badge.setBorder(BorderFactory.createCompoundBorder(
                new RoundBorder(BLUE, 10, 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));

        JLabel themeLabel = new JLabel("Dark ");
        themeLabel.setFont(BODY_FONT);
        themeLabel.setForeground(SUBTITLE_CLR);

        themeToggle = new ToggleSwitch(darkMode);
        themeToggle.addActionListener(e -> {
            applyTheme(themeToggle.isSelected());
            refreshTheme();
        });

        rightBox.add(badge);
        rightBox.add(themeLabel);
        rightBox.add(themeToggle);

        panel.add(titleBlock, BorderLayout.WEST);
        panel.add(rightBox,   BorderLayout.EAST);
        return panel;
    }

    // ─── Tabbed pane ─────────────────────────────────────────────────────────────
    private JTabbedPane buildTabbedPane() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(TAB_FONT);
        tabs.setFocusable(false);
        tabs.addTab("🔑  Tạo Khóa",    buildKeyTab());
        tabs.addTab("✍  Ký Văn Bản",   buildSignTab());
        tabs.addTab("✅  Xác Minh",     buildVerifyTab());
        tabs.addTab("📘  Hướng Dẫn",   buildHelpTab());
        return tabs;
    }

    // ════════════════════════════════════════════════════════════════════════════
    // TAB 1 – KEY GENERATION
    // ════════════════════════════════════════════════════════════════════════════
    private JPanel buildKeyTab() {
        JPanel tab = makeTabPanel();

        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);
        JLabel heading = sectionHeading("Thiết lập Tham số DSA & Cặp Khóa Bảo Mật");
        keyStatusLabel = makeStatusLabel("Trạng thái: Chưa tạo khóa");
        topRow.add(heading,        BorderLayout.WEST);
        topRow.add(keyStatusLabel, BorderLayout.EAST);

        JPanel cards = new JPanel(new GridLayout(1, 2, 18, 0));
        cards.setOpaque(false);
        cards.add(buildParamCard());
        cards.add(buildKeyOutputCard());

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 8));
        btnRow.setOpaque(false);

        generateKeysButton = makeBtn("⚙  Tạo khóa tự động",  BLUE,    BLUE_LIGHT);
        randomKeysButton   = makeBtn("🎲  Khóa ngẫu nhiên",   NEUTRAL, NEUTRAL_DARK);
        saveKeyButton      = makeBtn("💾  Lưu khóa ra tệp",   TEAL,    TEAL_DARK);

        generateKeysButton.addActionListener(e -> handleGenerateKeys());
        randomKeysButton  .addActionListener(e -> handleGenerateKeys());
        saveKeyButton     .addActionListener(e -> saveKeyPairToFile());

        btnRow.add(generateKeysButton);
        btnRow.add(randomKeysButton);
        btnRow.add(saveKeyButton);

        tab.add(topRow,  BorderLayout.NORTH);
        tab.add(cards,   BorderLayout.CENTER);
        tab.add(btnRow,  BorderLayout.SOUTH);
        return tab;
    }

    private JPanel buildParamCard() {
        pDisplay = makeCodeArea(2);
        qDisplay = makeCodeArea(2);
        gDisplay = makeCodeArea(2);

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 12));
        form.setOpaque(false);
        form.add(makeFieldLabel("Số nguyên tố P (1024-bit)"));
        form.add(wrapScroll(pDisplay));
        form.add(makeFieldLabel("Số nguyên tố Q (160-bit)"));
        form.add(wrapScroll(qDisplay));
        form.add(makeFieldLabel("Phần tử sinh G"));
        form.add(wrapScroll(gDisplay));

        return makeCard("Tham số P · Q · G", form);
    }

    private JPanel buildKeyOutputCard() {
        privateKeyDisplay = makeCodeArea(3);
        publicKeyDisplay  = makeCodeArea(3);
        keySummaryDisplay = makeCodeArea(5);

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 12));
        form.setOpaque(false);
        form.add(makeFieldLabel("Khóa bí mật (x)"));
        form.add(wrapScroll(privateKeyDisplay));
        form.add(makeFieldLabel("Khóa công khai (y = g^x mod p)"));
        form.add(wrapScroll(publicKeyDisplay));
        form.add(makeFieldLabel("Tóm tắt cặp khóa"));
        form.add(wrapScroll(keySummaryDisplay));

        return makeCard("Cặp Khóa DSA", form);
    }

    // ════════════════════════════════════════════════════════════════════════════
    // TAB 2 – SIGN
    // ════════════════════════════════════════════════════════════════════════════
    private JPanel buildSignTab() {
        JPanel tab = makeTabPanel();

        JPanel toolBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        toolBar.setOpaque(false);
        loadMessageButton    = makeBtn("📂  Tải văn bản",   NEUTRAL, NEUTRAL_DARK);
        saveMessageButton    = makeBtn("💾  Lưu văn bản",   NEUTRAL, NEUTRAL_DARK);
        saveSignatureButton  = makeBtn("📤  Xuất chữ ký",   TEAL,    TEAL_DARK);

        loadMessageButton  .addActionListener(e -> loadTextInto(messageInput));
        saveMessageButton  .addActionListener(e -> saveTextFrom(messageInput,  "vanban.txt"));
        saveSignatureButton.addActionListener(e -> saveTextFrom(signatureDisplay, "chuky_dsa.txt"));

        toolBar.add(loadMessageButton);
        toolBar.add(saveMessageButton);
        toolBar.add(saveSignatureButton);

        messageInput      = makeEditableArea(7);
        hashDisplay       = makeCodeArea(5);
        signatureDisplay  = makeCodeArea(5);

        JPanel center = new JPanel(new GridLayout(3, 1, 0, 14));
        center.setOpaque(false);
        center.add(makeCard("Văn bản cần ký",       wrapScroll(messageInput)));
        center.add(makeCard("Kết quả băm SHA-256",   wrapScroll(hashDisplay)));
        center.add(makeCard("Chữ ký DSA (r, s)",     wrapScroll(signatureDisplay)));

        signMessageButton = makeBtn("✍  Thực Hiện Ký", BLUE, BLUE_LIGHT);
        signMessageButton.setPreferredSize(new Dimension(210, 44));
        signMessageButton.setFont(new Font("Dialog", Font.BOLD, 15));
        signMessageButton.addActionListener(e -> handleSignMessage());

        signStatusLabel = makeStatusLabel("Trạng thái: Chưa ký văn bản");

        JPanel bottom = new JPanel(new BorderLayout(14, 0));
        bottom.setOpaque(false);
        bottom.add(signStatusLabel, BorderLayout.WEST);
        JPanel signBtnWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        signBtnWrap.setOpaque(false);
        signBtnWrap.add(signMessageButton);
        bottom.add(signBtnWrap, BorderLayout.EAST);

        tab.add(toolBar, BorderLayout.NORTH);
        tab.add(center,  BorderLayout.CENTER);
        tab.add(bottom,  BorderLayout.SOUTH);
        return tab;
    }

    // ════════════════════════════════════════════════════════════════════════════
    // TAB 3 – VERIFY
    // ════════════════════════════════════════════════════════════════════════════
    private JPanel buildVerifyTab() {
        JPanel tab = makeTabPanel();

        JPanel toolBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        toolBar.setOpaque(false);
        loadVerifyMessageButton   = makeBtn("📂  Tải văn bản",      NEUTRAL, NEUTRAL_DARK);
        loadVerifySignatureButton = makeBtn("📂  Tải chữ ký",       NEUTRAL, NEUTRAL_DARK);
        loadPublicKeyButton       = makeBtn("🔑  Tải khóa công khai",NEUTRAL, NEUTRAL_DARK);

        loadVerifyMessageButton  .addActionListener(e -> loadTextInto(verifyMessageInput));
        loadVerifySignatureButton.addActionListener(e -> loadTextInto(verifySignatureInput));
        loadPublicKeyButton      .addActionListener(e -> {
            if (currentKeyPair != null)
                verifyPublicKeyDisplay.setText(hexWrap(currentKeyPair.getPublicKey()));
        });

        toolBar.add(loadVerifyMessageButton);
        toolBar.add(loadVerifySignatureButton);
        toolBar.add(loadPublicKeyButton);

        verifyMessageInput   = makeEditableArea(6);
        verifySignatureInput = makeEditableArea(5);
        verifyPublicKeyDisplay = makeCodeArea(5);

        JPanel center = new JPanel(new GridLayout(3, 1, 0, 14));
        center.setOpaque(false);
        center.add(makeCard("Nội dung văn bản",            wrapScroll(verifyMessageInput)));
        center.add(makeCard("Dữ liệu chữ ký (r= và s=)",  wrapScroll(verifySignatureInput)));
        center.add(makeCard("Khóa công khai người gửi",    wrapScroll(verifyPublicKeyDisplay)));

        verifyButton = makeBtn("✅  Xác Minh Chữ Ký", BLUE, BLUE_LIGHT);
        verifyButton.setPreferredSize(new Dimension(230, 44));
        verifyButton.setFont(new Font("Dialog", Font.BOLD, 15));
        verifyButton.addActionListener(e -> handleVerifySignature());

        verifyStatusLabel = makeStatusLabel("Tiến trình: Đang chờ xác minh");

        verificationResultLabel = new JLabel("CHƯA XÁC MINH", SwingConstants.CENTER);
        verificationResultLabel.setFont(new Font("Serif", Font.BOLD, 17));
        verificationResultLabel.setOpaque(true);
        verificationResultLabel.setPreferredSize(new Dimension(0, 46));
        verificationResultLabel.setBackground(CARD_BG);
        verificationResultLabel.setForeground(MUTED);
        verificationResultLabel.setBorder(BorderFactory.createCompoundBorder(
                new RoundBorder(CARD_BORDER, 12, 1),
                BorderFactory.createEmptyBorder(0, 14, 0, 14)));

        JPanel resultArea = new JPanel(new BorderLayout(6, 6));
        resultArea.setOpaque(false);
        resultArea.add(verifyStatusLabel,       BorderLayout.NORTH);
        resultArea.add(verificationResultLabel, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout(14, 0));
        bottom.setOpaque(false);
        bottom.add(resultArea, BorderLayout.CENTER);
        JPanel vBtnWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 10));
        vBtnWrap.setOpaque(false);
        vBtnWrap.add(verifyButton);
        bottom.add(vBtnWrap, BorderLayout.EAST);

        tab.add(toolBar, BorderLayout.NORTH);
        tab.add(center,  BorderLayout.CENTER);
        tab.add(bottom,  BorderLayout.SOUTH);
        return tab;
    }

    // ════════════════════════════════════════════════════════════════════════════
    // TAB 4 – HELP
    // ════════════════════════════════════════════════════════════════════════════
    private JPanel buildHelpTab() {
        JPanel tab = makeTabPanel();
        instructionArea = makeCodeArea(20);
        instructionArea.setFont(new Font("Dialog", Font.PLAIN, 14));
        instructionArea.setText(
            "HƯỚNG DẪN SỬ DỤNG PHẦN MỀM CHỮ KÝ SỐ DSA\n" +
            "══════════════════════════════════════════════════════════\n\n" +
            "① Tab Tạo Khóa (Key Generation)\n" +
            "   • Nhấn 'Tạo khóa tự động' để sinh tham số (p, q, g) chuẩn 1024/160-bit\n" +
            "     và cặp khóa bí mật x / công khai y = g^x mod p.\n" +
            "   • Nhấn 'Lưu khóa ra tệp' để xuất file .txt.\n\n" +
            "② Tab Ký Văn Bản (Signing)\n" +
            "   • Nhập hoặc tải thông điệp vào ô 'Văn bản cần ký'.\n" +
            "   • Nhấn 'Thực Hiện Ký' — hệ thống dùng khóa bí mật (x) để sinh chữ ký (r, s).\n" +
            "   • Xuất chữ ký thành .txt để gửi cho bên nhận.\n\n" +
            "③ Tab Xác Minh (Verification)\n" +
            "   • Dán văn bản gốc, dán chữ ký (r= … / s= …), dán khóa công khai.\n" +
            "   • Nhấn 'Xác Minh Chữ Ký' để kiểm tra tính hợp lệ.\n" +
            "   • Kết quả: ✅ HỢP LỆ (xanh) hoặc ❌ KHÔNG HỢP LỆ (đỏ).\n\n" +
            "══════════════════════════════════════════════════════════\n" +
            "THUẬT TOÁN DSA (Digital Signature Algorithm) – FIPS 186-4\n" +
            "══════════════════════════════════════════════════════════\n\n" +
            "Tham số hệ thống:\n" +
            "  p : Số nguyên tố lớn 1024-bit\n" +
            "  q : Số nguyên tố 160-bit, q | (p − 1)\n" +
            "  g : Phần tử sinh nhóm con bậc q trong Z*_p\n\n" +
            "Phát sinh khóa:\n" +
            "  Chọn ngẫu nhiên x ∈ (1, q)       → khóa bí mật\n" +
            "  Tính y = g^x mod p                → khóa công khai\n\n" +
            "Ký thông điệp M:\n" +
            "  Chọn nonce k ∈ (1, q)  (KHÔNG tái sử dụng!)\n" +
            "  r = (g^k mod p) mod q\n" +
            "  s = k⁻¹ · (H(M) + x·r) mod q\n" +
            "  Chữ ký = (r, s)\n\n" +
            "Xác minh chữ ký (r, s) với khóa công khai y:\n" +
            "  w  = s⁻¹ mod q\n" +
            "  u1 = H(M)·w mod q\n" +
            "  u2 = r·w   mod q\n" +
            "  v  = (g^u1 · y^u2 mod p) mod q\n" +
            "  Hợp lệ ⟺ v == r\n\n" +
            "══════════════════════════════════════════════════════════\n" +
            "SO SÁNH DSA vs SCHNORR\n" +
            "══════════════════════════════════════════════════════════\n\n" +
            "  Thuộc tính        DSA              Schnorr\n" +
            "  ─────────────────────────────────────────────\n" +
            "  Chuẩn hóa        FIPS 186-4        Không chính thức\n" +
            "  Kích thước chữ ký ~320 bit         ~320 bit\n" +
            "  Tốc độ xác minh  Chậm hơn          Nhanh hơn\n" +
            "  Bảo mật provable Có                Có (random oracle)\n" +
            "  Dựa trên         DLP mod p         DLP mod p\n"
        );

        JScrollPane scroll = new JScrollPane(instructionArea);
        scroll.setBorder(BorderFactory.createLineBorder(CARD_BORDER, 1));
        scroll.getViewport().setBackground(CARD_BG);
        styleScrollBar(scroll);

        tab.add(makeCard("Hướng dẫn & Lý thuyết DSA", scroll), BorderLayout.CENTER);
        return tab;
    }

    // ════════════════════════════════════════════════════════════════════════════
    // HANDLERS
    // ════════════════════════════════════════════════════════════════════════════

    private void handleGenerateKeys() {
        keyStatusLabel.setText("⏳ Đang tạo tham số DSA (1024-bit)...");
        keyStatusLabel.setForeground(MUTED);
        generateKeysButton.setEnabled(false);
        randomKeysButton  .setEnabled(false);

        new Thread(() -> {
            try {
                DSAKeyPair kp = algorithm.generateKeys();
                SwingUtilities.invokeLater(() -> {
                    currentKeyPair = kp;
                    DSASignatureParams params = kp.getParams();

                    pDisplay.setText(hexWrap(params.getP()));
                    qDisplay.setText(hexWrap(params.getQ()));
                    gDisplay.setText(hexWrap(params.getG()));
                    privateKeyDisplay.setText(hexWrap(kp.getPrivateKey()));
                    publicKeyDisplay .setText(hexWrap(kp.getPublicKey()));
                    keySummaryDisplay.setText(
                        "Thuật toán  : DSA (FIPS 186-4)\n" +
                        "Kích thước p: " + params.getP().bitLength() + " bit\n" +
                        "Kích thước q: " + params.getQ().bitLength() + " bit\n" +
                        "Kích thước x: " + kp.getPrivateKey().bitLength() + " bit\n" +
                        "Kích thước y: " + kp.getPublicKey().bitLength()  + " bit\n"
                    );

                    keyStatusLabel.setText("✅ Đã tạo cặp khóa DSA thành công!");
                    keyStatusLabel.setForeground(TEAL);
                    generateKeysButton.setEnabled(true);
                    randomKeysButton  .setEnabled(true);

                    // Sao chép sang tab xác minh
                    if (verifyPublicKeyDisplay != null)
                        verifyPublicKeyDisplay.setText(hexWrap(kp.getPublicKey()));
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    keyStatusLabel.setText("❌ Lỗi: " + ex.getMessage());
                    keyStatusLabel.setForeground(DANGER);
                    generateKeysButton.setEnabled(true);
                    randomKeysButton  .setEnabled(true);
                });
            }
        }).start();
    }

    private void handleSignMessage() {
        if (!algorithm.hasKeyPair()) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng tạo cặp khóa trước khi ký!",
                "Chưa có khóa", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String msg = messageInput.getText().trim();
        if (msg.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng nhập văn bản cần ký!",
                "Thiếu nội dung", JOptionPane.WARNING_MESSAGE);
            return;
        }

        signStatusLabel.setText("⏳ Đang ký...");
        signStatusLabel.setForeground(MUTED);
        signMessageButton.setEnabled(false);

        new Thread(() -> {
            try {
                // Hiển thị hash trước
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] digest = md.digest(msg.getBytes(StandardCharsets.UTF_8));
                BigInteger hashVal = new BigInteger(1, digest);

                DSASignature sig = algorithm.sign(msg);

                SwingUtilities.invokeLater(() -> {
                    hashDisplay.setText("SHA-256(M) =\n" + hexWrap(hashVal));
                    signatureDisplay.setText(
                        "=== CHỮ KÝ DSA ===\n" +
                        "r =\n" + hexWrap(sig.getR()) +
                        "\n\ns =\n" + hexWrap(sig.getS())
                    );

                    // Cập nhật tab xác minh
                    verifyMessageInput  .setText(msg);
                    verifySignatureInput.setText(sig.serialize());

                    signStatusLabel.setText("✅ Đã ký thành công! Chữ ký (r, s) đã được tạo.");
                    signStatusLabel.setForeground(TEAL);
                    signMessageButton.setEnabled(true);
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    signStatusLabel.setText("❌ Lỗi: " + ex.getMessage());
                    signStatusLabel.setForeground(DANGER);
                    signMessageButton.setEnabled(true);
                });
            }
        }).start();
    }

    private void handleVerifySignature() {
        String msg    = verifyMessageInput  .getText().trim();
        String sigTxt = verifySignatureInput.getText().trim();
        String pubTxt = verifyPublicKeyDisplay.getText().trim();

        if (msg.isEmpty() || sigTxt.isEmpty() || pubTxt.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng điền đầy đủ: văn bản, chữ ký, và khóa công khai!",
                "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        verifyStatusLabel.setText("⏳ Đang xác minh...");
        verifyStatusLabel.setForeground(MUTED);
        verifyButton.setEnabled(false);

        new Thread(() -> {
            try {
                // Parse chữ ký từ text
                DSASignature sig = DSASignature.deserialize(sigTxt);

                // Nếu chưa có key pair nhưng có public key text → không thể verify
                // (cần full key pair với params). Dùng key pair hiện tại.
                if (!algorithm.hasKeyPair()) {
                    SwingUtilities.invokeLater(() -> {
                        verifyStatusLabel.setText("❌ Chưa có cặp khóa. Hãy tạo khóa trước.");
                        verifyStatusLabel.setForeground(DANGER);
                        verifyButton.setEnabled(true);
                    });
                    return;
                }

                boolean valid = algorithm.verify(msg, sig);

                SwingUtilities.invokeLater(() -> {
                    if (valid) {
                        verificationResultLabel.setText("✅  CHỮ KÝ HỢP LỆ  –  Văn bản toàn vẹn, nguồn gốc đã xác thực");
                        verificationResultLabel.setBackground(darkMode ? new Color(20, 60, 40) : new Color(220, 255, 240));
                        verificationResultLabel.setForeground(TEAL);
                        verifyStatusLabel.setText("✅ Xác minh thành công!");
                        verifyStatusLabel.setForeground(TEAL);
                    } else {
                        verificationResultLabel.setText("❌  CHỮ KÝ KHÔNG HỢP LỆ  –  Văn bản có thể bị giả mạo hoặc sai khóa");
                        verificationResultLabel.setBackground(darkMode ? new Color(60, 20, 20) : new Color(255, 230, 230));
                        verificationResultLabel.setForeground(DANGER);
                        verifyStatusLabel.setText("❌ Xác minh thất bại!");
                        verifyStatusLabel.setForeground(DANGER);
                    }
                    verifyButton.setEnabled(true);
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    verifyStatusLabel.setText("❌ Lỗi: " + ex.getMessage());
                    verifyStatusLabel.setForeground(DANGER);
                    verifyButton.setEnabled(true);
                });
            }
        }).start();
    }

    // ── Save / Load ───────────────────────────────────────────────────────────────
    private void saveKeyPairToFile() {
        if (currentKeyPair == null) {
            JOptionPane.showMessageDialog(this,
                "Chưa có cặp khóa để lưu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new java.io.File("dsa_keypair.txt"));
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                DSASignatureParams params = currentKeyPair.getParams();
                String content =
                    "=== DSA KEY PAIR ===\n" +
                    "p=\n"    + hexWrap(params.getP())               + "\n\n" +
                    "q=\n"    + hexWrap(params.getQ())               + "\n\n" +
                    "g=\n"    + hexWrap(params.getG())               + "\n\n" +
                    "x (private key)=\n" + hexWrap(currentKeyPair.getPrivateKey()) + "\n\n" +
                    "y (public key)=\n"  + hexWrap(currentKeyPair.getPublicKey())  + "\n";
                Files.writeString(fc.getSelectedFile().toPath(), content);
                JOptionPane.showMessageDialog(this,
                    "Đã lưu khóa thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                    "Lỗi lưu file: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadTextInto(JTextArea area) {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                String text = Files.readString(fc.getSelectedFile().toPath());
                area.setText(text);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                    "Lỗi tải file: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveTextFrom(JTextArea area, String defaultName) {
        if (area.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Không có nội dung để lưu!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JFileChooser fc = new JFileChooser();
        fc.setSelectedFile(new java.io.File(defaultName));
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                Files.writeString(fc.getSelectedFile().toPath(), area.getText());
                JOptionPane.showMessageDialog(this,
                    "Đã lưu thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                    "Lỗi lưu file: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ── Theme refresh ─────────────────────────────────────────────────────────────
    private void refreshTheme() {
        if (headerPanel instanceof GradientPanel gp)
            gp.setColors(HEADER_BG1, HEADER_BG2);
        if (mainPanel != null) mainPanel.repaint();
        if (headerTitleLabel    != null) headerTitleLabel   .setForeground(TITLE_CLR);
        if (headerSubtitleLabel != null) headerSubtitleLabel.setForeground(SUBTITLE_CLR);
        if (themeToggle != null) { themeToggle.setSelected(darkMode); themeToggle.repaint(); }
        if (verificationResultLabel != null
                && verificationResultLabel.getText().equals("CHƯA XÁC MINH")) {
            verificationResultLabel.setBackground(CARD_BG);
            verificationResultLabel.setForeground(MUTED);
        }
        refreshTree(mainPanel);
        SwingUtilities.updateComponentTreeUI(this);
        repaint();
    }

    private void refreshTree(Component c) {
        if (c == null) return;
        if (c instanceof JTextArea a) {
            a.setBackground(CARD_BG);
            a.setForeground(TEXT);
            a.setCaretColor(TEXT);
        } else if (c instanceof JScrollPane sp) {
            sp.getViewport().setBackground(CARD_BG);
            sp.setBorder(BorderFactory.createLineBorder(CARD_BORDER, 1));
            styleScrollBar(sp);
        } else if (c instanceof JPanel p) {
            if (p != headerPanel) p.setBackground(PANEL_BG);
        } else if (c instanceof JLabel l
                && l != headerTitleLabel && l != headerSubtitleLabel
                && l != verificationResultLabel) {
            if (l == keyStatusLabel || l == signStatusLabel || l == verifyStatusLabel) {
                if (l.getForeground().equals(TEAL) || l.getForeground().equals(TEAL_DARK)) return;
                l.setForeground(MUTED);
            } else { l.setForeground(TEXT); }
        }
        if (c instanceof Container ct)
            for (Component ch : ct.getComponents()) refreshTree(ch);
    }

    // ════════════════════════════════════════════════════════════════════════════
    // FACTORY HELPERS
    // ════════════════════════════════════════════════════════════════════════════

    private JPanel makeTabPanel() {
        JPanel p = new JPanel(new BorderLayout(0, 14));
        p.setBackground(PANEL_BG);
        p.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        return p;
    }

    private JPanel makeCard(String title, Component content) {
        ShadowCard card = new ShadowCard(CARD_BG, CARD_BORDER);
        card.setLayout(new BorderLayout(0, 8));
        card.setBorder(BorderFactory.createCompoundBorder(
                new RoundBorder(CARD_BORDER, 12, 1),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)));
        JLabel lbl = new JLabel(title);
        lbl.setFont(LABEL_FONT);
        lbl.setForeground(BLUE);
        javax.swing.JSeparator sep = new javax.swing.JSeparator();
        sep.setForeground(CARD_BORDER);
        sep.setBackground(CARD_BORDER);
        JPanel top = new JPanel(new BorderLayout(0, 6));
        top.setOpaque(false);
        top.add(lbl, BorderLayout.NORTH);
        top.add(sep, BorderLayout.SOUTH);
        card.add(top,     BorderLayout.NORTH);
        card.add(content, BorderLayout.CENTER);
        return card;
    }

    private JPanel makeCard(String title, JPanel content) {
        ShadowCard card = new ShadowCard(CARD_BG, CARD_BORDER);
        card.setLayout(new BorderLayout(0, 8));
        card.setBorder(BorderFactory.createCompoundBorder(
                new RoundBorder(CARD_BORDER, 12, 1),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)));
        JLabel lbl = new JLabel(title);
        lbl.setFont(LABEL_FONT);
        lbl.setForeground(BLUE);
        javax.swing.JSeparator sep = new javax.swing.JSeparator();
        sep.setForeground(CARD_BORDER);
        JPanel top = new JPanel(new BorderLayout(0, 6));
        top.setOpaque(false);
        top.add(lbl, BorderLayout.NORTH);
        top.add(sep, BorderLayout.SOUTH);
        card.add(top,     BorderLayout.NORTH);
        card.add(content, BorderLayout.CENTER);
        return card;
    }

    private JTextArea makeCodeArea(int rows) {
        JTextArea a = new JTextArea(rows, 20);
        a.setEditable(false);
        a.setLineWrap(true);
        a.setWrapStyleWord(true);
        a.setFont(CODE_FONT);
        a.setBackground(CARD_BG);
        a.setForeground(TEXT);
        a.setCaretColor(TEXT);
        a.setMargin(new Insets(6, 8, 6, 8));
        a.setSelectionColor(new Color(BLUE.getRed(), BLUE.getGreen(), BLUE.getBlue(), 90));
        return a;
    }

    private JTextArea makeEditableArea(int rows) {
        JTextArea a = makeCodeArea(rows);
        a.setEditable(true);
        return a;
    }

    private JScrollPane wrapScroll(JTextArea a) {
        JScrollPane sp = new JScrollPane(a);
        sp.setBorder(BorderFactory.createLineBorder(CARD_BORDER, 1));
        sp.getViewport().setBackground(CARD_BG);
        styleScrollBar(sp);
        return sp;
    }

    private void styleScrollBar(JScrollPane sp) {
        sp.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        sp.getVerticalScrollBar().setUI(new ThinScrollBarUI());
        sp.getVerticalScrollBar().setBackground(CARD_BG);
    }

    private JLabel makeFieldLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(LABEL_FONT);
        l.setForeground(TEXT);
        return l;
    }

    private JLabel makeStatusLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(STATUS_FONT);
        l.setForeground(MUTED);
        return l;
    }

    private JLabel sectionHeading(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Dialog", Font.BOLD, 14));
        l.setForeground(BLUE);
        return l;
    }

    private String hexWrap(BigInteger n) {
        String hex = n.toString(16).toUpperCase();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 64)
            sb.append(hex, i, Math.min(i + 64, hex.length())).append('\n');
        return sb.toString().trim();
    }

    private JButton makeBtn(String text, Color base, Color hover) {
        JButton btn = new JButton(text) {
            private boolean hovered = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hovered = true;  repaint(); }
                public void mouseExited (MouseEvent e) { hovered = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color c1 = hovered ? hover : base;
                Color c2 = hovered ? base  : base.darker();
                g2.setPaint(new GradientPaint(0, 0, c1, 0, getHeight(), c2));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(new Color(255, 255, 255, 40));
                g2.fillRoundRect(0, 0, getWidth(), getHeight() / 2, 10, 10);
                g2.setFont(getFont());
                g2.setColor(Color.WHITE);
                FontMetrics fm = g2.getFontMetrics();
                int tx = (getWidth()  - fm.stringWidth(getText())) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), tx, ty);
                g2.dispose();
            }
            @Override protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(base.darker());
                g2.setStroke(new java.awt.BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                g2.dispose();
            }
        };
        btn.setFont(BTN_FONT);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(175, 36));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ════════════════════════════════════════════════════════════════════════════
    // INNER CLASSES
    // ════════════════════════════════════════════════════════════════════════════

    static class GradientPanel extends JPanel {
        private Color c1, c2;
        GradientPanel(Color c1, Color c2) { this.c1 = c1; this.c2 = c2; setOpaque(false); }
        void setColors(Color c1, Color c2) { this.c1 = c1; this.c2 = c2; repaint(); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
            g2.dispose();
        }
    }

    static class RoundBorder implements javax.swing.border.Border {
        private final Color color; private final int arc, thick;
        RoundBorder(Color color, int arc, int thick) { this.color=color; this.arc=arc; this.thick=thick; }
        @Override public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new java.awt.BasicStroke(thick));
            g2.drawRoundRect(x, y, w-1, h-1, arc, arc);
            g2.dispose();
        }
        @Override public Insets getBorderInsets(Component c) { return new Insets(thick,thick,thick,thick); }
        @Override public boolean isBorderOpaque() { return false; }
    }

    class ShadowCard extends JPanel {
        ShadowCard(Color bg, Color border) { setBackground(bg); setOpaque(false); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for (int i = 4; i > 0; i--) {
                g2.setColor(new Color(0,0,0, darkMode ? 40-i*6 : 12-i*2));
                g2.fillRoundRect(i, i+1, getWidth()-i, getHeight()-i, 12, 12);
            }
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth()-4, getHeight()-4, 12, 12);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    static class ThinScrollBarUI extends BasicScrollBarUI {
        @Override protected void configureScrollBarColors() {
            thumbColor = new Color(150, 160, 175); trackColor = new Color(0,0,0,0); }
        @Override protected JButton createDecreaseButton(int o) { return zero(); }
        @Override protected JButton createIncreaseButton(int o) { return zero(); }
        private JButton zero() { JButton b=new JButton(); b.setPreferredSize(new Dimension(0,0)); return b; }
        @Override protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(thumbColor);
            g2.fillRoundRect(r.x+1, r.y+1, r.width-2, r.height-2, 6, 6);
            g2.dispose();
        }
        @Override protected void paintTrack(Graphics g, JComponent c, Rectangle r) {}
    }

    class ToggleSwitch extends JComponent {
        private boolean selected;
        private final List<ActionListener> listeners = new ArrayList<>();
        ToggleSwitch(boolean init) {
            selected = init;
            setPreferredSize(new Dimension(50, 26));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) { setSelected(!selected); fireAction(); }
            });
        }
        void addActionListener(ActionListener l) { listeners.add(l); }
        private void fireAction() {
            ActionEvent ev = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, selected?"ON":"OFF");
            listeners.forEach(l -> l.actionPerformed(ev));
        }
        void setSelected(boolean s) { selected = s; repaint(); }
        boolean isSelected() { return selected; }
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w=getWidth(), h=getHeight();
            Color track = selected ? BLUE : (darkMode ? new Color(70,78,92) : new Color(190,198,210));
            g2.setColor(track); g2.fillRoundRect(0,0,w,h,h,h);
            int ks=h-6, kx=selected?w-ks-3:3, ky=3;
            g2.setColor(new Color(0,0,0,35)); g2.fillOval(kx+1,ky+2,ks,ks);
            g2.setColor(Color.WHITE); g2.fillOval(kx,ky,ks,ks);
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
        catch (Exception ignored) {}
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext","true");
        SwingUtilities.invokeLater(DSAGUI::new);
    }
}
