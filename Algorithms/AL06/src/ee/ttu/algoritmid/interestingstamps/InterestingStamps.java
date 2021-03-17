package ee.ttu.algoritmid.interestingstamps;
import java.util.*;
import java.util.stream.Collectors;

public class InterestingStamps {

    //PUBLIC STATIC !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public static List<Integer> findStamps(int sum, List<Integer> stampOptions) throws IllegalArgumentException {
        if (sum == 0) {
            return new ArrayList<>();
        }
        if (stampOptions == null || stampOptions.isEmpty()) {
            throw new IllegalArgumentException();
        }
        stampOptions.sort(Collections.reverseOrder());
        Map<Integer, List<Integer>> possibleSolutions = new HashMap<>();
        for (int i = 0; i < stampOptions.size(); i++) {
            possibleSolutions.put(stampOptions.get(i), getSolution(sum, stampOptions.subList(i, stampOptions.size())));
        }
        List<Integer> minValue = null;
        for (Map.Entry<Integer, List<Integer>> entry : possibleSolutions.entrySet()) {
            if (minValue == null || entry.getValue() != null && entry.getValue().size() < minValue.size()) {
                minValue = entry.getValue();
            }
        }
        return minValue;

    }

    private static List<Integer> getSolution(Integer sum, List<Integer> stampOptions) {
        List<Integer> result = new ArrayList<>();
        int currentSum = 0;
        int helperSum = sum;
        while (currentSum != sum) {
            Map<Integer, Integer> possiblemMarks = new HashMap<>();
            for (Integer stamp : stampOptions) {
                if (stamp > 0) {
                    int count = helperSum / stamp;
                    if (count != 0) {
                        possiblemMarks.put(stamp, count);
                    }
                }
            }
            if (possiblemMarks.isEmpty()) {
                return null;
            }
            Map<Integer, Integer> sortedMarks = possiblemMarks.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            Map.Entry<Integer, Integer> bestChoice = null;
            for (Map.Entry<Integer, Integer> entry : sortedMarks.entrySet()) {
                if (bestChoice == null || (helperSum - entry.getKey() * entry.getValue())
                        < (helperSum - bestChoice.getKey() * bestChoice.getValue())
                        && entry.getValue() < bestChoice.getValue()) {
                    bestChoice = entry;
                }
            }
            /*int smallestValue = Collections.min(possiblemMarks.values());
            Map<Integer, Integer> minValues = possiblemMarks.entrySet().stream()
                    .filter(x -> x.getValue().equals(smallestValue))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            for (Map.Entry<Integer, Integer> entry : minValues.entrySet()) {
                if (bestChoice == null || entry.getKey()
                        .compareTo(bestChoice.getKey())
                        > 0) {
                    for (Map.Entry<Integer, Integer> minValuesEntry : possiblemMarks.entrySet()) {
                        if ((helperSum - entry.getKey() * entry.getValue()) % minValuesEntry.getKey() == 0
                                || (helperSum - entry.getKey() * entry.getValue()) == 0) {
                            bestChoice = entry;
                            break;
                        }
                    }
                }
                if (bestChoice == null) {
                    stampOptions.remove(entry.getKey());
                }
            }*/
            if (bestChoice != null) {
                for (int i = 0; i < bestChoice.getValue(); i++) {
                    result.add(bestChoice.getKey());
                    currentSum += bestChoice.getKey();
                    helperSum -= bestChoice.getKey();
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<Integer> result = InterestingStamps.findStamps(100, Arrays.asList(1, 10, 24, 30, 33, 36));
        assert result.stream().mapToInt(Integer::intValue).sum() == 100;
        System.out.println(result);
    }
}