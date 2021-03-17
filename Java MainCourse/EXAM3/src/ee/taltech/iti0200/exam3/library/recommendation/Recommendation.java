package ee.taltech.iti0200.exam3.library.recommendation;

import ee.taltech.iti0200.exam3.library.items.Item;

import java.util.Map;

public interface Recommendation {

    Map<Item, Integer> getRecommendedList(Map<Item, Integer> input);
}
