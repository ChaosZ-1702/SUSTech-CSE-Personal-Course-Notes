// Legacy code - DO NOT USE IN PRODUCTION
public class OrderServiceOriginal {
    private String _customerName;
    private double _totalAmount;

    public OrderServiceOriginal(String name, double amount) {
        this._customerName = name;
        this._totalAmount = amount;
    }

    public void processOrder() {
        // 1. Print Banner
        System.out.println("**************************");
        System.out.println("***** ORDER PROCESS ******");
        System.out.println("**************************");
        // 2. Validate Inventory (Simulated logic)
        if (_totalAmount <= 0) {
            System.out.println("Error: Invalid order amount.");
            return;
        }
        System.out.println("Checking inventory for customer: " + _customerName);
        // ... simplistic inventory check logic ...
        // 3. Process Payment
        // (This logic is mixed directly inside the method)
        System.out.println("Connecting to Standard Payment Gateway...");
        System.out.println("Processing payment of $" + _totalAmount);
        System.out.println("Payment Successful.");
        // 4. Print Receipt
        System.out.println("Name: " + _customerName);
        System.out.println("Amount: " + _totalAmount);
        System.out.println("Date: " + java.time.LocalDate.now());
    }
}
