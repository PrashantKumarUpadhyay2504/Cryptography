import java.util.Scanner;

public class AutokeyCipher {

    public static String autokeyEncrypt(String plaintext, String key) {
        plaintext = plaintext.toLowerCase();
        key = key.toLowerCase();
        StringBuilder ciphertext = new StringBuilder();
        StringBuilder keyStream = new StringBuilder(key + plaintext);

        for (int i = 0; i < plaintext.length(); i++) {
            char charPlain = plaintext.charAt(i);
            char charKey = keyStream.charAt(i);

            if (Character.isAlphabetic(charPlain)) {
                int charOffset = (charPlain + charKey - 2 * 'a') % 26;
                char charCipher = (char) (charOffset + 'A');
                ciphertext.append(charCipher);
            } else {
                ciphertext.append(charPlain);
            }
        }

        return ciphertext.toString();
    }

    public static String autokeyDecrypt(String ciphertext, String key) {
        ciphertext = ciphertext.toUpperCase();
        key = key.toLowerCase();
        StringBuilder plaintext = new StringBuilder();
        StringBuilder keyStream = new StringBuilder(key + ciphertext);

        for (int i = 0; i < ciphertext.length(); i++) {
            char charCipher = ciphertext.charAt(i);
            char charKey = keyStream.charAt(i);

            if (Character.isAlphabetic(charCipher)) {
                int charOffset = (charCipher - charKey + 26) % 26;
                char charPlain = (char) (charOffset + 'a');
                plaintext.append(charPlain);
            } else {
                plaintext.append(charCipher);
            }
        }

        return plaintext.toString();
    }

    public static void bruteForceAttack(String ciphertext) {
        ciphertext = ciphertext.toUpperCase();

        for (int i = 0; i < 26; i++) {
            char key = (char) ((i % 26) + 'a');
            String plaintextAttempt = autokeyDecrypt(ciphertext, String.valueOf(key));
            System.out.println("Key: " + Character.toUpperCase(key) + " - Decrypted Text: " + plaintextAttempt);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Encrypt");
            System.out.println("2. Decrypt");
            System.out.println("3. Brute Force Attack");
            System.out.println("4. Exit");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (option) {
                case 1:
                    System.out.print("Enter the plaintext: ");
                    String plaintextEncrypt = scanner.nextLine();
                    System.out.print("Enter the key: ");
                    String keyEncrypt = scanner.nextLine();
                    String ciphertext = autokeyEncrypt(plaintextEncrypt, keyEncrypt);
                    System.out.println("Ciphertext: " + ciphertext);
                    break;

                case 2:
                    System.out.print("Enter the ciphertext: ");
                    String ciphertextDecrypt = scanner.nextLine();
                    System.out.print("Enter the key: ");
                    String keyDecrypt = scanner.nextLine();
                    String decryptedText = autokeyDecrypt(ciphertextDecrypt, keyDecrypt);
                    System.out.println("Decrypted Text: " + decryptedText);
                    break;

                case 3:
                    System.out.print("Enter the ciphertext for brute force attack: ");
                    String ciphertextBruteForce = scanner.nextLine();
                    System.out.println("\nBrute Force Attack:");
                    bruteForceAttack(ciphertextBruteForce);
                    break;

                case 4:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
