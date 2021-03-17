package ee.taltech.iti0200.bar;

import ee.taltech.iti0200.bar.persons.AlcoholFreePerson;
import ee.taltech.iti0200.bar.persons.ByCategoryPerson;
import ee.taltech.iti0200.bar.persons.ByIngredientPerson;
import ee.taltech.iti0200.bar.persons.ByNamePerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BarTest {

    private static final int DAYTIME = 12;
    private static final int NIGHTTIME = 23;

    private Bar dayTimeBar;
    private Bar nightTimeBar;
    private ByNamePerson namePerson;
    private ByIngredientPerson ingredientPerson;
    private ByCategoryPerson categoryPerson;
    private AlcoholFreePerson alcoholFreePerson;
    private ByNamePerson doesntExistPerson;

    @BeforeEach
    void setUp() {
        dayTimeBar = new Bar(LocalTime.of(DAYTIME, 0));
        nightTimeBar = new Bar(LocalTime.of(NIGHTTIME, 0));
        namePerson = new ByNamePerson("margarita");
        doesntExistPerson = new ByNamePerson("milklemonade");
        ingredientPerson = new ByIngredientPerson("Gin");
        categoryPerson = new ByCategoryPerson("Cocktail");
        alcoholFreePerson = new AlcoholFreePerson("Choco");
    }

    @Test
    void dayTimeBarTest() throws IOException {
        dayTimeBar.getDrink(namePerson);
        assertEquals(0, namePerson.getDrinksList().size());
        dayTimeBar.getDrink(ingredientPerson);
        assertEquals(0, ingredientPerson.getDrinksList().size());
        dayTimeBar.getDrink(categoryPerson);
        assertEquals(0, categoryPerson.getDrinksList().size());
        dayTimeBar.getDrink(alcoholFreePerson);
        dayTimeBar.getDrink(alcoholFreePerson);
        assertEquals(2, alcoholFreePerson.getDrinksList().size());
        assertEquals(2, dayTimeBar.getDrinksList().size());
        dayTimeBar.getDrink(doesntExistPerson);
        assertEquals(0, doesntExistPerson.getDrinksList().size());
    }

    @Test
    void nightTimeBarTest() throws IOException {
        nightTimeBar.getDrink(namePerson);
        nightTimeBar.getDrink(namePerson);
        assertEquals(2, namePerson.getDrinksList().size());
        assertEquals("Alcoholic", namePerson.getDrinksList().get(0).getStrAlcoholic());
        assertEquals("Margarita", namePerson.getDrinksList().get(0).getName());
        nightTimeBar.getDrink(ingredientPerson);
        assertEquals(1, ingredientPerson.getDrinksList().size());
        nightTimeBar.getDrink(categoryPerson);
        assertEquals(1, categoryPerson.getDrinksList().size());
        nightTimeBar.getDrink(alcoholFreePerson);
        assertEquals(1, alcoholFreePerson.getDrinksList().size());
        assertEquals(5, nightTimeBar.getDrinksList().size());
    }
}
