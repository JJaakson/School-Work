package ee.taltech.iti0200.kt1.hotel;

public class Main {

    private static final int ROOM_1_NUMBER = 11;
    private static final int ROOM_2_NUMBER = 12;
    private static final int ROOM_3_NUMBER = 21;
    private static final int ROOM_4_NUMBER = 22;
    private static final int ROOM_1_SIZE = 10;
    private static final int ROOM_2_SIZE = 35;
    private static final int ROOM_3_SIZE = 25;
    private static final int ROOM_4_SIZE = 55;
    private static final int LOOK_FOR_A_ROOM = 27;

    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        HotelRoom regular1 = new HotelRoom(ROOM_1_NUMBER, ROOM_1_SIZE, Hotel.RoomType.REGULAR);
        HotelRoom regular2 = new HotelRoom(ROOM_2_NUMBER, ROOM_2_SIZE, Hotel.RoomType.REGULAR);
        HotelRoom suite1 = new HotelRoom(ROOM_3_NUMBER, ROOM_3_SIZE, Hotel.RoomType.SUITE);
        HotelRoom suite2 = new HotelRoom(ROOM_4_NUMBER, ROOM_4_SIZE, Hotel.RoomType.SUITE);

        hotel.addRoom(regular1);
        hotel.addRoom(regular2);
        hotel.addRoom(suite1);
        hotel.addRoom(suite2);

        HotelRoom room1 = hotel.lookForARoom(LOOK_FOR_A_ROOM);
        hotel.bookARoom(room1);
        HotelRoom room2 = hotel.lookForARoom(LOOK_FOR_A_ROOM);
        hotel.bookARoom(room2);

        hotel.unBookARoom(ROOM_4_NUMBER);
        hotel.unBookARoom(ROOM_2_NUMBER);

        System.out.println(hotel.getAvailableRooms());
        System.out.println(hotel.getBookedRooms());
    }
}
