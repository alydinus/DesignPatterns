package kg.spring.ort.designpatterns.structural.decorator;

public class WhipDecorator extends CondimentDecorator {

    public WhipDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Whipped Cream";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.50;
    }
}
