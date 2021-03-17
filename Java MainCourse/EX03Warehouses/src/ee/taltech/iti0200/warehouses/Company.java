package ee.taltech.iti0200.warehouses;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Company {
    private String name;
    private Set<Warehouse> warehouses = new HashSet<>();
    private Set<Worker> officeWorkers = new HashSet<>();
    private Set<Product> products = new HashSet<>();

    public Company(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Gets the cheapest product for customer.
     *
     * @return the cheapest product
     */
    public Optional<Product> getCheapestProductForCustomer() {
        if (products.isEmpty()) {
            return Optional.empty();
        } else {
            return products.stream().min(Comparator.comparing(Product::getGrossPrice));
        }
    }

    /**
     * Gets the most expensive product for customer.
     *
     * @return the most expensive product
     */
    public Optional<Product> getMostExpensiveProductForCustomer() {
        if (products.isEmpty()) {
            return Optional.empty();
        } else {
            return products.stream().max(Comparator.comparing(Product::getGrossPrice));
        }
    }

    /**
     * Reports the current state of products in the warehouses.
     *
     * @return the current state of products in the warehouses
     */
    public Map<Product, Long> reportInventory() {
        Map<Product, Long> currentState = new HashMap<>();
        for (Warehouse warehouse : warehouses) {
            for (Map.Entry<Product, Long> entry : warehouse.inventory.entrySet()) {
                if (currentState.containsKey(entry.getKey())) {
                    currentState.replace(entry.getKey(), currentState.get(entry.getKey()) + entry.getValue());
                } else {
                    currentState.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return currentState;
    }

    /**
     * Finds all warehouses where the product is available.
     *
     * @param product the product to check
     * @return list of warehouses where the product is availalble
     */
    public List<Warehouse> getAvailability(Product product) {
        ArrayList<Warehouse> availableAt = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            if (warehouse.inventory.containsKey(product) && warehouse.inventory.get(product) > 0) {
                availableAt.add(warehouse);
            }
        }
        return availableAt;
    }

    /**
     * Adds specified amount of specified product to all warehouses.
     *
     * @param product the product to add
     * @param amount the amount of product to add
     */
    public void restockProduct(Product product, Long amount) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.inventory.containsKey(product)) {
                warehouse.inventory.replace(product, warehouse.inventory.get(product) + amount);
            } else {
                warehouse.inventory.put(product, amount);
            }
        }
    }

     /**
     * Add a new product to companies products list and all the warehouses of that company.
     *
     * @param product the product to add
     */
    public void addProduct(Product product) {
        products.add(product);
    }

    /**
     * Gets all company workers.
     *
     * @return all company workers
     */
    public Set<Worker> getCompanyWorkers() {
        Set<Worker> allWorkers = new HashSet<>(officeWorkers);
        for (Warehouse warehouse : warehouses) {
            allWorkers.addAll(warehouse.getWorkers());
        }
        return allWorkers;
    }

    /**
     * Add an office worker.
     *
     * @param worker the worker to add
     */
    public void addOfficeWorker(Worker worker) {
        officeWorkers.add(worker);
    }

    public Set<Warehouse> getWarehouses() {
        return warehouses;
    }

    /**
     * Add a warehouse.
     *
     * @param wareHouse the warehouse to add
     */
    public void addWarehouse(Warehouse wareHouse) {
        warehouses.add(wareHouse);
    }

    /**
     * Gets total company goods value.
     *
     * @return total company goods value
     */
    public BigDecimal getCompanyGoodsValue() {
        BigDecimal value = BigDecimal.valueOf(0);
        for (Warehouse warehouse : warehouses) {
            value = value.add(warehouse.getInventoryValue());
        }
        return value;
    }
}
