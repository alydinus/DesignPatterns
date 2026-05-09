package kg.spring.ort.designpatterns.structural.adapter;

import lombok.extern.slf4j.Slf4j;

/**
 * Legacy cash-payment system that works in cents — incompatible with the modern PaymentProcessor interface.
 */
@Slf4j
public class CashPaymentSystem {

    public void insertCash(int cents) {
        log.info("[Legacy Cash] Inserted {} cents", cents);
    }

    public boolean validateCash(int insertedCents, int priceCents) {
        boolean ok = insertedCents >= priceCents;
        log.info("[Legacy Cash] Validating: {} >= {} → {}", insertedCents, priceCents, ok);
        return ok;
    }

    public int giveChange(int insertedCents, int priceCents) {
        return insertedCents - priceCents;
    }
}
