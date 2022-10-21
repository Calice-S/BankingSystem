package banking;

public class BankAccount {
    protected long cardNumber;
    protected long pin;
    protected int balance;

    Database db2 = new Database();

    public BankAccount() {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
    }


    public long getCardNumber() {
        return cardNumber;
    }

    public long getPin() {
        return pin;
    }

    public int getBalance() {
        return balance;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setPin(long pin) {
        this.pin = pin;
    }

    public void setBalance(int balance) {
        this.balance = 0;
    }


    protected void createAccount() {
        String bin = "400000";
        long randCardNum = (long) (Math.random() * 9_000_000_000L) + 1_000_000_000L;
        String str = bin + randCardNum;
        String secondStr = str.substring(0,str.length() -1);
        int sum =0;
        int checkSum = 0;
        int[] creditArr = new int[secondStr.length()];

        for ( int i = 0; i < secondStr.length(); i++) {
            creditArr[i] = Integer.parseInt(secondStr.substring(i, i+1));
        }


        for(int i = creditArr.length-1; i>= 0; i = i-2) {
            int num = creditArr[i];
            num *= 2;

            if(num > 9) {
                num -=9;
            }
            creditArr[i] = num;

        }

        for(int i = 0; i < creditArr.length; i++) {
            sum += creditArr[i];
        }

        if(sum % 10 != 0){
            checkSum = (10 - sum % 10) % 10;
        }


        String finalNum = secondStr + checkSum;
        long numCard = Long.parseLong(finalNum);

        long randPin = (long) (Math.random()*9000) + 1000;
        String dbpin = String.valueOf(randPin);
        setCardNumber(numCard);
        setPin(randPin);

        db2.insertData(finalNum,dbpin);
        System.out.println("Your card has been created");
        System.out.println("Your card number:\n" + getCardNumber());
        System.out.println("Your card PIN:\n" + getPin());
        System.out.println();
    }


}