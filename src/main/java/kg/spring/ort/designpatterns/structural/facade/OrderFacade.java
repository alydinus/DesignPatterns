package kg.spring.ort.designpatterns.structural.facade;

public class OrderFacade {

    private final InventoryService inventoryService = new InventoryService();
    private final PaymentService   paymentService   = new PaymentService();
    private final ReceiptService   receiptService   = new ReceiptService();

    public String placeOrder(String customerId, String itemName, double price) {
        System.out.printf("[Facade] === Starting order for customer: %s ===%n", customerId);

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
