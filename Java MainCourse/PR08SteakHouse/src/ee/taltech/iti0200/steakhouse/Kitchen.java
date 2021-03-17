package ee.taltech.iti0200.steakhouse;

import ee.taltech.iti0200.steakhouse.steak.Steak;
import ee.taltech.iti0200.steakhouse.steak.SteakType;
import ee.taltech.iti0200.steakhouse.strategy.CookStrategy;

import java.util.ArrayDeque;

public class Kitchen {

    private String chefCook;
    private ArrayDeque<String> allCooks = new ArrayDeque<>();

    public Kitchen(String chefCook) {
        this.chefCook = chefCook;
    }

    public Steak makeOrder(Order order) {
        SteakType steakType = order.getSteakType();
        CookStrategy strategy = order.getCookStrategy();
        if (steakType == SteakType.FILET_MIGNON) {
            return strategy.makeSteak(chefCook, steakType);
        }
        String chef = allCooks.getFirst();
        allCooks.removeFirst();
        allCooks.addLast(chef);
        return strategy.makeSteak(chef, steakType);
    }

    public void addCook(String cookName) {
        if (!allCooks.contains(cookName)) {
            allCooks.add(cookName);
        }
    }

    public ArrayDeque<String> getAllCooks() {
        return allCooks;
    }
}
