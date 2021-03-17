package ee.taltech.iti0200.kittenshelter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class KittenShelterTest {

    Person person;
    Person dogPerson;
    Person personWithTooManyCats;
    Person poorPerson;
    Person person5;
    Kitten globalKitten;

    @BeforeEach
    void setUp() {
        globalKitten = new Kitten(2, Kitten.Gender.MALE, Kitten.Breed.DOMESTIC);
        person = new Person(100, "John", false);
        dogPerson = new Person(100, "James", true);
        personWithTooManyCats = new Person(100, "James", false);
        poorPerson = new Person(10, "James", false);
        person5 = new Person(100, "James", true);
    }

    void addKittens(KittenShelter shelter) {
        Kitten kitten1 = new Kitten(3, Kitten.Gender.MALE, Kitten.Breed.DOMESTIC);
        Kitten kitten2 = new Kitten(1, Kitten.Gender.FEMALE, Kitten.Breed.FLUFFY);
        Kitten kitten3 = new Kitten(5, Kitten.Gender.FEMALE, Kitten.Breed.HAIRLESS);
        Kitten kitten4 = new Kitten(5, Kitten.Gender.MALE, Kitten.Breed.HAIRLESS);
        Kitten kitten5 = new Kitten(5, Kitten.Gender.MALE, Kitten.Breed.HAIRLESS);

        shelter.addKitten(kitten1, LocalDateTime.of(2020, 1, 4, 12, 30));
        shelter.addKitten(kitten2, LocalDateTime.now());
        shelter.addKitten(kitten3, LocalDateTime.of(2020, 1, 4, 12, 30));
        shelter.addKitten(kitten4, LocalDateTime.of(2020, 1, 4, 12, 30));
        personWithTooManyCats.addKitten(kitten5);
    }

    @Test
    void simplePlayAndAdopt() {
        Scanner scanner = new Scanner("gender\nmale\nplay\nadopt\nfemale\nleave");
        KittenShelter shelter = new KittenShelter(25, person, scanner);
        addKittens(shelter);
        shelter.startAVisit();
        assertEquals(1, person.getPersonsKittens().size());
    }

    @Test
    void simplePet() {
        Scanner scanner = new Scanner("gender\nmale\npet\nleave");
        KittenShelter shelter = new KittenShelter(25, person, scanner);
        addKittens(shelter);
        shelter.startAVisit();
        assertEquals(95, person.getMoney());
    }

    @Test
    void quarantinePet() {
        Scanner scanner = new Scanner("breed\nfluffy\npet\nplay\nadopt\nleave");
        KittenShelter shelter = new KittenShelter(25, person, scanner);
        addKittens(shelter);
        shelter.startAVisit();
        assertEquals(100, person.getMoney());
    }

    @Test
    void domesticPet() {
        Scanner scanner = new Scanner("breed\ndomestic\npet\nleave");
        KittenShelter shelter = new KittenShelter(25, person, scanner);
        addKittens(shelter);
        shelter.startAVisit();
        assertEquals(95, person.getMoney());
    }

    @Test
    void hairlessAdopt() {
        Scanner scanner = new Scanner("breed\nhairless\nadopt\nbreed\ndomestic\nleave");
        KittenShelter shelter = new KittenShelter(25, person, scanner);
        addKittens(shelter);
        shelter.startAVisit();
        assertEquals(50, person.getMoney());
    }

    @Test
    void petByAge() {
        Scanner scanner = new Scanner("age\n3\npet\nleave");
        KittenShelter shelter = new KittenShelter(25, person, scanner);
        addKittens(shelter);
        shelter.startAVisit();
        assertEquals(100, person.getMoney());
    }

    @Test
    void personHasDog() {
        Scanner scanner = new Scanner("gender\nmale\npet\nleave");
        KittenShelter shelter = new KittenShelter(25, dogPerson, scanner);
        addKittens(shelter);
        shelter.startAVisit();
        assertEquals(95, dogPerson.getMoney());
    }

    @Test
    void personHasTooManyCats() {
        Scanner scanner = new Scanner("gender\nmale\nadopt\nmale\nadopt\nleave");
        KittenShelter shelter = new KittenShelter(25, personWithTooManyCats, scanner);
        addKittens(shelter);
        shelter.startAVisit();
        assertEquals(85, personWithTooManyCats.getMoney());
    }

    @Test
    void poorPersonAdopt() {
        Scanner scanner = new Scanner("breed\nhairless\nadopt\nleave");
        KittenShelter shelter = new KittenShelter(25, poorPerson, scanner);
        addKittens(shelter);
        shelter.startAVisit();
        assertEquals(10, poorPerson.getMoney());
    }

    @Test
    void poorPersonPlay() {
        Scanner scanner = new Scanner("breed\nhairless\nplay\nplay\nleave");
        KittenShelter shelter = new KittenShelter(25, poorPerson, scanner);
        addKittens(shelter);
        shelter.startAVisit();
        assertEquals(0, poorPerson.getMoney());
    }

    @Test
    void poorPersonPet() {
        Scanner scanner = new Scanner("breed\nhairless\npet\npet\nleave");
        KittenShelter shelter = new KittenShelter(25, poorPerson, scanner);
        addKittens(shelter);
        shelter.startAVisit();
        assertEquals(0, poorPerson.getMoney());
    }

    @Test
    void wrongInput() {
        Scanner scanner = new Scanner("wrong\nbreed\nhairless\npet\nleave");
        KittenShelter shelter = new KittenShelter(25, person, scanner);
        addKittens(shelter);
        shelter.startAVisit();
        assertEquals(95, person.getMoney());
    }

    @Test
    void noKitten() {
        Scanner scanner = new Scanner("breed\ndomestic\nadopt\nbreed\ndomestic\ngender\nmale\nleave\nleave");
        KittenShelter shelter = new KittenShelter(25, person, scanner);
        addKittens(shelter);
        shelter.startAVisit();
        assertEquals(85, person.getMoney());
    }

    @Test
    void getKitten() {
        KittenShelter shelter = new KittenShelter(25, person);
        addKittens(shelter);
        shelter.addKitten(globalKitten, LocalDateTime.of(2020, 1, 4, 12, 30));
        shelter.getKitten(globalKitten);
        assertEquals(globalKitten, shelter.getCurrentKitten());
    }
}
