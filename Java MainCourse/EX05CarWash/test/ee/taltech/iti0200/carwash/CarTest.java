package ee.taltech.iti0200.carwash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarTest {

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car(100);
    }

    @Test
    void setDirtiness() {
        car.setDirtiness(1000);
        assertEquals(1000, car.getDirtiness());
    }
}
