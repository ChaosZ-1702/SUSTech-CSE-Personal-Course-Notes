import java.time.LocalDate;

// Legacy code - DO NOT USE IN PRODUCTION
public class OrderService {
    private String _customerName;
    private double _totalAmount;
    private PaymentGateway gateway;

    public OrderService(String name, double amount, PaymentGateway gateway) {
        this._customerName = name;
        this._totalAmount = amount;
        this.gateway = gateway;
    }

    public void processOrder() {
        // 1. Print Banner
        printBanner();
        // 2. Validate Inventory (Simulated logic)
        // 3. Process Payment
        processPayment();
        // 4. Print Receipt
        printReceipt(java.time.LocalDate.now());
    }

    private void printBanner() {
        System.out.println("**************************");
        System.out.println("***** ORDER PROCESS ******");
        System.out.println("**************************");
    }

    private void printReceipt(LocalDate date) {
        System.out.println("Name: " + _customerName);
        System.out.println("Amount: " + _totalAmount);
        System.out.println("Date: " + date);
    }

    private void processPayment() {
        if (_totalAmount <= 0) {
            System.out.println("Error: Invalid order amount.");
            return;
        }
        System.out.println("Checking inventory for customer: " + _customerName);
        // ... simplistic inventory check logic ...
        // (This logic is mixed directly inside the method)
        this.gateway.pay(_totalAmount);
    }
}
