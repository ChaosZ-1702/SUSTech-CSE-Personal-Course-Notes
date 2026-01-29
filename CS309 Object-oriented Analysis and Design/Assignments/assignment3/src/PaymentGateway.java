public interface PaymentGateway {
    // Returns true if successful
    void pay(double amount);
}