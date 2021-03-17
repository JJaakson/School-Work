package ee.taltech.iti0200.bonuscards.cards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CoopCardTest {

    private BonusCard coopCard;
    private static final int JAMESAGE = 25;
    private static final double BALANCE = 15.0;


    @BeforeEach
    void setUp() {
        Person person = new Person("James", "Bond", JAMESAGE, Person.Gender.MALE);
        Store store = new Store("Shop");
        coopCard = BonusCard.createCard(BonusCard.CardType.COOP, store, person);
    }

    @Test
    void collectBonus() {
        assertEquals(BigDecimal.valueOf(5.0), coopCard.collectBonus(100));
        assertEquals(BigDecimal.valueOf(BALANCE), coopCard.getBonusBalance());
    }
}
