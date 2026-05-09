package kg.spring.ort.designpatterns.structural.adapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CardPaymentProcessor implements PaymentProcessor {

    private final String cardNumber;

    public CardPaymentProcessor(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean pay(double amount) {
        String masked = "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
        log.info("[Card] Charging ${} to {}", String.format("%.2f", amount), masked);
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return "CARD";
    }
}
