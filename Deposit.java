import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Deposit extends JFrame {
    private Operation operation;
    private String cardNumber;
    private JTextField amountField;

    public Deposit(Operation operation, String cardNumber) {
        this.operation = operation;
        this.cardNumber = cardNumber;

        // Set up the frame
        setTitle("Deposit");
        setSize(300, 200);
        setLocation(500, 300);
        setLayout(new FlowLayout());

        // Create and add components
        add(new JLabel("Enter amount to deposit:"));
        amountField = new JTextField(15);
        add(amountField);
        JButton depositButton = new JButton("Deposit");
        add(depositButton);

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    if (amount > 0) {
                        operation.updateBalance(amount, "Deposit");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Amount must be greater than zero.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid amount.");
                }
            }
        });

        setVisible(true);
    }
}
