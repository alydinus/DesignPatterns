package kg.spring.ort.designpatterns.structural.facade;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderFacade {

    private final InventoryService inventoryService = new InventoryService();
    private final PaymentService   paymentService   = new PaymentService();
    private final ReceiptService   receiptService   = new ReceiptService();

    public String placeOrder(String customerId, String itemName, double price) {
        log.info("[Facade] === Starting order for customer: {} ===", customerId);

        if (!inventoryService.checkAvailability(itemName)) {
            return "FAILED: Item not available — " + itemName;
        }
        if (!paymentService.processPayment(customerId, price)) {
            return "FAILED: Payment declined for customer " + customerId;
        }
        inventoryService.deductItem(itemName);

        return receiptService.generateReceipt(customerId, itemName, price);
    }
}
