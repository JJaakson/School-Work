package ee.ttu.algoritmid.sports;
import java.util.*;

public class Sports {

    private HashMap<String, Integer> answers = new HashMap<>();
    private HashMap<String, Integer> people = new HashMap<>();

    void addAnswer(String name, String sport) {
        int x = people.size();
        people.putIfAbsent(name, 0);
        if (people.size() > x) {
            answers.merge(sport, 1, Integer::sum);
        }
        /*if (!people.containsKey(name)) {
            people.put(name, 0);
            if (!answers.containsKey(sport)) {
                answers.put(sport, 1);
            } else {
                answers.replace(sport, answers.get(sport) + 1);
            }
        }*/
    }

    int sportFanclubSize(String sport) {
        return answers.getOrDefault(sport, 0);
    }

    Optional<String> mostPopularSport() {
        if (!answers.isEmpty()) {
            String x = "";
            int q = 0;
            for (Map.Entry<String, Integer> entry : answers.entrySet()) {
                if (entry.getValue() > q) {
                    q = entry.getValue();
                    x = entry.getKey();
                }
            }
            return Optional.of(x);
        }
        return Optional.empty();
    }

    int numberOfAnswerers() {
        return people.size();
    }
}
