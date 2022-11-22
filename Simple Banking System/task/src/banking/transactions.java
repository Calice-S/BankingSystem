package banking;
import java.util.Scanner;

public class transactions {

    private final Main mainOne = new Main();
    private final BankAccount baTwo = new BankAccount();
    private final Database dbThree = new Database();
    protected String initialCardNum;
    protected int income;
    protected int balMoney;

    public void menu() {
        Scanner scan = new Scanner(System.in);
        int choice;
        Outerloop:
        while (true)  {

            System.out.println();
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
                    //get user card input
                    String creditCardNumber = mainOne.firstCardNum();
                    System.out.println("Balance: " + dbThree.resultBalance(creditCardNumber));
                    break;
                case 2:
                    System.out.println("Enter income: ");
                    income = scan.nextInt();
                    initialCardNum = String.valueOf(mainOne.firstCardNum());
                    dbThree.updateBalance(income,initialCardNum);
                    baTwo.setBalance(income);
                    System.out.println("Income was added!");
                    break;
                case 3:
                    System.out.println("Transfer");
                    System.out.println("Enter card number: ");
                    long transferCardNum = scan.nextLong();
                    String strTransferCardNum = String.valueOf(transferCardNum);
                    String originalNum = mainOne.firstCardNum();
                    if (strTransferCardNum.equals(originalNum)){
                        System.out.println("You can't transfer money to the same account!");
                    } else if (!baTwo.validate(strTransferCardNum)) {
                        System.out.println("Probably you made a mistake in the card number. Please try again!");
                    } else if (!dbThree.cardNumPresent(strTransferCardNum)) {
                        System.out.println("Such a card does not exist.");
                    } else {
                        System.out.println("Transfer");
                        System.out.println("Enter how much money you want to transfer:");
                        int transferMoney = scan.nextInt();
                        balMoney = dbThree.resultBalance(mainOne.firstCardNum());
                        if(balMoney < transferMoney) {
                            System.out.println("Not enough money!");
                        } else {
                            dbThree.transfer(originalNum,transferMoney,strTransferCardNum);
                            System.out.println("Success!");
                        }
                    }
                    break;
                case 4:
                    String deleteNum = mainOne.firstCardNum();
                    dbThree.deleteAccount(deleteNum);
                    System.out.println("The account has been closed!");
                    System.out.println();
                    break Outerloop;
                case 5:
                    break Outerloop;
            }
        }


    }



}

