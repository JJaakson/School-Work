package ee.taltech.iti0200.stream;
import java.util.Optional;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Comparator;
import java.util.stream.Collectors;

public class KittenStatistics {

    private List<Kitten> kittens;

    public void setKittens(List<Kitten> kittens) {
        this.kittens = kittens;
    }

    public OptionalDouble findKittensAverageAge() {
        if (!kittens.isEmpty()) {
            double kittenAgeSum = kittens.stream().map(Kitten::getAge).reduce(0, Integer::sum);
            int kittenCount = kittens.size();
            return OptionalDouble.of(kittenAgeSum / kittenCount);
        }
        return OptionalDouble.empty();
    }

    public Optional<Kitten> findOldestKitten() {
        if (!kittens.isEmpty()) {
            return kittens.stream().max(Comparator.comparing(Kitten::getAge));
        }
        return Optional.empty();
    }

    public List<Kitten> findYoungestKittens() {
        Optional<Kitten> youngestKitten = kittens.stream().min(Comparator.comparing(Kitten::getAge));
        List<Kitten> youngestKittens = null;
        if (youngestKitten.isPresent()) {
            int youngestAge = youngestKitten.get().getAge();
            youngestKittens = kittens.stream().filter(x -> x.getAge() == youngestAge)
                    .collect(Collectors.toList());
        }
        return youngestKittens;
    }

    public List<Kitten> findKittensAccordingToGender(Kitten.Gender gender) {
        return kittens.stream().filter(kitten -> kitten.getGender().equals(gender)).collect(Collectors.toList());
    }

    public List<Kitten> findKittensBetweenAges(int minAge, int maxAge) {
        return kittens.stream().filter(kitten -> minAge <= kitten.getAge() && kitten.getAge() <= maxAge)
                .collect(Collectors.toList());
    }

    public Optional<Kitten> findFirstKittenWithGivenName(String givenName) {
        return kittens.stream().filter(kitten -> kitten.getName().toLowerCase().equals(givenName.toLowerCase()))
                .findFirst();
    }

    public List<Kitten> kittensSortedByAgeYoungerFirst() {
        return kittens.stream().sorted(Comparator.comparing(Kitten::getAge)).collect(Collectors.toList());
    }

    public List<Kitten> kittensSortedByAgeOlderFirst() {
        return kittens.stream().sorted(Comparator.comparing(Kitten::getAge).reversed()).collect(Collectors.toList());
    }
}
