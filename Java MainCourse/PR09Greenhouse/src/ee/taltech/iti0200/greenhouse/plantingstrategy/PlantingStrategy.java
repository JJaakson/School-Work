package ee.taltech.iti0200.greenhouse.plantingstrategy;

import java.util.Map;

public interface PlantingStrategy {

    String[][] plantPlants(int length, int width, Map<String, Integer> plants);
}
