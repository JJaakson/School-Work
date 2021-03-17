package ee.taltech.iti0200.warehouses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class WorkerTest {
    Worker worker;
    private static final int SEVENTYYEARS = 70;
    private static final int SEVENHUNDRED = 700;


    @BeforeEach
    void setUp() {
        worker = new Worker("James", "Bond", "007", SEVENTYYEARS);
    }

    @Test
    void getWorkerSummary() {
        assertEquals("James Bond, 70 (007)", worker.getWorkerSummary());
    }

    @Test
    void getFirstName() {
        assertEquals("James", worker.getFirstName());
    }

    @Test
    void setFirstName() {
        worker.setFirstName("Better James");
        assertEquals("Better James", worker.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Bond", worker.getLastName());
    }

    @Test
    void setLastName() {
        worker.setLastName("Better Bond");
        assertEquals("Better Bond", worker.getLastName());
    }

    @Test
    void getIdCode() {
        assertEquals("007", worker.getIdCode());
    }

    @Test
    void setIdCode() {
        worker.setIdCode("007, cant change that");
        assertEquals("007, cant change that", worker.getIdCode());
    }

    @Test
    void getAge() {
        assertEquals(SEVENTYYEARS, worker.getAge());
    }

    @Test
    void setAge() {
        worker.setAge(SEVENHUNDRED);
        assertEquals(SEVENHUNDRED, worker.getAge());
    }
}
