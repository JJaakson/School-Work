package ee.taltech.iti0200.bonuscards.cards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RimiCardTest {

    private Person person;
    private Store store;
    private BonusCard rimiCard;
    private static final int JAMESAGE = 25;

    @BeforeEach
    void setUp() {
        person = new Person("James", "Bond", JAMESAGE, Person.Gender.MALE);
        store = new Store("Shop");
        rimiCard = BonusCard.createCard(BonusCard.CardType.RIMI, store, person);
    }

    @Test
    void collectBonus() {
        assertEquals(BigDecimal.valueOf(2.0), rimiCard.collectBonus(100));
        assertEquals(BigDecimal.valueOf(0), rimiCard.collectBonus(5));
        assertEquals(BigDecimal.valueOf(2.0), rimiCard.getBonusBalance());
    }
}
