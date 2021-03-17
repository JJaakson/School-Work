package ee.taltech.iti0200.bonuscards.cards;
import ee.taltech.iti0200.bonuscards.exceptions.AlreadyExistingCardTypeException;
import ee.taltech.iti0200.bonuscards.exceptions.BonusException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BonusCardTest {

    private Store store;
    private Person personOne;
    private Person personTwo;
    private BonusCard rimiCardTwo;
    private static final int JAMESAGE = 25;
    private static final int MARIAGE = 35;

    @BeforeEach
    void setUp() {
        personOne = new Person("James", "Bond", JAMESAGE, Person.Gender.MALE);
        personTwo = new Person("Mari", "Bond", MARIAGE, Person.Gender.FEMALE);
        store = new Store("Shop");
        rimiCardTwo = BonusCard.createCard(BonusCard.CardType.RIMI, store, personTwo);
    }

    @Test
    void createCard() {
        BonusCard rimiCardOne = BonusCard.createCard(BonusCard.CardType.RIMI, store, personOne);
        BonusCard coopCard = BonusCard.createCard(BonusCard.CardType.COOP, store, personOne);
        Set<BonusCard> cards = new HashSet<>();
        cards.add(rimiCardOne);
        cards.add(coopCard);
        assertEquals(cards, personOne.getBonusCards());
        assertThrows(AlreadyExistingCardTypeException.class, () ->
                BonusCard.createCard(BonusCard.CardType.RIMI, store, personOne));
    }

    @Test
    void useBonus() {
        rimiCardTwo.setBonusBalance(BigDecimal.valueOf(10));
        assertEquals(BigDecimal.valueOf(5), rimiCardTwo.useBonus(BigDecimal.valueOf(5)));
        assertThrows(BonusException.class, () ->
                rimiCardTwo.useBonus(BigDecimal.valueOf(100)));
    }

    @Test
    void setBonusBalance() {
        rimiCardTwo.setBonusBalance(BigDecimal.valueOf(1000));
        assertEquals(BigDecimal.valueOf(1000), rimiCardTwo.getBonusBalance());
    }

    @Test
    void getType() {
        assertEquals(BonusCard.CardType.RIMI, rimiCardTwo.getType());
    }

    @Test
    void getStore() {
        assertEquals(store, rimiCardTwo.getStore());
    }

    @Test
    void getPerson() {
        assertEquals(personTwo, rimiCardTwo.getPerson());
    }
}
