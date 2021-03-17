package ee.ttu.algoritmid.bond;

import java.util.HashMap;
import java.util.Map;

public class DisjointSubsets {

    private static Map<String, String> parentMap = new HashMap<>();
    private static Map<String, Integer> ranksMap = new HashMap<>();

    public String find(String element) throws IllegalArgumentException {
        if (element == null || element.equals("") || !parentMap.containsKey(element)) {
            throw new IllegalArgumentException();
        }
        String parent = parentMap.get(element);
        if (parent.equals(element)) {
            return element;
        } else {
            return find(parent);
        }
        // should throw IllegalArgumentException if the element is not present
    }

    // should throw IllegalArgumentException if any of elements is not present
    public void union(String element1, String element2) throws IllegalArgumentException {
        if (!parentMap.containsKey(element1) || !parentMap.containsKey(element2)) {
            throw new IllegalArgumentException();
        }
        while (!parentMap.get(element1).equals(element1)) {
            element1 = parentMap.get(element1);
        }
        while (!parentMap.get(element2).equals(element2)) {
            element2 = parentMap.get(element2);
        }
        int first = ranksMap.get(element1);
        int second = ranksMap.get(element2);
        if (first > second) {
            parentMap.put(element2, element1);
            update(element2);
        } else if (second > first) {
            parentMap.put(element1, element2);
            update(element1);
        } else {
            parentMap.put(element2, element1);
            update(element2);
        }
        // should throw IllegalArgumentException if any of elements is not present
    }

    private void update(String element) {
        int depth = ranksMap.get(element);
        String parent = parentMap.get(element);
        int parentDepth = ranksMap.get(parent);
        if (!(depth<parentDepth || parent.equals(element))) {
            ranksMap.put(parent, depth + 1);
            update(parent);
        }
    }

    public void addSubset(String element) throws IllegalArgumentException {
        if (parentMap.containsKey(element)) {
            throw new IllegalArgumentException();
        }
        parentMap.put(element, element);
        ranksMap.put(element, 1);
        // should throw IllegalArgumentException if the element is already present
    }

    public static void main(String[] args) throws Exception{


        DisjointSubsets disjointSubsets = new DisjointSubsets();
        disjointSubsets.addSubset("A"); // 1
        disjointSubsets.addSubset("B"); // 2
        disjointSubsets.addSubset("C"); // 3
        disjointSubsets.addSubset("D"); // 4
        disjointSubsets.addSubset("E"); // 5
        disjointSubsets.addSubset("F"); // 6
        disjointSubsets.addSubset("G"); // 7
        disjointSubsets.union("B", "A");
        disjointSubsets.union("D", "C");
        disjointSubsets.union("E", "C");
        disjointSubsets.union("E", "F");
        disjointSubsets.union("A", "G");

        // A, A, C, C, C, C, A

        System.out.println((parentMap));
    }

}