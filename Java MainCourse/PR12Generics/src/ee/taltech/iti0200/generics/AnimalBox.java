package ee.taltech.iti0200.generics;

import ee.taltech.iti0200.generics.foods.Food;

import java.util.Optional;

public class AnimalBox<T extends Animal> {

    private T animalHere;

    public void put(T animal) {
        animalHere = animal;
    }

   public void sound() {
       animalHere.sound();
   }

   public void feed(Food food) {
        if (animalHere != null) {
            System.out.println(animalHere.name + " was fed with " + food.name);
        }
   }

   public Optional<T> getAnimal() {
        return Optional.ofNullable(animalHere);
   }
}
