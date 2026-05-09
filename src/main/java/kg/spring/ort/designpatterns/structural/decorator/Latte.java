package kg.spring.ort.designpatterns.structural.decorator;

public class Latte extends Beverage {

    public Latte() {
        description = "Latte";
    }

    @Override
    public double cost() {
        return 4.00;
    }
}
