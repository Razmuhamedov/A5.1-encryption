import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_RESET = "\u001B[0m";
        A51 a5 = new A51();
        System.out.println("Enter message to encrypt by A5/1: ");
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        String encrypt = a5.encrypt(text);
        String decrypt = a5.decrypt(encrypt);
        System.out.println("Cyper text: " +  ANSI_RED + encrypt + ANSI_RESET);
        System.out.println("Cyper text: " +  ANSI_RED + decrypt + ANSI_RESET);
    }
}
