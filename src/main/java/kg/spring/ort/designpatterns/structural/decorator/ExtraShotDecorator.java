package kg.spring.ort.designpatterns.structural.decorator;

public class ExtraShotDecorator extends CondimentDecorator {

    public ExtraShotDecorator(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Extra Espresso Shot";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.60;
    }
}
