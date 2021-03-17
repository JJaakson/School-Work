package ee.taltech.iti0200.steakhouse.strategy;

import ee.taltech.iti0200.steakhouse.steak.Steak;
import ee.taltech.iti0200.steakhouse.steak.SteakType;

public interface CookStrategy {

    double calculateWeightLoss(String cookName, SteakType steaktype);

    default Steak makeSteak(String cookName, SteakType steakType) {
        return new Steak(cookName, steakType.getWeigth() - this.calculateWeightLoss(cookName, steakType));
    }
}
