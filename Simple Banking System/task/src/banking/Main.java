package banking;

import java.util.Scanner;
public class Main {

    protected static boolean flag;
    protected static String cardNum;
    protected static String cardPin;

    public static void main(String[] args) {

        Database db = new Database();

        if (args.length > 1) {
            db.createDatabase(args[1]);
        }
        db.createTable();

        Scanner scan = new Scanner(System.in);
        Outerloop:
        while (true) {
            System.out.println(" 1. Create an account\n 2. Log into account\n 0. Exit");
            System.out.println();
            int input = scan.nextInt();

            if (input == 0) {
                System.out.println("Bye!");
                db.closeCon();
                break;
            }

            switch (input) {
                case 1:
                    BankAccount entry = new BankAccount();
                    entry.createAccount();
                    System.out.println();
                    break;
                case 2:
                    System.out.println("Enter your card number:");
                    long inputCardNum = scan.nextLong();
                    cardNum = String.valueOf(inputCardNum);
                    System.out.println("Enter your PIN:");
                    long inputPin = scan.nextInt();
                    cardPin = String.valueOf(inputPin);
                    db.results(cardNum, cardPin);


            }


        }

    }

    public String firstCardNum() {
        String firstCCNum = cardNum;
        return firstCCNum;
    }

}