# Design Patterns — Coffee Shop Order System

## Тема проекта

Система заказов кофейни. Клиент собирает кастомный заказ (напиток + добавки), оплачивает его, получает чек и уведомления о статусе приготовления.

---

## Паттерны

### Creational (Порождающие)

---

#### 1. Builder — `CoffeeOrder`

**Что делает:**
Позволяет пошагово конструировать сложный объект с множеством необязательных параметров без огромного конструктора.

**Как реализован:**
`CoffeeOrder` имеет приватный конструктор. Снаружи создание идёт только через вложенный `Builder`. Обязателен только `drinkType`, остальное (`size`, `milkType`, `espressoShots`, `whippedCream`, `syrupFlavor`, `iced`) имеет дефолтные значения и задаётся через fluent-методы.

```java
CoffeeOrder order = new CoffeeOrder.Builder("Latte")
    .size("LARGE")
    .milkType("OAT")
    .espressoShots(2)
    .withWhip()
    .syrupFlavor("VANILLA")
    .iced()
    .build();
```

**Зачем нужен:**
Без Builder потребовался бы конструктор с 7+ параметрами, и вызывающий код был бы нечитаем. Builder делает API выразительным и безопасным — нельзя забыть обязательное поле.

---

#### 2. Singleton — `MenuRegistry`

**Что делает:**
Гарантирует, что во всём приложении существует ровно один экземпляр реестра меню.

**Как реализован:**
Double-checked locking: поле `instance` помечено `volatile`, первая проверка без блокировки для производительности, вторая — внутри `synchronized` блока для потокобезопасности.

```java
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
```

**Зачем нужен:**
Меню — общий ресурс. Нет смысла создавать его копии. Любое добавление позиции (`menu1.addItem(...)`) немедленно видно через `menu2.getPrice(...)`, так как это один и тот же объект.

---

### Structural (Структурные)

---

#### 3. Decorator — `Beverage` + `CondimentDecorator`

**Что делает:**
Динамически добавляет новое поведение объекту, не изменяя его класс. Декораторы "оборачивают" базовый объект как матрёшки.

**Как реализован:**
- `Beverage` — абстрактный базовый класс с `getDescription()` и `cost()`
- `Espresso`, `Latte` — конкретные напитки
- `CondimentDecorator` — абстрактный декоратор, хранит `Beverage beverage`
- `WhipDecorator`, `CaramelDecorator`, `ExtraShotDecorator` — конкретные декораторы, расширяют описание и добавляют к цене

```java
Beverage drink = new Espresso();                   // $2.50
drink = new WhipDecorator(drink);                  // $3.00
drink = new CaramelDecorator(drink);               // $3.75
drink = new ExtraShotDecorator(drink);             // $4.35
```

**Зачем нужен:**
Без Decorator пришлось бы создавать отдельный класс для каждой комбинации напитка и добавок (`EspressoWithWhipAndCaramel`, ...) — комбинаторный взрыв. Decorator решает это элегантно.

---

#### 4. Facade — `OrderFacade`

**Что делает:**
Скрывает сложную подсистему (инвентарь, оплата, чек) за одним простым методом.

**Как реализован:**
`OrderFacade` содержит три сервиса: `InventoryService`, `PaymentService`, `ReceiptService`. Метод `placeOrder()` последовательно вызывает их и возвращает итоговую строку-чек.

```java
OrderFacade facade = new OrderFacade();
String receipt = facade.placeOrder("customer-42", "Cappuccino", 3.75);
```

Клиент не знает, что внутри произошло: проверка остатков → списание → генерация чека.

**Зачем нужен:**
Клиентский код не должен зависеть от деталей реализации каждого сервиса. Facade снижает coupling и упрощает использование.

---

#### 5. Adapter — `CashPaymentAdapter`

**Что делает:**
Позволяет использовать старый, несовместимый класс через новый интерфейс.

**Как реализован:**
- `PaymentProcessor` — современный интерфейс с методом `pay(double amount)`
- `CashPaymentSystem` — легаси-класс, работает с центами (int), несовместимые методы
- `CashPaymentAdapter` — реализует `PaymentProcessor`, внутри делегирует вызовы к `CashPaymentSystem`, конвертируя доллары → центы
- `CardPaymentProcessor` — современная реализация того же интерфейса (для сравнения)

```java
PaymentProcessor cash = new CashPaymentAdapter(new CashPaymentSystem(), 500);
cash.pay(4.50); // конвертируется в 450 центов внутри адаптера
```

**Зачем нужен:**
Нельзя изменить легаси-код `CashPaymentSystem` (например, сторонняя библиотека). Adapter позволяет интегрировать его без изменений.

