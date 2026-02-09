import java.sql.*;

public class AccountValidationAndDetails {

    public void displayMiniStatement(int accNo) {
        // Query to get the 5 most recent transactions
        String query = "SELECT date, type, amount, balance FROM bank.transaction WHERE accno = ? ORDER BY id DESC LIMIT 5";

        try (Connection conn = getConnection.getconnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, accNo);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\n============================================================");
                System.out.println("                ZUBAIR BANK - MINI STATEMENT                ");
                System.out.println("============================================================");
                System.out.printf("%-12s | %-12s | %-10s | %-10s%n", "Date", "Type", "Amount", "Balance");
                System.out.println("------------------------------------------------------------");

                boolean hasData = false;
                while (rs.next()) {
                    hasData = true;
                    System.out.printf("%-12s | %-12s | %-10.2f | %-10.2f%n",
                            rs.getDate("date"),
                            rs.getString("type"),
                            rs.getBigDecimal("amount"),
                            rs.getBigDecimal("balance"));
                }

                if (!hasData) {
                    System.out.println("          No transactions found for account: " + accNo);
                }
                System.out.println("============================================================\n");
            }
        } catch (SQLException e) {
            System.out.println("Error: Could not retrieve statement. " + e.getMessage());
        }
    }
}