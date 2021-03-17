package ee.taltech.iti0200.burgerbooth.burgers;

public enum BurgerType {

    CHEESE("cheese", 2.50, 5),
    VEGETARIAN("vegetarian", 3.50, 5),
    CHILLI("chilli", 5.0, 7);

    private final String condiments;
    private final double cost;
    private final int cookingTime;

    BurgerType(String condiments, double cost, int cookingTime) {
        this.condiments = condiments;
        this.cost = cost;
        this.cookingTime = cookingTime;
    }

    public String getCondiments() {
        return condiments;
    }

    public double getCost() {
        return cost;
    }

    public int getCookingTime() {
        return cookingTime;
    }
}
