package kg.spring.ort.designpatterns;

import kg.spring.ort.designpatterns.behavioral.observer.CoffeeShopOrder;
import kg.spring.ort.designpatterns.behavioral.observer.CustomerNotifier;
import kg.spring.ort.designpatterns.behavioral.observer.KitchenNotifier;
import kg.spring.ort.designpatterns.behavioral.observer.OrderStatus;
import kg.spring.ort.designpatterns.behavioral.strategy.LoyaltyDiscountStrategy;
import kg.spring.ort.designpatterns.behavioral.strategy.PriceCalculator;
import kg.spring.ort.designpatterns.behavioral.strategy.RegularPriceStrategy;
import kg.spring.ort.designpatterns.behavioral.strategy.SeasonalDiscountStrategy;
import kg.spring.ort.designpatterns.creational.builder.CoffeeOrder;
import kg.spring.ort.designpatterns.creational.singleton.MenuRegistry;
import kg.spring.ort.designpatterns.structural.adapter.CardPaymentProcessor;
import kg.spring.ort.designpatterns.structural.adapter.CashPaymentAdapter;
import kg.spring.ort.designpatterns.structural.adapter.CashPaymentSystem;
import kg.spring.ort.designpatterns.structural.adapter.PaymentProcessor;
import kg.spring.ort.designpatterns.structural.decorator.Beverage;
import kg.spring.ort.designpatterns.structural.decorator.CaramelDecorator;
import kg.spring.ort.designpatterns.structural.decorator.Espresso;
import kg.spring.ort.designpatterns.structural.decorator.ExtraShotDecorator;
import kg.spring.ort.designpatterns.structural.decorator.Latte;
import kg.spring.ort.designpatterns.structural.decorator.WhipDecorator;
import kg.spring.ort.designpatterns.structural.facade.OrderFacade;

public class Main {

    public static void main(String[] args) {
        System.out.println("\n========================================");
        System.out.println("   COFFEE SHOP — Design Patterns Demo   ");
        System.out.println("========================================\n");

        demonstrateBuilder();
        demonstrateSingleton();
        demonstrateDecorator();
        demonstrateFacade();
        demonstrateAdapter();
        demonstrateObserver();
        demonstrateStrategy();

        System.out.println("\n========================================");
        System.out.println("              Demo complete             ");
        System.out.println("========================================");
    }

    // ── Creational: Builder ──────────────────────────────────────────────────
    private static void demonstrateBuilder() {
        System.out.println("\n--- [BUILDER] Building a customized coffee order ---");

        CoffeeOrder simple = new CoffeeOrder.Builder("Espresso")
                .build();

        CoffeeOrder complex = new CoffeeOrder.Builder("Latte")
                .size("LARGE")
                .milkType("OAT")
                .espressoShots(2)
                .withWhip()
                .syrupFlavor("VANILLA")
                .iced()
                .build();

        System.out.println("Simple order  : " + simple);
        System.out.println("Complex order : " + complex);
    }

    // ── Creational: Singleton ────────────────────────────────────────────────
    private static void demonstrateSingleton() {
        System.out.println("\n--- [SINGLETON] Menu Registry ---");

        MenuRegistry menu1 = MenuRegistry.getInstance();
        MenuRegistry menu2 = MenuRegistry.getInstance();
        System.out.println("Same instance? " + (menu1 == menu2));
        System.out.println("Full menu: " + menu1.getFullMenu());

        menu1.addItem("Matcha Latte", 4.75);
        System.out.printf("Matcha Latte price via menu2: $%.2f%n", menu2.getPrice("Matcha Latte"));
    }

    // ── Structural: Decorator ────────────────────────────────────────────────
    private static void demonstrateDecorator() {
        System.out.println("\n--- [DECORATOR] Wrapping beverages with condiments ---");

        Beverage drink = new Espresso();
        System.out.printf("Base: %s — $%.2f%n", drink.getDescription(), drink.cost());

        drink = new WhipDecorator(drink);
        drink = new CaramelDecorator(drink);
        drink = new ExtraShotDecorator(drink);
        System.out.printf("After decorators: %s — $%.2f%n", drink.getDescription(), drink.cost());

        Beverage latte = new CaramelDecorator(new WhipDecorator(new Latte()));
        System.out.printf("Latte combo: %s — $%.2f%n", latte.getDescription(), latte.cost());
    }

    // ── Structural: Facade ───────────────────────────────────────────────────
    private static void demonstrateFacade() {
        System.out.println("\n--- [FACADE] Placing an order through one method ---");

        OrderFacade facade = new OrderFacade();
        String receipt = facade.placeOrder("customer-42", "Cappuccino", 3.75);
        System.out.println("Result: " + receipt);
    }

    // ── Structural: Adapter ──────────────────────────────────────────────────
    private static void demonstrateAdapter() {
        System.out.println("\n--- [ADAPTER] Paying with different systems via common interface ---");

        PaymentProcessor card = new CardPaymentProcessor("4111111111111234");
        System.out.printf("Method: %s | Success: %s%n", card.getPaymentMethod(), card.pay(4.50));

        PaymentProcessor cash = new CashPaymentAdapter(new CashPaymentSystem(), 500);
        System.out.printf("Method: %s | Success: %s%n", cash.getPaymentMethod(), cash.pay(4.50));
    }

    // ── Behavioral: Observer ─────────────────────────────────────────────────
    private static void demonstrateObserver() {
        System.out.println("\n--- [OBSERVER] Order status notifications ---");

        CoffeeShopOrder order = new CoffeeShopOrder("ORD-001");
        order.addObserver(new CustomerNotifier("Alice"));
        order.addObserver(new KitchenNotifier());

        order.updateStatus(OrderStatus.PREPARING);
        order.updateStatus(OrderStatus.READY);
        order.updateStatus(OrderStatus.DELIVERED);
    }

    // ── Behavioral: Strategy ─────────────────────────────────────────────────
    private static void demonstrateStrategy() {
        System.out.println("\n--- [STRATEGY] Applying different discount strategies ---");

        double basePrice = 4.00;
        PriceCalculator calculator = new PriceCalculator(new RegularPriceStrategy());
        calculator.calculatePrice(basePrice);

        calculator.setDiscountStrategy(new LoyaltyDiscountStrategy());
        calculator.calculatePrice(basePrice);

        calculator.setDiscountStrategy(new SeasonalDiscountStrategy("Summer", 0.20));
        calculator.calculatePrice(basePrice);
    }
}
