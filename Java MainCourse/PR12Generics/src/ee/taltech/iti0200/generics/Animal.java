package ee.taltech.iti0200.generics;

public abstract class Animal {

  String name;

  public Animal(String name) {
    this.name = name;
  }

  public abstract void sound();
}
