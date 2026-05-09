package kg.spring.ort.designpatterns.structural.adapter;

public class CashPaymentAdapter implements PaymentProcessor {

    private final CashPaymentSystem legacySystem;
    private final int providedCents;

    public CashPaymentAdapter(CashPaymentSystem legacySystem, int providedCents) {
        this.legacySystem  = legacySystem;
        this.providedCents = providedCents;
    }

    @Override
    public boolean pay(double amount) {
        int priceCents = (int) (amount * 100);
        legacySystem.insertCash(providedCents);
        boolean success = legacySystem.validateCash(providedCents, priceCents);
        if (success) {
            int change = legacySystem.giveChange(providedCents, priceCents);
            System.out.printf("[Adapter] Cash accepted. Change: %d cents%n", change);
        }
        return success;
    }

    @Override
    public String getPaymentMethod() {
        return "CASH (legacy system via Adapter)";
    }
}
