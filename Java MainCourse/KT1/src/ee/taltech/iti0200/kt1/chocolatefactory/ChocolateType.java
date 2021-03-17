package ee.taltech.iti0200.kt1.chocolatefactory;

public class ChocolateType {

    private String stringForm;
    private int price;

    public ChocolateType(int price, String stringForm) {
        this.price = price;
        this.stringForm = stringForm;
    }

    public String getStringForm() {
        return stringForm;
    }

    public int getPricePerPiece() {
        return price;
    }
}
