package ee.ttu.algoritmid.bond;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AL07 {

    public enum Network {
        FRIENDLY, UNFRIENDLY, UNKNOWN;
    }

    Map<Network, Set<String>> networkMap = new HashMap<>();

    private DisjointSubsets disjointSubsets = new DisjointSubsets();

    public AL07() {
        // don't remove
    }

    public DisjointSubsets getDisjointSubsets() {
        return disjointSubsets;
    }

    public void talkedToEachOther(String name1, String name2) {
        disjointSubsets.union(name1, name2);
        for (Map.Entry<Network, Set<String>> group : networkMap.entrySet()) {
            if (group.getValue().contains(name1)) {
                Set<String> group1 = group.getValue();
                group1.add(name2);
                networkMap.replace(group.getKey(), group1);
            } else if (group.getValue().contains(name2)) {
                Set<String> group2 = group.getValue();
                group2.remove(name2);
                networkMap.replace(group.getKey(), group2);
            }
        }

    }

    public void addPerson(String name) {
        disjointSubsets.addSubset(name);
        Set<String> newGroup = new HashSet<>();
        if (networkMap.containsKey(Network.UNKNOWN)) {
            newGroup = networkMap.get(Network.UNKNOWN);
            newGroup.add(name);
            networkMap.replace(Network.UNKNOWN, newGroup);
        } else {
            newGroup.add(name);
            networkMap.put(Network.UNKNOWN, newGroup);
        }
    }

    public void friendly(String name) {
        disjointSubsets.addSubset(name);
        Set<String> newGroup = new HashSet<>();
        if (networkMap.containsKey(Network.FRIENDLY)) {
            newGroup = networkMap.get(Network.FRIENDLY);
            newGroup.add(name);
            networkMap.replace(Network.FRIENDLY, newGroup);
        } else {
            newGroup.add(name);
            networkMap.put(Network.FRIENDLY, newGroup);
        }
    }

    public void unfriendly(String name) {
        disjointSubsets.addSubset(name);
        Set<String> newGroup = new HashSet<>();
        if (networkMap.containsKey(Network.UNFRIENDLY)) {
            newGroup = networkMap.get(Network.UNFRIENDLY);
            newGroup.add(name);
            networkMap.replace(Network.UNFRIENDLY, newGroup);
        } else {
            newGroup.add(name);
            networkMap.put(Network.UNFRIENDLY, newGroup);
        }
    }

    public Network memberOfNetwork(String name) {
        for (Map.Entry<AL07.Network, Set<String>> group : networkMap.entrySet()) {
            if (group.getValue().contains(name)) {
                return group.getKey();
            }
        }
        return null;
    }

}