package ee.taltech.iti0200.bonuscards.cards;
import ee.taltech.iti0200.bonuscards.exceptions.AlreadyExistingCardTypeException;
import ee.taltech.iti0200.bonuscards.exceptions.BonusException;

import java.math.BigDecimal;

public abstract class BonusCard {

    protected Person person;
    protected Store store;
    protected BigDecimal bonus;
    protected CardType type;

    public enum CardType { COOP, RIMI }

    /**
     * Creates a bonus card.
     *
     * @param cardType the card type to create
     * @param store the store to add the card to
     * @param person the person to add the card to
     * @return the bonus card that was created
     */
    public static BonusCard createCard(CardType cardType, Store store, Person person) {
        if (person.getBonusCardByType(cardType).isEmpty()) {
            if (cardType.equals(CardType.COOP)) {
                CoopCard card = new CoopCard(store, person);
                person.addBonusCard(card);
                store.addCustomer(person);
                return card;
            } else if (cardType.equals(CardType.RIMI)) {
                RimiCard card = new RimiCard(store, person);
                person.addBonusCard(card);
                store.addCustomer(person);
                return card;
            }
        } else {
            throw new AlreadyExistingCardTypeException("Card already exists.");
        }
        return null;
    }

    public abstract BigDecimal collectBonus(double paymentAmount);

    /**
     * Uses the specified amount of bonus.
     *
     * @param value the bonus value to use
     * @return remaining bonus
     */
    public BigDecimal useBonus(BigDecimal value) {
        if (this.bonus.compareTo(value) >= 0) {
            this.bonus = this.bonus.subtract(value);
            return this.bonus;
        } else {
            throw new BonusException("Not enough bonuspoints");
        }
    }

    public void setBonusBalance(BigDecimal bonusBalance) {
        this.bonus = bonusBalance;
    }

    public CardType getType() {
        return this.type;
    }

    public Store getStore() {
        return this.store;
    }

    public BigDecimal getBonusBalance() {
        return this.bonus;
    }

    public Person getPerson() {
        return this.person;
    }
}
