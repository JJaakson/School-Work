package ee.taltech.iti0200.greenhouse.plantingstrategy;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Collections;
import java.util.stream.Collectors;

public class PlantInTurns implements PlantingStrategy {

    @Override
    public String[][] plantPlants(int length, int width, Map<String, Integer> plants) {
        String[][] field = new String[length][width];
        Map<String, Integer> sortedPlants = plants.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        List<String> plantList = new LinkedList<>(sortedPlants.keySet());
        Map<String, Integer> plantCounter = new HashMap<>();
        for (String plant : plantList) {
            plantCounter.put(plant, 0);
        }
        Collections.reverse(plantList);
        int plant = 0;
        for (int l = 0; l < length; l++) {
            for (int w = 0; w < width; w++) {
                if (plant >= plantList.size()) {
                    plant = 0;
                }
                if (sortedPlants.get(plantList.get(plant)) <= plantCounter.get(plantList.get(plant))) {
                    plantList.remove(plantList.get(plant));
                    plant = 0;
                }
                field[l][w] = plantList.get(plant);
                plantCounter.replace(plantList.get(plant), plantCounter.get(plantList.get(plant)) + 1);
                plant++;
            }
        }
        sortedPlants.clear();
        plantList.clear();
        plantCounter.clear();
        return field;
    }
}
