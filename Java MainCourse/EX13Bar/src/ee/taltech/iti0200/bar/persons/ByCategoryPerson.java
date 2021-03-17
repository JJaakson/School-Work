package ee.taltech.iti0200.bar.persons;

import ee.taltech.iti0200.bar.Bar;
import ee.taltech.iti0200.bar.Drink;

import java.util.ArrayList;
import java.util.List;

public class ByCategoryPerson implements Person {

    private String drinkCategory;
    private List<Drink> drinkList = new ArrayList<>();
    private Bar.GetDrinkBy onlyDrinks = Bar.GetDrinkBy.CATEGORY;

    public ByCategoryPerson(String drinkCategory) {
        this.drinkCategory = drinkCategory;
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
        return drinkCategory;
    }

    @Override
    public Bar.GetDrinkBy getOnlyDrinks() {
        return onlyDrinks;
    }
}
