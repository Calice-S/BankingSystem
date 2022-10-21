package banking;
import java.sql.*;
import java.util.ArrayList;

import org.sqlite.*;

public class Database {

   protected ArrayList<String> list = new ArrayList<>();

    public static void createDatabase(String fileName ) {
        String url = "jdbc:sqlite:" + fileName;
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

    }
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

    public void insertData(String number, String pin) {
        String sql = "INSERT OR IGNORE INTO card(number,pin) VALUES(?, ?)";
        try(Connection con = this.connectdb(); PreparedStatement prepstmt = con.prepareStatement(sql)) {
                    prepstmt.setString(1, number);
                    prepstmt.setString(2, pin);
                    prepstmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void results(String num , String pin) {
            String sql = "SELECT number, pin FROM card WHERE number = ? AND pin = ?";
            try(Connection con = this.connectdb(); PreparedStatement prepstmt = con.prepareStatement(sql)) {
                con.setAutoCommit(false);
                prepstmt.setString(1, num);
                prepstmt.setString(2, pin);

                ResultSet rs = prepstmt.executeQuery();

                while(rs.next()) {
                    String cardNumber = rs.getString("number");
                    String card_Pin = rs.getString("pin");
                    list.add(cardNumber);
                    list.add(card_Pin);
                }

                con.commit();
                rs.close();
                prepstmt.close();


                transactions ts = new transactions();
                if (list.contains(num) && list.contains(pin)) {
                    System.out.println("You have successfully logged in!");
                    System.out.println();
                    ts.menu();
                } else {
                    System.out.println("Wrong card number or PIN!");
                }



            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        public void updateBalance(int newBalance , String cardNumber) {
        String sql = "UPDATE card SET balance = ? WHERE number = ?";
        try(Connection con = this.connectdb() ){
            PreparedStatement prepstmt = con.prepareStatement(sql);
            prepstmt.setInt(1,newBalance);
            prepstmt.setString(2, cardNumber);
            prepstmt.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
            }
        }

        public void resultBalance(int balanceValue, String cardNum) {
            String sql = "SELECT balance FROM card WHERE number = ?";
            try(Connection con = this.connectdb()) {
                PreparedStatement prepstmt = con.prepareStatement(sql);

            } catch(SQLException e) {
              e.printStackTrace();
            }


        }

        public boolean isThere() {
            String sql = "SELECT number, pin FROM card WHERE number = ? AND pin = ?";

            return false;
        }



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




