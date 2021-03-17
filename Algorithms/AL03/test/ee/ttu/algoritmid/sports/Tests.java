package ee.ttu.algoritmid.sports;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testng.Assert.assertTrue;

public class Tests {

    @Test
    public void testSimple() {
        Sports sports = new Sports();

        sports.addAnswer("Mart", "Soccer");

        assertEquals(1, sports.numberOfAnswerers());
        assertTrue(sports.mostPopularSport().isPresent());
        assertEquals("Soccer", sports.mostPopularSport().get());
        assertEquals(1, sports.sportFanclubSize("Soccer"));
        assertEquals(0, sports.sportFanclubSize("Football"));
    }

    @Test
    public void testDoubleAnswer() {
        Sports sports = new Sports();

        sports.addAnswer("Mari", "Basketball");
        sports.addAnswer("Mari", "Basketball");

        assertEquals(1, sports.numberOfAnswerers());
        assertEquals(1, sports.sportFanclubSize("Basketball"));
    }

    @Test
    public void testEmpty() {
        Sports sports = new Sports();
        assertTrue(sports.mostPopularSport().isEmpty());
        assertEquals(0, sports.numberOfAnswerers());
        assertEquals(0, sports.sportFanclubSize("Badminton"));
    }
}
