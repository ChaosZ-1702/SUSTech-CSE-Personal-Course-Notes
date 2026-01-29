public class CreditCardProcessor implements PaymentGateway {

    @Override
    public void pay(double amount) {
        System.out.println("using a simple implementation of PaymentGateway...");
    }
}

