package ee.taltech.iti0200.bordercontrol.entity;

import ee.taltech.iti0200.bordercontrol.Validator;

public class Vehicle extends BorderEntity {

    private String vin;

    public Vehicle(String vin) {
        this.vin = vin;
    }

    public Long getVin() {
        return Long.valueOf(this.vin);
    }

    @Override
    public boolean accept(Validator validator) {
        return validator.visit(this);
    }
}
