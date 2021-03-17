package ee.taltech.iti0200.greenhouse.gardener;

import ee.taltech.iti0200.greenhouse.plantingstrategy.PlantingStrategy;

import java.util.Map;

public class Gardener {

    private String name;
    private PlantingStrategy strategy;

    public Gardener(String name, PlantingStrategy strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    public String[][] plantPlants(int length, int width, Map<String, Integer> plants) {
        return strategy.plantPlants(length, width, plants);
    }
}
