package ee.taltech.iti0200.steakhouse.steak;

public enum SteakType {

    FILET_MIGNON(150),
    RIB_EYE_STEAK(300),
    NEW_YORK_STRIP(350);

    private final int weigth;

    SteakType(int i) {
        this.weigth = i;
    }

    public int getWeigth() {
        return this.weigth;
    }
}
