package ee.taltech.iti0200.burgerbooth.orders;

import ee.taltech.iti0200.burgerbooth.Booth;
import ee.taltech.iti0200.burgerbooth.Customer;
import ee.taltech.iti0200.burgerbooth.burgers.BurgerType;

import java.time.LocalDateTime;
import java.util.Map;

public abstract class Order {

    private double orderCost;
    private Customer customer;
    private Booth booth;
    private Map<BurgerType, Integer> order;
    private LocalDateTime timeInitiated;

    public Order(Customer customer, Booth booth) {
        this.customer = customer;
        this.booth = booth;
    }

    public void initiateOrder(Map<BurgerType, Integer> order) {
        this.orderCost = 0;
        for (Map.Entry<BurgerType, Integer> entry : order.entrySet()) {
            orderCost += entry.getKey().getCost() * entry.getValue() + this.bonusBox();
        }
        timeInitiated = LocalDateTime.now();
        if (customer.getMoney() < orderCost) {
            System.out.println("You don't have enough money.");
        } else if (!booth.ableToFullFill(order)) {
            System.out.println("We dont have enough burgers!");
        } else {
            this.order = order;
            booth.setProfit(orderCost);
            customer.setMoney(customer.getMoney() - orderCost);
            System.out.println("Coming right up!");
        }
    }

    public Map<BurgerType, Integer> getOrder() {
        return order;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getOrderCost() {
        return orderCost;
    }

    public abstract int makingTime();

    public abstract double bonusBox();

    public LocalDateTime timeInitiated() {
        return timeInitiated;
    }
}
