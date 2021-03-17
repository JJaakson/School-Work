package ee.taltech.iti0200.kt1.hotel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class HotelTest {

    private static final int ROOM_1_NUMBER = 11;
    private static final int ROOM_2_NUMBER = 12;
    private static final int ROOM_3_NUMBER = 22;
    private static final int ROOM_1_SIZE = 10;
    private static final int ROOM_2_SIZE = 35;
    private static final int ROOM_3_SIZE = 55;
    private static final int LOOK_FOR_A_ROOM = 100;
    private static final int LOOK_FOR_A_ROOM_2 = 45;
    private static final int LOOK_FOR_A_ROOM_3 = 9;
    private static final int BOOK_A_ROOM = 24;
    private static final int UNBOOK_A_ROOM = 12;

    private Hotel hotel;
    private HotelRoom regular1;
    private HotelRoom regular2;
    private HotelRoom suite1;
    private HotelRoom suite2;
    private HotelRoom falseroom;

    @BeforeEach
    void setUp() {
        hotel = new Hotel();
        regular1 = new HotelRoom(ROOM_1_NUMBER, ROOM_1_SIZE, Hotel.RoomType.REGULAR);
        regular2 = new HotelRoom(ROOM_2_NUMBER, ROOM_2_SIZE, Hotel.RoomType.REGULAR);
        suite2 = new HotelRoom(ROOM_3_NUMBER, ROOM_3_SIZE, Hotel.RoomType.SUITE);
        falseroom = new HotelRoom(ROOM_1_NUMBER, ROOM_1_SIZE, Hotel.RoomType.REGULAR);

        hotel.addRoom(regular1);
        hotel.addRoom(regular2);
        hotel.addRoom(suite2);
    }

    @Test
    void addFalseRoomTest() {
        assertFalse(hotel.addRoom(falseroom));
    }

    @Test
    void findARoomTest() {
        assertEquals(null, hotel.lookForARoom(LOOK_FOR_A_ROOM));
        HotelRoom room = hotel.lookForARoom(LOOK_FOR_A_ROOM_2);
        assertNotEquals(null, room);
        hotel.bookARoom(room);
        HotelRoom room2 = hotel.lookForARoom(LOOK_FOR_A_ROOM_2);
        assertEquals(null, room2);
    }

    @Test
    void bookingTest() {
        hotel.bookARoom(hotel.lookForARoom(BOOK_A_ROOM));
        assertEquals(1, hotel.getBookedRooms().size());
        assertEquals(2, hotel.getAvailableRooms().size());
    }

    @Test
    void unBookingTest() {
        hotel.bookARoom(hotel.lookForARoom(LOOK_FOR_A_ROOM_2));
        hotel.bookARoom(hotel.lookForARoom(LOOK_FOR_A_ROOM_3));
        hotel.unBookARoom(UNBOOK_A_ROOM);
        assertEquals(1, hotel.getBookedRooms().size());
        assertEquals(2, hotel.getAvailableRooms().size());
    }

    @Test
    void main() {
        String[] args = {""};
        Main.main(args);
    }
}
