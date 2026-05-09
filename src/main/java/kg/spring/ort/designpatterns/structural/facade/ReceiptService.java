package kg.spring.ort.designpatterns.structural.facade;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReceiptService {

    public String generateReceipt(String customerId, String itemName, double amount) {
        String receipt = String.format(
            "RECEIPT | Customer: %s | Item: %s | Total: $%.2f",
            customerId, itemName, amount
        );
        log.info("[Receipt] {}", receipt);
        return receipt;
    }
}
