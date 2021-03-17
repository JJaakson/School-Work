package ee.taltech.iti0200.bonuscards.cards;
import ee.taltech.iti0200.bonuscards.exceptions.PersonException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PersonTest {

    private Person person;
    private static final int AGE = 18;
    private static final int NEGATIVEAGE = -1;
    private BonusCard rimiCard;
    private Store store;
    private Set<BonusCard> cards = new HashSet<>();

    @BeforeEach
    void setUp() {
        person = new Person("James", "Bond", AGE, Person.Gender.FEMALE);
        store = new Store("Rimi");

        rimiCard = BonusCard.createCard(BonusCard.CardType.RIMI, store, person);
        person.addBonusCard(rimiCard);
        cards.add(rimiCard);
    }

    @Test
    void negativeAge() {
        Exception exception = assertThrows(PersonException.class, () ->
                new Person("Young", "Wrong", NEGATIVEAGE, Person.Gender.MALE));
        //String expectedMessage = "Invalid age!";
        //String actualMessage = exception.getMessage();
        //assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getFirstName() {
        assertEquals("James", person.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Bond", person.getLastName());
    }

    @Test
    void getAge() {
        assertEquals(AGE, person.getAge());
    }

    @Test
    void getGender() {
        assertEquals(Person.Gender.FEMALE, person.getGender());
    }

    @Test
    void getBonusCards() {
        assertEquals(cards, person.getBonusCards());
    }

    @Test
    void getBonusCardByType() {
        assertEquals(Optional.of(rimiCard), person.getBonusCardByType(BonusCard.CardType.RIMI));
        assertEquals(Optional.empty(), person.getBonusCardByType(BonusCard.CardType.COOP));
    }

    @Test
    void addBonusCard() {
        BonusCard coopCard = BonusCard.createCard(BonusCard.CardType.COOP, store, person);
        person.addBonusCard(coopCard);
        Set<BonusCard> moreCards = new HashSet<>(cards);
        moreCards.add(coopCard);
        assertEquals(moreCards, person.getBonusCards());
    }

    @Test
    void removeBonusCard() {
        BonusCard coopCard = BonusCard.createCard(BonusCard.CardType.COOP, store, person);
        person.addBonusCard(coopCard);
        person.removeBonusCard(coopCard);
        Set<BonusCard> moreCards = new HashSet<>(cards);
        moreCards.add(coopCard);
        moreCards.remove(coopCard);
        assertEquals(moreCards, person.getBonusCards());
    }
}
