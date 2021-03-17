package ee.taltech.iti0200.bordercontrol;

import ee.taltech.iti0200.bordercontrol.entity.Goods;
import ee.taltech.iti0200.bordercontrol.entity.Person;
import ee.taltech.iti0200.bordercontrol.entity.Vehicle;

public interface Validator {

    boolean visit(Goods goods);

    boolean visit(Person person);

    boolean visit(Vehicle vehicle);
}
