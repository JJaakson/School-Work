package ee.taltech.iti0200.tk1.shop;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.List;
import java.util.LinkedList;

public class Shop {

    private Set<Product> products = new HashSet<>();

    public boolean addProduct(Product product) {
        boolean isThereAnything = false;
        for (Product prod : products) {
            if (product.getName().equals(prod.getName()) || product.getName().equals(prod.getName())
                    && product.getPrice() == prod.getPrice() && product.getPrice() > 0) {
                isThereAnything = true;
                break;
            }
        }
        if (!isThereAnything && product.getPrice() > 0) {
            products.add(product);
            return true;
        }
        return false;
    }

    public Optional<Product> sellProduct(String name, int maxPrice) {
        Set<Product> correctProducts = new HashSet<>();
        for (Product product : products) {
            if (product.getName().equals(name) && product.getPrice() < maxPrice) {
                correctProducts.add(product);
            }
        }
        return correctProducts.stream().max(Comparator.comparing(Product::getPrice));
    }

    public List<Product> getProducts() {
        return new LinkedList<>(products);
    }

}
