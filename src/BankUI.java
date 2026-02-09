import java.util.Scanner;

public class BankUI {
    private Scanner scanner = new Scanner(System.in);
    private BankBusinessService service = new BankBusinessService();

    public void start() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- ZUBAIR BANK MAIN MENU ---");
            System.out.println("0. Open New Account");
            System.out.println("1. Deposit Money");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Check Balance");
            System.out.println("4. Mini Statement");
            System.out.println("5. Transfer Money"); // New
            System.out.println("6. Exit");
            System.out.print("Please enter your choice: ");

            int choice = getSafeIntInput();

            switch (choice) {
                case 0: handleCreateAccount(); break;
                case 1: handleDeposit(); break;
                case 2: handleWithdraw(); break;
                case 3: handleBalanceCheck(); break;
                case 4: handleStatement(); break;
                case 5: handleTransfer(); break; // New
                case 6: exit = true; System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void handleTransfer() {
        System.out.print("Enter Sender Account Number: ");
        int fromAcc = getSafeIntInput();
        System.out.print("Enter Receiver Account Number: ");
        int toAcc = getSafeIntInput();
        System.out.print("Enter Amount to Transfer: ");
        double amount = getSafeDoubleInput();

        if (amount > 0) {
            service.transfer(fromAcc, toAcc, amount);
        } else {
            System.out.println("Invalid amount.");
        }
    }

    private void handleCreateAccount() {
        System.out.println("\n--- OPEN NEW ACCOUNT ---");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter PAN Number: ");
        String pan = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Initial Deposit: ");
        double initialBalance = getSafeDoubleInput();

        int newAccNo = service.createNewAccount(name, address, pan, phone, initialBalance);
        if (newAccNo != -1) System.out.println("Success! Account Number: " + newAccNo);
        else System.out.println("Error creating account.");
    }

    private void handleDeposit() {
        System.out.print("Enter Account Number: ");
        int accNo = getSafeIntInput();
        System.out.print("Enter Amount: ");
        double amount = getSafeDoubleInput();
        service.deposit(accNo, amount);
    }

    private void handleWithdraw() {
        System.out.print("Enter Account Number: ");
        int accNo = getSafeIntInput();
        System.out.print("Enter Amount: ");
        double amount = getSafeDoubleInput();
        service.withdraw(accNo, amount);
    }

    private void handleBalanceCheck() {
        System.out.print("Enter Account Number: ");
        int accNo = getSafeIntInput();
        double balance = service.checkBalance(accNo);
        if (balance != -1.0) System.out.printf("Balance: $%.2f%n", balance);
        else System.out.println("Account not found.");
    }

    private void handleStatement() {
        System.out.print("Enter Account Number: ");
        int accNo = getSafeIntInput();
        new AccountValidationAndDetails().displayMiniStatement(accNo);
    }

    private int getSafeIntInput() {
        try { return Integer.parseInt(scanner.nextLine()); } catch (Exception e) { return -1; }
    }

    private double getSafeDoubleInput() {
        try { return Double.parseDouble(scanner.nextLine()); } catch (Exception e) { return 0.0; }
    }
}