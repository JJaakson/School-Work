package ee.taltech.iti0200.bonuscards.cards;

import java.math.BigDecimal;

public final class RimiCard extends BonusCard {

    private static final double TWOPERCENT = 0.02;

    RimiCard(Store store, Person person) {
        this.store = store;
        this.person = person;
        this.bonus = BigDecimal.valueOf(0);
        this.type = CardType.RIMI;
    }

    /**
     * Collects bonus on the specified payment amount.
     *
     * @param paymentAmount the payment amount
     * @return collected bonus
     */
    @Override
    public BigDecimal collectBonus(double paymentAmount) {
        if (paymentAmount >= 10.0) {
            BigDecimal bonusPoints = BigDecimal.valueOf(paymentAmount * TWOPERCENT);
            bonus = bonus.add(bonusPoints);
            return bonusPoints;
        }
        return BigDecimal.valueOf(0);
    }
}
