package kg.spring.ort.designpatterns.structural.decorator;

public class CaramelDecorator extends CondimentDecorator {

    public CaramelDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Caramel Syrup";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.75;
    }
}
