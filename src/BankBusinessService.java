import java.sql.*;
import java.math.BigDecimal;

public class BankBusinessService {
    private AccountMasterDAO accountDAO = new AccountMasterDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    public double checkBalance(int accNo) {
        try (Connection conn = getConnection.getconnection()) {
            accountMaster account = accountDAO.retrieve(accNo, conn);
            return (account != null) ? account.getBalance().doubleValue() : -1.0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1.0;
        }
    }

    public void deposit(int accNo, double amount) {
        updateBalance(accNo, amount, "Deposit");
    }

    public void withdraw(int accNo, double amount) {
        double currentBal = checkBalance(accNo);
        if (currentBal >= amount) {
            updateBalance(accNo, -amount, "Withdrawal");
        } else {
            System.out.println("Error: Insufficient funds.");
        }
    }

    private void updateBalance(int accNo, double amount, String type) {
        try (Connection conn = getConnection.getconnection()) {
            conn.setAutoCommit(false);
            accountMaster account = accountDAO.retrieve(accNo, conn);
            if (account != null) {
                BigDecimal moveAmount = BigDecimal.valueOf(amount);
                BigDecimal newBalance = account.getBalance().add(moveAmount);
                account.setBalance(newBalance);
                accountDAO.update(account, conn);

                transaction tx = new transaction();
                tx.setAccno(accNo);
                tx.setDate(new Date(System.currentTimeMillis()));
                tx.setType(type);
                tx.setAmount(moveAmount.abs());
                tx.setBalance(newBalance);
                transactionDAO.insertTransaction(tx, conn);

                conn.commit();
                System.out.println(type + " Successful!");
            } else {
                System.out.println("Error: Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- NEW TRANSFER METHOD ---
    public void transfer(int fromAcc, int toAcc, double amount) {
        try (Connection conn = getConnection.getconnection()) {
            conn.setAutoCommit(false); // Start Transaction

            accountMaster sender = accountDAO.retrieve(fromAcc, conn);
            accountMaster receiver = accountDAO.retrieve(toAcc, conn);

            if (sender == null || receiver == null) {
                System.out.println("Error: One or both accounts do not exist.");
                return;
            }

            BigDecimal transferAmount = BigDecimal.valueOf(amount);
            if (sender.getBalance().compareTo(transferAmount) < 0) {
                System.out.println("Error: Insufficient balance in sender account.");
                return;
            }

            // 1. Deduct from Sender
            sender.setBalance(sender.getBalance().subtract(transferAmount));
            accountDAO.update(sender, conn);

            // 2. Add to Receiver
            receiver.setBalance(receiver.getBalance().add(transferAmount));
            accountDAO.update(receiver, conn);

            // 3. Log Transaction for Sender
            transaction txFrom = new transaction();
            txFrom.setAccno(fromAcc);
            txFrom.setDate(new Date(System.currentTimeMillis()));
            txFrom.setType("Transfer Out");
            txFrom.setAmount(transferAmount);
            txFrom.setBalance(sender.getBalance());
            transactionDAO.insertTransaction(txFrom, conn);

            // 4. Log Transaction for Receiver
            transaction txTo = new transaction();
            txTo.setAccno(toAcc);
            txTo.setDate(new Date(System.currentTimeMillis()));
            txTo.setType("Transfer In");
            txTo.setAmount(transferAmount);
            txTo.setBalance(receiver.getBalance());
            transactionDAO.insertTransaction(txTo, conn);

            conn.commit(); // Finalize both updates
            System.out.println("Transfer of $" + amount + " successful!");

        } catch (SQLException e) {
            System.out.println("Critical Error: Transfer failed. No money was moved.");
            e.printStackTrace();
        }
    }

    public int createNewAccount(String name, String address, String pan, String phone, double initialBalance) {
        try (Connection conn = getConnection.getconnection()) {
            int newAccNo = 1001;
            String lastAccQuery = "SELECT MAX(accno) FROM bank.accountmaster";
            try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(lastAccQuery)) {
                if (rs.next() && rs.getInt(1) != 0) {
                    newAccNo = rs.getInt(1) + 1;
                }
            }
            String insertQuery = "INSERT INTO bank.accountmaster (accno, name, address, date, pan, phone, balance) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertQuery)) {
                ps.setInt(1, newAccNo);
                ps.setString(2, name);
                ps.setString(3, address);
                ps.setDate(4, new Date(System.currentTimeMillis()));
                ps.setString(5, pan);
                ps.setString(6, phone);
                ps.setBigDecimal(7, BigDecimal.valueOf(initialBalance));
                ps.executeUpdate();
            }
            transaction tx = new transaction();
            tx.setAccno(newAccNo);
            tx.setDate(new Date(System.currentTimeMillis()));
            tx.setType("Opening Dep");
            tx.setAmount(BigDecimal.valueOf(initialBalance));
            tx.setBalance(BigDecimal.valueOf(initialBalance));
            transactionDAO.insertTransaction(tx, conn);

            return newAccNo;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}