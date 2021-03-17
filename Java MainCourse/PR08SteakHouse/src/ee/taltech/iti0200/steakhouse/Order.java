package ee.taltech.iti0200.steakhouse;

import ee.taltech.iti0200.steakhouse.steak.SteakType;
import ee.taltech.iti0200.steakhouse.strategy.CookStrategy;

public class Order {

    private CookStrategy cookStrategy;
    private SteakType steakType;

    public Order(SteakType steakType, CookStrategy cookStrategy) {
        this.steakType = steakType;
        this.cookStrategy = cookStrategy;
    }

    public CookStrategy getCookStrategy() {
        return cookStrategy;
    }

    public void setCookStrategy(CookStrategy cookStrategy) {
        this.cookStrategy = cookStrategy;
    }

    public SteakType getSteakType() {
        return steakType;
    }

    public void setSteakType(SteakType steakType) {
        this.steakType = steakType;
    }
}
