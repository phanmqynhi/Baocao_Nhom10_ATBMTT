import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Triển khai thuật toán DSA (Digital Signature Algorithm) – FIPS 186-4
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * GIAI ĐOẠN 1 – KEY GENERATION
 * ─────────────────────────────────────────────────────────────────────────────
 *   Tham số hệ thống (p, q, g) được sinh bởi Java DSA provider (1024/160-bit).
 *   1. Chọn ngẫu nhiên x ∈ (1, q)  →  khóa bí mật
 *   2. Tính y = g^x mod p           →  khóa công khai
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * GIAI ĐOẠN 2 – SIGNING
 * ─────────────────────────────────────────────────────────────────────────────
 *   Ký thông điệp M với khóa bí mật x:
 *   1. Chọn ngẫu nhiên k ∈ (1, q)  (nonce bí mật, KHÔNG tái sử dụng!)
 *   2. r = (g^k mod p) mod q
 *   3. h = H(M)  dùng SHA-256 (rút gọn mod q)
 *   4. s = k^{-1} · (h + x·r) mod q
 *   5. Nếu r = 0 hoặc s = 0, quay lại bước 1
 *   Chữ ký = (r, s)
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * GIAI ĐOẠN 3 – VERIFICATION
 * ─────────────────────────────────────────────────────────────────────────────
 *   Xác minh chữ ký (r, s) cho thông điệp M với khóa công khai y:
 *   1. Kiểm tra r, s ∈ (0, q); nếu không → từ chối
 *   2. w  = s^{-1} mod q
 *   3. h  = H(M) mod q
 *   4. u1 = h·w  mod q
 *   5. u2 = r·w  mod q
 *   6. v  = (g^{u1} · y^{u2} mod p) mod q
 *   7. Hợp lệ ⟺ v == r
 */
public class DSASignatureAlgorithm {

    private DSAKeyPair keyPair;
    private final SecureRandom random = new SecureRandom();

    // ─────────────────────────────────────────────────────────────────────────
    // GIAI ĐOẠN 1: Phát sinh khóa (Key Generation)
    // ─────────────────────────────────────────────────────────────────────────
    public DSAKeyPair generateKeys() {
        DSASignatureParams params = DSASignatureParams.generateSystemParameters();

        BigInteger q = params.getQ();
        BigInteger g = params.getG();
        BigInteger p = params.getP();

        // Chọn ngẫu nhiên x ∈ (1, q)
        BigInteger x;
        do {
            x = new BigInteger(q.bitLength(), random);
        } while (x.compareTo(BigInteger.ONE) <= 0 || x.compareTo(q) >= 0);

        // Tính y = g^x mod p
        BigInteger y = g.modPow(x, p);

        this.keyPair = new DSAKeyPair(x, y, params);
        return keyPair;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GIAI ĐOẠN 2: Tạo chữ ký (Signing)
    // ─────────────────────────────────────────────────────────────────────────
    public DSASignature sign(String message) throws Exception {
        if (keyPair == null)
            throw new IllegalStateException("Chưa phát sinh khóa. Vui lòng gọi generateKeys() trước.");

        DSASignatureParams params = keyPair.getParams();
        BigInteger p = params.getP();
        BigInteger q = params.getQ();
        BigInteger g = params.getG();
        BigInteger x = keyPair.getPrivateKey();

        BigInteger r, s = null, k;

        do {
            // Bước 1: Chọn nonce bí mật k ∈ (1, q)
            do {
                k = new BigInteger(q.bitLength(), random);
            } while (k.compareTo(BigInteger.ONE) <= 0 || k.compareTo(q) >= 0);

            // Bước 2: r = (g^k mod p) mod q
            r = g.modPow(k, p).mod(q);

            if (r.equals(BigInteger.ZERO)) continue;

            // Bước 3: h = H(M) mod q
            BigInteger h = hashSHA256(message, q);

            // Bước 4: s = k^{-1} · (h + x·r) mod q
            BigInteger kInv = k.modInverse(q);
            s = kInv.multiply(h.add(x.multiply(r))).mod(q);

        } while (s.equals(BigInteger.ZERO));

        return new DSASignature(r, s);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GIAI ĐOẠN 3: Xác minh chữ ký (Verification)
    // ─────────────────────────────────────────────────────────────────────────
    public boolean verify(String message, DSASignature signature) throws Exception {
        if (keyPair == null)
            throw new IllegalStateException("Chưa có khóa. Vui lòng gọi generateKeys() trước.");

        DSASignatureParams params = keyPair.getParams();
        BigInteger p = params.getP();
        BigInteger q = params.getQ();
        BigInteger g = params.getG();
        BigInteger y = keyPair.getPublicKey();

        BigInteger r = signature.getR();
        BigInteger s = signature.getS();

        // Bước 1: Kiểm tra phạm vi r, s ∈ (0, q)
        if (r.compareTo(BigInteger.ZERO) <= 0 || r.compareTo(q) >= 0) return false;
        if (s.compareTo(BigInteger.ZERO) <= 0 || s.compareTo(q) >= 0) return false;

        // Bước 2: w = s^{-1} mod q
        BigInteger w = s.modInverse(q);

        // Bước 3: h = H(M) mod q
        BigInteger h = hashSHA256(message, q);

        // Bước 4: u1 = h·w mod q
        BigInteger u1 = h.multiply(w).mod(q);

        // Bước 5: u2 = r·w mod q
        BigInteger u2 = r.multiply(w).mod(q);

        // Bước 6: v = (g^{u1} · y^{u2} mod p) mod q
        BigInteger gu1 = g.modPow(u1, p);
        BigInteger yu2 = y.modPow(u2, p);
        BigInteger v   = gu1.multiply(yu2).mod(p).mod(q);

        // Bước 7: Hợp lệ ⟺ v == r
        return v.equals(r);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Hàm băm SHA-256 → BigInteger mod q
    // ─────────────────────────────────────────────────────────────────────────
    private BigInteger hashSHA256(String message, BigInteger q)
            throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(message.getBytes(StandardCharsets.UTF_8));
        return new BigInteger(1, digest).mod(q);
    }

    // ── Getters ──────────────────────────────────────────────────────────────────
    public DSAKeyPair getKeyPair() { return keyPair; }
    public boolean hasKeyPair()   { return keyPair != null; }
}
