package kg.spring.ort.designpatterns.behavioral.observer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomerNotifier implements OrderObserver {

    private final String customerName;

    public CustomerNotifier(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public void onOrderStatusChanged(String orderId, OrderStatus newStatus) {
        log.info("[SMS/Email → {}] Your order #{} status: {}", customerName, orderId, newStatus);
    }
}
