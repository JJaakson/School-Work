package ee.taltech.iti0200.kt1.hotel;

import static ee.taltech.iti0200.kt1.hotel.Hotel.RoomType;

public class HotelRoom {

    private int size;
    private int roomNumber;
    private RoomType type;

    public HotelRoom(int roomNumber, int size, RoomType type) {
        this.roomNumber = roomNumber;
        this.size = size;
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "HotelRoom{"
                + "size=" + size
                + ", roomNumber=" + roomNumber
                + ", type=" + type
                + '}';
    }
}
