package ee.taltech.iti0200.warehouses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CompanyTest {
    Company company;
    Product book;
    Product paper;
    Product plastic;
    Warehouse firstWarehouse;
    Warehouse secondWarehouse;
    Worker firstWarehouseWorker;
    Worker secondWarehouseWorker;
    Worker officeWorker;
    private static final int SIXBOOKS = 6;
    private static final int ELEVENPAPER = 11;
    private static final double HALFAKG = 0.5;
    private static final int OFFICEWORKERAGE = 25;
    private static final int WORKERAGE1 = 18;
    private static final int WORKERAGE2 = 45;
    private static final long PAPERAMOUNT = 6L;
    private static final long PAPERAMOUNT2 = 1L;
    private static final long PLASTICAMOUNT1 = 2L;
    private static final long PLASTICAMOUNT2 = 12L;
    private static final long BOOKAMOUNT = 5L;
    private static final long BOOKAMOUNT2 = 3L;


    @BeforeEach
    void setUp() {
        company = new Company("Company1");

        book = new Product("book1", BigDecimal.valueOf(1), BigDecimal.valueOf(3),
                BigDecimal.valueOf(SIXBOOKS));
        company.addProduct(book);
        paper = new Product("paper1", BigDecimal.valueOf(1), BigDecimal.valueOf(HALFAKG),
                BigDecimal.valueOf(ELEVENPAPER));
        company.addProduct(paper);
        plastic = new Product("plastic1", BigDecimal.valueOf(2), BigDecimal.valueOf(4),
                BigDecimal.valueOf(2));
        company.addProduct(plastic);

        officeWorker = new Worker("One", "Office", "000", OFFICEWORKERAGE);
        company.addOfficeWorker(officeWorker);

        firstWarehouse = new Warehouse("Estonia 1");
        firstWarehouseWorker = new Worker("First", "Warehouse", "001", WORKERAGE1);
        firstWarehouse.addWorker(firstWarehouseWorker);
        company.addWarehouse(firstWarehouse);

        secondWarehouse = new Warehouse("Estonia 2");
        secondWarehouseWorker = new Worker("Second", "Warehouse", "002", WORKERAGE2);
        secondWarehouse.addWorker(secondWarehouseWorker);
        company.addWarehouse(secondWarehouse);

        Map<Product, Long> firstWarehouseInventory = new HashMap<>();
        Map<Product, Long> secondWarehouseInvenvtory = new HashMap<>();
        firstWarehouseInventory.put(book, BOOKAMOUNT2);
        firstWarehouseInventory.put(plastic, PLASTICAMOUNT1);
        firstWarehouseInventory.put(paper, PAPERAMOUNT2);
        for (Map.Entry<Product, Long> entry : firstWarehouseInventory.entrySet()) {
            firstWarehouse.addProduct(entry);
        }
        secondWarehouseInvenvtory.put(book, PLASTICAMOUNT1);
        secondWarehouseInvenvtory.put(paper, BOOKAMOUNT);
        for (Map.Entry<Product, Long> entry : secondWarehouseInvenvtory.entrySet()) {
            secondWarehouse.addProduct(entry);
        }
    }

    @Test
    void getName() {
        assertEquals("Company1", company.getName());
    }

    @Test
    void getCheapestProductForCustomer() {
        assertEquals(Optional.of(plastic), company.getCheapestProductForCustomer());
    }

    @Test
    void getMostExpensiveProductForCustomer() {
        assertEquals(Optional.of(paper), company.getMostExpensiveProductForCustomer());
    }

    @Test
    void reportInventory() {
        Map<Product, Long> result = new HashMap<>();
        result.put(book, BOOKAMOUNT);
        result.put(plastic, PLASTICAMOUNT1);
        result.put(paper, PAPERAMOUNT);
        assertEquals(result, company.reportInventory());
    }

    @Test
    void getAvailability() {
        ArrayList<Warehouse> availableAt = new ArrayList<>();
        availableAt.add(firstWarehouse);
        availableAt.add(secondWarehouse);
        assertEquals(availableAt, company.getAvailability(paper));
    }

    @Test
    void restockProduct() {
        Map<Product, Long> productRestock = new HashMap<>();
        productRestock.put(plastic, BOOKAMOUNT);
        for (Map.Entry<Product, Long> entry : productRestock.entrySet()) {
            firstWarehouse.addProduct(entry);
            secondWarehouse.addProduct(entry);
        }
        Map<Product, Long> result = new HashMap<>();
        result.put(book, BOOKAMOUNT);
        result.put(plastic, PLASTICAMOUNT2);
        result.put(paper, PAPERAMOUNT);
        assertEquals(result, company.reportInventory());
    }

    @Test
    void getCompanyWorkers() {
        Set<Worker> result = new HashSet<>();
        result.add(officeWorker);
        result.add(firstWarehouseWorker);
        result.add(secondWarehouseWorker);
        assertEquals(result, company.getCompanyWorkers());
    }

    @Test
    void addOfficeWorker() {
        Set<Worker> result = new HashSet<>();
        result.add(officeWorker);
        result.add(firstWarehouseWorker);
        result.add(secondWarehouseWorker);
        Worker newOfficeWorker = new Worker("New", "Worker", "004", WORKERAGE1);
        result.add(newOfficeWorker);
        company.addOfficeWorker(newOfficeWorker);
        assertEquals(result, company.getCompanyWorkers());
    }

    @Test
    void getWarehouses() {
        Set<Warehouse> result = new HashSet<>();
        result.add(firstWarehouse);
        result.add(secondWarehouse);
        assertEquals(result, company.getWarehouses());
    }

    @Test
    void addWarehouse() {
        Set<Warehouse> result = new HashSet<>();
        result.add(firstWarehouse);
        result.add(secondWarehouse);
        Warehouse thirdNewWarehouse = new Warehouse("Estonia 3");
        result.add(thirdNewWarehouse);
        company.addWarehouse(thirdNewWarehouse);
        assertEquals(result, company.getWarehouses());
    }

    @Test
    void getCompanyGoodsValue() {
        BigDecimal result = BigDecimal.ZERO;
        result = result.add(firstWarehouse.getInventoryValue());
        result = result.add(secondWarehouse.getInventoryValue());
        assertEquals(result, company.getCompanyGoodsValue());
    }
}
