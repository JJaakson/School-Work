package ee.taltech.iti0200.bordercontrol.database;

import java.util.List;

public interface Database {

    List<String> getStolenVehicles();

    List<String> getMissingPersons();

    List<String> getTerrorists();

    List<Long> getIllegalGoods();

    void setStolenVehicles(List<String> stolenVehicles);

    void setMissingPersons(List<String> missingPersons);

    void setTerrorists(List<String> terrorists);

    void setIllegalGoods(List<Long> illegalGoods);
}
