import javax.swing.*;
import java.awt.*;

public class Balance extends JFrame {
    JLabel label1, label2;
    JButton refreshButton;
    double balance; // This should be fetched from your account database

    Balance() {
        setTitle("Check Balance");

        // Set background color
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Label for title
        label1 = new JLabel("Your Current Balance");
        label1.setFont(new Font("Arial", Font.BOLD, 24));
        label1.setBounds(50, 20, 300, 30);
        add(label1);

        // Balance Label
        label2 = new JLabel("Balance: $" + balance);
        label2.setFont(new Font("Arial", Font.PLAIN, 20));
        label2.setBounds(50, 80, 300, 30);
        add(label2);

        // Refresh Button
        refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 16));
        refreshButton.setBounds(50, 130, 150, 40);
        refreshButton.addActionListener(e -> refreshBalance());
        add(refreshButton);

        setLayout(null);
        setSize(400, 250);
        setLocation(500, 300);
        setVisible(true);
    }

    // Method to refresh balance
    private void refreshBalance() {
        // Here you would typically fetch the balance from the database or other storage
        balance = getBalanceFromDatabase();
        label2.setText("Balance: $" + balance);
    }

    // Mock method to simulate fetching balance from a database
    private double getBalanceFromDatabase() {
        // Simulating a balance fetch
        return 1234.56; // Replace with actual logic to fetch balance
    }

    public static void main(String[] args) {
        new Balance();
    }
}

