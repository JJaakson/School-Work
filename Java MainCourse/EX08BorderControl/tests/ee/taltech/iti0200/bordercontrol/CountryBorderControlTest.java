package ee.taltech.iti0200.bordercontrol;

import ee.taltech.iti0200.bordercontrol.database.DatabaseImpl;
import ee.taltech.iti0200.bordercontrol.entity.BorderEntity;
import ee.taltech.iti0200.bordercontrol.entity.Goods;
import ee.taltech.iti0200.bordercontrol.entity.Person;
import ee.taltech.iti0200.bordercontrol.entity.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountryBorderControlTest {

    private static final long ALLOWED = 1111L;
    private static final long NOT_ALLOWED = 2222L;

    List<BorderEntity> crossers = new LinkedList<>();
    List<String> terrorists = new LinkedList<>();
    List<String> missingPersons = new LinkedList<>();
    List<String> stolenVehicles = new LinkedList<>();
    List<Long> illegalGoods = new LinkedList<>();
    CountryBorderControl control;
    BorderValidator validator;
    DatabaseImpl database;

    @BeforeEach
    void setUp() {
        crossers.add(new Person("James", "007"));
        crossers.add(new Person("Blond", "007.5"));
        missingPersons.add("Blond");
        missingPersons.add("007.5");
        crossers.add(new Person("Villain", "001"));
        terrorists.add("Villain");
        terrorists.add("001");

        crossers.add(new Vehicle("1234"));
        crossers.add(new Vehicle("4321"));
        stolenVehicles.add("4321");

        crossers.add(new Goods(ALLOWED));
        crossers.add(new Goods(NOT_ALLOWED));
        illegalGoods.add(NOT_ALLOWED);

        database = new DatabaseImpl();
        database.setTerrorists(terrorists);
        database.setMissingPersons(missingPersons);
        database.setIllegalGoods(illegalGoods);
        database.setStolenVehicles(stolenVehicles);

        validator = new BorderValidator(database);
        control = new CountryBorderControl("Secret Island", "Tunnel", validator);
    }

    @Test
    void processBorderCrossers() {
        assertEquals(3, control.processBorderCrossers(crossers).size());
    }

    @Test
    void processBorderCrossersParallel() {
        assertEquals(3, control.processBorderCrossersParallel(crossers).size());
    }
}
