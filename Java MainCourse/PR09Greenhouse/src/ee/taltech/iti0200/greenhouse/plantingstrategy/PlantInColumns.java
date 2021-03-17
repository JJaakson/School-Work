package ee.taltech.iti0200.greenhouse.plantingstrategy;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlantInColumns implements PlantingStrategy {

    @Override
    public String[][] plantPlants(int length, int width, Map<String, Integer> plants) {
        String[][] field = new String[length][width];
        Map<String, Integer> sortedPlants = plants.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        List<String> plantList = new LinkedList<>(sortedPlants.keySet());
        int counter = 0;
        int plant = 0;
        for (int w = 0; w < width; w++) {
            for (int l = 0; l < length; l++) {
                if (sortedPlants.get(plantList.get(plant)) <= counter) {
                    plant++;
                    counter = 0;
                }
                field[l][w] = plantList.get(plant);
                counter++;
            }
        }
        plantList.clear();
        sortedPlants.clear();
        return field;    }
}
