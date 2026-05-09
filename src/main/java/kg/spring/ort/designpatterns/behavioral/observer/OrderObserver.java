package kg.spring.ort.designpatterns.behavioral.observer;

public interface OrderObserver {

    void onOrderStatusChanged(String orderId, OrderStatus newStatus);
}
