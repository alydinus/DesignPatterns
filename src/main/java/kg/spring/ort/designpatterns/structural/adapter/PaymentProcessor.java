package kg.spring.ort.designpatterns.structural.adapter;

public interface PaymentProcessor {

    boolean pay(double amount);

    String getPaymentMethod();
}
