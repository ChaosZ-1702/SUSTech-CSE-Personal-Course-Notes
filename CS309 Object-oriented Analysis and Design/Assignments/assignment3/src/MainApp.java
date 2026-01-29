public class MainApp {
    public static void main(String[] args) {
        // Scenario A
        new OrderService("John Doe", 199.99, new CreditCardProcessor())
                .processOrder();

        // Scenario B
        PayPalAPI ppAPI = new PayPalAPI();
        PayPalAdapter ppA = new PayPalAdapter(ppAPI);
        OrderService os = new OrderService("Alice", 100.0, ppA);
        os.processOrder();
    }
}