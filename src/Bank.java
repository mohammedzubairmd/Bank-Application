public class Bank {
    public static void main(String[] args) {
        System.out.println("------------------------------------------");
        System.out.println("      INITIALIZING MDZ BANK         ");
        System.out.println("------------------------------------------");

        // Initialize the UI and start the program
        BankUI ui = new BankUI();
        ui.start();

        System.out.println("\n[System Information]: Session closed safely.");
    }
}