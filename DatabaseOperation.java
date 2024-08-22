import java.sql.*;

public class DatabaseOperation {
    private static final String URL = "jdbc:mysql://localhost:3306/atm_system";
    private static final String USERNAME = "root"; // Update with your database username
    private static final String PASSWORD = "password"; // Update with your database password

    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Connect to the database
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Insert example data
            insertUser(connection, "john_doe", "123456");
            insertUser(connection, "jane_smith", "654321");

            insertAccount(connection, 1, 1000.00);
            insertAccount(connection, 2, 1500.00);

            // Perform transactions
            insertTransaction(connection, 1, "Deposit", 500.00, 1500.00);
            insertTransaction(connection, 1, "Withdrawal", 200.00, 1300.00);
            insertTransaction(connection, 2, "Deposit", 300.00, 1800.00);

            // Update balance example
            updateBalance(connection, 1, 200.00); // Deposit example
            updateBalance(connection, 2, -100.00); // Withdrawal example

            // Retrieve and display data
            displayAccountStatement(connection, 1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the connection
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to insert a new user
    private static void insertUser(Connection connection, String username, String pin) throws SQLException {
        String query = "INSERT INTO users (username, pin) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, pin);
            pstmt.executeUpdate();
        }
    }

    // Method to insert a new account
    private static void insertAccount(Connection connection, int userId, double balance) throws SQLException {
        String query = "INSERT INTO accounts (user_id, balance) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setDouble(2, balance);
            pstmt.executeUpdate();
        }
    }

    // Method to insert a transaction
    private static void insertTransaction(Connection connection, int accountId, String transactionType, double amount, double balanceAfter) throws SQLException {
        String query = "INSERT INTO transactions (account_id, transaction_type, amount, balance_after) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, accountId);
            pstmt.setString(2, transactionType);
            pstmt.setDouble(3, amount);
            pstmt.setDouble(4, balanceAfter);
            pstmt.executeUpdate();
        }
    }

    // Method to update account balance
    private static void updateBalance(Connection connection, int accountId, double amount) throws SQLException {
        String query = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, accountId);
            pstmt.executeUpdate();
        }
    }

    // Method to display account statement
    private static void displayAccountStatement(Connection connection, int accountId) throws SQLException {
        String query = "SELECT * FROM transactions WHERE account_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, accountId);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Account Statement:");
            while (rs.next()) {
                System.out.println("Date: " + rs.getTimestamp("transaction_date") +
                        ", Type: " + rs.getString("transaction_type") +
                        ", Amount: " + rs.getDouble("amount") +
                        ", Balance After: " + rs.getDouble("balance_after"));
            }
        }
    }
}
