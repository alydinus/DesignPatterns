package kg.spring.ort.designpatterns.structural.facade;

public class PaymentService {

    public boolean processPayment(String customerId, double amount) {
        System.out.printf("[Payment] Charging $%.2f to customer %s%n", amount, customerId);
        return true;
    }
}
