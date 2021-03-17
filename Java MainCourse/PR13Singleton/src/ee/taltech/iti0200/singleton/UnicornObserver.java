package ee.taltech.iti0200.singleton;

import java.util.Set;

public class UnicornObserver {

    private static final int MAX_SKILL = 16;
    private static final int CONTROL_NUMBER = 9999;
    private String name;
    private int skill;

    public UnicornObserver(String name, int skill) {
        this.name = name;
        this.skill = skill;
    }

    void observe(Unicorn unicorn) {
        UnicornRegistry registry = UnicornRegistry.getInstance();
        if (skill >= MAX_SKILL) {
            registry.registerUnicorn(unicorn, this);
        } else if (skill >= 10 && unicorn.getSize() != Unicorn.Size.TINY) {
            registry.registerUnicorn(unicorn, this);
        } else if (skill >= 4 && unicorn.getSize() != Unicorn.Size.TINY && unicorn.getSize() != Unicorn.Size.SMALL) {
            registry.registerUnicorn(unicorn, this);
        } else if (skill >= 1 && unicorn.getSize() == Unicorn.Size.LARGE) {
            registry.registerUnicorn(unicorn, this);
        }
    }

    public String brag() {
        UnicornRegistry registry = UnicornRegistry.getInstance();
        Set<Unicorn> unicornsObservers = registry.getUnicornsDiscoveredBy(this);
        int rating = CONTROL_NUMBER;
        Unicorn currentOne = null;
        for (Unicorn unicorn : unicornsObservers) {
            int helper = registry.getUnicornRarityIndex(unicorn);
            if (helper != 0 && helper < rating) {
                rating = helper;
                currentOne = unicorn;
            } else if (helper == 0) {
                return "Sadly I have not discovered any unicorns.";
            }
            if (rating == 1 && currentOne != null) {
                return "I discovered a " + currentOne.getColor() + " " + currentOne.getSize()
                        + " unicorn at " + currentOne.getLocation() + " and it is the rares unicorn.";
            }
        }
        if (rating != CONTROL_NUMBER && currentOne != null) {
            return "I discovered a " + currentOne.getColor() + " " + currentOne.getSize() + " unicorn at "
                    + currentOne.getLocation() + ".";
        }
        return null;
    }
}
