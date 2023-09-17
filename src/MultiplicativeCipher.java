import java.util.Scanner;

public class MultiplicativeCipher {

    public static String encrypt(String plaintext, int key) {
        StringBuilder ciphertext = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            if (Character.isLowerCase(c)) {
                char encryptedChar = (char) (((c - 'a') * key % 26) + 'A');
                ciphertext.append(encryptedChar);
            } else {
                ciphertext.append(c);
            }
        }
        return ciphertext.toString();
    }

    public static String decrypt(String ciphertext, int key) {
        StringBuilder plaintext = new StringBuilder();
        int modInverse = -1;
        for (int i = 0; i < 26; i++) {
            if ((key * i) % 26 == 1) {
                modInverse = i;
                break;
            }
        }

        if (modInverse == -1) {
            System.out.println("The key does not have a multiplicative inverse.");
            return "";
        }

        for (char c : ciphertext.toCharArray()) {
            if (Character.isUpperCase(c)) {
                char decryptedChar = (char) ((((c - 'A') * modInverse) % 26) + 'a');
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
            System.out.println("Multiplicative Cipher");
            System.out.println("1. Encrypt");
            System.out.println("2. Decrypt");
            System.out.println("3. Brute Force Decryption");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1/2/3/4): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter plaintext (lowercase only): ");
                    scanner.nextLine();  // Consume the newline character
                    text = scanner.nextLine();
                    System.out.print("Enter key (an integer coprime with 26): ");
                    key = scanner.nextInt();
                    String ciphertext="";
                    char chr=' ';
                    for(int i=0 ; i< text.length() ; i++){
                        if(text.charAt(i)>='a'&& text.charAt(i)<='z'){
                            ciphertext = encrypt(text, key);
                        }else{
                            chr=text.charAt(i);
                        }
                    }
                     
                    if(Character.isLowerCase(chr)){
                        System.out.println("Encrypted message (uppercase): " + ciphertext);
                    }else{
                        System.out.println("Not in LowerCase");
                    }
                    
                    break;

                case 2:
                    System.out.print("Enter ciphertext (uppercase only): ");
                    scanner.nextLine();  // Consume the newline character
                    text = scanner.nextLine();
                    System.out.print("Enter key (an integer coprime with 26): ");
                    key = scanner.nextInt();
                    String decryptedText="";
                    char chr1=' ';
                    for(int i=0 ; i< text.length() ; i++){
                        if(text.charAt(i)>='A'&& text.charAt(i)<='Z'){
                            decryptedText = decrypt(text, key);
                            
                        }else{
                            System.out.println("Not in Upper Case ");
                            break;
                        }
                    }
                     if(Character.isLowerCase(chr1)){
                        System.out.println("Encrypted message (uppercase): " + decryptedText);
                    }else{
                        System.out.println("Not in LowerCase");
                    }
                    break;

                case 3:
                    System.out.print("Enter ciphertext (uppercase only): ");
                    scanner.nextLine();  // Consume the newline character
                    text = scanner.nextLine();
                    System.out.println("Brute Force Decryption:");
                    for (int i = 1; i < 26; i++) {
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