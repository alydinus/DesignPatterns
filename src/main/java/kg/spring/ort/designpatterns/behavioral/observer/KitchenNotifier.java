package kg.spring.ort.designpatterns.behavioral.observer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KitchenNotifier implements OrderObserver {

    @Override
    public void onOrderStatusChanged(String orderId, OrderStatus newStatus) {
        switch (newStatus) {
            case PREPARING -> log.info("[Kitchen Display] START preparing order #{}", orderId);
            case READY     -> log.info("[Kitchen Display] Order #{} is READY for pickup", orderId);
            case CANCELLED -> log.info("[Kitchen Display] Order #{} CANCELLED — discard", orderId);
            default        -> { /* kitchen ignores other statuses */ }
        }
    }
}
