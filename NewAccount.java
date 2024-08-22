import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NewAccount extends JFrame {

    JLabel label1;
    JTextField textField1, textField2, textField3, textField4, textField5, textField6, textField7;
    JRadioButton male, female;
    ButtonGroup genderGroup;
    JButton nextButton, submitButton;
    JDateChooser dateChooser;
    Connection connection;

    public NewAccount() {
        // Set up the database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ATM", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        setTitle("New Account");
        setLayout(null);

        // Set the background image
        ImageIcon ii1 = new ImageIcon(ClassLoader.getSystemResource("icon/background.jpeg"));
        Image ii2 = ii1.getImage().getScaledInstance(800, 650, Image.SCALE_DEFAULT); // Adjust size to frame dimensions
        ImageIcon ii3 = new ImageIcon(ii2);
        JLabel iimage = new JLabel(ii3);
        iimage.setBounds(0, 0, 800, 650); // Full screen size of the frame
        add(iimage);

        label1 = new JLabel("APPLICATION FORM NO. 2396");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 30));
        label1.setBounds(205, 10, 450, 40);
        iimage.add(label1); // Add to iimage

        label1 = new JLabel("Personal Details ");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 30));
        label1.setBounds(285, 50, 450, 40);
        iimage.add(label1);

        label1 = new JLabel("Full Name:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 20));
        label1.setBounds(150, 100, 450, 40);
        iimage.add(label1);

        textField1 = new JTextField(15);
        textField1.setBounds(350, 100, 230, 30);
        textField1.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        iimage.add(textField1);

        label1 = new JLabel("Father's Name:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 20));
        label1.setBounds(150, 150, 450, 40);
        iimage.add(label1);

        textField2 = new JTextField(15);
        textField2.setBounds(350, 150, 230, 30);
        textField2.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        iimage.add(textField2);

        label1 = new JLabel("Gender:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 20));
        label1.setBounds(150, 200, 450, 40);
        iimage.add(label1);

        male = new JRadioButton("Male");
        male.setBounds(350, 200, 100, 30);
        male.setFont(new Font("Arial", Font.BOLD, 18));
        male.setBackground(Color.WHITE);
        iimage.add(male);

        female = new JRadioButton("Female");
        female.setBounds(450, 200, 100, 30);
        female.setFont(new Font("Arial", Font.BOLD, 18));
        female.setBackground(Color.WHITE);
        iimage.add(female);

        genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);

        label1 = new JLabel("Date Of Birth:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 20));
        label1.setBounds(150, 250, 450, 40);
        iimage.add(label1);

        dateChooser = new JDateChooser();
        dateChooser.setForeground(new Color(105, 105, 105));
        dateChooser.setBounds(350, 250, 230, 30);
        iimage.add(dateChooser);

        label1 = new JLabel("Email Address:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 20));
        label1.setBounds(150, 300, 450, 40);
        iimage.add(label1);

        textField3 = new JTextField(15);
        textField3.setBounds(350, 300, 230, 30);
        textField3.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        iimage.add(textField3);

        label1 = new JLabel("Address:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 20));
        label1.setBounds(150, 350, 450, 40);
        iimage.add(label1);

        textField4 = new JTextField(15);
        textField4.setBounds(350, 350, 230, 30);
        textField4.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        iimage.add(textField4);

        label1 = new JLabel("City:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 20));
        label1.setBounds(150, 400, 450, 40);
        iimage.add(label1);

        textField5 = new JTextField(15);
        textField5.setBounds(350, 400, 230, 30);
        textField5.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        iimage.add(textField5);

        label1 = new JLabel("Pin Code:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 20));
        label1.setBounds(150, 450, 450, 40);
        iimage.add(label1);

        textField6 = new JTextField(15);
        textField6.setBounds(350, 450, 230, 30);
        textField6.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        iimage.add(textField6);

        label1 = new JLabel("State:");
        label1.setForeground(Color.BLACK);
        label1.setFont(new Font("AvantFrade", Font.BOLD, 20));
        label1.setBounds(150, 500, 450, 40);
        iimage.add(label1);

        textField7 = new JTextField(15);
        textField7.setBounds(350, 500, 230, 30);
        textField7.setFont(new Font("AvantaGrade", Font.BOLD, 20));
        iimage.add(textField7);

        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 20));
        nextButton.setForeground(Color.BLACK);
        nextButton.setBounds(500, 550, 150, 30);
        iimage.add(nextButton);

        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 20));
        submitButton.setForeground(Color.BLACK);
        submitButton.setBounds(300, 550, 150, 30);
        iimage.add(submitButton);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate to NewAccount2
                new NewAccount2();
                dispose(); // Close current frame
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve form data
                String fullName = textField1.getText();
                String fatherName = textField2.getText();
                String gender = male.isSelected() ? "Male" : "Female";
                java.util.Date dob = dateChooser.getDate(); // Retrieve date from JDateChooser
                String email = textField3.getText();
                String address = textField4.getText();
                String city = textField5.getText();
                String pinCode = textField6.getText();
                String state = textField7.getText();

                // Insert data into the database
                try {
                    String sql = "INSERT INTO NewAccount (full_name, father_name, gender, date_of_birth, email, address, city, pin_code, state) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, fullName);
                    statement.setString(2, fatherName);
                    statement.setString(3, gender);
                    statement.setDate(4, new java.sql.Date(dob.getTime())); // Convert java.util.Date to java.sql.Date
                    statement.setString(5, email);
                    statement.setString(6, address);
                    statement.setString(7, city);
                    statement.setString(8, pinCode);
                    statement.setString(9, state);

                    statement.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Account Created Successfully!");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error while creating account.");
                }
            }
        });

        setSize(800, 650);
        setLocation(350, 100);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new NewAccount();
    }
}
