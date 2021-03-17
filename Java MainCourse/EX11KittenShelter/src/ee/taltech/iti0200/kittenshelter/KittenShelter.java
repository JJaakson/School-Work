package ee.taltech.iti0200.kittenshelter;

import java.lang.management.PlatformLoggingMXBean;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class KittenShelter {

    private static final int MAX_NUMBER_OF_CATS_A_PERSON_CAN_OWN = 2;
    private static final int QUARANTINE_DAYS = 7;
    private static final int PLAY_COST = 10;
    private static final int PET_COST = 5;

    private int maxCapacity;
    private List<Kitten> kittens = new LinkedList<>();
    private Kitten currentChosenKitten;
    Scanner scanner;
    private Person currentPerson;

    public KittenShelter(int maxCapacity, Person currentVisitor) {
        this.maxCapacity = maxCapacity;
        this.currentPerson = currentVisitor;
        scanner = new Scanner(System.in);
    }

    public KittenShelter(int maxCapacity, Person currentVisitor, Scanner scannerForTesting) {
        this.maxCapacity = maxCapacity;
        this.currentPerson = currentVisitor;
        scanner = scannerForTesting;
    }

    public void startAVisit() {
        System.out.println("Hello " + currentPerson.getName() + ", here is a list of our kittens: ");
        chooseKitten();
    }

    public void showKittens() {
        for(Kitten kitten : kittens) {
            System.out.println(kitten);
        }
        System.out.println();
    }

    public void showCurrentPersonsKittens() {
        for(Kitten kitten : currentPerson.getPersonsKittens()) {
            System.out.println(kitten);
        }
        System.out.println();
    }

    public void addKitten(Kitten kitten, LocalDateTime arrivalTime) {
        if (kittens.size() < maxCapacity) {
            kitten.addShelterArrival(arrivalTime);
            kittens.add(kitten);
        }
    }

    public void chooseKitten() {
        showKittens();
        System.out.println("Choose a kitten that you want to interact with,\nattributes to choose" +
                " kitten by : age(insert \"age\"), gender(insert \"gender\"), breed(insert \"breed\")");
        String attribute = scanner.nextLine();
        switch (attribute.toLowerCase()) {
            case "age":
                System.out.println("Insert age");
                int kittenAge = scanner.nextInt();
                getKitten(kittenAge);
                break;
            case "gender":
                System.out.println("Insert gender(\"female\" or\"male\").");
                String gender = scanner.nextLine();
                if (gender.toLowerCase().contains("female")) {
                    getKitten(Kitten.Gender.FEMALE);
                } else if (gender.toLowerCase().contains("male")) {
                    getKitten(Kitten.Gender.MALE);
                }
                break;
            case "breed":
                System.out.println("Insert breed(\"domestic\", \"fluffy\", \"hairless\")");
                String breed = scanner.nextLine();
                if (breed.toLowerCase().contains("domestic")) {
                    getKitten(Kitten.Breed.DOMESTIC);
                } else if (breed.toLowerCase().contains("fluffy")) {
                    getKitten(Kitten.Breed.FLUFFY);
                } else if (breed.toLowerCase().contains("hairless")) {
                    getKitten(Kitten.Breed.HAIRLESS);
                }
                break;
        }
        if (currentChosenKitten == null) {
            System.out.println("Could not find a kitten with correct attribute, choose again.");
            chooseKitten();
        } else {
            System.out.println();
            System.out.println("Here is your chosen kitten " + currentChosenKitten);
            System.out.println();
            nextAction();
        }
    }

    public void nextAction() {
        System.out.println("What would you like to do with the cat?\n" +
                "The choices are: play (insert \"play\"), cost: 10 units; pet (insert \"pet\"), cost 5 units;" +
                " \nadopt(insert \"adopt\"), cost depends on the breed" +
                "(domestic: 15 units, fluffy: 25 units, hairless: 50 units) \n" +
                "choose a new kitten(insert \"kitten\") or leave(insert \"leave\")");
        System.out.println("And here is your balance: " + currentPerson.getMoney() + " units");
        String choice = scanner.nextLine();
        if (choice.toLowerCase().contains("play")) {
            playWithKitten();
        } else if (choice.toLowerCase().contains("pet")) {
            petKitten();
        } else if (choice.toLowerCase().contains("adopt")) {
            adoptKitten();
        } else if (choice.toLowerCase().contains("kitten")) {
            chooseKitten();
        } else if (choice.toLowerCase().contains("leave")) {
            if (!currentPerson.getPersonsKittens().isEmpty()) {
                System.out.println("Here are your adopted kittens :");
                showCurrentPersonsKittens();
            }
            System.out.println("Visit us again!");
        }
    }

    public void getKitten(Object catsAttribute) {
        currentChosenKitten = null;
        if (catsAttribute instanceof Integer) {
            for (Kitten kitten : kittens) {
                if (Integer.valueOf(kitten.getAge()) == catsAttribute) {
                    currentChosenKitten = kitten;
                    break;
                }
            }
        } else if (catsAttribute instanceof Kitten.Gender) {
            for (Kitten kitten : kittens) {
                if (kitten.getGender() == catsAttribute) {
                    currentChosenKitten = kitten;
                    break;
                }
            }
        } else if (catsAttribute instanceof Kitten.Breed) {
            for (Kitten kitten : kittens) {
                if (kitten.getBreed() == catsAttribute) {
                    currentChosenKitten = kitten;
                    break;
                }
            }
        } else if (catsAttribute instanceof Kitten) {
            if (kittens.contains(catsAttribute)) {
                currentChosenKitten = (Kitten) catsAttribute;
            }
        }
        if (currentChosenKitten == null) {
            System.out.println("Could not find a kitten with correct attribute, choose again.");
            chooseKitten();
        }
    }

    private boolean checkQuarantine() {
        long daysSinceArrival = ChronoUnit.DAYS.between(currentChosenKitten.getShelterArrival(), LocalDateTime.now());
        if (daysSinceArrival < QUARANTINE_DAYS) {
            System.out.println("Sorry, this kitten is in quarantine, choose a new one!");
            return false;
        }
        return true;
    }

    private void adoptKitten() {
        if (checkQuarantine()) {
            if (currentPerson.getPersonsKittens().size() >= MAX_NUMBER_OF_CATS_A_PERSON_CAN_OWN) {
                System.out.println(currentPerson.getName() + " already has too many kittens.");
            } else if (currentChosenKitten.getBreed().getAdoptionCost() > currentPerson.getMoney()) {
                System.out.println(currentPerson.getName() + " can't afford to adopt this kitten.");
            } else if (currentPerson.isHasADog()) {
                System.out.println(currentPerson.getName() + " can't adopt, because the dog might hurt the kitten.");
            } else {
                currentPerson.addKitten(currentChosenKitten);
                currentPerson.subtractMoney(currentChosenKitten.getBreed().getAdoptionCost());
                kittens.remove(currentChosenKitten);
                System.out.println("Kitten adopted : " + currentChosenKitten);
                chooseKitten();
            }
        } else {
            nextAction();
        }
    }

    public void setCurrentPerson(Person person) {
        currentPerson = person;
        startAVisit();
    }

    private void playWithKitten() {
        if (checkQuarantine()) {
            if (currentPerson.getMoney() >= PLAY_COST) {
                currentPerson.setMoney(currentPerson.getMoney() - PLAY_COST);
                currentChosenKitten.addAttractedTo(currentPerson);
                System.out.println("Kitten is happy and so are you!");
            } else {
                System.out.println("Not enough money!");
            }
        }
        nextAction();
    }

    private void petKitten() {
        if (checkQuarantine()) {
            if (currentPerson.getMoney() >= PET_COST) {
                currentPerson.setMoney(currentPerson.getMoney() - PET_COST);
                currentChosenKitten.addAttractedTo(currentPerson);
                System.out.println("Kitten is happy and so are you!");
            } else {
                System.out.println("Not enough money!");
            }
        }
        nextAction();
    }

    public Kitten getCurrentKitten() {
        return currentChosenKitten;
    }
}
