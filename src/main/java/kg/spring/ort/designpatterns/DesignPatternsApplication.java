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
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class DesignPatternsApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DesignPatternsApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("\n========================================");
        log.info("   COFFEE SHOP — Design Patterns Demo   ");
        log.info("========================================\n");

        demonstrateBuilder();
        demonstrateSingleton();
        demonstrateDecorator();
        demonstrateFacade();
        demonstrateAdapter();
        demonstrateObserver();
        demonstrateStrategy();

        log.info("\n========================================");
        log.info("              Demo complete             ");
        log.info("========================================");
    }

    // ── Creational: Builder ──────────────────────────────────────────────────
    private void demonstrateBuilder() {
        log.info("\n--- [BUILDER] Building a customized coffee order ---");

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

        log.info("Simple order  : {}", simple);
        log.info("Complex order : {}", complex);
    }

    // ── Creational: Singleton ────────────────────────────────────────────────
    private void demonstrateSingleton() {
        log.info("\n--- [SINGLETON] Menu Registry ---");

        MenuRegistry menu1 = MenuRegistry.getInstance();
        MenuRegistry menu2 = MenuRegistry.getInstance();
        log.info("Same instance? {}", menu1 == menu2);
        log.info("Full menu: {}", menu1.getFullMenu());

        menu1.addItem("Matcha Latte", 4.75);
        log.info("Matcha Latte price via menu2: ${}", menu2.getPrice("Matcha Latte"));
    }

    // ── Structural: Decorator ────────────────────────────────────────────────
    private void demonstrateDecorator() {
        log.info("\n--- [DECORATOR] Wrapping beverages with condiments ---");

        Beverage drink = new Espresso();
        log.info("Base: {} — ${}", drink.getDescription(), drink.cost());

        drink = new WhipDecorator(drink);
        drink = new CaramelDecorator(drink);
        drink = new ExtraShotDecorator(drink);
        log.info("After decorators: {} — ${}", drink.getDescription(), drink.cost());

        Beverage latte = new CaramelDecorator(new WhipDecorator(new Latte()));
        log.info("Latte combo: {} — ${}", latte.getDescription(), latte.cost());
    }

    // ── Structural: Facade ───────────────────────────────────────────────────
    private void demonstrateFacade() {
        log.info("\n--- [FACADE] Placing an order through one method ---");

        OrderFacade facade = new OrderFacade();
        String receipt = facade.placeOrder("customer-42", "Cappuccino", 3.75);
        log.info("Result: {}", receipt);
    }

    // ── Structural: Adapter ──────────────────────────────────────────────────
    private void demonstrateAdapter() {
        log.info("\n--- [ADAPTER] Paying with different systems via common interface ---");

        PaymentProcessor card = new CardPaymentProcessor("4111111111111234");
        log.info("Method: {} | Success: {}", card.getPaymentMethod(), card.pay(4.50));

        CashPaymentSystem legacy = new CashPaymentSystem();
        PaymentProcessor cash = new CashPaymentAdapter(legacy, 500); // 500 cents = $5.00
        log.info("Method: {} | Success: {}", cash.getPaymentMethod(), cash.pay(4.50));
    }

    // ── Behavioral: Observer ─────────────────────────────────────────────────
    private void demonstrateObserver() {
        log.info("\n--- [OBSERVER] Order status notifications ---");

        CoffeeShopOrder order = new CoffeeShopOrder("ORD-001");
        order.addObserver(new CustomerNotifier("Alice"));
        order.addObserver(new KitchenNotifier());

        order.updateStatus(OrderStatus.PREPARING);
        order.updateStatus(OrderStatus.READY);
        order.updateStatus(OrderStatus.DELIVERED);
    }

    // ── Behavioral: Strategy ─────────────────────────────────────────────────
    private void demonstrateStrategy() {
        log.info("\n--- [STRATEGY] Applying different discount strategies ---");

        double basePrice = 4.00;
        PriceCalculator calculator = new PriceCalculator(new RegularPriceStrategy());
        calculator.calculatePrice(basePrice);

        calculator.setDiscountStrategy(new LoyaltyDiscountStrategy());
        calculator.calculatePrice(basePrice);

        calculator.setDiscountStrategy(new SeasonalDiscountStrategy("Summer", 0.20));
        calculator.calculatePrice(basePrice);
    }
}
