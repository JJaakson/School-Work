package ee.taltech.iti0200.exam3.library;

import ee.taltech.iti0200.exam3.library.items.Item;
import ee.taltech.iti0200.exam3.library.items.ItemType;
import ee.taltech.iti0200.exam3.library.person.Person;
import ee.taltech.iti0200.exam3.library.receipt.BorrowReceipt;
import ee.taltech.iti0200.exam3.library.recommendation.Availability;
import ee.taltech.iti0200.exam3.library.recommendation.Newest;
import ee.taltech.iti0200.exam3.library.recommendation.Popularity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class LibraryTest {

    private static final int TWENTY_UNITS = 20;
    private static final int FIFTEEN_UNITS = 15;
    private static final int TWELVE_UNITS = 12;
    private static final int FIFTY_UNITS = 50;
    private static final int FIFTYFIVE_UNITS = 55;
    private static final int SEVEN_UNITS = 7;
    private static final int FOURTEEN_UNITS = 14;
    private static final int TWENTYTWO_UNITS = 22;
    private static final int SIXTEEN_UNITS = 16;
    private static final double FINE_UNITS = 1.2;

    private Library library;
    private Person person;
    private Person person2;
    private Item book1;
    private Item book2;
    private Item book3;
    private Item book4;
    private Item book5;
    private Item dvd;
    private Item dvd2;
    private Item dvd3;
    private Item dvd4;
    private Item software;
    private Item software2;
    private Item software1;
    private Map<Item, Integer> newerItems;
    private Map<Item, Integer> correctOrder;
    private Map<Item, Integer> newItems;

    @BeforeEach
    void setUp() {
        library = new Library("Library");
        person = new Person("James");
        person2 = new Person("Bond");
        book1 = new Item("Harry Potter", ItemType.BOOK);
        book2 = new Item("Game of Thrones", ItemType.BOOK);
        book3 = new Item("Game of Thrones 1", ItemType.BOOK);
        book4 = new Item("Game of Thrones 2", ItemType.BOOK);
        book5 = new Item("Game of Thrones 3", ItemType.BOOK);
        dvd = new Item("Game of Thrones", ItemType.DVD);
        dvd2 = new Item("Season", ItemType.DVD);
        dvd3 = new Item("Gameshow", ItemType.DVD);
        dvd4 = new Item("History", ItemType.DVD);
        software = new Item("Windows 10", ItemType.SOFTWARE);
        software1 = new Item("Ubuntu", ItemType.SOFTWARE);
        software2 = new Item("Windows 11", ItemType.SOFTWARE);
        newerItems = new LinkedHashMap<>();
        correctOrder = new LinkedHashMap<>();
        newItems = new LinkedHashMap<>();
    }

    @Test
    void creationTest() {
        assertEquals("Library", library.getName());
        assertEquals(0, library.getMapOfInventory().size());
        assertEquals(0, library.getSetOfMembers().size());
    }

    @Test
    void registerTest() {
        library.registerAMember(person);
        assertEquals(1, library.getSetOfMembers().size());
        Library firstMembership = person.getMemberships().get(0);
        assertEquals(library, firstMembership);
        library.registerAMember(person);
        assertEquals(1, library.getSetOfMembers().size());
        library.registerAMember(person2);
        assertEquals(2, library.getSetOfMembers().size());
        Library secondLibrary = new Library("Second");
        secondLibrary.registerAMember(person2);
        assertEquals(2, person2.getMemberships().size());
    }
    @Test
    void addInventoryTests() {
        newItems.put(book1, TWENTY_UNITS);
        newItems.put(book2, FIFTEEN_UNITS);
        newItems.put(dvd, 5);
        newItems.put(software, 1);
        library.addInventory(newItems);
        assertEquals(4, library.getMapOfInventory().size());
        newerItems.put(dvd, 10);
        newerItems.put(book2, 5);
        library.addInventory(newerItems);
        Map<Item, Integer> libraryInventory = library.getMapOfInventory();
        assertEquals(4, libraryInventory.size());
        assertEquals(TWENTY_UNITS, libraryInventory.get(book2));
        assertEquals(FIFTEEN_UNITS, libraryInventory.get(dvd));
    }


    @Test
    void borrowTests() {
        library.registerAMember(person);
        newItems.put(book1, TWENTY_UNITS);
        newItems.put(book2, FIFTEEN_UNITS);
        newItems.put(dvd, 5);
        newItems.put(software, 1);
        library.addInventory(newItems);
        //correct person, success
        assertTrue(library.borrowAnItem(person, "Game of Thrones", 1, ItemType.BOOK, new Date()));
        assertTrue(library.borrowAnItem(person, "Game of Thrones", 1, ItemType.BOOK, new Date()));
        assertTrue(library.borrowAnItem(person, "Game of Thrones", 1, ItemType.BOOK, new Date()));
        Map<Item, Integer> libraryInventory = library.getMapOfInventory();
        assertEquals(1, person.getBorrowedItems().size());
        assertEquals(3, person.getBorrowedItems().get(book2));
        assertEquals(TWELVE_UNITS, libraryInventory.get(book2));
        //wrong person
        assertFalse(library.borrowAnItem(person2, "Game of Thrones", 1, ItemType.DVD, new Date()));
        //wrong item
        assertFalse(library.borrowAnItem(person, "wrong", 10, ItemType.SOFTWARE, new Date()));
        //Tries to borrow items, that are already all borrowed out
        assertTrue(library.borrowAnItem(person, "Game of Thrones", 5, ItemType.DVD, new Date()));
        assertFalse(library.borrowAnItem(person, "Game of Thrones", 2, ItemType.DVD, new Date()));
        //Check if they really are all borrowed out
        Map<Item, Integer> libraryInventoryNewer = library.getMapOfInventory();
        assertEquals(0, libraryInventoryNewer.get(dvd));
        //Borrow software twice
        assertTrue(library.borrowAnItem(person, ("Windows 10"), 100, ItemType.SOFTWARE, new Date()));
        assertFalse(library.borrowAnItem(person, ("Windows 10"), 100, ItemType.SOFTWARE, new Date()));
        //How many different items has the person borrowed total
        assertEquals(3, person.getBorrowedItems().size());
        assertEquals(1, library.getMapOfReceipts().size());
    }

    @Test
    void returnAnItemTest() {
        library.registerAMember(person);
        newItems.put(book1, TWENTY_UNITS);
        library.addInventory(newItems);
        library.borrowAnItem(person, "Harry Potter", 1, ItemType.BOOK, new Date());
        BorrowReceipt receipt = person.getReceipts().get(library).get(0);
        //test if the items are returned correctly and everything works
        assertTrue(library.returnAnItem(receipt));
        assertEquals(0, person.getBorrowedItems().size());
        assertFalse(library.returnAnItem(receipt));
    }

    @Test
    void returnWithFine() {
        library.registerAMember(person);
        newItems.put(book1, TWENTY_UNITS);
        library.addInventory(newItems);
        try {
            library.borrowAnItem(person, "Harry Potter", 1, ItemType.BOOK,
                    new SimpleDateFormat("yyyyMMdd").parse("20200509"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BorrowReceipt receipt = person.getReceipts().get(library).get(0);
        //Calculate fine first
        BigDecimal fineValue = library.calculateFine(receipt);
        assertEquals(BigDecimal.valueOf(FINE_UNITS), fineValue);
        //See if fine is being created and added
        assertTrue(library.returnAnItem(receipt));
        assertEquals(1, person.getFines().size());
        assertEquals(person, library.getMapOfFines().get(person).get(0).getMember());
        assertEquals(receipt, library.getMapOfFines().get(person).get(0).getReceipt());
        assertEquals(BigDecimal.valueOf(FINE_UNITS), library.getMapOfFines().get(person).get(0).getFineCost());
        assertEquals(1, library.getMapOfFines().size());
    }


    @Test
    void newestTests() {
        newItems.put(book1, TWENTY_UNITS);
        newItems.put(book2, FIFTEEN_UNITS);
        newItems.put(dvd, 5);
        newItems.put(software, 1);
        library.addInventory(newItems);
        //Newest test
        Map<Item, Integer> newestItems = library.getRecommendation(new Newest());
        assertEquals(4, newestItems.size());
        assertEquals(newItems, newestItems);
        newerItems.put(book3, FIFTY_UNITS);
        newerItems.put(book4, 5);
        newerItems.put(book5, FIFTYFIVE_UNITS);
        newerItems.put(dvd2, 10);
        newerItems.put(dvd4, 10);
        newerItems.put(software1, 1);
        newerItems.put(software2, 1);
        newerItems.put(dvd3, 10);
        library.addInventory(newerItems);
        Map<Item, Integer> evenNewestItems = library.getRecommendation(new Newest());
        correctOrder.put(dvd3, 10);
        correctOrder.put(software2, 1);
        correctOrder.put(software1, 1);
        correctOrder.put(dvd4, 10);
        correctOrder.put(dvd2, 10);
        correctOrder.put(book5, FIFTYFIVE_UNITS);
        correctOrder.put(book4, 5);
        correctOrder.put(book3, FIFTY_UNITS);
        correctOrder.put(software, 1);
        correctOrder.put(dvd, 5);
        //Test if the order and size is correct when requestin newest items
        assertEquals(10, evenNewestItems.size());
        assertEquals(correctOrder, evenNewestItems);
    }

    @Test
    void availabilityTest() {
        newItems.put(book1, TWENTY_UNITS);
        newItems.put(book2, FIFTEEN_UNITS);
        newItems.put(book3, 2);
        newItems.put(book4, 3);
        newItems.put(book5, SEVEN_UNITS);
        newItems.put(dvd, 5);
        newItems.put(dvd2, TWELVE_UNITS);
        newItems.put(dvd3, SIXTEEN_UNITS);
        newItems.put(dvd4, TWENTYTWO_UNITS);
        newItems.put(software, 1);
        newItems.put(software2, 1);
        library.addInventory(newItems);
        Map<Item, Integer> bestAvailabilityItems = library.getRecommendation(new Availability());
        correctOrder.put(dvd4, TWENTYTWO_UNITS);
        correctOrder.put(book1, TWENTY_UNITS);
        correctOrder.put(dvd3, SIXTEEN_UNITS);
        correctOrder.put(book2, FIFTEEN_UNITS);
        correctOrder.put(dvd2, TWELVE_UNITS);
        correctOrder.put(book5, SEVEN_UNITS);
        correctOrder.put(dvd, 5);
        correctOrder.put(book4, 3);
        correctOrder.put(book3, 2);
        correctOrder.put(software, 1);
        //test if the size and order is correct by availability
        assertEquals(10, bestAvailabilityItems.size());
        assertEquals(correctOrder, bestAvailabilityItems);
    }

    @Test
    void popularityTest() {
        library.registerAMember(person);
        newItems.put(book1, TWENTY_UNITS);
        newItems.put(book2, FIFTEEN_UNITS);
        newItems.put(book3, 5);
        library.addInventory(newItems);
        library.borrowAnItem(person, "Harry Potter", 2, ItemType.BOOK, new Date());
        library.borrowAnItem(person, "Harry Potter", 2, ItemType.BOOK, new Date());
        library.borrowAnItem(person, "Game of Thrones", 1, ItemType.BOOK, new Date());
        library.borrowAnItem(person, "Game of Thrones 1", 1, ItemType.BOOK, new Date());
        library.borrowAnItem(person, "Game of Thrones 1", 1, ItemType.BOOK, new Date());
        library.borrowAnItem(person, "Game of Thrones 1", 1, ItemType.BOOK, new Date());
        Map<Item, Integer> popularItems = library.getRecommendation(new Popularity());
        correctOrder.put(book1, SIXTEEN_UNITS);
        correctOrder.put(book3, 2);
        correctOrder.put(book2, FOURTEEN_UNITS);
        //test if the order of the items is correct by poplularity
        assertEquals(correctOrder, popularItems);
        newerItems.put(book4, 3);
        newerItems.put(book5, SEVEN_UNITS);
        newerItems.put(dvd, 5);
        newerItems.put(dvd2, TWELVE_UNITS);
        newerItems.put(dvd3, SIXTEEN_UNITS);
        newerItems.put(dvd4, TWENTYTWO_UNITS);
        newerItems.put(software, 1);
        newerItems.put(software2, 1);
        library.addInventory(newerItems);
        Map<Item, Integer> recentPopularItems = library.getRecommendation(new Popularity());
        //test if it is correc size
        assertEquals(10, recentPopularItems.size());
    }

    @Test
    void reserveTest() {
        library.registerAMember(person);
        library.registerAMember(person2);
        newItems.put(book1, TWENTY_UNITS);
        newItems.put(book2, FIFTEEN_UNITS);
        newItems.put(book3, 5);
        library.addInventory(newItems);
        try {
            library.borrowAnItem(person, "Game of Thrones 1", 5, ItemType.BOOK,
                    new SimpleDateFormat("yyyyMMdd").parse("20200525"));
            library.reserveAnItem(person2, "Game of Thrones 1", 2, ItemType.BOOK);
            //test if borrwing works and if the reserved items borrowing starts on the correct day
            assertEquals(1, person2.getReceipts().size());
            assertEquals(new SimpleDateFormat("yyyyMMdd").parse("20200605"),
                    person2.getReceipts().get(library).get(0).getStartDate());
            library.returnAnItem(person.getReceipts().get(library).get(0));
            //test if when item is returned, it is handed over to the one that reserved it and return uneccessary
            //items back to the library inventory
            assertEquals(0, library.getMapOfBookings().get(person2).size());
            assertEquals(2, library.getMapOfReceipts().size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void itemSearchTest() {
        library.registerAMember(person);
        newItems.put(book1, TWENTY_UNITS);
        newItems.put(book2, FIFTEEN_UNITS);
        newItems.put(book3, 5);
        library.addInventory(newItems);
        //Check if returns correct items if searched for by name
        assertEquals("{BOOK-> name='Game of Thrones 1' quantity=5}",
                library.searchForAnItem("Game of Thrones 1"));
        library.borrowAnItem(person, "Game of Thrones 1", 5, ItemType.BOOK, new Date());
        assertEquals("{BOOK-> name='Game of Thrones 1' quantity=0}",
                library.searchForAnItem("Game of Thrones 1"));
        assertEquals("James does not exist in this library.", library.searchForAnItem("James"));
    }

    @Test
    void getByDateTest() {
        library.registerAMember(person);
        newItems.put(book1, TWENTY_UNITS);
        newItems.put(book2, FIFTEEN_UNITS);
        newItems.put(book3, 5);
        library.addInventory(newItems);
        try {
            library.borrowAnItem(person, "Game of Thrones 1", 5, ItemType.BOOK,
                    new SimpleDateFormat("yyyyMMdd").parse("20200515"));
            //Get receipts by date
            assertEquals(1,
                    library.getBorrowedItemsByDate(new SimpleDateFormat("yyyyMMdd").parse("20200515"))
                            .size());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getReceiptsInOrder() {
        library.registerAMember(person);
        newItems.put(book1, TWENTY_UNITS);
        newItems.put(book2, FIFTEEN_UNITS);
        newItems.put(book3, 5);
        library.addInventory(newItems);
        library.borrowAnItem(person, "Game of Thrones 1", 5, ItemType.BOOK, new Date());
        library.borrowAnItem(person, "Harry Potter", 5, ItemType.BOOK, new Date());
        library.borrowAnItem(person, "Game of Thrones", 5, ItemType.BOOK, new Date());
        assertEquals(3, library.getBorrowedItemsInOrder().size());
        assertEquals(person.getReceipts().get(library), library.getBorrowedItemsInOrder());
    }
}
