import java.math.*;
import java.util.*;

class RSA {
	public static void main(String args[])
	{
        Scanner sc = new Scanner(System.in);
		int p, q, n, z, d = 0, e, i;

		
		double c;
		BigInteger msgback;

        System.out.println("Enter value of p (Prime no. is valid): ");
		p = sc.nextInt();

        System.out.println("Enter value of q (Prime no. is valid): ");
		q = sc.nextInt();
		n = p * q;
		z = (p - 1) * (q - 1);
		System.out.println("the value of z = " + z);

		for (e = 2; e < z; e++) {

			if (gcd(e, z) == 1) {
				break;
			}
		}
		System.out.println("the value of e = " + e);
		for (i = 0; i <= 9; i++) {
			int x = 1 + (i * z);

			if (x % e == 0) {
				d = x / e;
				break;
			}
		}
        System.out.println("Enter the length of Message : ");
        int msg=sc.nextInt();
        
		System.out.println("the value of d = " + d);
		c = (Math.pow(msg, e)) % n;
		System.out.println("Encrypted message is : " + c);

		BigInteger N = BigInteger.valueOf(n);

		BigInteger C = BigDecimal.valueOf(c).toBigInteger();
		msgback = (C.pow(d)).mod(N);
		System.out.println("Decrypted message is : "
						+ msgback);
	}

	static int gcd(int e, int z)
	{
		if (e == 0)
			return z;
		else
			return gcd(z % e, e);
	}
}
