package ee.taltech.iti0200.exam3.library.person;

import ee.taltech.iti0200.exam3.library.Library;
import ee.taltech.iti0200.exam3.library.fine.Fine;
import ee.taltech.iti0200.exam3.library.items.Item;
import ee.taltech.iti0200.exam3.library.receipt.BorrowReceipt;

import java.util.*;

public class Person {

    private final String name;
    private List<Library> memberships = new ArrayList<>();
    private Map<Item, Integer> borrowedItems = new HashMap<>();
    private Map<Library, List<BorrowReceipt>> receipts = new HashMap<>();
    private Map<Library, List<Fine>> fines = new HashMap<>();

    public Person(String name) {
        this.name = name;
    }

    public void addMembership(Library library) {
        if (!memberships.contains(library)) {
            memberships.add(library);
        }
    }

    public void removeMembership(Library library) {
        memberships.remove(library);
    }

    public Map<Item, Integer> getBorrowedItems() {
        return borrowedItems;
    }

    public void addItem(Item item, int quantity) {
        if (borrowedItems.containsKey(item)) {
            borrowedItems.replace(item, borrowedItems.get(item) + quantity);
        } else {
            borrowedItems.put(item, quantity);
        }
    }

    public void removeItem(Item item, int quantity) {
        if (borrowedItems.containsKey(item)) {
            borrowedItems.replace(item, borrowedItems.get(item) - quantity);
        }
        Map<Item, Integer> newItems = new HashMap<>();
        for (Map.Entry<Item, Integer> entry : borrowedItems.entrySet()) {
            if (entry.getValue() > 0) {
                newItems.put(entry.getKey(), entry.getValue());
            }
        }
        borrowedItems = newItems;
    }

    public String getName() {
        return name;
    }

    public List<Library> getMemberships() {
        return memberships;
    }

    public Map<Library, List<BorrowReceipt>> getReceipts() {
        return receipts;
    }

    public void addReceipt(Library library, BorrowReceipt receipt) {
        if (receipts.keySet().contains(library)) {
            receipts.get(library).add(receipt);
        } else {
            List<BorrowReceipt> newReceipt = new ArrayList<>();
            newReceipt.add(receipt);
            receipts.put(library, newReceipt);
        }
    }

    public Map<Library, List<Fine>> getFines() {
        return fines;
    }

    public void addFine(Library library, Fine fine) {
        if (fines.keySet().contains(library)) {
            fines.get(library).add(fine);
        } else {
            List<Fine> newFine = new ArrayList<>();
            newFine.add(fine);
            fines.put(library, newFine);
        }
    }
}
