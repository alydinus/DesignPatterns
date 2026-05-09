package kg.spring.ort.designpatterns.behavioral.strategy;

public class RegularPriceStrategy implements DiscountStrategy {

    @Override
    public double applyDiscount(double originalPrice) {
        return originalPrice;
    }

    @Override
    public String getStrategyName() {
        return "Regular Price (no discount)";
    }
}
