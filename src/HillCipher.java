import java.util.Scanner;

public class HillCipher {

    public static final int MODULO = 26;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the key matrix dimension (n): ");
        int dimension = scanner.nextInt();

        int[][] keyMatrix;
        do {
            System.out.println("Enter the key matrix (should be invertible):");
            keyMatrix = readMatrix(scanner, dimension);
        } while (getDeterminant(keyMatrix, dimension) == 0 || !isMatrixInvertible(keyMatrix, dimension));

        System.out.println("\nKey Matrix:");
        printMatrix(keyMatrix, dimension);

        int[][] keyInverse = getMatrixInverse(keyMatrix, dimension);
        System.out.println("\nKey Inverse Matrix:");
        printMatrix(keyInverse, dimension);

        System.out.print("\nEnter the plaintext: ");
        // scanner.nextLine();  // Consume the newline character
        String plaintext = scanner.nextLine().toUpperCase().replaceAll("[^A-Z]", "");

        int[] plaintextVector = convertTextToVector(plaintext, dimension);
        int[] encryptedVector = encrypt(plaintextVector, keyMatrix, dimension);
        System.out.println("\nEncrypted Vector: ");
        printVector(encryptedVector);

        int[] decryptedVector = decrypt(encryptedVector, keyInverse, dimension);
        System.out.println("\nDecrypted Vector: ");
        printVector(decryptedVector);

        String decryptedText = convertVectorToText(decryptedVector, dimension);
        System.out.println("\nDecrypted Text: " + decryptedText);

        scanner.close();
    }

    private static int[][] readMatrix(Scanner scanner, int dimension) {
        int[][] matrix = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                System.out.print("Enter element at position [" + i + "][" + j + "]: ");
                matrix[i][j] = scanner.nextInt() % MODULO;
            }
        }
        return matrix;
    }

    private static void printMatrix(int[][] matrix, int dimension) {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static int[] convertTextToVector(String text, int dimension) {
        int[] vector = new int[dimension];
        for (int i = 0; i < dimension; i++) {
            vector[i] = (i < text.length()) ? text.charAt(i) - 'A' : 0;
        }
        return vector;
    }

    private static void printVector(int[] vector) {
        for (int value : vector) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    private static int[] encrypt(int[] vector, int[][] keyMatrix, int dimension) {
        int[] result = new int[dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                result[i] += keyMatrix[i][j] * vector[j];
            }
            result[i] %= MODULO;
        }

        return result;
    }

    private static int[] decrypt(int[] vector, int[][] keyInverse, int dimension) {
        int[] result = new int[dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                result[i] += keyInverse[i][j] * vector[j];
            }
            result[i] = (result[i] + MODULO) % MODULO; // Ensure non-negative result
        }

        return result;
    }

    private static int getDeterminant(int[][] matrix, int dimension) {
        if (dimension == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        int determinant = 0;
        for (int i = 0; i < dimension; i++) {
            determinant += matrix[0][i] * getCofactor(matrix, 0, i, dimension - 1) * Math.pow(-1, i);
        }

        return determinant;
    }

    private static int getCofactor(int[][] matrix, int row, int col, int dimension) {
        int submatrixSize = dimension + 1;
        int[][] submatrix = new int[submatrixSize][submatrixSize];

        int submatrixRow = 0, submatrixCol = 0;
        for (int i = 0; i <= dimension; i++) {
            for (int j = 0; j <= dimension; j++) {
                if (i != row && j != col) {
                    submatrix[submatrixRow][submatrixCol] = matrix[i][j];
                    submatrixCol++;

                    if (submatrixCol == submatrixSize) {
                        submatrixRow++;
                        submatrixCol = 0;
                    }
                }
            }
        }

        return getDeterminant(submatrix, dimension);
    }

    private static boolean isMatrixInvertible(int[][] matrix, int dimension) {
        return getDeterminant(matrix, dimension) % MODULO != 0 &&
                getGCD(getDeterminant(matrix, dimension), MODULO) == 1;
    }

    private static int getGCD(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static int getMultiplicativeInverse(int a, int m) {
        for (int i = 1; i < m; i++) {
            if ((a * i) % m == 1) {
                return i;
            }
        }
        return -1;
    }

    private static int[][] getMatrixInverse(int[][] matrix, int dimension) {
        int determinant = getDeterminant(matrix, dimension);
        int multiplicativeInverse = getMultiplicativeInverse(determinant, MODULO);

        int[][] cofactorMatrix = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                cofactorMatrix[i][j] = getCofactor(matrix, i, j, dimension - 1);
                cofactorMatrix[i][j] = (cofactorMatrix[i][j] * multiplicativeInverse) % MODULO;
                cofactorMatrix[i][j] = (cofactorMatrix[i][j] + MODULO) % MODULO; // Ensure non-negative result
            }
        }

        // Transpose of the cofactor matrix is the inverse of the original matrix
        int[][] inverseMatrix = transposeMatrix(cofactorMatrix, dimension);

        return inverseMatrix;
    }

    private static int[][] transposeMatrix(int[][] matrix, int dimension) {
        int[][] transposedMatrix = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                transposedMatrix[i][j] = matrix[j][i];
            }
        }
        return transposedMatrix;
    }

    private static String convertVectorToText(int[] vector, int dimension) {
        StringBuilder text = new StringBuilder();
        for (int value : vector) {
            text.append((char) (value + 'A'));
        }
        return text.toString();
    }
}
