package ee.taltech.iti0200.personstatistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonStatisticsTest {

    private static final int PERSON1_AGE = 32;
    private static final int PERSON2_AGE = 18;
    private static final double PERSON1_HEIGHT = 1.9;
    private static final double PERSON2_HEIGHT = 1.7;
    private static final double AVERAGE_HEIGHT = 1.800;
    private static final int AGE_START = 19;

    private PersonStatistics pStat;
    private PersonStatistics emptyStat;
    private Person person1;
    private Person person2;

    @BeforeEach
    void setUp() {
        List<Person> personList = new ArrayList<>();
        person1 = new Person("Bob", "Bond", PERSON1_AGE, Gender.MALE, PERSON1_HEIGHT,
                "driver", "Bosnia");
        person2 = new Person("Mari", "Bonds", PERSON2_AGE, Gender.FEMALE, PERSON2_HEIGHT,
                "agent", "usa");
        personList.add(person1);
        personList.add(person2);
        pStat = new PersonStatistics(personList);
        emptyStat = new PersonStatistics(new ArrayList<>());
    }

    @Test
    void countPersons() {
        assertEquals(2, pStat.countPersons());
        assertEquals(0, emptyStat.countPersons());
    }

    @Test
    void findAverageHeight() {
        assertEquals(OptionalDouble.of(AVERAGE_HEIGHT), pStat.findAverageHeight());
        assertEquals(OptionalDouble.empty(), emptyStat.findAverageHeight());
    }

    @Test
    void findYoungestPerson() {
        assertEquals(Optional.of(person2), pStat.findYoungestPerson());
    }

    @Test
    void findOldestPerson() {
        assertEquals(Optional.of(person1), pStat.findOldestPerson());
    }

    @Test
    void findLongestLastName() {
        assertEquals(Optional.of("Bonds"), pStat.findLongestLastName());
        assertEquals(Optional.empty(), emptyStat.findLongestLastName());
    }

    @Test
    void getNationalityData() {
        List<String> result = new ArrayList<>();
        result.add("Bosnia");
        result.add("usa");
        assertEquals(result, pStat.getNationalityData());
    }

    @Test
    void getHeightInCm() {
        assertEquals(2, pStat.getHeightInCm().size());
    }

    @Test
    void findSamples() {
        assertEquals(new ArrayList<>(), pStat.findSamples(0));
        assertEquals(1, pStat.findSamples(1).size());
    }

    @Test
    void findSamplePerson() {
        assertEquals(Optional.of(person1), pStat.findSamplePerson("Bosnia", Gender.MALE, PERSON1_AGE));
    }

    @Test
    void getDistinctFirstNames() {
        assertEquals(2, pStat.getDistinctFirstNames().size());
    }

    @Test
    void getReverseOrderedByHeight() {
        List<Person> result = new ArrayList<>();
        result.add(person1);
        result.add(person2);
        assertEquals(result, pStat.getReverseOrderedByHeight());
    }

    @Test
    void findBetweenAge() {
        assertEquals(1, pStat.findBetweenAge(AGE_START, PERSON1_AGE).size());
    }

    @Test
    void findSameLetterNameAndNationality() {
        assertEquals(1, pStat.findSameLetterNameAndNationality().size());
    }

    @Test
    void mapOccupationToPersons() {
        assertEquals(2, pStat.mapOccupationToPersons().size());
    }
}