---

### Behavioral (Поведенческие)

---

#### 6. Observer — `CoffeeShopOrder` + `OrderObserver`

**Что делает:**
Реализует механизм подписки: при изменении состояния объекта все подписчики автоматически получают уведомление.

**Как реализован:**
- `OrderObserver` — интерфейс с методом `onOrderStatusChanged(orderId, status)`
- `CoffeeShopOrder` — Subject, хранит список наблюдателей, при вызове `updateStatus()` итерирует и уведомляет всех
- `CustomerNotifier` — Observer, шлёт SMS/email клиенту
- `KitchenNotifier` — Observer, обновляет кухонный дисплей

```java
CoffeeShopOrder order = new CoffeeShopOrder("ORD-001");
order.addObserver(new CustomerNotifier("Alice"));
order.addObserver(new KitchenNotifier());
order.updateStatus(OrderStatus.PREPARING); // оба Observer получают уведомление
```

**Зачем нужен:**
`CoffeeShopOrder` не должен знать про `CustomerNotifier` или `KitchenNotifier`. Observer убирает жёсткую зависимость — можно добавлять/убирать подписчиков без изменения субъекта.

---

#### 7. Strategy — `DiscountStrategy` + `PriceCalculator`

**Что делает:**
Инкапсулирует семейство алгоритмов (стратегии скидок) и делает их взаимозаменяемыми во время выполнения.

**Как реализован:**
- `DiscountStrategy` — интерфейс с методами `applyDiscount()` и `getStrategyName()`
- `RegularPriceStrategy` — без скидки
- `LoyaltyDiscountStrategy` — 10% для держателей карты лояльности
- `SeasonalDiscountStrategy` — параметризованная скидка (сезон + процент)
- `PriceCalculator` — Context, хранит текущую стратегию; стратегию можно заменить через `setDiscountStrategy()`

```java
PriceCalculator calc = new PriceCalculator(new RegularPriceStrategy());
calc.calculatePrice(4.00);  // $4.00

calc.setDiscountStrategy(new LoyaltyDiscountStrategy());
calc.calculatePrice(4.00);  // $3.60

calc.setDiscountStrategy(new SeasonalDiscountStrategy("Summer", 0.20));
calc.calculatePrice(4.00);  // $3.20
```

**Зачем нужен:**
Без Strategy в `PriceCalculator` был бы длинный `if/else` для каждого типа скидки. Добавить новую скидку = добавить новый класс, не трогая Calculator.

---

## Compile Time vs Runtime

### Compile Time (время компиляции)

На этапе компиляции Java-компилятор + Maven выполняют следующее:

**1. Проверка типов и интерфейсов**

Компилятор статически проверяет, что все контракты соблюдены:
- `CashPaymentAdapter` обязан реализовать оба метода `PaymentProcessor` — иначе ошибка компиляции
- `CustomerNotifier` и `KitchenNotifier` обязаны реализовать `OrderObserver.onOrderStatusChanged()` — иначе ошибка
- `RegularPriceStrategy`, `LoyaltyDiscountStrategy`, `SeasonalDiscountStrategy` обязаны реализовать `DiscountStrategy`

**2. Lombok annotation processing**

Lombok — annotation processor, работающий **до компиляции** (фаза `generate-sources`). Он читает аннотации `@Getter`, `@ToString`, `@Slf4j`, `@Setter` и **генерирует байткод** напрямую, без промежуточных `.java` файлов. На выходе — `.class` файлы с уже готовыми геттерами, `toString()` и полем `log`. Это чистый compile-time механизм.

**3. Структура паттернов фиксируется**

- Иерархия `Beverage → CondimentDecorator → WhipDecorator/CaramelDecorator/ExtraShotDecorator` полностью известна компилятору
- `CoffeeOrder.Builder` и его fluent-методы проверяются статически — нельзя вызвать несуществующий метод
- `MenuRegistry` — статическое поле `instance` типа `MenuRegistry volatile` создаётся как часть класса

**4. Spring Boot annotation processing (`@SpringBootApplication`)**

`@SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan`. На этапе компиляции Maven-плагин Spring Boot упаковывает всё в исполняемый fat-JAR с `META-INF/MANIFEST.MF`. Сами аннотации Spring не разворачиваются до runtime.

---

### Runtime (время выполнения)

**1. Spring Boot startup**

`SpringApplication.run()` запускает IoC-контейнер:
- Сканирует classpath, находит `@SpringBootApplication`
- Поскольку нет `spring-boot-starter-web`, контейнер поднимается в non-web режиме
- Находит бин `DesignPatternsApplication`, так как он реализует `CommandLineRunner`, и после инициализации контекста вызывает `run()`

