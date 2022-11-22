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
        this.balance = balance;
    }

    /**
     *
     * @param strCardNum
     * @return valid String that will always pass the luhn Algorithm
     */
    protected String luhnAlgorithm(String strCardNum) {
        String secondStr = strCardNum.substring(0,strCardNum.length() -1);
        int sum =0;
        int checkSum = 0;
        int[] creditArr = new int[secondStr.length()];

        for ( int i = 0; i < secondStr.length(); i++) {
            creditArr[i] = Integer.parseInt(secondStr.substring(i, i+1));
        }

        for(int i = creditArr.length-1; i >= 0; i = i-2) {
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
        //create checksum
        if(sum % 10 != 0){
            checkSum = (10 - sum % 10) % 10;
        }


       return secondStr + checkSum;

    }

    /**
     *
     * @param strValidate
     * @return true if card number pass luhn algorithm
     */
    protected boolean validate(String strValidate) {
        int total =0;
        int[] cardNumArr = new int[strValidate.length()];

        for ( int i = 0; i < strValidate.length(); i++) {
            cardNumArr[i] = Integer.parseInt(strValidate.substring(i, i+1));
        }

        for(int i = cardNumArr.length-2; i>= 0; i = i-2) {
            int num = cardNumArr[i];
            num *= 2;

            if(num > 9) {
                num -=9;
            }
            cardNumArr[i] = num;

        }
        for(int i = 0; i < cardNumArr.length; i++) {
            total += cardNumArr[i];
        }

        if(total % 10 == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * creates the credit card and pin number which is type String
     */
    protected void createAccount() {
        String bin = "400000";
        long randCardNum = (long) (Math.random() * 9_000_000_000L) + 1_000_000_000L;
        String str = bin + randCardNum;
        String finalNum = luhnAlgorithm(str);

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