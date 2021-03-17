package ee.taltech.iti0200.tk1.shop;

public class Product {


    private String name;
    private int price;

    public Product(int price) {
        this.price = price;
    }

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        String result = "";
        if (!this.name.isEmpty()) {
            result = this.name + " " + "(" + this.price + ")";
        } else {
            result = "(" + this.price + ")";
        }
        return result;
    }
}
