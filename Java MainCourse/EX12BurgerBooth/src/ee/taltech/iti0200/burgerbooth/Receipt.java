package ee.taltech.iti0200.burgerbooth;

import ee.taltech.iti0200.burgerbooth.burgers.BurgerType;

import java.time.LocalDateTime;
import java.util.Map;

public class Receipt {

    private final double orderCost;
    private final Map<BurgerType, Integer> order;
    private final LocalDateTime time;
    private final Customer customer;
    private final Booth booth;

    public Receipt(double orderCost, Map<BurgerType, Integer> order, LocalDateTime time, Customer customer,
                   Booth booth) {
        this.orderCost = orderCost;
        this.order = order;
        this.time = time;
        this.customer = customer;
        this.booth = booth;
    }

    public double getOrderCost() {
        return orderCost;
    }

    public Map<BurgerType, Integer> getOrder() {
        return order;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getTimeAsAtring() {
        return time.toString().replace("T", " ");
    }

    @Override
    public String toString() {
        return "Receipt{"
                + "orderCost=" + orderCost
                + "eur, order=" + order
                + ", time=" + getTimeAsAtring()
                + ", customer=" + customer.getName()
                + ", booth=" + booth.name
                + '}';
    }
}
