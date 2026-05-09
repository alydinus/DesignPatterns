package kg.spring.ort.designpatterns.structural.adapter;

/**
 * Legacy cash-payment system that works in cents — incompatible with the modern PaymentProcessor interface.
 */
public class CashPaymentSystem {

    public void insertCash(int cents) {
        System.out.printf("[Legacy Cash] Inserted %d cents%n", cents);
    }

    public boolean validateCash(int insertedCents, int priceCents) {
        boolean ok = insertedCents >= priceCents;
        System.out.printf("[Legacy Cash] Validating: %d >= %d → %s%n", insertedCents, priceCents, ok);
        return ok;
    }

    public int giveChange(int insertedCents, int priceCents) {
        return insertedCents - priceCents;
    }
}
