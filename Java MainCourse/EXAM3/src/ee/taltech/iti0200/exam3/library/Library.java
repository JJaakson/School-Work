package ee.taltech.iti0200.exam3.library;

import ee.taltech.iti0200.exam3.library.fine.Fine;
import ee.taltech.iti0200.exam3.library.items.Item;
import ee.taltech.iti0200.exam3.library.items.ItemType;
import ee.taltech.iti0200.exam3.library.person.Person;
import ee.taltech.iti0200.exam3.library.receipt.BorrowReceipt;
import ee.taltech.iti0200.exam3.library.recommendation.Recommendation;

import java.math.BigDecimal;
import java.util.*;

public class Library {

    private static final double FINE_PER_DAY = 0.2;

    private final String name;
    private Map<Item, Integer> mapOfInventory = new LinkedHashMap<>();
    private Map<Person, List<BorrowReceipt>> mapOfReceipts = new HashMap<>();
    private Map<Person, List<BorrowReceipt>> mapOfBookings = new HashMap<>();
    private Map<Person, List<Fine>> mapOfFines = new HashMap<>();
    private Set<Person> setOfMembers = new HashSet<>();

    public Library(String name) {
        this.name = name;
    }

    public void addInventory(Map<Item, Integer> newItems) {
        for (Map.Entry<Item, Integer> entry : newItems.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            if (mapOfInventory.containsKey(item)) {
                mapOfInventory.replace(item, mapOfInventory.get(item) + quantity);
            } else {
                mapOfInventory.put(item, quantity);
            }
        }
    }

    public void registerAMember(Person newMember) {
        setOfMembers.add(newMember);
        newMember.addMembership(this);
    }

