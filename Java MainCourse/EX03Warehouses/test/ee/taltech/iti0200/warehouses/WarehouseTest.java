package ee.taltech.iti0200.warehouses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class WarehouseTest {
    Warehouse warehouse;
    Product book;
    Product paper;
    Product plastic;
    Worker worker;
    Map<Product, Long> inventory;
    private static final long PAPERAMOUNT = 33L;
    private static final int SIXBOOKS = 6;
    private static final double HALFAKG = 0.5;
    private static final int ELEVENPAPER = 11;
    private static final int WORKERAGE = 18;
    private static final int NEWWORKERAGE = 17;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse("Estonia 1");

        book = new Product("book1", BigDecimal.valueOf(1), BigDecimal.valueOf(3),
                BigDecimal.valueOf(SIXBOOKS));
        paper = new Product("paper1", BigDecimal.valueOf(1), BigDecimal.valueOf(HALFAKG),
                BigDecimal.valueOf(ELEVENPAPER));
        plastic = new Product("plastic1", BigDecimal.valueOf(2), BigDecimal.valueOf(4),
                BigDecimal.valueOf(2));

        worker = new Worker("First", "Warehouse", "003", WORKERAGE);
        warehouse.addWorker(worker);

        inventory = new HashMap<>();
        inventory.put(book, 2L);
        inventory.put(paper, PAPERAMOUNT);
        for (Map.Entry<Product, Long> entry : inventory.entrySet()) {
            warehouse.addProduct(entry);
        }
    }

    @Test
    void getAmount() {
        assertEquals(PAPERAMOUNT, warehouse.getAmount(paper));
    }

    @Test
    void getAddress() {
        assertEquals("Estonia 1", warehouse.getAddress());
    }

    @Test
    void addProduct() {
        Map<Product, Long> newProducts = new HashMap<>();
        newProducts.put(plastic, 5L);
        for (Map.Entry<Product, Long> entry : newProducts.entrySet()) {
            warehouse.addProduct(entry);
        }
        assertEquals(5L, warehouse.getAmount(plastic));
    }

    @Test
    void hasProduct() {
        assertFalse(warehouse.hasProduct(plastic));
    }

    @Test
    void hasEnoughProduct() {
        assertTrue(warehouse.hasEnoughProduct(paper, 3L));
    }

    @Test
    void getWorkers() {
        Set<Worker> workers = new HashSet<>();
        workers.add(worker);
        assertEquals(workers, warehouse.getWorkers());
    }

    @Test
    void addWorker() {
        Set<Worker> workers = new HashSet<>();
        workers.add(worker);
        Worker newWorker = new Worker("First*", "Warehouse", "007", NEWWORKERAGE);
        workers.add(newWorker);
        warehouse.addWorker(newWorker);
        assertEquals(workers, warehouse.getWorkers());
    }

    @Test
    void getInventoryValue() {
        BigDecimal inventoryValue = BigDecimal.ZERO;
        for (Map.Entry<Product, Long> product : inventory.entrySet()) {
            inventoryValue = inventoryValue.add(product.getKey().getNetPrice().
                    multiply(BigDecimal.valueOf(product.getValue())));
        }
        assertEquals(inventoryValue, warehouse.getInventoryValue());
    }
}
