package ee.taltech.iti0200.bordercontrol.entity;

import ee.taltech.iti0200.bordercontrol.Validator;

public class Person extends BorderEntity {

    private String name;
    private String idCode;

    public Person(String name, String idCode) {
        this.name = name;
        this.idCode = idCode;
    }

    public String getName() {
        return this.name;
    }

    public String getIdCode() {
        return this.idCode;
    }

    @Override
    public boolean accept(Validator validator) {
        return validator.visit(this);
    }
}
