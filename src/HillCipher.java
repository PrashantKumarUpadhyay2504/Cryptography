import java.util.Scanner;

public class HillCipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get the key matrix from the user
        System.out.print("Enter the key matrix dimension (e.g., 2 for a 2x2 matrix): ");
        int dimension = scanner.nextInt();
        int[][] keyMatrix = new int[dimension][dimension];

        System.out.println("Enter the key matrix values:");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                keyMatrix[i][j] = scanner.nextInt();
            }
        }

        // Calculate the key matrix inverse
        int[][] keyInverse = getKeyInverse(keyMatrix, 26); // 26 is the size of the English alphabet

        // Display the key matrix and its inverse
        System.out.println("Key Matrix:");
        printMatrix(keyMatrix);

        System.out.println("Key Inverse:");
        printMatrix(keyInverse);

        // Get the message from the user
        System.out.print("Enter the message to encrypt: ");
        scanner.nextLine(); // Consume the newline character
        String message = scanner.nextLine().toUpperCase();

        // Encrypt the message
        String encryptedMessage = encrypt(message, keyMatrix, 26);
        System.out.println("Encrypted Message: " + encryptedMessage);

        // Decrypt the message
        String decryptedMessage = decrypt(encryptedMessage, keyInverse, 26);
        System.out.println("Decrypted Message: " + decryptedMessage);

        scanner.close();
    }

    private static int[][] getKeyInverse(int[][] keyMatrix, int mod) {
        int det = determinant(keyMatrix, mod);
        int detInverse = modInverse(det, mod);
        int[][] adjugate = adjugateMatrix(keyMatrix, mod);

        int[][] keyInverse = new int[keyMatrix.length][keyMatrix.length];

        for (int i = 0; i < keyMatrix.length; i++) {
            for (int j = 0; j < keyMatrix.length; j++) {
                keyInverse[i][j] = (adjugate[i][j] * detInverse) % mod;
                if (keyInverse[i][j] < 0) {
                    keyInverse[i][j] += mod;
                }
            }
        }

        return keyInverse;
    }

    private static int determinant(int[][] matrix, int mod) {
        int n = matrix.length;
        int det = 1;

        for (int i = 0; i < n; i++) {
            int pivot = matrix[i][i];

            if (pivot == 0) {
                for (int j = i + 1; j < n; j++) {
                    if (matrix[j][i] != 0) {
                        // Swap rows
                        for (int k = 0; k < n; k++) {
                            int temp = matrix[i][k];
                            matrix[i][k] = matrix[j][k];
                            matrix[j][k] = temp;
                        }

                        det = (det * -1) % mod;
                        break;
                    }
                }
            }

            det = (det * pivot) % mod;

            if (det < 0) {
                det += mod;
            }

            for (int j = i + 1; j < n; j++) {
                int factor = matrix[j][i] * modInverse(pivot, mod) % mod;

                for (int k = 0; k < n; k++) {
                    matrix[j][k] = (matrix[j][k] - factor * matrix[i][k] + mod) % mod;
                }
            }
        }

        return det;
    }

    private static int modInverse(int a, int mod) {
        a = a % mod;

        for (int i = 1; i < mod; i++) {
            if ((a * i) % mod == 1) {
                return i;
            }
        }

        return 0;
    }

    private static int[][] adjugateMatrix(int[][] matrix, int mod) {
        int n = matrix.length;
        int[][] adjugate = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int sign = ((i + j) % 2 == 0) ? 1 : -1;
                int subDeterminant = determinant(subMatrix(matrix, i, j), mod);

                adjugate[j][i] = (sign * subDeterminant) % mod;

                if (adjugate[j][i] < 0) {
                    adjugate[j][i] += mod;
                }
            }
        }

        return adjugate;
    }

    private static int[][] subMatrix(int[][] matrix, int row, int col) {
        int n = matrix.length;
        int[][] subMatrix = new int[n - 1][n - 1];

        for (int i = 0, r = 0; i < n; i++) {
            if (i != row) {
                for (int j = 0, c = 0; j < n; j++) {
                    if (j != col) {
                        subMatrix[r][c++] = matrix[i][j];
                    }
                }
                r++;
            }
        }

        return subMatrix;
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    private static String encrypt(String message, int[][] keyMatrix, int mod) {
        int dimension = keyMatrix.length;
        StringBuilder encryptedMessage = new StringBuilder();

        // Padding if necessary
        while (message.length() % dimension != 0) {
            message += 'X';
        }

        for (int i = 0; i < message.length(); i += dimension) {
            for (int j = 0; j < dimension; j++) {
                int sum = 0;
                for (int k = 0; k < dimension; k++) {
                    sum += (keyMatrix[j][k] * (message.charAt(i + k) - 'A')) % mod;
                }
                encryptedMessage.append((char) ((sum % mod) + 'A'));
            }
        }

        return encryptedMessage.toString();
    }

    private static String decrypt(String message, int[][] keyInverse, int mod) {
        int dimension = keyInverse.length;
        StringBuilder decryptedMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i += dimension) {
            for (int j = 0; j < dimension; j++) {
                int sum = 0;
                for (int k = 0; k < dimension; k++) {
                    sum += (keyInverse[j][k] * (message.charAt(i + k) - 'A')) % mod;
                }
                decryptedMessage.append((char) ((sum % mod) +
