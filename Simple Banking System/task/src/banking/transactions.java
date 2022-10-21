package banking;

import javax.xml.crypto.Data;
import java.util.Scanner;

public class transactions {
   // protected boolean flag;
    protected String initialCardNum;
    protected int income;
    public void menu() {
        Scanner scan = new Scanner(System.in);
        int choice = 0;

        while (true) {
           // flag = true;
            System.out.println(
                            "1. Balance\n" +
                            "2. Add income\n" +
                            "3. Do transfer\n" +
                            "4. Close account\n" +
                            "5. Log out\n" +
                            "0. Exit"
            );
            System.out.println();
            choice = scan.nextInt();

            if (choice == 0) {
                System.out.println("Bye!");
                System.exit(0);

            }

            switch (choice) {
                case 1:
                    // redo balance
                    Database dbBalance = new Database();
                    dbBalance.updateBalance(income,initialCardNum);
                    break;
                case 2:
                    System.out.println("Enter income: ");
                    income = scan.nextInt();
                    System.out.println("Income was added!");
                    BankAccount baTwo = new BankAccount();
                    initialCardNum = String.valueOf(baTwo.getCardNumber());

                    break;
                case 3:
                    System.out.println("Transfer\n Enter card number: ");
                    int transfer = scan.nextInt();
                    break;
                case 4:
                    break;
                case 5:
                   // flag = false;
                    break;
            }
        }


    }



}
/* System.out.println(
              "1. Balance\n" +
              "2. Add income\n" +
              "3. Do transfer\n" +
              "4. Close account\n" +
              "5. Log out\n" +
              "0. Exit"
      ); */

/* do {
            flag = true;
            System.out.println(
                    "1. Balance\n" +
                            "2. Add income\n" +
                            "3. Do transfer\n" +
                            "4. Close account\n" +
                            "5. Log out\n" +
                            "0. Exit"
            );

            switch (choice) {
                case 1:
                    // redo balance
                    BankAccount ba = new BankAccount();
                    System.out.println(ba.getBalance());
                    break;
                case 2:
                    System.out.println("Enter income: ");
                    int income = scan.nextInt();
                    System.out.println("Income was added!");
                    BankAccount baTwo = new BankAccount();
                    baTwo.setBalance(income);
                    break;
                case 3:
                    System.out.println("Transfer\n Enter card number: ");
                    int transfer = scan.nextInt();
                    break;
                case 4:
                    break;
                case 5:
                    flag = false;
                    break;
            }

        } while(flag);
        */