package kg.spring.ort.designpatterns.behavioral.observer;

public class KitchenNotifier implements OrderObserver {

    @Override
    public void onOrderStatusChanged(String orderId, OrderStatus newStatus) {
        switch (newStatus) {
            case PREPARING -> System.out.printf("[Kitchen Display] START preparing order #%s%n", orderId);
            case READY     -> System.out.printf("[Kitchen Display] Order #%s is READY for pickup%n", orderId);
            case CANCELLED -> System.out.printf("[Kitchen Display] Order #%s CANCELLED — discard%n", orderId);
            default        -> { /* kitchen ignores other statuses */ }
        }
    }
}
