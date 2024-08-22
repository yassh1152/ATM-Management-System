import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class NewAccount2 extends JFrame {

    JLabel label1;
    JComboBox<String> religionComboBox, categoryComboBox;
    JTextField textFieldIncome, textFieldOccupation, textFieldPan, textFieldAadhar;
    JRadioButton yesButton, noButton;
    ButtonGroup customerGroup;
    JButton jbutton;

    NewAccount2() {
        setTitle("Application Form");

        ImageIcon ii1 = new ImageIcon(ClassLoader.getSystemResource("icon/background.jpeg"));
        Image ii2 = ii1.getImage().getScaledInstance(800, 650, Image.SCALE_DEFAULT); // Adjust size to frame dimensions
        ImageIcon ii3 = new ImageIcon(ii2);
        JLabel iimage = new JLabel(ii3);
        iimage.setBounds(0, 0, 800, 650); // Full screen size of the frame
        add(iimage);

        label1 = new JLabel("Additional Details");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 30));
        label1.setBounds(285, 20, 450, 40);
        iimage.add(label1);

        // Religion ComboBox
        label1 = new JLabel("Religion:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 20));
        label1.setBounds(150, 100, 450, 40);
        iimage.add(label1);

        String[] religions = {"Hindu", "Islam", "Jain", "Punjabi", "Sikh"};
        religionComboBox = new JComboBox<>(religions);
        religionComboBox.setBounds(350, 100, 230, 30);
        religionComboBox.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        iimage.add(religionComboBox);

        // Category ComboBox
        label1 = new JLabel("Category:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 20));
        label1.setBounds(150, 150, 450, 40);
        iimage. add(label1);

        String[] categories = {"OPEN", "OBC", "SC/ST", "VJNT", "EWC"};
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setBounds(350, 150, 230, 30);
        categoryComboBox.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        iimage.add(categoryComboBox);

        // Income TextField
        label1 = new JLabel("Income:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 20));
        label1.setBounds(150, 200, 450, 40);
        iimage.add(label1);

        textFieldIncome = new JTextField(15);
        textFieldIncome.setBounds(350, 200, 230, 30);
        textFieldIncome.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        iimage.add(textFieldIncome);

        // Occupation TextField
        label1 = new JLabel("Occupation:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 20));
        label1.setBounds(150, 250, 450, 40);
        iimage.add(label1);

        textFieldOccupation = new JTextField(15);
        textFieldOccupation.setBounds(350, 250, 230, 30);
        textFieldOccupation.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        iimage.add(textFieldOccupation);

        // Pan Number TextField
        label1 = new JLabel("Pan Number:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 20));
        label1.setBounds(150, 300, 450, 40);
        iimage.add(label1);

        textFieldPan = new JTextField(15);
        textFieldPan.setBounds(350, 300, 230, 30);
        textFieldPan.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        iimage.add(textFieldPan);

        // Aadhar Number TextField
        label1 = new JLabel("Aadhar Number:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 20));
        label1.setBounds(150, 350, 450, 40);
        iimage.add(label1);

        textFieldAadhar = new JTextField(15);
        textFieldAadhar.setBounds(350, 350, 230, 30);
        textFieldAadhar.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        iimage.add(textFieldAadhar);

        // Existing Customer Yes/No Buttons
        label1 = new JLabel("Existing Customer:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 20));
        label1.setBounds(150, 400, 450, 40);
        iimage.add(label1);

        yesButton = new JRadioButton("Yes");
        yesButton.setBounds(350, 400, 100, 30);
        yesButton.setFont(new Font("Arial", Font.BOLD, 18));
        yesButton.setBackground(Color.WHITE);
        iimage.add(yesButton);

        noButton = new JRadioButton("No");
        noButton.setBounds(450, 400, 100, 30);
        noButton.setFont(new Font("Arial", Font.BOLD, 18));
        noButton.setBackground(Color.WHITE);
        iimage.add(noButton);

        customerGroup = new ButtonGroup();
        customerGroup.add(yesButton);
        customerGroup.add(noButton);

        // Sign Up Button
        jbutton = new JButton("SIGN UP");
        jbutton.setFont(new Font("Arial", Font.BOLD, 20));
        jbutton.setForeground(Color.BLACK);
        jbutton.setBounds(400, 450, 150, 30);
        iimage.add(jbutton);

        // Add action listener to SIGN UP button
        jbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the form data
                String religion = (String) religionComboBox.getSelectedItem();
                String category = (String) categoryComboBox.getSelectedItem();
                String income = textFieldIncome.getText();
                String occupation = textFieldOccupation.getText();
                String pan = textFieldPan.getText();
                String aadhar = textFieldAadhar.getText();
                String existingCustomer = yesButton.isSelected() ? "Yes" : "No";

                // Validate form data
                if (religion == null || category == null || income.isEmpty() || occupation.isEmpty() || pan.isEmpty() || aadhar.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                    return;
                }

                // Generate random card number and PIN
                Random random = new Random();
                int cardNumber = 1000 + random.nextInt(9000); // Generates a number between 1000 and 9999
                String generatedPassword = generatePassword(8);

                // Store details in the database
                try {
                    String url = "jdbc:mysql://localhost:3306/ATM"; // Update as necessary
                    String user = "root"; // Update as necessary
                    String password = "root"; // Update as necessary

                    // Connect to the database
                    Connection connection = DriverManager.getConnection(url, user, password);

                    // Insert into NewAccount2 table
                    String insertQuery = "INSERT INTO NewAccount2 (religion, category, income, occupation, pan, aadhar, existing_customer) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement pstmt = connection.prepareStatement(insertQuery);
                    pstmt.setString(1, religion);
                    pstmt.setString(2, category);
                    pstmt.setString(3, income);
                    pstmt.setString(4, occupation);
                    pstmt.setString(5, pan);
                    pstmt.setString(6, aadhar);
                    pstmt.setString(7, existingCustomer);
                    pstmt.executeUpdate();

                    // Insert into users table with generated card number and PIN
                    String updateQuery = "INSERT INTO users (card_no, pin) VALUES (?, ?)";
                    PreparedStatement updatePstmt = connection.prepareStatement(updateQuery);
                    updatePstmt.setInt(1, cardNumber);
                    updatePstmt.setString(2, generatedPassword);
                    updatePstmt.executeUpdate();

                    // Close the connections
                    pstmt.close();
                    updatePstmt.close();
                    connection.close();

                    // Show messages
                    JOptionPane.showMessageDialog(null, "Account Created Successfully");
                    JOptionPane.showMessageDialog(null, "Your Card Number: " + cardNumber + "\nYour Password: " + generatedPassword);

                    // Dispose of the current frame and open the Signup frame
                    dispose();
                    new Signup();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error saving account details.");
                }
            }
        });

        setLayout(null);
        setSize(850, 700);
        setLocation(450, 100);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Method to generate a random password of specified length
    private String generatePassword(int length) {
        String chars = "0123456789";
        StringBuilder password = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return password.toString();
    }

    public static void main(String[] args) {
        new NewAccount2();
    }

}

