package ee.taltech.iti0200.bar;

import java.util.ArrayList;
import java.util.List;

public class Drink {

    private String strDrink;
    private String strAlcoholic;
    private String strGlass;
    private List<String> ingredients = new ArrayList<>();

    public String getName() {
        return strDrink;
    }

    public String getStrAlcoholic() {
        return strAlcoholic;
    }

    public String getStrGlass() {
        return strGlass;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void addIngredient(String ingredient) {
        if (!ingredients.contains(ingredient)) {
            ingredients.add(ingredient);
        }
    }

    @Override
    public String toString() {
        return "Drink{"
                + "name='" + strDrink + '\''
                + ", strAlcoholic='" + strAlcoholic + '\''
                + ", strGlass='" + strGlass + '\''
                + ", ingredients=" + ingredients
                + '}';
    }
}
