package ee.taltech.iti0200.exam3.library.recommendation;

import ee.taltech.iti0200.exam3.library.items.Item;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Availability implements Recommendation {

    @Override
    public Map<Item, Integer> getRecommendedList(Map<Item, Integer> input) {
        return input.entrySet().stream().filter(x -> x.getValue() != 0)
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> x, LinkedHashMap::new));
    }
}
