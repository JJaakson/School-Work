package ee.taltech.iti0200.carwash;

public class CheapWash extends WashStrategy {

    private static final int WASH_FACTOR = 40;
    private static final int WASH_DURATION = 10;
    private static final int DRY_DURATION = 1;
    private static final int WASH_COST = 10;
    private static final int DRY_COST = 2;

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
