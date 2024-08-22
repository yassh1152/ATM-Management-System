import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePin extends JFrame implements ActionListener {
    JLabel label1, label2, label3, label4;
    JPasswordField oldPinField, newPinField, confirmPinField;
    JButton changePinButton;
    private String cardNumber;  // Store the card number

    // Constructor to set up the frame and pass the card number
    public ChangePin(String cardNumber) {
        this.cardNumber = cardNumber;  // Store the card number

        setTitle("Change Pin");

        // Set background color
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Label for title
        label1 = new JLabel("Change Pin");
        label1.setFont(new Font("Arial", Font.BOLD, 24));
        label1.setBounds(150, 20, 200, 30);
        add(label1);

        label2 = new JLabel("Old Pin:");
        label2.setBounds(50, 70, 100, 30);
        add(label2);

        oldPinField = new JPasswordField();
        oldPinField.setBounds(150, 70, 150, 30);
        add(oldPinField);

        label3 = new JLabel("New Pin:");
        label3.setBounds(50, 110, 100, 30);
        add(label3);

        newPinField = new JPasswordField();
        newPinField.setBounds(150, 110, 150, 30);
        add(newPinField);

        label4 = new JLabel("Confirm Pin:");
        label4.setBounds(50, 150, 100, 30);
        add(label4);

        confirmPinField = new JPasswordField();
        confirmPinField.setBounds(150, 150, 150, 30);
        add(confirmPinField);

        changePinButton = new JButton("Change Pin");
        changePinButton.setBounds(100, 200, 150, 30);
        add(changePinButton);

        changePinButton.addActionListener(this);

        setLayout(null);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ActionListener method for the Change Pin button
    @Override
    public void actionPerformed(ActionEvent e) {
        String oldPin = new String(oldPinField.getPassword());
        String newPin = new String(newPinField.getPassword());
        String confirmPin = new String(confirmPinField.getPassword());

        if (newPin.equals(confirmPin)) {
            if (validateOldPin(oldPin)) {
                changePin(newPin);
                JOptionPane.showMessageDialog(this, "Pin changed successfully.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Old Pin is incorrect.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "New Pin and Confirm Pin do not match.");
        }
    }

    // Method to validate the old pin from the database
    private boolean validateOldPin(String oldPin) {
        Connection connection = DatabaseConnection.getConnection();
        boolean isValid = false;
        if (connection != null) {
            try {
                String sql = "SELECT pin FROM users WHERE card_no = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, cardNumber);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String storedPin = resultSet.getString("pin");
                    if (storedPin.equals(oldPin)) {
                        isValid = true;
                    }
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
        return isValid;
    }

    // Method to update the pin in the database
    private void changePin(String newPin) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                String sql = "UPDATE users SET pin = ? WHERE card_no = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, newPin);
                statement.setString(2, cardNumber);
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

    public static void main(String[] args) {

    }
}
