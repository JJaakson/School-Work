package ee.taltech.iti0200.burgerbooth;

import ee.taltech.iti0200.burgerbooth.burgers.BurgerType;
import ee.taltech.iti0200.burgerbooth.orders.CashierOrder;
import ee.taltech.iti0200.burgerbooth.orders.DriveThroughOrder;
import ee.taltech.iti0200.burgerbooth.orders.SelfServiceOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoothTest {

    private static final int CHEESE_BURGERS = 25;
    private static final int VEGETARIAN_BURGERS = 3;
    private static final int CHILLI_BURGERS = 10;
    private static final int RICH_CUSTOMER = 100;
    private static final int MIDDLE_CUSTOMER = 50;
    private static final int POOR_CUSTOMER = 0;
    private static final int FIVE_BURGERS = 5;
    private static final int TWO_BURGERS = 2;
    private static final double ORDER_COST = 10.0;
    private static final double MONEY_LEFT = 90.0;

    private Map<BurgerType, Integer> inventory = new HashMap<>();
    private Booth booth;
    private Customer customer;
    private Customer customer1;
    private Customer customer2;
    private CashierOrder order;
    private DriveThroughOrder order1;
    private SelfServiceOrder order2;

    @BeforeEach
    void setUp() {
        inventory.put(BurgerType.CHEESE, CHEESE_BURGERS);
        inventory.put(BurgerType.VEGETARIAN, VEGETARIAN_BURGERS);
        inventory.put(BurgerType.CHILLI, CHILLI_BURGERS);
        booth = new Booth(inventory, "Diner");
        customer = new Customer(RICH_CUSTOMER, "Peep");
        customer1 = new Customer(POOR_CUSTOMER, "Paap");
        customer2 = new Customer(MIDDLE_CUSTOMER, "James");
        order = new CashierOrder(customer, booth);
        order1 = new DriveThroughOrder(customer1, booth);
        order2 = new SelfServiceOrder(customer2, booth);
    }

    @Test
    void orderNoMoney() {
        Map<BurgerType, Integer> burgerOrder = new HashMap<>();
        burgerOrder.put(BurgerType.CHEESE, FIVE_BURGERS);
        order1.initiateOrder(burgerOrder);
        assertEquals(null, booth.fullFillOrder(order1));
    }

    @Test
    void orderNotEnoughBurgers() {
        Map<BurgerType, Integer> burgerOrder = new HashMap<>();
        burgerOrder.put(BurgerType.VEGETARIAN, FIVE_BURGERS);
        order.initiateOrder(burgerOrder);
        assertEquals(null, booth.fullFillOrder(order));
    }

    @Test
    void orderSuccessful() {
        Map<BurgerType, Integer> burgerOrder = new HashMap<>();
        burgerOrder.put(BurgerType.CHILLI, TWO_BURGERS);
        order.initiateOrder(burgerOrder);
        assertEquals(ORDER_COST, booth.fullFillOrder(order).getOrderCost());
        assertEquals(MONEY_LEFT, customer.getMoney());
    }

    @Test
    void orderSelfService() {
        Map<BurgerType, Integer> burgerOrder = new HashMap<>();
        burgerOrder.put(BurgerType.VEGETARIAN, TWO_BURGERS);
        order2.initiateOrder(burgerOrder);
        booth.fullFillOrder(order2);
        assertEquals(1, booth.getReceipts().size());
    }
}
