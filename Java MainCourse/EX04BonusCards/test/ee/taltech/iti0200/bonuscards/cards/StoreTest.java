package ee.taltech.iti0200.bonuscards.cards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StoreTest {

    private Store store;
    private Person personOne;
    private Person personTwo;
    private BonusCard rimiCardTwo;
    private Store newStore;
    private static final int JAMESAGE = 25;
    private static final int MARIAGE = 35;
    private static final int RANDOMNUMBER = 555;
    private static final int RANDOM = 55;

    @BeforeEach
    void setUp() {
        personOne = new Person("James", "Bond", JAMESAGE, Person.Gender.MALE);
        personTwo = new Person("Mari", "Bond", MARIAGE, Person.Gender.FEMALE);
        store = new Store("Shop");
        rimiCardTwo = BonusCard.createCard(BonusCard.CardType.RIMI, store, personTwo);
        newStore = new Store("prisma");
    }

    @Test
    void getName() {
        assertEquals("Shop", store.getName());
    }

    @Test
    void getCustomers() {
        Set<Person> customers = new HashSet<>();
        customers.add(personTwo);
        assertEquals(customers, store.getCustomers());
    }

    @Test
    void addCustomer() {
        Set<Person> customers = new HashSet<>();
        customers.add(personTwo);
        customers.add(personOne);
        store.addCustomer(personOne);
        assertEquals(customers, store.getCustomers());
    }

    @Test
    void removeCustomer() {
        Set<Person> customers = new HashSet<>();
        store.removeCustomer(personTwo);
        assertEquals(customers, store.getCustomers());
    }

    @Test
    void getCustomerWithHighestBonusBalance() {
        BonusCard rimiCardOne = BonusCard.createCard(BonusCard.CardType.RIMI, store, personOne);
        assert rimiCardOne != null;
        rimiCardOne.setBonusBalance(BigDecimal.valueOf(100));
        rimiCardTwo.setBonusBalance(BigDecimal.valueOf(10));
        assertEquals(Optional.of(personOne), store.getCustomerWithHighestBonusBalance(BonusCard.CardType.RIMI));
        assertEquals(Optional.empty(), newStore.getCustomerWithHighestBonusBalance(BonusCard.CardType.RIMI));
    }

    @Test
    void getCustomerWithLowestBonusBalanceYoungerThan() {
        BonusCard rimiCardOne = BonusCard.createCard(BonusCard.CardType.RIMI, store, personOne);
        assert rimiCardOne != null;
        rimiCardOne.setBonusBalance(BigDecimal.valueOf(100));
        rimiCardTwo.setBonusBalance(BigDecimal.valueOf(10));
        assertEquals(Optional.of(personOne),
                store.getCustomerWithLowestBonusBalanceYoungerThan(BonusCard.CardType.RIMI, MARIAGE));
        assertEquals(Optional.empty(),
                newStore.getCustomerWithLowestBonusBalanceYoungerThan(BonusCard.CardType.RIMI, MARIAGE + JAMESAGE));
    }

    @Test
    void getTotalBonuses() {
        rimiCardTwo.setBonusBalance(BigDecimal.valueOf(RANDOMNUMBER));
        assertEquals(BigDecimal.valueOf(RANDOMNUMBER), store.getTotalBonuses(BonusCard.CardType.RIMI));
        assertEquals(BigDecimal.ZERO, newStore.getTotalBonuses(BonusCard.CardType.RIMI));
    }

    @Test
    void getAverageBonus() {
        BonusCard rimiCardOne = BonusCard.createCard(BonusCard.CardType.RIMI, store, personOne);
        assert rimiCardOne != null;
        rimiCardOne.setBonusBalance(BigDecimal.valueOf(100));
        rimiCardTwo.setBonusBalance(BigDecimal.valueOf(10));
        assertEquals(BigDecimal.valueOf(RANDOM), store.getAverageBonus(BonusCard.CardType.RIMI));
        assertEquals(BigDecimal.ZERO, newStore.getAverageBonus(BonusCard.CardType.RIMI));
    }
}
