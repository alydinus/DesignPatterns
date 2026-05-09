package kg.spring.ort.designpatterns.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

public class CoffeeShopOrder {

    private final String orderId;
    private OrderStatus status;
    private final List<OrderObserver> observers = new ArrayList<>();

    public CoffeeShopOrder(String orderId) {
        this.orderId = orderId;
        this.status  = OrderStatus.RECEIVED;
    }

    public String      getOrderId() { return orderId; }
    public OrderStatus getStatus()  { return status; }

    public void addObserver(OrderObserver observer)    { observers.add(observer); }
    public void removeObserver(OrderObserver observer) { observers.remove(observer); }

    public void updateStatus(OrderStatus newStatus) {
        System.out.printf("[Order %s] %s → %s%n", orderId, status, newStatus);
        this.status = newStatus;
        for (OrderObserver observer : observers) {
            observer.onOrderStatusChanged(orderId, newStatus);
        }
    }
}
