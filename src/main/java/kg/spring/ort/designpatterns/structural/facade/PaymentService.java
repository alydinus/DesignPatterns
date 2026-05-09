package kg.spring.ort.designpatterns.structural.facade;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PaymentService {

    public boolean processPayment(String customerId, double amount) {
        log.info("[Payment] Charging ${} to customer {}", String.format("%.2f", amount), customerId);
        return true;
    }
}
