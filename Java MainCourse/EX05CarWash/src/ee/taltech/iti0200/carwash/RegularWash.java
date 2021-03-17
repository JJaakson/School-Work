package ee.taltech.iti0200.carwash;

public class RegularWash extends WashStrategy {

    private static final int WASH_FACTOR = 70;
    private static final int WASH_DURATION = 15;
    private static final int DRY_DURATION = 5;
    private static final int WASH_COST = 30;
    private static final int DRY_COST = 5;

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
        super.setSessionDuration(WASH_DURATION + DRY_DURATION);
        owner.setBalance(owner.getBalance() - DRY_COST);
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