    public boolean reserveAnItem(Person member, String itemName, int quantity, ItemType itemType) {
        if (!isMember(member)) {
            return false;
        }
        for (Person person : mapOfReceipts.keySet()) {
            for (BorrowReceipt receipt : mapOfReceipts.get(person)) {
                Item borrowedItem = receipt.getItem();
                int borrowedQuantity = receipt.getQuantity();
                ItemType borrowedType = borrowedItem.getType();
                Optional<Date> endDate = receipt.getEndDate();
                if (borrowedItem.getName().toLowerCase().equals(itemName.toLowerCase())
                        && borrowedType == itemType
                        && borrowedQuantity + mapOfInventory.get(borrowedItem) >= quantity) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(endDate.get());
                    c.add(Calendar.DATE, 1);
                    BorrowReceipt resultReceipt = createReceipt(member, borrowedItem, quantity, c.getTime());
                    int quantityReserved;
                    if (quantity - borrowedQuantity > 0) {
                        quantityReserved = mapOfInventory.get(borrowedItem) - (quantity - borrowedQuantity);
                    } else {
                        quantityReserved = 0;
                    }
                    mapOfInventory.replace(borrowedItem,
                            quantityReserved);
                    if (mapOfBookings.containsKey(member)) {
                        mapOfBookings.get(member).add(resultReceipt);
                    } else {
                        List<BorrowReceipt> newReceipts = new ArrayList<>();
                        newReceipts.add(resultReceipt);
                        mapOfBookings.put(member, newReceipts);
                    }
                    member.addReceipt(this, resultReceipt);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean borrowAnItem(Person member, String itemName, int quantity, ItemType itemType, Date borrowDate) {
        if (!isMember(member)) {
            return false;
        }
        for (Map.Entry<Item, Integer> entry : mapOfInventory.entrySet()) {
            Item item = entry.getKey();
            int availableQuantity = entry.getValue();
            if (item.getName().toLowerCase().equals(itemName.toLowerCase())
                    && item.getType() == itemType && availableQuantity >= quantity
                    || item.getName().toLowerCase().equals(itemName.toLowerCase())
                    && item.getType() == ItemType.SOFTWARE) {
                if (itemType == ItemType.SOFTWARE && !member.getBorrowedItems().containsKey(item)) {
                    member.addItem(item, 1);
                    item.addPopularity(10);
                    BorrowReceipt receipt = createReceipt(member, item, quantity, borrowDate);
                    member.addReceipt(this, receipt);
                    if (mapOfReceipts.containsKey(member)) {
                        mapOfReceipts.get(member).add(receipt);
                    } else {
                        List<BorrowReceipt> newReceipt = new ArrayList<>();
                        newReceipt.add(receipt);
                        mapOfReceipts.put(member, newReceipt);
                    }
                    return true;
                } else if (itemType != ItemType.SOFTWARE) {
                    member.addItem(item, quantity);
                    item.addPopularity(10 * quantity);
                    mapOfInventory.replace(item, availableQuantity - quantity);
                    BorrowReceipt receipt = createReceipt(member, item, quantity, borrowDate);
                    member.addReceipt(this, receipt);
                    if (mapOfReceipts.containsKey(member)) {
                        mapOfReceipts.get(member).add(receipt);
                    } else {
                        List<BorrowReceipt> newReceipt = new ArrayList<>();
                        newReceipt.add(receipt);
                        mapOfReceipts.put(member, newReceipt);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public BorrowReceipt createReceipt(Person member, Item item, int quantity, Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 10);
        Date endDate = c.getTime();
        if (item.getType() == ItemType.SOFTWARE) {
            return new BorrowReceipt(date, Optional.empty(), member, item, quantity);
        } else {
            return new BorrowReceipt(date, Optional.of(endDate), member, item, quantity);
        }

    }

    public boolean isMember(Person person) {
        return setOfMembers.contains(person);
    }

    public String searchForAnItem(String itemName) {
        Map<Item, Integer> foundItems = new HashMap<>();
        for (Item item : mapOfInventory.keySet()) {
            if (item.getName().toLowerCase().equals(itemName.toLowerCase())) {
                foundItems.put(item, mapOfInventory.get(item));
            }
        }
        if (foundItems.size() != 0) {
            return foundItems.toString();
        }
        return itemName + " does not exist in this library.";
    }

    public List<BorrowReceipt> getBorrowedItemsByDate(Date date) {
        List<BorrowReceipt> receipts = new LinkedList<>();
        for (Person person : mapOfReceipts.keySet()) {
            for (BorrowReceipt borrowing : mapOfReceipts.get(person)) {
                if (borrowing.getStartDate().equals(date)) {
                    receipts.add(borrowing);
                }
            }
        }
        return receipts;
    }

    public List<BorrowReceipt> getBorrowedItemsInOrder() {
        List<BorrowReceipt> receipts = new LinkedList<>();
        for (Person person : mapOfReceipts.keySet()) {
            receipts.addAll(mapOfReceipts.get(person));
        }
        receipts.sort(Comparator.comparing(BorrowReceipt::getStartDate));
        return receipts;
    }

    public BigDecimal calculateFine(BorrowReceipt receipt) {
        Optional<Date> lastDate;
        lastDate = receipt.getEndDate();
        Date currentDate = new Date();
        BigDecimal fineCost = BigDecimal.ZERO;
        if (lastDate.isPresent() && lastDate.get().before(currentDate)) {
            Calendar last = Calendar.getInstance();
            last.setTime(lastDate.get());
            Calendar current = Calendar.getInstance();
            current.setTime(currentDate);
            for (Date date = last.getTime(); last.before(current); last.add(Calendar.DATE, 1), date = last.getTime()) {
                Calendar helper = Calendar.getInstance();
                helper.setTime(date);
                if (helper.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                        && helper.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                    fineCost = fineCost.add(BigDecimal.valueOf(FINE_PER_DAY));
                }
            }
        }
        return fineCost;
    }

    public boolean returnAnItem(BorrowReceipt receipt) {
        Person member = receipt.getPerson();
        int quantity = receipt.getQuantity();
        if (!isMember(member) || quantity < 1 || receipt.isBeenReturned()) {
            return false;
        }
        Item item = receipt.getItem();
        BigDecimal fineCost = calculateFine(receipt);
        if (fineCost.compareTo(BigDecimal.valueOf(0)) > 0) {
            Fine newFine = new Fine(member, receipt, fineCost);
            if (mapOfFines.containsKey(member)) {
                mapOfFines.get(member).add(newFine);
            } else {
                List<Fine> fineSet = new ArrayList<>();
                fineSet.add(newFine);
                mapOfFines.put(member, fineSet);
            }
            member.addFine(this, newFine);
        }
        if (mapOfInventory.containsKey(item) && member.getBorrowedItems().get(item) >= quantity) {
            for (Person person : mapOfBookings.keySet()) {
                List<BorrowReceipt> personsNewBookings = new ArrayList<>();
                for (BorrowReceipt bookingReceipt : mapOfBookings.get(person)) {
                    if (bookingReceipt.getItem() == item) {
                        quantity = quantity - bookingReceipt.getQuantity();
                        Person newBorrower = bookingReceipt.getPerson();
                        if (mapOfReceipts.containsKey(newBorrower)) {
                            mapOfReceipts.get(newBorrower).add(bookingReceipt);
                        } else {
                            List<BorrowReceipt> newList = new ArrayList<>();
                            newList.add(bookingReceipt);
                            mapOfReceipts.put(newBorrower, newList);
                        }
                    } else {
                        personsNewBookings.add(bookingReceipt);
                    }
                }
                mapOfBookings.replace(person, personsNewBookings);
            }
            if (quantity > 0) {
                mapOfInventory.replace(item, mapOfInventory.get(item) + quantity);
            }
            member.removeItem(item, quantity);
            receipt.setBeenReturned(true);
            return true;
        }
        return false;
    }

    public Map<Item, Integer> getRecommendation(Recommendation recommendation) {
        return recommendation.getRecommendedList(mapOfInventory);
    }

    public Map<Item, Integer> getMapOfInventory() {
        return mapOfInventory;
    }

    public Map<Person, List<BorrowReceipt>> getMapOfReceipts() {
        return mapOfReceipts;
    }

    public Set<Person> getSetOfMembers() {
        return setOfMembers;
    }

    public Map<Person, List<Fine>> getMapOfFines() {
        return mapOfFines;
    }

    public String getName() {
        return name;
    }

    public Map<Person, List<BorrowReceipt>> getMapOfBookings() {
        return mapOfBookings;
    }
}
