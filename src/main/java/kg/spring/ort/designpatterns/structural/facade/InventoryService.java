package kg.spring.ort.designpatterns.structural.facade;

public class InventoryService {

    public boolean checkAvailability(String item) {
        System.out.printf("[Inventory] Checking stock for: %s%n", item);
        return true;
    }

    public void deductItem(String item) {
        System.out.printf("[Inventory] Deducted item: %s%n", item);
    }
}
