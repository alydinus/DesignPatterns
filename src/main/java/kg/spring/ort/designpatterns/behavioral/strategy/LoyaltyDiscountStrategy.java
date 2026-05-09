package kg.spring.ort.designpatterns.behavioral.strategy;

public class LoyaltyDiscountStrategy implements DiscountStrategy {

    private static final double DISCOUNT = 0.10;

    @Override
    public double applyDiscount(double originalPrice) {
        return originalPrice * (1 - DISCOUNT);
    }

    @Override
    public String getStrategyName() {
        return "Loyalty Card (10% off)";
    }
}