**2. Singleton — создаётся лениво, один раз**

```
Первый вызов MenuRegistry.getInstance()
  → instance == null → входим в synchronized блок
  → new MenuRegistry() — инициализируется HashMap с меню
  → instance присваивается
Второй вызов — instance != null, сразу возвращается
```

Объект живёт всё время работы JVM. Оба вызова `getInstance()` возвращают `==` один объект.

**3. Builder — создаёт объект по шагам**

Вызов `.build()` создаёт `CoffeeOrder`, копируя состояние Builder. После `build()` Builder можно выбросить. Immutability `CoffeeOrder` обеспечивается тем, что все поля `final`, а сеттеров нет.

**4. Decorator — цепочка вызовов разворачивается рекурсивно**

```
ExtraShotDecorator.cost()
  → CaramelDecorator.cost()
    → WhipDecorator.cost()
      → Espresso.cost() → 2.50
    ← 2.50 + 0.50 = 3.00
  ← 3.00 + 0.75 = 3.75
← 3.75 + 0.60 = 4.35
```

Каждый декоратор при вызове `cost()` и `getDescription()` делегирует вниз по цепочке. Структура цепочки динамическая — создаётся в runtime.

**5. Facade — координирует три сервиса**

`placeOrder()` вызывает методы `InventoryService`, `PaymentService`, `ReceiptService` **последовательно**. Клиент видит один вызов — Facade скрывает оркестрацию. Если любой шаг вернёт `false`, цепочка прерывается и возвращается сообщение об ошибке.

**6. Adapter — конвертирует в runtime**

`CashPaymentAdapter.pay(4.50)` в runtime:
- Умножает `4.50 * 100 = 450` (конвертация в центы)
- Вызывает `legacySystem.insertCash(500)`
- Вызывает `legacySystem.validateCash(500, 450)` → `true`
- Вычисляет сдачу: `500 - 450 = 50 cents`

Клиентский код (`demonstrateAdapter`) работает с `PaymentProcessor` и не знает о центах.

**7. Observer — уведомления разлетаются при каждом `updateStatus()`**

```
order.updateStatus(PREPARING)
  → итерация по observers (ArrayList с 2 элементами)
    → CustomerNotifier.onOrderStatusChanged("ORD-001", PREPARING)  [лог SMS]
    → KitchenNotifier.onOrderStatusChanged("ORD-001", PREPARING)   [лог Kitchen Display]
```

Список наблюдателей — `ArrayList`, наполняется через `addObserver()` в runtime. Порядок уведомлений определяется порядком добавления.

**8. Strategy — алгоритм подменяется без перезапуска**

```
calc.setDiscountStrategy(new LoyaltyDiscountStrategy())
calc.calculatePrice(4.00)
  → discountStrategy.applyDiscount(4.00)   // вызов через интерфейс → dynamic dispatch
  → 4.00 * 0.90 = 3.60
```

JVM использует **виртуальную диспетчеризацию** (vtable lookup) для вызова правильной реализации `applyDiscount()`. Конкретный класс определяется в runtime по типу объекта в поле `discountStrategy`.

---

## Итоговая таблица

| Паттерн   | Категория   | Ключевой механизм | Compile Time | Runtime |
|-----------|-------------|-------------------|-------------|---------|
| Builder   | Creational  | Fluent Builder    | Проверка типов, Lombok `@Getter/@ToString` | Поэтапное создание объекта, копирование в immutable `CoffeeOrder` |
| Singleton | Creational  | `volatile` + DCL  | Статическое поле `instance` | Ленивая инициализация, `synchronized` блок при первом вызове |
| Decorator | Structural  | Наследование + делегирование | Проверка иерархии `Beverage` | Рекурсивный вызов `cost()` вглубь цепочки |
| Facade    | Structural  | Composition       | Компилятор проверяет методы трёх сервисов | Оркестрация 3 сервисов в одном методе |
| Adapter   | Structural  | Реализация интерфейса | `CashPaymentAdapter implements PaymentProcessor` — компилятор требует все методы | Конвертация double→int, делегирование к legacy |
| Observer  | Behavioral  | Список + интерфейс | Проверка `OrderObserver` у всех наблюдателей | Итерация по `ArrayList`, вызов каждого `onOrderStatusChanged` |
| Strategy  | Behavioral  | Интерфейс + dynamic dispatch | Компилятор проверяет `DiscountStrategy` у всех стратегий | JVM vtable lookup, вызов нужной `applyDiscount()` |
