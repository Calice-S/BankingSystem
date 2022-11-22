package banking;
import java.sql.*;

import org.sqlite.*;

public class Database {

    public static void createDatabase(String fileName ) {
        String url = "jdbc:sqlite:" + fileName;
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

    }

    /**
     * create database table card
     */
    public static void createTable() {
        String url = "jdbc:sqlite:card.s3db";
        String sql = "CREATE TABLE IF NOT EXISTS card ("
                + "	id INTEGER PRIMARY KEY,"
                + "	number TEXT,"
                + "	pin TEXT, "
                + " balance INTEGER DEFAULT 0"
                + ");";

        try(Connection con = DriverManager.getConnection(url);  Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            if(stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * connects to the database, so I can call it later
     * @return
     */
    private Connection connectdb(){
        String url = "jdbc:sqlite:card.s3db";
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        Connection con = null;

        try {
            con = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;

    }

    /**
     * insert credit card and pin number into database
     * @param number
     * @param pin
     */
    public void insertData(String number, String pin) {
        String sql = "INSERT OR IGNORE INTO card(number,pin) VALUES(?, ?)";
        try(Connection con = this.connectdb(); PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                    preparedStatement.setString(1, number);
                    preparedStatement.setString(2, pin);
                    preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    /**
     * print database and check if user's input card and pin is validate to log in to account
     * @param num
     * @param pin
     */
        public void results(String num , String pin) {
            String sql = "SELECT number, pin FROM card WHERE number = ? AND pin = ?";
            try(Connection con = this.connectdb(); PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                con.setAutoCommit(false);
                preparedStatement.setString(1, num);
                preparedStatement.setString(2, pin);

                ResultSet rs = preparedStatement.executeQuery();
                String cardNumCheck = null;
                String pinNumCheck = null;
                while(rs.next()) {
                    String cardNumber = rs.getString("number");
                    String card_Pin = rs.getString("pin");
                    if(cardNumber.equals(num) && card_Pin.equals(pin)){
                        cardNumCheck = cardNumber;
                        pinNumCheck = card_Pin;
                    }
                }

                con.commit();
                rs.close();
                preparedStatement.close();
                transactions ts = new transactions();
                if(cardNumPresent(cardNumCheck) && cardPinPresent(pinNumCheck)){
                    System.out.println();
                    System.out.println("You have successfully logged in!");
                    System.out.println();
                    ts.menu();
                } else {
                    System.out.println("Wrong card number or PIN!");
                    System.out.println();
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    /**
     * update balance of account once an income is added
     * @param newBalance
     * @param cardNumber
     */
        public void updateBalance(int newBalance , String cardNumber) {
        String sql = "UPDATE card SET balance = balance + ? WHERE number = ?";
        try(Connection con = this.connectdb(); PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, newBalance);
            preparedStatement.setString(2, cardNumber);
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            }
        }

    /**
     *
     * @param cardNum
     * @return balance
     */
         public int resultBalance(String cardNum) {
            String sql = "SELECT balance FROM card WHERE number = ?";
            try(Connection con = this.connectdb(); PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setString(1,cardNum);
                 ResultSet rs = preparedStatement.executeQuery();
                 if(rs.next()){
                     return (rs.getInt("balance"));
                 }

            } catch(SQLException e){
                e.printStackTrace();
            }

            return 0;

        }

    /**
     * transfer income from one account to another
     * @param firstCardNum
     * @param income
     * @param secondCardNum
     */
         public void transfer (String firstCardNum , int income, String secondCardNum) {
            //sender
            String firstSql = "UPDATE card SET balance = balance - ? WHERE number = ?";
            //receiver
            String secondSql = "UPDATE card SET balance = balance + ? WHERE number = ?";

            try {
                connectdb().setAutoCommit(false);
                try(PreparedStatement preparedStatement = connectdb().prepareStatement(firstSql);
                    PreparedStatement preparedStatement1 = connectdb().prepareStatement(secondSql)) {

                    preparedStatement.setInt(1,income);
                    preparedStatement.setString(2,firstCardNum);
                    preparedStatement.executeUpdate();

                    preparedStatement1.setInt(1,income);
                    preparedStatement1.setString(2,secondCardNum);
                    preparedStatement1.executeUpdate();

                    connectdb().commit();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    /**
     *
     * @param creditCardNum
     * @return true if credit card number is in the database
     */
    public boolean cardNumPresent(String creditCardNum) {
            String sql = "SELECT number FROM card WHERE number = ?";
            boolean isPresent = false;
            try(Connection con = this.connectdb(); PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setString(1,creditCardNum);
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()) {
                    String ccNum = rs.getString("number");
                    if(creditCardNum.equals(ccNum)) {
                        isPresent = true;
                    }

                }

            } catch(SQLException e) {
                e.printStackTrace();
            }
            return isPresent;
        }

    /**
     *
     * @param pinNumber
     * @return true if card pin is present
     */
        public boolean cardPinPresent(String pinNumber){
            String sql = "SELECT pin FROM card WHERE pin = ?";
            boolean isPresent = false;
            try(Connection con = this.connectdb(); PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setString(1,pinNumber);
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()) {
                    String ccPin = rs.getString("pin");
                    if(pinNumber.equals(ccPin)) {
                        isPresent = true;
                    }

                }

            } catch(SQLException e) {
                e.printStackTrace();
            }
            return isPresent;
        }

    /**
     * delete row from database identified by card number
     * @param deleteCardNum
     */
    public void deleteAccount(String deleteCardNum) {
        String sql = "DELETE FROM card WHERE number = ?";
            try(Connection con = this.connectdb(); PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1,deleteCardNum);
            preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    /**
     * close connection
     */
    public void closeCon() {
            if (this.connectdb() != null) {
                try {
                    connectdb().close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }



