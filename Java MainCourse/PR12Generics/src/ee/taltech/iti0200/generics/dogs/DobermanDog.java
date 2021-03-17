package ee.taltech.iti0200.generics.dogs;

import ee.taltech.iti0200.generics.Animal;

public class DobermanDog extends Animal {

    public DobermanDog(String name) {
        super(name);
    }

    @Override
    public void sound() {
        System.out.println("Woof!");
    }
}
