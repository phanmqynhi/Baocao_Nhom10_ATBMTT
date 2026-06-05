import java.math.BigInteger;
import java.security.*;
import java.security.spec.DSAParameterSpec;

/**
 * Lớp chứa các tham số cơ bản cho thuật toán DSA (Digital Signature Algorithm)
 * Chuẩn FIPS 186-4: sử dụng bộ tham số (p, q, g) sinh bởi Java DSA provider
 *
 *  p : Số nguyên tố lớn 1024-bit
 *  q : Số nguyên tố 160-bit, q | (p − 1)
 *  g : Phần tử sinh nhóm con bậc q trong Z*_p
 */
public class DSASignatureParams {

    private final BigInteger p;
    private final BigInteger q;
    private final BigInteger g;

    public DSASignatureParams(BigInteger p, BigInteger q, BigInteger g) {
        this.p = p;
        this.q = q;
        this.g = g;
    }

    /**
     * Sinh tham số hệ thống DSA chuẩn 1024/160-bit
     * Dùng AlgorithmParameterGenerator của Java để đảm bảo đúng chuẩn FIPS.
     */
    public static DSASignatureParams generateSystemParameters() {
        try {
            AlgorithmParameterGenerator apg =
                    AlgorithmParameterGenerator.getInstance("DSA");
            apg.init(1024);
            AlgorithmParameters ap = apg.generateParameters();
            DSAParameterSpec spec = ap.getParameterSpec(DSAParameterSpec.class);
            return new DSASignatureParams(spec.getP(), spec.getQ(), spec.getG());
        } catch (Exception e) {
            throw new RuntimeException("Không thể sinh tham số DSA: " + e.getMessage(), e);
        }
    }

    // ── Getters ──────────────────────────────────────────────────────────────────
    public BigInteger getP() { return p; }
    public BigInteger getQ() { return q; }
    public BigInteger getG() { return g; }

    @Override
    public String toString() {
        return "DSASignatureParams{p=" + p.bitLength() + "-bit, q="
                + q.bitLength() + "-bit, g=" + g.bitLength() + "-bit}";
    }
}
