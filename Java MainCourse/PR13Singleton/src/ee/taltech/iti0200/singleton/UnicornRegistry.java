package ee.taltech.iti0200.singleton;

import java.util.Set;
import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;

public class UnicornRegistry {

    private static UnicornRegistry instance = null;
    private Set<Unicorn> unicorns = new LinkedHashSet<>();
    private Map<UnicornObserver, Set<Unicorn>> observerUnicorns = new HashMap<>();

    public static UnicornRegistry getInstance() {
        if (instance == null) {
            instance = new UnicornRegistry();
        }
        return instance;
    }

    public void registerUnicorn(Unicorn unicorn, UnicornObserver discoverer) {
        if (!unicorns.contains(unicorn)) {
            unicorns.add(unicorn);
        }
        if (observerUnicorns.keySet().contains(discoverer)) {
            Set<Unicorn> discoverersUnicorns = observerUnicorns.get(discoverer);
            discoverersUnicorns.add(unicorn);
            observerUnicorns.replace(discoverer, discoverersUnicorns);
        } else {
            Set<Unicorn> discoverersUnicorns = new HashSet<>();
            discoverersUnicorns.add(unicorn);
            observerUnicorns.put(discoverer, discoverersUnicorns);
        }
    }

    public Set<Unicorn> getAllDiscoveredUnicorns() {
        return unicorns;
    }

    public int getUnicornRarityIndex(Unicorn unicorn) {
        if (!unicorns.contains(unicorn)) {
            return 0;
        }
        Set<Unicorn> rarityLeaderboard = new LinkedHashSet<>();
        int index = 0;
        int indexX = 0;
        int indexY = 0;
        for (Unicorn x : unicorns) {
            indexX++;
            for (Unicorn y : unicorns) {
                indexY++;
                if (x.getLocation().compareTo(y.getLocation()) > 0) {
                    rarityLeaderboard.add(y);
                    rarityLeaderboard.add(x);
                } else if  (x.getLocation().compareTo(y.getLocation()) == 0) {
                    if (x.getColor().compareTo(y.getColor()) > 0) {
                        rarityLeaderboard.add(y);
                        rarityLeaderboard.add(x);
                    } else if (x.getColor().compareTo(y.getColor()) == 0) {
                        if (x.getHornLength() > y.getHornLength()) {
                            rarityLeaderboard.add(x);
                            rarityLeaderboard.add(y);
                        } else if (x.getHornLength() == y.getHornLength()) {
                            if (x.getSize().getI() < y.getSize().getI()) {
                                rarityLeaderboard.add(x);
                                rarityLeaderboard.add(y);
                            } else if (x.getSize().getI() == y.getSize().getI()) {
                                if (indexX > indexY) {
                                    rarityLeaderboard.add(x);
                                    rarityLeaderboard.add(y);
                                }
                            }
                        }
                    }
                }
            }
            indexY = 0;
        }
        for (Unicorn k : rarityLeaderboard) {
            index++;
            if (k == unicorn) {
                return index;
            }
        }
        return 0;
    }

    public Set<Unicorn> getUnicornsDiscoveredBy(UnicornObserver observer) {
        if (observerUnicorns.containsKey(observer)) {
            return observerUnicorns.get(observer);
        }
        return null;
    }

    public void forgetAllUnicorns() {
        unicorns = new HashSet<>();
        observerUnicorns = new HashMap<>();
    }
}
