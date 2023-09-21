import java.util.Scanner;

public class AffineCipher {

    public static String encrypt(String plaintext, int a, int b) {
        StringBuilder ciphertext = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            if (plaintext.charAt(c) >= 'a' && plaintext.charAt(c) <= 'z') {
                for (char A : plaintext.toCharArray()) {
                    if (Character.isLowerCase(c)) {
                        int x = A - 'a';
                        int encryptedChar = (a * x + b) % 26 + 'A';
                        ciphertext.append((char) encryptedChar);
                    } else {
                        ciphertext.append(A);
                    }
                }
            } else {
                System.out.println("Please Enter plaintext in lowercase");
            }
        }
        return ciphertext.toString();
    }

    public static String decrypt(String ciphertext, int a, int b) {
        StringBuilder plaintext = new StringBuilder();
        int aInverse = -1;
        for (int i = 0; i < 26; i++) {
            if ((a * i) % 26 == 1) {
                aInverse = i;
                break;
            }
        }

        if (aInverse == -1) {
            System.out.println("The 'a' value does not have a multiplicative inverse.");
            return "";
        }

        for (char c : ciphertext.toCharArray()) {
            if (Character.isUpperCase(c)) {
                int y = c - 'A';
                int decryptedChar = aInverse * (y - b + 26) % 26 + 'a';
                plaintext.append((char) decryptedChar);
            } else {
                plaintext.append(c);
            }
        }
        return plaintext.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a, b;
        String text;

        while (true) {
            System.out.println("Affine Cipher");
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
                    System.out.print("Enter 'a' value (an integer coprime with 26): ");
                    a = scanner.nextInt();
                    System.out.print("Enter 'b' value (an integer): ");
                    b = scanner.nextInt();
                    String ciphertext = encrypt(text, a, b);
                    System.out.println("Encrypted message (uppercase): " + ciphertext);
                    break;

                case 2:
                    System.out.print("Enter ciphertext (uppercase only): ");
                    scanner.nextLine(); // Consume the newline character
                    text = scanner.nextLine();
                    System.out.print("Enter 'a' value (an integer coprime with 26): ");
                    a = scanner.nextInt();
                    System.out.print("Enter 'b' value (an integer): ");
                    b = scanner.nextInt();
                    String decryptedText = decrypt(text, a, b);
                    System.out.println("Decrypted message (lowercase): " + decryptedText);
                    break;

                case 3:
                    System.out.print("Enter ciphertext (uppercase only): ");
                    scanner.nextLine(); // Consume the newline character
                    text = scanner.nextLine();
                    System.out.println("Brute Force Decryption:");
                    for (int i = 1; i < 26; i++) {
                        for (int j = 0; j < 26; j++) {
                            String possibleDecryption = decrypt(text, i, j);
                            System.out.println("a=" + i + ", b=" + j + ": " + possibleDecryption);
                        }
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