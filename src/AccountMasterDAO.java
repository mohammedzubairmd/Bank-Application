import java.sql.*;

public class AccountMasterDAO {

    public accountMaster retrieve(int accNo, Connection connection) {
        String query = "SELECT * FROM bank.accountmaster WHERE accno = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, accNo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    accountMaster account = new accountMaster();
                    account.setAccno(resultSet.getInt("accno"));
                    account.setName(resultSet.getString("name"));
                    account.setAddress(resultSet.getString("address"));
                    account.setDate(resultSet.getDate("date"));
                    account.setPan(resultSet.getString("pan"));
                    account.setPhone(resultSet.getString("phone"));
                    account.setEmail(resultSet.getString("email"));
                    account.setBalance(resultSet.getBigDecimal("balance"));
                    return account;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(accountMaster account, Connection connection) {
        String query = "UPDATE bank.accountmaster SET name=?, address=?, date=?, pan=?, phone=?, email=?, balance=? WHERE accno=?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, account.getName());
            statement.setString(2, account.getAddress());
            statement.setDate(3, account.getDate());
            statement.setString(4, account.getPan());
            statement.setString(5, account.getPhone());
            statement.setString(6, account.getEmail());
            statement.setBigDecimal(7, account.getBalance());
            statement.setInt(8, account.getAccno());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}