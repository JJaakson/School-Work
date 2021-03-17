package ee.taltech.iti0200.bordercontrol.entity;

import ee.taltech.iti0200.bordercontrol.Validator;

public abstract class BorderEntity {

    private Long borderCrossingId;

    public long getBorderCrossingId() {
        return this.borderCrossingId;
    }

    public void setBorderCrossingId(Long borderCrossingId) {
        this.borderCrossingId = borderCrossingId;
    }

    public abstract boolean accept(Validator validator);
}
