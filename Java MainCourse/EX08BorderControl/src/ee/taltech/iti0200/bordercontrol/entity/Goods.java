package ee.taltech.iti0200.bordercontrol.entity;

import ee.taltech.iti0200.bordercontrol.Validator;

public class Goods extends BorderEntity {

    private Long productId;

    public Goods(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return this.productId;
    }

    @Override
    public boolean accept(Validator validator) {
        return validator.visit(this);
    }
}
