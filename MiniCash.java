import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class MiniCash extends JFrame {

    JTextArea textArea;
    JLabel label;
    String cardNo;  // Card number of the logged-in user

    // Constructor that accepts card number
    MiniCash(String cardNo) {
        this.cardNo = cardNo;
        setTitle("Mini Cash");

        // Set background color
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Label for the title
        label = new JLabel("Mini Cash Transactions");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setBounds(50, 20, 300, 30);
        add(label);

        // Text area to display transactions
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setBounds(50, 70, 600, 300);
        textArea.setEditable(false);
        add(textArea);

        // Fetch and display transactions
        displayTransactions();

        setLayout(null);
        setSize(700, 450);
        setLocation(500, 300);
        setVisible(true);
    }

    private void displayTransactions() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String url = "jdbc:mysql://localhost:3306/ATM"; // Update with your database URL
            String user = "root"; // Update with your database username
            String password = "root"; // Update with your database password

            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();

            // Get today's date
            LocalDate today = LocalDate.now();
            String query = "SELECT id, transaction_type, balancee, date_time FROM transactions WHERE card_no = '"
                    + this.cardNo + "' AND DATE(date_time) = '" + today + "'";
            rs = stmt.executeQuery(query);

            StringBuilder transactions = new StringBuilder("Transaction No  |  Type         |  Amount  |  Date & Time\n");
            transactions.append("-------------------------------------------------------------\n");

            while (rs.next()) {
                int transactionNo = rs.getInt("id");
                String type = rs.getString("transaction_type");
                double amount = rs.getDouble("balancee");
                String dateTime = rs.getString("date_time");

                transactions.append(String.format("%-16d%-15s%-10.2f%-20s\n", transactionNo, type, amount, dateTime));
            }

            textArea.setText(transactions.toString());

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching transaction details.");
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Example method to get the logged-in user's card number
    private static String getLoggedInUserCardNo() {// Replace with the actual logic to retrieve the logged-in user's card number
        return "1234567890123456"; // Example card number
    }

    public static void main(String[] args) {
        String loggedInCardNo = getLoggedInUserCardNo();
        new MiniCash(loggedInCardNo);
    }
}

