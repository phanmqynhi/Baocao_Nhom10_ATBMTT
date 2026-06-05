import java.math.BigInteger;

/**
 * Lớp đại diện cho một chữ ký DSA
 *
 *  r = (g^k mod p) mod q
 *  s = k^{-1} · (H(M) + x·r) mod q
 *
 * Chữ ký hợp lệ ⟺ (r, s) ≠ (0, 0) và r, s ∈ (0, q).
 */
public class DSASignature {

    private final BigInteger r;
    private final BigInteger s;

    public DSASignature(BigInteger r, BigInteger s) {
        this.r = r;
        this.s = s;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────
    public BigInteger getR() { return r; }
    public BigInteger getS() { return s; }

    /**
     * Tuần tự hóa thành chuỗi văn bản để lưu / truyền.
     * Định dạng: "r=<hex>\ns=<hex>"
     */
    public String serialize() {
        return "r=" + r.toString(16).toUpperCase() +
               "\ns=" + s.toString(16).toUpperCase();
    }

    /**
     * Giải tuần tự hóa từ chuỗi do serialize() tạo ra.
     */
    public static DSASignature deserialize(String text) {
        try {
            String[] lines = text.trim().split("\\s+");
            BigInteger rVal = null, sVal = null;
            for (String line : lines) {
                line = line.trim();
                if (line.startsWith("r=") || line.startsWith("R="))
                    rVal = new BigInteger(line.substring(2), 16);
                else if (line.startsWith("s=") || line.startsWith("S="))
                    sVal = new BigInteger(line.substring(2), 16);
            }
            if (rVal == null || sVal == null)
                throw new IllegalArgumentException("Định dạng chữ ký không hợp lệ (cần r= và s=)");
            return new DSASignature(rVal, sVal);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Giá trị hex trong chữ ký không hợp lệ: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        String rHex = r.toString(16).toUpperCase();
        String sHex = s.toString(16).toUpperCase();
        int max = 64;
        if (rHex.length() > max) rHex = rHex.substring(0, max) + "...";
        if (sHex.length() > max) sHex = sHex.substring(0, max) + "...";
        return "DSASignature{\n  r=" + rHex + "\n  s=" + sHex + "\n}";
    }
}
