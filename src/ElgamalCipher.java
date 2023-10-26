import java.util.Scanner;

public class ElgamalCipher {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n =sc.nextInt();

        switch(n){
            case 1: 
            String plaintext = sc.next();
            encrypt(plaintext);
            break;
            case 2: 
            break;
        }
    }
}
