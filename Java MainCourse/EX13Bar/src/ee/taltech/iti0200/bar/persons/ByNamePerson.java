package ee.taltech.iti0200.bar.persons;

import ee.taltech.iti0200.bar.Bar;
import ee.taltech.iti0200.bar.Drink;

import java.util.ArrayList;
import java.util.List;

public class ByNamePerson implements Person {

    private String drinkName;
    private List<Drink> drinkList = new ArrayList<>();
    private Bar.GetDrinkBy onlyDrinks = Bar.GetDrinkBy.NAME;

    public ByNamePerson(String drinkName) {
        this.drinkName = drinkName;
    }

    @Override
    public void addDrink(Drink drink) {
        drinkList.add(drink);
    }

    @Override
    public List<Drink> getDrinksList() {
        return drinkList;
    }

    @Override
    public String getAttribute() {
        return drinkName;
    }

    @Override
    public Bar.GetDrinkBy getOnlyDrinks() {
        return onlyDrinks;
    }
}
