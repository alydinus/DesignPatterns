package kg.spring.ort.designpatterns.structural.decorator;

public abstract class CondimentDecorator extends Beverage {

    protected final Beverage beverage;

    protected CondimentDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public abstract String getDescription();
}
