package ee.taltech.iti0200.personstatistics;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Optional;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * For calculating and finding statistical info based on persons.
 */
public class PersonStatistics {

    private List<Person> persons;
    private int personCount;

    public PersonStatistics(List<Person> persons) {
        this.persons = persons;
        this.personCount = persons.size();
    }

    public long countPersons() {
       return personCount;
    }

    public OptionalDouble findAverageHeight() {
        if (!persons.isEmpty()) {
            double heightSum = persons.stream().map(Person::getHeightInMeters).reduce((double) 0, Double::sum);
            BigDecimal number = new BigDecimal(heightSum).setScale(3, RoundingMode.HALF_UP);
            heightSum = number.doubleValue();
            return OptionalDouble.of(heightSum / personCount);
        }
        return OptionalDouble.empty();
    }

    public Optional<Person> findYoungestPerson() {
        return persons.stream().min(Comparator.comparing(Person::getAge));
    }

    public Optional<Person> findOldestPerson() {
       return persons.stream().max(Comparator.comparing(Person::getAge));
    }

    public Optional<String> findLongestLastName() {
        if (!persons.isEmpty()) {
            List<String> lastNames = persons.stream().map(Person::getLastName)
                    .sorted(Comparator.comparing(String::length).reversed()).collect(Collectors.toList());
            return Optional.ofNullable(lastNames.get(0));
        }
        return Optional.empty();
    }

    public List<String> getNationalityData() {
        List<String> nationalities = new ArrayList<>();
        persons.forEach(person -> nationalities.add(person.getNationality()));
        return nationalities;
    }

    /**
     * Converts persons heights from m to cm.
     *
     * @return list of heights in cm
     */
    public List<Double> getHeightInCm() {
        return persons.stream().map(Person::getHeightInMeters).map(height -> height * 100).collect(Collectors.toList());
    }

    public List<Person> findSamples(int sampleSize) {
        if (sampleSize <= 0) {
            return new ArrayList<>();
        }
        return persons.stream().limit(sampleSize).collect(Collectors.toList());
    }

    /**
     * Find first person matching provided parameters criterias.
     *
     * @return first matching person
     */
    public Optional<Person> findSamplePerson(String nationality, Gender gender, int age) {
        return persons.stream().filter(person -> person.getNationality().equals(nationality)
                && person.getGender().equals(gender) && person.getAge() == age).findAny();
    }

    public Set<String> getDistinctFirstNames() {
        return persons.stream().map(Person::getFirstName).collect(Collectors.toSet());
    }

    /**
     * Order persons from tallest to shortest.
     *
     * @return ordered list of persons
     */
    public List<Person> getReverseOrderedByHeight() {
        return persons.stream().sorted(Comparator.comparing(Person::getHeightInMeters).reversed())
                .collect(Collectors.toList());
    }

    public List<Person> findBetweenAge(int ageFrom, int ageTo) {
        return persons.stream().filter(person -> person.getAge() >= ageFrom && person.getAge() <= ageTo)
                .collect(Collectors.toList());
    }

    /**
     * Find persons whose first name first letter sis same as his/her nationality first letter.
     *
     * @return list of matching persons
     */
    public List<Person> findSameLetterNameAndNationality() {
        return persons.stream().filter(person -> person.getFirstName().charAt(0) == person.getNationality().charAt(0))
                .collect(Collectors.toList());
    }

    /**
     * Create map where each occupation has list of persons who have that occupation.
     *
     * @return map of occupations with persons
     */
    public Map<String, List<Person>> mapOccupationToPersons() {
        Map<String, List<Person>> mapOccupationAndPersons = new HashMap<>();
        persons.forEach(person -> mapOccupationAndPersons.computeIfAbsent(person.getOccupation(),
                key -> new ArrayList<>()).add(person));
        return mapOccupationAndPersons;
    }
}
