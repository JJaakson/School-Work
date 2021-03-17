package ee.taltech.iti0200.generics.cats;

import ee.taltech.iti0200.generics.Animal;

public class PersianCat extends Animal {

    public PersianCat(String name) {
        super(name);
    }

    @Override
    public void sound() {
        System.out.println("Prrr-prrr");
    }
}
