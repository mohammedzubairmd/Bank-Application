import java.sql.*;

public class TransactionDAO {
    public void insertTransaction(transaction tx, Connection connection) {
        String query = "INSERT INTO bank.transaction (accno, date, type, amount, balance) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, tx.getAccno());
            statement.setDate(2, tx.getDate());
            statement.setString(3, tx.getType());
            statement.setBigDecimal(4, tx.getAmount());
            statement.setBigDecimal(5, tx.getBalance());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error saving transaction: " + e.getMessage());
        }
    }
}