package ee.taltech.iti0200.carwash;

public class PremiumWash extends WashStrategy {

    private static final int WASH_FACTOR = 100;
    private static final int WASH_DURATION = 20;
    private static final int DRY_DURATION = 10;
    private static final int WASH_COST = 60;
    private static final int DRY_COST = 10;

    @Override
    public void wash(Car car, CarOwner owner) {
        if (car.getDirtiness() - WASH_FACTOR <= 0) {
            car.setDirtiness(0);
        } else {
            car.setDirtiness(car.getDirtiness() - WASH_FACTOR);
        }
        super.setSessionDuration(WASH_DURATION);
        owner.setBalance(owner.getBalance() - WASH_COST);
    }

    @Override
    public void dry(Car car, CarOwner owner) {
        owner.setBalance(owner.getBalance() - DRY_COST);
        super.setSessionDuration(getSessionDuration() + DRY_DURATION);
    }

    @Override
    public int getWashAndDryPrice() {
        return WASH_COST + DRY_COST;
    }

    @Override
    public int getWashPrice() {
        return WASH_COST;
    }
}
