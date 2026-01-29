public class PayPalAdapter implements PaymentGateway {
    private PayPalAPI payPalAPI;

    public PayPalAdapter(PayPalAPI payPalAPI) {
        this.payPalAPI = payPalAPI;
    }
    @Override
    public void pay(double amount) {
        String token = this.payPalAPI.getToken();
        float tAmount = (float) amount;
        this.payPalAPI.makePayment(token, tAmount);
    }
}
