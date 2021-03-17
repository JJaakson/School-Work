package ee.taltech.iti0200.steakhouse.steak;

public class Steak {

    private String cookName;
    private double weight;

    public Steak(String cookName, double weight) {
        this.cookName = cookName;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getCookName() {
        return cookName;
    }

    public void setCookName(String cookName) {
        this.cookName = cookName;
    }
}
