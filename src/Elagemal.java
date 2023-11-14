import java.util.*;

public class Elagemal {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int n, e1, d, r, pt;
        long e2, c1, c2, dec;
        do {
            System.out.println("Enter a prime number");
            n = scn.nextInt();
        } while (!isPrime(n));
        List<Integer> roots = primitiveRoots(n);
        do {
            System.out.println("Enter any one of the roots given below");
            System.out.println(roots);
            e1 = scn.nextInt();
        } while (!roots.contains(e1));
        do {
            System.out.printf("Enter a private key between %d to %d\n", 1, n -
                    2);
            d = scn.nextInt();
        } while (d < 1 && d > n - 2);
        System.out.println("Enter a plain text in numbers");
        pt = scn.nextInt();
        r = 5;
        e2 = (long) Math.pow(e1, d) % n;
        c1 = (long) Math.pow(e1, r) % n;
        c2 = (long) (pt * Math.pow(e2, r)) % n;
        dec = (long) (c2 * Math.pow(c1, n - 1 - d)) % n;
        System.out.printf("Encryption pair is (%d,%d)\n", c1, c2);
        System.out.printf("Decrypt message is %d\n", dec);

    }

    private static List<Integer> primitiveRoots(int n) {
        return rootsOfPrime(n);
    }

    private static boolean isPrime(int n) {
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    private static List<Integer> rootsOfPrime(int n) {
        List<Integer> list = new ArrayList<>();
        int phi = n - 1;
        for (int a = 2; a <= phi; a++) {
            for (int i = 1; i <= n; i++) {
                int pow = (int) Math.pow(a, i);
                int mod = pow % n;
                if (mod == 1) {
                    if (phi == i) {
                        list.add(a);
                    } else {
                        break;
                    }
                }
            }
        }
        return list;
    }
}