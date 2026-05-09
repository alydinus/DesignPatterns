package kg.spring.ort.designpatterns.behavioral.strategy;

public class SeasonalDiscountStrategy implements DiscountStrategy {

    private final String season;
    private final double discountRate;

    public SeasonalDiscountStrategy(String season, double discountRate) {
        this.season       = season;
        this.discountRate = discountRate;
    }

    @Override
    public double applyDiscount(double originalPrice) {
        return originalPrice * (1 - discountRate);
    }

    @Override
    public String getStrategyName() {
        return season + " Sale (" + (int) (discountRate * 100) + "% off)";
    }
}
