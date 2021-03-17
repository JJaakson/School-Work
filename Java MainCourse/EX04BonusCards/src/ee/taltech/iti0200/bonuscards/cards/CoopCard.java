package ee.taltech.iti0200.bonuscards.cards;

import java.math.BigDecimal;

public final class CoopCard extends BonusCard {

    private static final double FIVEPERCENT = 0.05;

    CoopCard(Store store, Person person) {
        this.store = store;
        this.person = person;
        this.bonus = BigDecimal.valueOf(10.0);
        this.type = CardType.COOP;
    }

    /**
     * Collects bonus on the specified payment amount.
     *
     * @param paymentAmount the payment amount
     * @return collected bonus
     */
    @Override
    public BigDecimal collectBonus(double paymentAmount) {
        BigDecimal bonuspoints = BigDecimal.valueOf(paymentAmount * FIVEPERCENT);
        bonus = bonus.add(bonuspoints);
        return bonuspoints;
    }
}
