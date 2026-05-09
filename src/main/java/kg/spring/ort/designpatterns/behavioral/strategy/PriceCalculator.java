package kg.spring.ort.designpatterns.behavioral.strategy;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PriceCalculator {

    @Setter
    private DiscountStrategy discountStrategy;

    public PriceCalculator(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double calculatePrice(double basePrice) {
        double final_ = discountStrategy.applyDiscount(basePrice);
        log.info("[Pricing] Strategy: {} | Base: ${} | Final: ${}",
            discountStrategy.getStrategyName(),
            String.format("%.2f", basePrice),
            String.format("%.2f", final_));
        return final_;
    }
}
