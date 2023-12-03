import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.Scanner;

public class ElGamalDigitalSignature {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        KeyPair keyPair = generateKeys();

        System.out.println("Public Key (p, g, y, q): " + keyPair.publicKey);
        System.out.println("Private Key (x): " + keyPair.privateKey);

        System.out.print("\nEnter a message to sign: ");
        String message = scanner.nextLine();

        BigInteger[] signature = generateSignature(message, keyPair);
        System.out.println("\nDigital Signature (r, s): " + signature[0] + ", " + signature[1]);

        boolean isVerified = verifySignature(message, signature, keyPair.publicKey);
        System.out.println("Signature is " + (isVerified ? "verified." : "NOT verified."));

        scanner.close();
    }

    private static KeyPair generateKeys() {
        Random random = new Random();
        BigInteger q = BigInteger.probablePrime(160, random);
        BigInteger p = q.multiply(BigInteger.valueOf(2)).add(BigInteger.ONE);
        BigInteger g, x;

        do {
            g = new BigInteger(p.bitLength(), random);
        } while (g.compareTo(BigInteger.TWO) < 0 || g.compareTo(p.subtract(BigInteger.ONE)) > 0);

        do {
            x = new BigInteger(q.bitLength(), random);
        } while (x.compareTo(q) >= 0);

        BigInteger y = g.modPow(x, p);
        return new KeyPair(new Key(p, g, y, q), x);
    }

    private static BigInteger[] generateSignature(String message, KeyPair keyPair) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedMessage = messageDigest.digest(message.getBytes());
            BigInteger m = new BigInteger(1, hashedMessage);

            Random random = new Random();
            BigInteger k;

            do {
                k = new BigInteger(keyPair.publicKey.q.bitLength(), random);
            } while (k.compareTo(keyPair.publicKey.q.subtract(BigInteger.TWO)) >= 0);

            BigInteger r = keyPair.publicKey.g.modPow(k, keyPair.publicKey.p).mod(keyPair.publicKey.p);
            BigInteger s = k.modInverse(keyPair.publicKey.q).multiply(m.add(keyPair.privateKey.multiply(r))).mod(keyPair.publicKey.q);

            return new BigInteger[]{r, s};
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found.");
        }
    }

    private static boolean verifySignature(String message, BigInteger[] signature, Key publicKey) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedMessage = messageDigest.digest(message.getBytes());
            BigInteger m = new BigInteger(1, hashedMessage);

            BigInteger w = signature[1].modInverse(publicKey.q);
            BigInteger u1 = m.multiply(w).mod(publicKey.q);
            BigInteger u2 = signature[0].multiply(w).mod(publicKey.q);

            BigInteger v1 = publicKey.g.modPow(u1, publicKey.p).multiply(publicKey.y.modPow(u2, publicKey.p)).mod(publicKey.p).mod(publicKey.q);

            return v1.equals(signature[0]);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found.");
        }
    }

    private static class Key {
        private final BigInteger p, g, y, q;

        public Key(BigInteger p, BigInteger g, BigInteger y, BigInteger q) {
            this.p = p;
            this.g = g;
            this.y = y;
            this.q = q;
        }

        @Override
        public String toString() {
            return "(" + p + ", " + g + ", " + y + ", " + q + ")";
        }
    }

    private static class KeyPair {
        private final Key publicKey;
        private final BigInteger privateKey;

        public KeyPair(Key publicKey, BigInteger privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }
    }
}
