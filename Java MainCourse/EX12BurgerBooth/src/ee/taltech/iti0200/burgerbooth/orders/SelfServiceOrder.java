package ee.taltech.iti0200.burgerbooth.orders;

import ee.taltech.iti0200.burgerbooth.Booth;
import ee.taltech.iti0200.burgerbooth.Customer;

public class SelfServiceOrder extends Order {

    public static final int LINE_WAIT_TIME = 5;
    public static final int BONUS_COST_PER_BURGER = 0;

    public SelfServiceOrder(Customer customer, Booth booth) {
        super(customer, booth);
    }

    @Override
    public int makingTime() {
        return LINE_WAIT_TIME;
    }

    @Override
    public double bonusBox() {
        return BONUS_COST_PER_BURGER;
    }
}
