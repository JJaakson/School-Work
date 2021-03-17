package ee.taltech.iti0200.carwash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CarWashServiceTest {

    private static final int DIRT = 100;
    private static final int BALANCE = 1000;
    private static final int NEW_BALANCE = 5;

    private Car car;
    private CarOwner owner;
    private CarWashService service;

    @BeforeEach
    void setUp() {
        car = new Car(DIRT);
        owner = new CarOwner("Bond", BALANCE);
        service = new CarWashService();
        CheapWash strategy = new CheapWash();
        service.setWashStrategy(strategy);
    }

    @Test
    void wash() {
        assertTrue(service.wash(car, owner));
        owner.setBalance(NEW_BALANCE);
        assertFalse(service.wash(car, owner));
    }

    @Test
    void washAndDry() {
        assertTrue(service.washAndDry(car, owner));
        owner.setBalance(NEW_BALANCE);
        assertFalse(service.washAndDry(car, owner));
    }
}
