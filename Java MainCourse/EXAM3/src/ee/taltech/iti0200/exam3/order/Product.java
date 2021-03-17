package ee.taltech.iti0200.exam3.order;

public class Product {

    private int price;
    private String name;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
