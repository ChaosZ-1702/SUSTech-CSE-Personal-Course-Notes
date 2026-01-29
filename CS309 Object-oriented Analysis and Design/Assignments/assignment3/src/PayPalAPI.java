// The "Adaptee"
public class PayPalAPI {
    // Note: It uses Token-based logic and different method names
    public void makePayment(String token, float transactionAmount) {
        System.out.println("PayPal: Processing payment of " + transactionAmount + " with token " + token);
    }
    public String getToken() {
        return "User-Token-123";
    }
}