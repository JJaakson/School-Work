package ee.taltech.iti0200.carwash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class WashStrategyTest {

    private static final int OWNER_BALANCE = 1000;

    private CarOwner owner;
    private CheapWash strategy;

    @BeforeEach
    void setUp() {
        owner = new CarOwner("Bond", OWNER_BALANCE);
        strategy = new CheapWash();
    }

    @Test
    void isClientBlacklisted() {
        assertFalse(strategy.isClientBlacklisted(owner.getName()));
    }

    @Test
    void setSessionDuration() {
        strategy.setSessionDuration(10);
        assertEquals(10, strategy.getSessionDuration());
    }

    @Test
    void setSessionPrice() {
        strategy.setSessionPrice(10);
        assertEquals(10, strategy.getSessionPrice());
    }
}
