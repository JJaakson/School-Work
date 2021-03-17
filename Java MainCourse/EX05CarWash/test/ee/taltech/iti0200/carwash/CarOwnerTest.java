package ee.taltech.iti0200.carwash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CarOwnerTest {

    private static final int BALANCE = 7;
    private static final int NEW_BALANCE = 700;

    private CarOwner owner;

    @BeforeEach
    void setUp() {
        owner = new CarOwner("Bond", BALANCE);
    }

    @Test
    void setBalance() {
        owner.setBalance(NEW_BALANCE);
        assertEquals(NEW_BALANCE, owner.getBalance());
    }

    @Test
    void getName() {
        assertEquals("Bond", owner.getName());
    }
}
