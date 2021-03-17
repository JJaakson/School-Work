package ee.taltech.iti0200.exam3.library.fine;

import ee.taltech.iti0200.exam3.library.person.Person;
import ee.taltech.iti0200.exam3.library.receipt.BorrowReceipt;

import java.math.BigDecimal;

public class Fine {

    private final Person member;
    private final BorrowReceipt receipt;
    private BigDecimal fineCost;

    public Fine(Person member, BorrowReceipt receipt, BigDecimal fineCost) {
        this.member = member;
        this.receipt = receipt;
        this.fineCost = fineCost;
    }

    public Person getMember() {
        return member;
    }

    public BorrowReceipt getReceipt() {
        return receipt;
    }

    public BigDecimal getFineCost() {
        return fineCost;
    }
}
