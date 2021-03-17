package ee.taltech.iti0200.exam3.library.items;

public class Item {

    private final String name;
    private final ItemType type;
    private int popularity;

    public Item(String name, ItemType type) {
        this.name = name;
        this.type = type;
        this.popularity = 0;
    }

    public String getName() {
        return name;
    }

    public ItemType getType() {
        return type;
    }

    public int getPopularity() {
        return popularity;
    }

    public void addPopularity(int popularity) {
        this.popularity += popularity;
    }

    @Override
    public String toString() {
        return type + "-> name='" + name + "\' quantity";
    }
}
