package ee.taltech.iti0200.singleton;

public class Unicorn {
    private double hornLength;
    private String color;
    private String location;
    private Size size;

    enum Size { TINY(0), SMALL(1), MEDIUM(2), LARGE(3);

        private final int i;

        Size(int i) {
            this.i = i;
        }

        public int getI() {
            return i;
        }
    }

    public Unicorn(double hornLength, String color, String location, Size size) {
        this.hornLength = hornLength;
        this.color = color;
        this.location = location;
        this.size = size;
    }

    public double getHornLength() {
        return hornLength;
    }

    public String getColor() {
        return color;
    }

    public String getLocation() {
        return location;
    }

    public Size getSize() {
        return size;
    }
}
