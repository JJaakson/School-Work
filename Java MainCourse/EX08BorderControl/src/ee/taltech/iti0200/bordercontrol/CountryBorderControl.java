package ee.taltech.iti0200.bordercontrol;

import ee.taltech.iti0200.bordercontrol.database.Database;
import ee.taltech.iti0200.bordercontrol.database.DatabaseImpl;
import ee.taltech.iti0200.bordercontrol.entity.BorderEntity;

import java.util.List;
import java.util.stream.Collectors;

public class CountryBorderControl {

    private Validator validator;
    private String borderName;
    private String country;
    private Database database;

    public CountryBorderControl(String country, String borderName, Validator validator) {
        this.country = country;
        this.borderName = borderName;
        this.validator = validator;
        this.database = new DatabaseImpl();
    }

    public List<BorderEntity> processBorderCrossers(List<BorderEntity> crossers) {
        return crossers.stream().filter(crosser -> crosser.accept(validator)).collect(Collectors.toList());
    }

    public List<BorderEntity> processBorderCrossersParallel(List<BorderEntity> crossers) {
        return crossers.parallelStream().filter(crosser -> crosser.accept(validator)).collect(Collectors.toList());
    }
}
