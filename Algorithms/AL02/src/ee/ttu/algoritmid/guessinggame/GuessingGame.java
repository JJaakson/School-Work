package ee.ttu.algoritmid.guessinggame;

import java.util.*;
import java.util.stream.Collectors;

public class GuessingGame {

    Oracle oracle;

    public GuessingGame(Oracle oracle) {
        this.oracle = oracle;
    }

    /**
     * @param fruitArray - All the possible fruits.
     * @return the name of the fruit.
     */
    public String play(Fruit[] fruitArray) {
        List<Fruit> fruits = Arrays.asList(fruitArray.clone());
        fruits = fruits.stream().sorted(Comparator.comparing(Fruit::getWeight)).collect(Collectors.toList());
        Fruit checkerFruit = fruits.get(fruits.size() / 2);
        String oracleAnwser = oracle.isIt(checkerFruit);
        while (!oracleAnwser.equals("correct!")) {
            List<Fruit> smallerOnes = new ArrayList<>();
            List<Fruit> biggerOnes = new ArrayList<>();
            for (Fruit x : fruits) {
                if (x.getWeight() > checkerFruit.getWeight()) {
                    biggerOnes.add(x);
                } else if (x.getWeight() < checkerFruit.getWeight()) {
                    smallerOnes.add(x);
                }
            }
            if (oracleAnwser.equals("heavier")) {
                fruits = biggerOnes;
            } else {
                fruits = smallerOnes;
            }
            checkerFruit = fruits.get(fruits.size() / 2);
            oracleAnwser = oracle.isIt(checkerFruit);
        }
        return checkerFruit.getName();
    }
}