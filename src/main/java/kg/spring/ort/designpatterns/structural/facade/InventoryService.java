package kg.spring.ort.designpatterns.structural.facade;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InventoryService {

    public boolean checkAvailability(String item) {
        log.info("[Inventory] Checking stock for: {}", item);
        return true;
    }

    public void deductItem(String item) {
        log.info("[Inventory] Deducted item: {}", item);
    }
}
