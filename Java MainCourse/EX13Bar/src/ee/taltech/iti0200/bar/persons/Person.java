package ee.taltech.iti0200.bar.persons;

import ee.taltech.iti0200.bar.Bar;
import ee.taltech.iti0200.bar.Drink;

import java.util.List;

public interface Person {

    void addDrink(Drink drink);

    List<Drink> getDrinksList();

    String getAttribute();

    Bar.GetDrinkBy getOnlyDrinks();
}
