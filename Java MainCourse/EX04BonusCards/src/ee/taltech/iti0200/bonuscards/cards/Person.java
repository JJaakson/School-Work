package ee.taltech.iti0200.bonuscards.cards;

import ee.taltech.iti0200.bonuscards.exceptions.PersonException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Person {

    private final String firstName;
    private final String lastName;
    private int age;
    private final Gender gender;
    private Set<BonusCard> cards = new HashSet<>();

    public enum Gender {
        MALE, FEMALE
    }

    public Person(String firstName, String lastName, int age, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        if (age > 0) {
            this.age = age;
        } else {
            throw new PersonException("Invalid age!");
        }
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getAge() {
        return this.age;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Set<BonusCard> getBonusCards() {
        return this.cards;
    }

    /**
     * Gets bonus card by the specified type.
     *
     * @param cardType the bonus card type
     * @return bonus card with the specified type
     */
    public Optional<BonusCard> getBonusCardByType(BonusCard.CardType cardType) {
        for (BonusCard card : cards) {
            if (cardType.equals(card.getType())) {
                return Optional.of(card);
            }
        }
        return Optional.empty();
    }

    public void addBonusCard(BonusCard bonusCard) {
        cards.add(bonusCard);
    }

    public void removeBonusCard(BonusCard bonusCard) {
        cards.remove(bonusCard);
    }
}
