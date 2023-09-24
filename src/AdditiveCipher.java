import java.util.Scanner;
import java.util.*;

public class AdditiveCipher {

    public static String encrypt(String plaintext, int key) {
        StringBuilder ciphertext = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            if (Character.isLowerCase(c)) {
                char encryptedChar = (char) (((c - 'a' + key) % 26) + 'A');
                ciphertext.append(encryptedChar);
            } else {
                ciphertext.append(c);
            }
        }
        return ciphertext.toString();
    }

    public static String decrypt(String ciphertext, int key) {
        StringBuilder plaintext = new StringBuilder();
        for (char c : ciphertext.toCharArray()) {
            if (Character.isUpperCase(c)) {
                char decryptedChar = (char) (((c - 'A' - key + 26) % 26) + 'a');
                plaintext.append(decryptedChar);
            } else {
                plaintext.append(c);
            }
        }
        return plaintext.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int key;
        String text;

        while (true) {
            System.out.println("Additive Cipher");
            System.out.println("1. Encrypt");
            System.out.println("2. Decrypt");
            System.out.println("3. Brute Force Decryption");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1/2/3/4): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter plaintext (lowercase only): ");
                    scanner.nextLine(); // Consume the newline character
                    text = scanner.nextLine();
                    if (text.matches("[a-z]+$")) {
                        System.out.print("Enter key (positive integer): ");
                        key = scanner.nextInt();
                        String ciphertext = encrypt(text, key);
                        System.out.println("Encrypted message (uppercase): " + ciphertext);
                    } else {
                        System.out.println("Please Enter in LowerCase");
                    }

                    break;

                case 2:
                    System.out.print("Enter ciphertext (uppercase only): ");
                    scanner.nextLine(); // Consume the newline cha1racter
                    text = scanner.nextLine();
                    if (text.matches("[A-Z]+$")) {
                        System.out.print("Enter key (positive integer): ");
                        key = scanner.nextInt();
                        String decryptedText = decrypt(text, key);
                        System.out.println("Decrypted message (lowercase): " + decryptedText);
                    } else {
                        System.out.println("Please Enter in UpperCase");
                    }
                    break;

                case 3:
                    System.out.print("Enter ciphertext (uppercase only): ");
                    scanner.nextLine(); // Consume the newline character
                    text = scanner.nextLine();
                    System.out.println("Brute Force Decryption:");
                    for (int i = 1; i <= 25; i++) {
                        String possibleDecryption = decrypt(text, i);
                        System.out.println("Key " + i + ": " + possibleDecryption);
                    }
                    break;

                case 4:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
            }
        }
    }
}