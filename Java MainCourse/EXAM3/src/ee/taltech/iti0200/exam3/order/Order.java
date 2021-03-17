package ee.taltech.iti0200.exam3.order;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private int number;
    private List<Product> products = new ArrayList<>();
    private boolean cancelled = false;

    public Order(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProducts(Product product) {
        if (!products.contains(product)) {
            products.add(product);
        }
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
