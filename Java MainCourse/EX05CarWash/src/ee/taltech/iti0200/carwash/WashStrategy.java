package ee.taltech.iti0200.carwash;

import static java.util.Arrays.asList;
import java.util.List;
public abstract class WashStrategy {

    private final List<String> blacklistedClients = asList("Mait", "Mari", "Mati", "Kai");
    private int sessionDuration = 0;
    private int sessionPrice = 0;

    public final boolean isClientBlacklisted(String name) {
        return blacklistedClients.contains(name);
    }

    /**
     * Overriddesen by concretes wash strategy.
     */
    public abstract void wash(Car car, CarOwner owner);

    /**
     * Overridden by concrete dry strategy.
     */
    public abstract void dry(Car car, CarOwner owner);

    /**
     * Calculates wash and dry price based on wash strategy used.
     *
     * @return the wash and dry price
     */
    public abstract int getWashAndDryPrice();

    /**
     * Calculates wash price based on wash strategy used.
     *
     * @return the wash price
     */
    public abstract int getWashPrice();

    public void setSessionDuration(int duration) {
        this.sessionDuration = duration;
    }

    public int getSessionDuration() {
        return this.sessionDuration;
    }

    public void setSessionPrice(int price) {
        this.sessionPrice = price;
    }

    public int getSessionPrice() {
       return this.sessionPrice;
    }

}
