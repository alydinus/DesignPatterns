package kg.spring.ort.designpatterns.behavioral.strategy;

public class PriceCalculator {

    private DiscountStrategy discountStrategy;

    public PriceCalculator(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double calculatePrice(double basePrice) {
        double result = discountStrategy.applyDiscount(basePrice);
        System.out.printf("[Pricing] Strategy: %s | Base: $%.2f | Final: $%.2f%n",
            discountStrategy.getStrategyName(), basePrice, result);
        return result;
    }
}
