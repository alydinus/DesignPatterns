package kg.spring.ort.designpatterns.behavioral.observer;

public class CustomerNotifier implements OrderObserver {

    private final String customerName;

    public CustomerNotifier(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public void onOrderStatusChanged(String orderId, OrderStatus newStatus) {
        System.out.printf("[SMS/Email → %s] Your order #%s status: %s%n", customerName, orderId, newStatus);
    }
}
