package ee.taltech.iti0200.exam3.library.recommendation;

import ee.taltech.iti0200.exam3.library.items.Item;

import java.util.*;

public class Newest implements Recommendation {
    @Override
    public Map<Item, Integer> getRecommendedList(Map<Item, Integer> input) {
        List<Item> items = new ArrayList<>(input.keySet());
        Collections.reverse(items);
        Map<Item, Integer> result = new LinkedHashMap<>();
        int counter = 0;
        for (Item item : items) {
            if (counter < 10) {
                result.put(item, input.get(item));
            } else {
                break;
            }
            counter++;
        }
        return result;
    }
}
