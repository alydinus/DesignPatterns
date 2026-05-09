package kg.spring.ort.designpatterns.behavioral.strategy;

public interface DiscountStrategy {

    double applyDiscount(double originalPrice);

    String getStrategyName();
}
