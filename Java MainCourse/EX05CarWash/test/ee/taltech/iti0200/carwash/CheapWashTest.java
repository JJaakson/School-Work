package ee.taltech.iti0200.carwash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CheapWashTest {

    private static final int CAR_DIRTINESS = 50;
    private static final int CAR_DIRTINESS_AFTER_WASH = 10;
    private static final int OWNER_BALANCE = 1000;
    private static final int OWNER_BALANCE_AFTER_DRY = 998;
    private static final int WASH_PRICE = 10;
    private static final int WASH_AND_DRY_PRICE = 12;

    private Car car;
    private CarOwner owner;
    private CheapWash strategy;

    @BeforeEach
    void setUp() {
        car = new Car(CAR_DIRTINESS);
        owner = new CarOwner("Bond", OWNER_BALANCE);
        strategy = new CheapWash();
    }

    @Test
    void wash() {
        strategy.wash(car, owner);
        assertEquals(CAR_DIRTINESS_AFTER_WASH, car.getDirtiness());
        strategy.wash(car, owner);
        assertEquals(0, car.getDirtiness());
    }

    @Test
    void dry() {
        strategy.dry(car, owner);
        assertEquals(OWNER_BALANCE_AFTER_DRY, owner.getBalance());
    }

    @Test
    void getWashAndDryPrice() {
        assertEquals(WASH_AND_DRY_PRICE, strategy.getWashAndDryPrice());
    }

    @Test
    void getWashPrice() {
        assertEquals(WASH_PRICE, strategy.getWashPrice());
    }
}
