package ee.taltech.iti0200.burgerbooth;

import ee.taltech.iti0200.burgerbooth.burgers.BurgerType;
import ee.taltech.iti0200.burgerbooth.orders.Order;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Booth {

    private static final int DATETIME_HH_MM = 16;

    private Map<BurgerType, Integer> inventory;
    private List<Receipt> receipts = new ArrayList<>();
    public String name;
    private double profit = 0;

    public Booth(Map<BurgerType, Integer> inventory, String name) {
        this.inventory = inventory;
        this.name = name;
        System.out.println("Welcome to " + name + "!");
        showMakeableBurgers();
    }

    public Receipt fullFillOrder(Order order) {
        if (order.getOrder() == null) {
            return null;
        }
        Map<BurgerType, Integer> burgers = order.getOrder();
        long makingTime = order.makingTime();
        for (Map.Entry<BurgerType, Integer> entry : burgers.entrySet()) {
            inventory.replace(entry.getKey(), inventory.get(entry.getKey()) - entry.getValue());
            makingTime += entry.getKey().getCookingTime() * entry.getValue();
        }
        makingTime = makingTime / 3;

        LocalDateTime orderTime = order.timeInitiated().plusMinutes(makingTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String helper = orderTime.toString().substring(0, DATETIME_HH_MM).replace("T", " ");
        LocalDateTime doneBy = LocalDateTime.parse(helper, formatter);

        Receipt receipt = new Receipt(order.getOrderCost(), burgers, doneBy, order.getCustomer(), this);
        receipts.add(receipt);
        order.getCustomer().addReceipt(receipt);
        System.out.println("Your receipt: " + receipt.toString());
        return receipt;
    }

    public void showMakeableBurgers() {
        int cheeseBurgers = inventory.get(BurgerType.CHEESE);
        int vegetarianBurgers = inventory.get(BurgerType.VEGETARIAN);
        int chilliBurgers = inventory.get(BurgerType.CHILLI);
        System.out.println("Here are the burgers, we can make : " + cheeseBurgers
                + " - cheeseburgers(" + BurgerType.CHEESE.getCost() + " eur), " + vegetarianBurgers
                + " - vegetarianBurgers(" + BurgerType.VEGETARIAN.getCost() + " eur), "
                + chilliBurgers + " - chilliBurgers(" + BurgerType.CHILLI.getCost() + " eur)");
    }

    public void restockInventory(Map<BurgerType, Integer> shipment) {
        for (Map.Entry<BurgerType, Integer> entry : shipment.entrySet()) {
            if (inventory.containsKey(entry.getKey())) {
                inventory.replace(entry.getKey(), inventory.get(entry.getKey()) + entry.getValue());
            }
        }
    }

    public boolean ableToFullFill(Map<BurgerType, Integer> order) {
        for (Map.Entry<BurgerType, Integer> entry : order.entrySet()) {
            if (!inventory.containsKey(entry.getKey())) {
                return false;
            } else {
                if (inventory.get(entry.getKey()) < entry.getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public Map<BurgerType, Integer> getInventory() {
        return inventory;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }
}
