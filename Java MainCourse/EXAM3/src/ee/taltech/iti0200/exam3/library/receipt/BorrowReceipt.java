package ee.taltech.iti0200.exam3.library.receipt;

import ee.taltech.iti0200.exam3.library.items.Item;
import ee.taltech.iti0200.exam3.library.person.Person;

import java.util.Date;
import java.util.Optional;

public class BorrowReceipt {

    private final Date startDate;
    private final Optional<Date> endDate;
    private final Person person;
    private final Item item;
    private final int quantity;
    private boolean beenReturned = false;

    public BorrowReceipt(Date startDate, Optional<Date> endDate, Person person, Item item, int quantity) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.person = person;
        this.item = item;
        this.quantity = quantity;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Optional<Date> getEndDate() {
        return endDate;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public Person getPerson() {
        return person;
    }

    public boolean isBeenReturned() {
        return beenReturned;
    }

    public void setBeenReturned(boolean hasBeenReturned) {
        this.beenReturned = hasBeenReturned;
    }
}
