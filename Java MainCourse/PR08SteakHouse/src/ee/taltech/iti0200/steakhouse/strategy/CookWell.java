package ee.taltech.iti0200.steakhouse.strategy;

import ee.taltech.iti0200.steakhouse.steak.SteakType;

public class CookWell implements CookStrategy {

    private static final double PERCENTAGE = 0.2;

    @Override
    public double calculateWeightLoss(String cookName, SteakType steaktype) {
        return steaktype.getWeigth() * PERCENTAGE;
    }
}
