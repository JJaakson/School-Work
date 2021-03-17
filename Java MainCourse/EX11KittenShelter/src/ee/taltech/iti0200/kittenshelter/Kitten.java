package ee.taltech.iti0200.kittenshelter;

import java.time.LocalDateTime;
import java.util.*;

public class Kitten {

    private int age;
    private Gender gender;
    private Breed breed;
    private LocalDateTime shelterArrival;
    public List<Person> attractedTo = new ArrayList<>();

    public enum Breed {
        DOMESTIC(15),
        FLUFFY(25),
        HAIRLESS(50);

        private final int adoptionCost;

        Breed(int adoptionCost) {
            this.adoptionCost = adoptionCost;
        }

        public int getAdoptionCost() {
            return adoptionCost;
        }
    }

    public enum Gender {
        MALE,
        FEMALE
    }

    public Kitten(int ageInMonths, Gender gender, Breed breed) {
        this.age = ageInMonths;
        this.gender = gender;
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public Breed getBreed() {
        return breed;
    }

    public void addShelterArrival(LocalDateTime dateOfArrival) {
        shelterArrival = dateOfArrival;
    }

    public void addAttractedTo(Person person) {
        if (!attractedTo.contains(person)) {
            attractedTo.add(person);
        }
    }

    public LocalDateTime getShelterArrival() {
        return shelterArrival;
    }

    @Override
    public String toString() {
        return "Kitten{" +
                "age=" + age +
                ", gender=" + gender +
                ", breed=" + breed +
                '}';
    }
}
