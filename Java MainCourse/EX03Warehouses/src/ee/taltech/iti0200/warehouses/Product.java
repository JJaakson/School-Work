package ee.taltech.iti0200.warehouses;
import java.math.BigDecimal;
import java.util.Objects;
import java.math.RoundingMode;

public class Product {
    private String name;
    private BigDecimal weight;
    private BigDecimal grossPrice; // müügihind
    private BigDecimal netPrice; // omahind

    public Product(String name, BigDecimal weight, BigDecimal netPrice, BigDecimal grossPrice) {
        this.name = name;
        this.weight = weight;
        this.netPrice = netPrice;
        this.grossPrice = grossPrice;
    }

    public String getProductDescription() {
        return this.name + " " + this.grossPrice + " € " + this.weight + " kg";
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getWeight() {
        return this.weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getNetPrice() {
        return this.netPrice;
    }

    public void setNetPrice(BigDecimal netPrice) {
        this.netPrice = netPrice;
    }

    public BigDecimal getGrossPrice() {
        return this.grossPrice;
    }

    public void setGrossPrice(BigDecimal grossPrice) {
        this.grossPrice = grossPrice;
    }

    /**
     * Calculates the profitability percentage.
     *
     * @return the profitability percentage
     */
    public BigDecimal getProfitabilityPercentage() {
       return (grossPrice.subtract(netPrice)).divide(grossPrice, 4,
               RoundingMode.CEILING).multiply(BigDecimal.valueOf(100));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(name, product.name)
                && Objects.equals(weight, product.weight)
                && Objects.equals(netPrice, product.netPrice)
                && Objects.equals(grossPrice, product.grossPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weight, netPrice, grossPrice);
    }
}
