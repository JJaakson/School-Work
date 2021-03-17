package ee.taltech.iti0200.kittenshelter;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Person {

    private int money;
    private String name;
    private boolean hasADog;
    private List<Kitten> personsKittens = new LinkedList<>();

    public Person(int money, String name, boolean hasADog) {
        this.money = money;
        this.name = name;
        this.hasADog = hasADog;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public List<Kitten> getPersonsKittens() {
        return personsKittens;
    }

    public void setPersonsKittens(List<Kitten> personsKittens) {
        this.personsKittens = personsKittens;
    }

    public boolean isHasADog() {
        return hasADog;
    }

    public void addKitten(Kitten kitten) {
        personsKittens.add(kitten);
    }

    public void subtractMoney(int amount) {
        money -= amount;
    }
}
