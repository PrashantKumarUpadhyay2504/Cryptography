import java.util.Random;
import java.util.Scanner;

public class RabinMillerPrimalityTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number to test for primality: ");
        long numberToTest = scanner.nextLong();

        if (isPrime(numberToTest, 5)) {
            System.out.println(numberToTest + " is likely to be prime.");
        } else {
            System.out.println(numberToTest + " is composite.");
        }

        scanner.close();
    }

    private static boolean isPrime(long n, int k) {
        if (n <= 1 || n == 4) {
            return false;
        }
        if (n <= 3) {
            return true;
        }

        long d = n - 1;
        while (d % 2 == 0) {
            d /= 2;
        }

        for (int i = 0; i < k; i++) {
            if (!millerRabinTest(n, d)) {
                return false;
            }
        }

        return true;
    }

    private static boolean millerRabinTest(long n, long d) {
        Random rand = new Random();
        long a = 2 + rand.nextInt((int) (n - 4));

        long x = power(a, d, n);

        if (x == 1 || x == n - 1) {
            return true;
        }

        while (d != n - 1) {
            x = (x * x) % n;
            d *= 2;

            if (x == 1) {
                return false;
            }
            if (x == n - 1) {
                return true;
            }
        }

        return false;
    }

    private static long power(long a, long b, long c) {
        long result = 1;
        a = a % c;

        while (b > 0) {
            if (b % 2 == 1) {
                result = (result * a) % c;
            }

            b /= 2;
            a = (a * a) % c;
        }

        return result;
    }
}
