import java.util.Scanner;

public class PlayfairCipher {

    private static final int MATRIX_SIZE = 5;

    public static String preprocessText(String text) {
        // Remove spaces and non-alphabet characters, and convert to uppercase
        text = text.replaceAll("[^a-zA-Z]", "");
        return text.toUpperCase();
    }

    public static char[][] generateKeyMatrix(String key) {
        key = key.replaceAll("[^a-zA-Z]", ""); // Remove non-alphabet characters
        key = key + "ABCDEFGHIKLMNOPQRSTUVWXYZ"; // Append the remaining letters

        char[][] matrix = new char[MATRIX_SIZE][MATRIX_SIZE];
        int index = 0;

        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                matrix[i][j] = key.charAt(index);
                index++;
            }
        }

        return matrix;
    }

    public static String encrypt(String plaintext, char[][] keyMatrix) {
        StringBuilder ciphertext = new StringBuilder();
        int length = plaintext.length();
        for (int i = 0; i < length; i += 2) {
            char firstChar = plaintext.charAt(i);
            char secondChar = (i + 1 < length) ? plaintext.charAt(i + 1) : 'X';

            int[] firstCharPos = findPosition(keyMatrix, firstChar);
            int[] secondCharPos = findPosition(keyMatrix, secondChar);

            if (firstCharPos[0] == secondCharPos[0]) { // Same row
                ciphertext.append(keyMatrix[firstCharPos[0]][(firstCharPos[1] + 1) % MATRIX_SIZE]);
                ciphertext.append(keyMatrix[secondCharPos[0]][(secondCharPos[1] + 1) % MATRIX_SIZE]);
            } else if (firstCharPos[1] == secondCharPos[1]) { // Same column
                ciphertext.append(keyMatrix[(firstCharPos[0] + 1) % MATRIX_SIZE][firstCharPos[1]]);
                ciphertext.append(keyMatrix[(secondCharPos[0] + 1) % MATRIX_SIZE][secondCharPos[1]]);
            } else { // Different rows and columns
                ciphertext.append(keyMatrix[firstCharPos[0]][secondCharPos[1]]);
                ciphertext.append(keyMatrix[secondCharPos[0]][firstCharPos[1]]);
            }
        }
        return ciphertext.toString();
    }

    public static String decrypt(String ciphertext, char[][] keyMatrix) {
        StringBuilder plaintext = new StringBuilder();
        int length = ciphertext.length();
        for (int i = 0; i < length; i += 2) {
            char firstChar = ciphertext.charAt(i);
            char secondChar = (i + 1 < length) ? ciphertext.charAt(i + 1) : 'X';

            int[] firstCharPos = findPosition(keyMatrix, firstChar);
            int[] secondCharPos = findPosition(keyMatrix, secondChar);

            if (firstCharPos[0] == secondCharPos[0]) { // Same row
                plaintext.append(keyMatrix[firstCharPos[0]][(firstCharPos[1] - 1 + MATRIX_SIZE) % MATRIX_SIZE]);
                plaintext.append(keyMatrix[secondCharPos[0]][(secondCharPos[1] - 1 + MATRIX_SIZE) % MATRIX_SIZE]);
            } else if (firstCharPos[1] == secondCharPos[1]) { // Same column
                plaintext.append(keyMatrix[(firstCharPos[0] - 1 + MATRIX_SIZE) % MATRIX_SIZE][firstCharPos[1]]);
                plaintext.append(keyMatrix[(secondCharPos[0] - 1 + MATRIX_SIZE) % MATRIX_SIZE][secondCharPos[1]]);
            } else { // Different rows and columns
                plaintext.append(keyMatrix[firstCharPos[0]][secondCharPos[1]]);
                plaintext.append(keyMatrix[secondCharPos[0]][firstCharPos[1]]);
            }
        }
        return plaintext.toString();
    }

    public static int[] findPosition(char[][] keyMatrix, char target) {
        int[] position = new int[2];
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (keyMatrix[i][j] == target) {
                    position[0] = i;
                    position[1] = j;
                    return position;
                }
            }
        }
        return position;
    }

    public static void bruteForceDecrypt(String ciphertext) {
        System.out.println("Brute Force Decryption:");
        for (int shift = 0; shift < MATRIX_SIZE; shift++) {
            char[][] shiftedKeyMatrix = generateShiftedKeyMatrix(shift);
            String plaintext = decrypt(ciphertext, shiftedKeyMatrix);
            System.out.println("Shift " + shift + ": " + plaintext);
        }
    }

    public static char[][] generateShiftedKeyMatrix(int shift) {
        char[][] matrix = new char[MATRIX_SIZE][MATRIX_SIZE];

        // Generate a shifted key matrix
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                matrix[i][j] = (char) ('A' + (MATRIX_SIZE * i + (j + shift)) % (MATRIX_SIZE * MATRIX_SIZE));
            }
        }

        return matrix;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String key, text;

        while (true) {
            System.out.println("Playfair Cipher");
            System.out.println("1. Encrypt");
            System.out.println("2. Decrypt");
            System.out.println("3. Brute Force Decryption");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1/2/3/4): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter key (no spaces, uppercase): ");
                    key = scanner.next();
                    System.out.print("Enter plaintext (uppercase only): ");
                    text = scanner.next();
                    text = preprocessText(text);
                    char[][] keyMatrix = generateKeyMatrix(key);
                    String ciphertext = encrypt(text, keyMatrix);
                    System.out.println("Encrypted message: " + ciphertext);
                    break;

                case 2:
                    System.out.print("Enter key (no spaces, uppercase): ");
                    key = scanner.next();
                    System.out.print("Enter ciphertext: ");
                    text = scanner.next();
                    char[][] decryptKeyMatrix = generateKeyMatrix(key);
                    String decryptedText = decrypt(text, decryptKeyMatrix);
                    System.out.println("Decrypted message: " + decryptedText);
                    break;

                case 3:
                    System.out.print("Enter ciphertext: ");
                    text = scanner.next();
                    bruteForceDecrypt(text);
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
