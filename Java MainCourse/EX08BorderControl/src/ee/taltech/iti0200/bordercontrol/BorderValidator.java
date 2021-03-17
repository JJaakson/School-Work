package ee.taltech.iti0200.bordercontrol;

import ee.taltech.iti0200.bordercontrol.database.Database;
import ee.taltech.iti0200.bordercontrol.entity.Goods;
import ee.taltech.iti0200.bordercontrol.entity.Person;
import ee.taltech.iti0200.bordercontrol.entity.Vehicle;

public class BorderValidator implements Validator {

    private Database database;

    public BorderValidator(Database database) {
        this.database = database;
    }

    @Override
    public boolean visit(Goods goods) {
        return !database.getIllegalGoods().contains(goods.getProductId());
    }

    @Override
    public boolean visit(Person person) {
        if (database.getTerrorists().contains(person.getName())
                || database.getTerrorists().contains(person.getIdCode())) {
            return false;
        } else return !database.getMissingPersons().contains(person.getName())
                && !database.getMissingPersons().contains(person.getIdCode());
    }

    @Override
    public boolean visit(Vehicle vehicle) {
        return !database.getStolenVehicles().contains(vehicle.getVin().toString());
    }
}
