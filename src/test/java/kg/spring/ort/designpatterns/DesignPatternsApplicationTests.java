package kg.spring.ort.designpatterns;

import kg.spring.ort.designpatterns.creational.builder.CoffeeOrder;
import kg.spring.ort.designpatterns.creational.singleton.MenuRegistry;
import kg.spring.ort.designpatterns.structural.decorator.Beverage;
import kg.spring.ort.designpatterns.structural.decorator.Espresso;
import kg.spring.ort.designpatterns.structural.decorator.WhipDecorator;
import kg.spring.ort.designpatterns.behavioral.strategy.LoyaltyDiscountStrategy;
import kg.spring.ort.designpatterns.behavioral.strategy.PriceCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DesignPatternsApplicationTests {

    @Test
    void builderCreatesOrderWithDefaults() {
        CoffeeOrder order = new CoffeeOrder.Builder("Espresso").build();
        assertEquals("Espresso", order.getDrinkType());
        assertEquals("MEDIUM", order.getSize());
        assertFalse(order.isWhippedCream());
    }

    @Test
    void builderCreatesFullyCustomOrder() {
        CoffeeOrder order = new CoffeeOrder.Builder("Latte")
                .size("LARGE").milkType("OAT").espressoShots(2).withWhip().iced()
                .build();
        assertEquals("LARGE", order.getSize());
        assertEquals(2, order.getEspressoShots());
        assertTrue(order.isWhippedCream());
        assertTrue(order.isIced());
    }

    @Test
    void singletonReturnsSameInstance() {
        MenuRegistry a = MenuRegistry.getInstance();
        MenuRegistry b = MenuRegistry.getInstance();
        assertSame(a, b);
    }

    @Test
    void singletonSharesState() {
        MenuRegistry.getInstance().addItem("TestDrink", 9.99);
        assertEquals(9.99, MenuRegistry.getInstance().getPrice("TestDrink"), 0.001);
    }

    @Test
    void decoratorAccumulatesCostAndDescription() {
        Beverage drink = new WhipDecorator(new Espresso());
        assertEquals(3.00, drink.cost(), 0.001);
        assertTrue(drink.getDescription().contains("Whipped Cream"));
    }

    @Test
    void strategySwapsDiscountAtRuntime() {
        PriceCalculator calc = new PriceCalculator(new LoyaltyDiscountStrategy());
        assertEquals(3.60, calc.calculatePrice(4.00), 0.001);
    }
}
