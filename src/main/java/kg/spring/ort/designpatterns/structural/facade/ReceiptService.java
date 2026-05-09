package kg.spring.ort.designpatterns.structural.facade;

public class ReceiptService {

    public String generateReceipt(String customerId, String itemName, double amount) {
        String receipt = String.format(
            "RECEIPT | Customer: %s | Item: %s | Total: $%.2f",
            customerId, itemName, amount
        );
        System.out.printf("[Receipt] %s%n", receipt);
        return receipt;
    }
}
