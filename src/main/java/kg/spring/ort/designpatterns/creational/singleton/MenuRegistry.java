package kg.spring.ort.designpatterns.creational.singleton;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MenuRegistry {

    private static volatile MenuRegistry instance;
    private final Map<String, Double> menuItems;

    private MenuRegistry() {
        menuItems = new HashMap<>();
        menuItems.put("Espresso",   2.50);
        menuItems.put("Latte",      4.00);
        menuItems.put("Cappuccino", 3.75);
        menuItems.put("Americano",  3.00);
        menuItems.put("Mocha",      4.50);
    }

    public static MenuRegistry getInstance() {
        if (instance == null) {
            synchronized (MenuRegistry.class) {
                if (instance == null) {
                    instance = new MenuRegistry();
                }
            }
        }
        return instance;
    }

    public double getPrice(String item) {
        return menuItems.getOrDefault(item, 0.0);
    }

    public Map<String, Double> getFullMenu() {
        return Collections.unmodifiableMap(menuItems);
    }

    public void addItem(String name, double price) {
        menuItems.put(name, price);
    }
}
