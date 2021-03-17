package ee.taltech.iti0200.exam3.order;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Shop {

    private List<Product> listOfProducts = new ArrayList<>();
    private List<Order> listOfOrders = new ArrayList<>();

    public boolean addProduct(Product product) {
        if (!listOfProducts.contains(product)) {
            listOfProducts.add(product);
            return true;
        }
        return false;
    }

    public int createNewOrder() {
        int size = listOfOrders.size() + 1;
        listOfOrders.add(new Order(size));
        return size;
    }

    public boolean addProductToOrder(int orderNumber, String itemName) {
        Order correctOrder = null;
        List<Product> listOfCorrectProducts = new ArrayList<>();
        for (Order order : listOfOrders) {
            if (order.getNumber() == orderNumber) {
                correctOrder = order;
            }
        }
        for (Product product : listOfProducts) {
            if (product.getName().equals(itemName) && correctOrder != null
                    && !correctOrder.getProducts().contains(product)) {
                listOfCorrectProducts.add(product);
            }
        }
        listOfCorrectProducts.sort(Comparator.comparing(Product::getPrice));
        if (correctOrder != null && !correctOrder.isCancelled() && listOfCorrectProducts.size() > 0) {
            Product correctProduct = listOfCorrectProducts.get(0);
            listOfProducts.remove(correctProduct);
            correctOrder.addProducts(correctProduct);
            return true;
        }
        return false;
    }

    public int getOrderSum(int orderNumber) {
        Order correctOrder = null;
        for (Order order : listOfOrders) {
            if (order.getNumber() == orderNumber) {
                correctOrder = order;
            }
        }
        if (correctOrder != null) {
            int sum = 0;
            for (Product product : correctOrder.getProducts()) {
                sum += product.getPrice();
            }
            return sum;
        }
        return -1;
    }

    public boolean cancelOrder(int orderNumber) {
        Order correctOrder = null;
        for (Order order : listOfOrders) {
            if (order.getNumber() == orderNumber) {
                correctOrder = order;
            }
        }
        if (correctOrder != null && !correctOrder.isCancelled()) {
            for (Product product : correctOrder.getProducts()) {
                listOfProducts.add(product);
            }
            correctOrder.setProducts(new ArrayList<>());
            correctOrder.setCancelled(true);
            return true;
        }
        return false;
    }

    public List<Product> getAvailableProducts() {
        return listOfProducts;
    }
}
