import java.math.BigInteger;

/**
 * Lớp đại diện cho cặp khóa công khai / bí mật của DSA
 *
 *  x : Khóa bí mật (private key),  x ∈ (1, q)
 *  y : Khóa công khai (public key), y = g^x mod p
 */
public class DSAKeyPair {

    private final BigInteger privateKey;   // x
    private final BigInteger publicKey;    // y = g^x mod p
    private final DSASignatureParams params;

    public DSAKeyPair(BigInteger privateKey, BigInteger publicKey,
                      DSASignatureParams params) {
        this.privateKey = privateKey;
        this.publicKey  = publicKey;
        this.params     = params;
    }

    // ── Getters ──────────────────────────────────────────────────────────────────
    public BigInteger getPrivateKey() { return privateKey; }
    public BigInteger getPublicKey()  { return publicKey;  }
    public DSASignatureParams getParams() { return params; }

    @Override
    public String toString() {
        int len = 32;
        String xHex = privateKey.toString(16);
        String yHex = publicKey.toString(16);
        return "DSAKeyPair{" +
                "x=" + xHex.substring(0, Math.min(len, xHex.length())) + "..." +
                ", y=" + yHex.substring(0, Math.min(len, yHex.length())) + "...}";
    }
}
