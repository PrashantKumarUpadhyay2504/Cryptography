import java.util.Scanner;
import java.math.BigInteger;

public class DiffieHellman {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a prime number (p): ");
        BigInteger p = new BigInteger(scanner.nextLine());

        System.out.print("Enter a primitive root modulo p (g): ");
        BigInteger g = new BigInteger(scanner.nextLine());

        System.out.print("Enter Alice's private key (a): ");
        BigInteger aPrivate = new BigInteger(scanner.nextLine());

        System.out.print("Enter Bob's private key (b): ");
        BigInteger bPrivate = new BigInteger(scanner.nextLine());

        // Calculate public keys for Alice and Bob
        BigInteger A = g.modPow(aPrivate, p); // A = g^a mod p
        BigInteger B = g.modPow(bPrivate, p); // B = g^b mod p

        // Shared secret key calculation
        BigInteger sharedKeyA = B.modPow(aPrivate, p); // sharedKeyA = B^a mod p
        BigInteger sharedKeyB = A.modPow(bPrivate, p); // sharedKeyB = A^b mod p

        // Ensure that the shared keys match
        if (sharedKeyA.equals(sharedKeyB)) {
            System.out.println("\nShared Secret Key: " + sharedKeyA);

            System.out.print("\nEnter a message to encrypt: ");
            String message = scanner.nextLine();

            // Encrypt the message using the shared key
            String encryptedMessage = encrypt(message, sharedKeyA);
            System.out.println("Encrypted Message: " + encryptedMessage);

            // Decrypt the message using the shared key
            String decryptedMessage = decrypt(encryptedMessage, sharedKeyA);
            System.out.println("Decrypted Message: " + decryptedMessage);
        } else {
            System.out.println("Error: Shared keys do not match.");
        }

        scanner.close();
    }

    private static String encrypt(String message, BigInteger key) {
        StringBuilder encryptedMessage = new StringBuilder();
        for (char c : message.toCharArray()) {
            int charValue = c;
            int encryptedValue = charValue ^ key.intValue(); // XOR operation
            encryptedMessage.append((char) encryptedValue);
        }
        return encryptedMessage.toString();
    }

    private static String decrypt(String encryptedMessage, BigInteger key) {
        return encrypt(encryptedMessage, key); // XOR operation is its own inverse
    }
}
