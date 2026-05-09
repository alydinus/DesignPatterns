package kg.spring.ort.designpatterns.structural.adapter;

public class CardPaymentProcessor implements PaymentProcessor {

    private final String cardNumber;

    public CardPaymentProcessor(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean pay(double amount) {
        String masked = "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
        System.out.printf("[Card] Charging $%.2f to %s%n", amount, masked);
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return "CARD";
    }
}
