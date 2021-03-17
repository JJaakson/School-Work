package ee.taltech.iti0200.steakhouse.strategy;

import ee.taltech.iti0200.steakhouse.steak.SteakType;

public class CookMedium implements CookStrategy {

    private static final double PERCENTAGE = 0.01;

    @Override
    public double calculateWeightLoss(String cookName, SteakType steaktype) {
        double multiplier = 0;
        for (int i = 0; i < cookName.length(); i++) {
            multiplier += PERCENTAGE;
        }
        return steaktype.getWeigth() * multiplier;
    }
}
