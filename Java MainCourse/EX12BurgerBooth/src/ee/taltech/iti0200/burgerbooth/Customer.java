package ee.taltech.iti0200.burgerbooth;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private double money;
    private String name;
    private List<Receipt> receipts = new ArrayList<>();

    public Customer(double money, String name) {
        this.money = money;
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public String getName() {
        return name;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void addReceipt(Receipt receipt) {
        receipts.add(receipt);
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }
}
