package ee.taltech.iti0200.bar.persons;

import ee.taltech.iti0200.bar.Bar;
import ee.taltech.iti0200.bar.Drink;

import java.util.ArrayList;
import java.util.List;

public class ByIngredientPerson implements Person {

    private String drinkIngredient;
    private List<Drink> drinkList = new ArrayList<>();
    private Bar.GetDrinkBy onlyDrinks = Bar.GetDrinkBy.INGREDIENT;

    public ByIngredientPerson(String drinkIngredient) {
        this.drinkIngredient = drinkIngredient;
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
        return drinkIngredient;
    }

    @Override
    public Bar.GetDrinkBy getOnlyDrinks() {
        return onlyDrinks;
    }
}
