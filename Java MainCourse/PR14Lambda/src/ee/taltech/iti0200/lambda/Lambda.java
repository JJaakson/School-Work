package ee.taltech.iti0200.lambda;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Lambda {

    private static final double BMI_CAP_UPPER = 24.9;
    private static final double BMI_CAP_DOWN = 18.5;

    private List<Person> personList;

    public Lambda(List<Person> personList) {
        this.personList = personList;
    }

    public Optional<Person> findTheTallestPerson() {
        if (!personList.isEmpty()) {
            return personList.stream().max(Comparator.comparing(Person::getHeight));
        }
        return Optional.empty();
    }

    public List<Person> filterListByGender(String gender) {
        return personList.stream()
                .filter(person -> person.getGender().toLowerCase().equals(gender.toLowerCase())
                        || person.getGender().equals("Undefined"))
                .collect(Collectors.toList());
    }

    public List<Person> filterListByAge(Integer bottomAge, Integer upperAge) {
        return personList.stream().filter(person -> bottomAge <= person.getAge() && person.getAge() <= upperAge)
                .collect(Collectors.toList());
    }

    public List<Person> filterListByBMI() {
        return personList.stream()
                .filter(person -> (person.getWeight() / person.getHeight() * person.getHeight()) <= BMI_CAP_UPPER
                        && (person.getWeight() / person.getHeight() * person.getHeight()) >= BMI_CAP_DOWN)
                .collect(Collectors.toList());
    }

    public BigInteger getTheRatingProduct() {
        return personList.stream().map(person -> BigInteger.valueOf(Long.valueOf(person.getRating())))
                .reduce(BigInteger.valueOf(Long.valueOf(1)), BigInteger::multiply);
    }

    public List<Person> sortByNameLength() {
        return personList.stream().sorted(Comparator.comparing(Person::getNameLentgh).reversed())
                .collect(Collectors.toList());
    }

    public List<Integer> getListOfIncreasedRatings(Integer number) {
        return personList.stream().map(person -> person.increaseRating(number)).collect(Collectors.toList());
    }

    public List<Person> getPeopleWithTheLowestRating() {
        Optional<Person> lowestRating = personList.stream().min(Comparator.comparing(Person::getRating));
        List<Person> lowestRatingList = null;
        if (lowestRating.isPresent()) {
            Integer rating = lowestRating.get().getRating();
            lowestRatingList = personList.stream().filter(person -> person.getRating() == rating)
                    .collect(Collectors.toList());
        }
        return lowestRatingList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
}
