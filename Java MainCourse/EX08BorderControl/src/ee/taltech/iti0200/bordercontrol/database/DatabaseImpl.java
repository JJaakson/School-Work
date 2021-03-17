package ee.taltech.iti0200.bordercontrol.database;

import java.util.LinkedList;
import java.util.List;

public class DatabaseImpl implements Database {

    private List<String> stolenVehicles = new LinkedList<>();
    private List<String> missingPersons = new LinkedList<>();
    private List<String> terrorists = new LinkedList<>();
    private List<Long> illegalGoods = new LinkedList<>();

    @Override
    public List<String> getStolenVehicles() {
        return this.stolenVehicles;
    }

    @Override
    public List<String> getMissingPersons() {
        return this.missingPersons;
    }

    @Override
    public List<String> getTerrorists() {
        return this.terrorists;
    }

    @Override
    public List<Long> getIllegalGoods() {
        return this.illegalGoods;
    }

    @Override
    public void setStolenVehicles(List<String> stolenVehicles) {
        this.stolenVehicles = stolenVehicles;
    }

    @Override
    public void setMissingPersons(List<String> missingPersons) {
        this.missingPersons = missingPersons;
    }

    @Override
    public void setTerrorists(List<String> terrorists) {
        this.terrorists = terrorists;
    }

    @Override
    public void setIllegalGoods(List<Long> illegalGoods) {
        this.illegalGoods = illegalGoods;
    }
}
