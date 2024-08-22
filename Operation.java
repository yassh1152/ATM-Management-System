import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Operation extends JFrame {
    JLabel label4, label5;
    JButton button1, button2, button3, button4, button5, button6;
    private double balance;
    private String cardNumber;
    List<String> transactions;

    public Operation(String cardNumber) {
        this.cardNumber = cardNumber;
        this.balance = fetchBalance(cardNumber);
        transactions = new ArrayList<>();

        // Background Image
        ImageIcon ii1 = new ImageIcon(ClassLoader.getSystemResource("icon/back.jpg"));
        Image ii2 = ii1.getImage().getScaledInstance(600, 400, Image.SCALE_DEFAULT);
        ImageIcon ii3 = new ImageIcon(ii2);
        JLabel iimage = new JLabel(ii3);
        iimage.setBounds(0, 0, 600, 400);
        add(iimage);

        label4 = new JLabel("MY ATM");
        label4.setForeground(Color.WHITE);
        label4.setFont(new Font("AvantGarde", Font.BOLD, 38));
        label4.setBounds(250, 50, 450, 30);
        iimage.add(label4);

        button1 = new JButton("Deposit");
        button1.setForeground(Color.BLACK);
        button1.setBounds(100, 120, 150, 40);
        iimage.add(button1);

        button2 = new JButton("Withdraw");
        button2.setForeground(Color.BLACK);
        button2.setBounds(375, 120, 150, 40);
        iimage.add(button2);

        button3 = new JButton("Mini Transaction");
        button3.setForeground(Color.BLACK);
        button3.setBounds(100, 185, 150, 40);
        iimage.add(button3);

        button4 = new JButton("Statement");
        button4.setForeground(Color.BLACK);
        button4.setBounds(375, 185, 150, 40);
        iimage.add(button4);

        button5 = new JButton("Balance");
        button5.setForeground(Color.BLACK);
        button5.setBounds(100, 250, 150, 40);
        iimage.add(button5);

        button6 = new JButton("Change Pin");
        button6.setForeground(Color.BLACK);
        button6.setBounds(375, 250, 150, 40);
        iimage.add(button6);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setBounds(235, 300, 150, 40);
        iimage.add(logoutButton);

        label5 = new JLabel("THANK YOU");
        label5.setForeground(Color.BLACK);
        label5.setFont(new Font("AvantGarde", Font.BOLD, 38));
        label5.setBounds(585, 380, 450, 40);
        iimage.add(label5);

        // Action listeners for buttons
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Deposit(Operation.this, cardNumber);
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Withdraw(Operation.this, cardNumber);
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MiniCash(cardNumber);  // Pass cardNumber to MiniCash constructor
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Statement(fetchTransactions(cardNumber));
            }
        });

        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Current balance: ₹" + balance);
            }
        });

        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChangePin(cardNumber);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Close the current Operation window
                new Signup();  // Open the Signup class window
            }
        });

        setLayout(null);
        setSize(600, 400);
        setLocation(450, 200);
        setVisible(true);
    }

    private double fetchBalance(String cardNumber) {
        Connection connection = DatabaseConnection.getConnection();
        double balance = 0.0;
        if (connection != null) {
            try {
                String sql = "SELECT balancee FROM transactions WHERE card_no = ? ORDER BY date_time DESC LIMIT 1";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, cardNumber);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    balance = resultSet.getDouble("balancee");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return balance;
    }

    public void updateBalance(double amount, String transactionType) {
        if ("Deposit".equals(transactionType)) {
            balance += amount;
        } else if ("Withdraw".equals(transactionType)) {
            balance -= amount;
        }
        transactions.add(transactionType + ": ₹" + amount + " | New Balance: ₹" + balance);
        recordTransaction(cardNumber, transactionType, balance);
        JOptionPane.showMessageDialog(null, "Balance updated! New balance: ₹" + balance);
    }

    private void recordTransaction(String cardNo, String transactionType, double balance) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String sql = "INSERT INTO transactions (card_no, transaction_type, balancee, date_time) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, cardNo);
                statement.setString(2, transactionType);
                statement.setDouble(3, balance);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                statement.setString(4, now.format(formatter));

                statement.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private List<String> fetchTransactions(String cardNumber) {
        List<String> transactionList = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String sql = "SELECT transaction_type, balancee, date_time FROM transactions WHERE card_no = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, cardNumber);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String transactionType = resultSet.getString("transaction_type");
                    double balance = resultSet.getDouble("balancee");
                    String dateTime = resultSet.getString("date_time");
                    transactionList.add(transactionType + ": ₹" + balance + " | Date & Time: " + dateTime);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return transactionList;
    }

    public void refreshBalance() {
        this.balance = fetchBalance(cardNumber);
    }

    public double getBalance() {
        return balance;
    }

    public static void main(String[] args) {
        String cardNumber = "actual_card_number";
        new Operation(cardNumber);
    }
}
