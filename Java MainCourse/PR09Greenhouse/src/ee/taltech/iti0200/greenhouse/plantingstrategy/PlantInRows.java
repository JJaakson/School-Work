package ee.taltech.iti0200.greenhouse.plantingstrategy;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class PlantInRows implements PlantingStrategy {

    @Override
    public String[][] plantPlants(int length, int width, Map<String, Integer> plants) {
        String[][] field = new String[length][width];
        Map<String, Integer> sortedPlants = plants.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        List<String> plantList = new LinkedList<>(sortedPlants.keySet());
        int counter = 0;
        int plant = plantList.size() - 1;
        for (int l = 0; l < length; l++) {
            for (int w = 0; w < width; w++) {
                if (sortedPlants.get(plantList.get(plant)) == counter) {
                    plant--;
                    counter = 0;
                }
                if (plant < plantList.size()) {
                    field[l][w] = plantList.get(plant);
                    counter++;
                }
            }
        }
        sortedPlants.clear();
        plantList.clear();
        return field;
    }
}
