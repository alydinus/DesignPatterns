package kg.spring.ort.designpatterns.behavioral.observer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CoffeeShopOrder {

    @Getter
    private final String orderId;
    @Getter
    private OrderStatus status;
    private final List<OrderObserver> observers = new ArrayList<>();

    public CoffeeShopOrder(String orderId) {
        this.orderId = orderId;
        this.status  = OrderStatus.RECEIVED;
    }

    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    public void updateStatus(OrderStatus newStatus) {
        log.info("[Order {}] {} → {}", orderId, status, newStatus);
        this.status = newStatus;
        for (OrderObserver observer : observers) {
            observer.onOrderStatusChanged(orderId, newStatus);
        }
    }
}
