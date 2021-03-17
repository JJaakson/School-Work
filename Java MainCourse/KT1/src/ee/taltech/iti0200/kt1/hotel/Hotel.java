package ee.taltech.iti0200.kt1.hotel;

import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private List<HotelRoom> availableRooms = new ArrayList<>();
    private List<HotelRoom> bookedRooms = new ArrayList<>();

    public enum RoomType {
        REGULAR,
        SUITE
    }

    public boolean addRoom(HotelRoom room) {
        if (availableRooms.size() == 0) {
            availableRooms.add(room);
        } else {
            List<Integer> roomNumbers = new ArrayList<>();
            for (HotelRoom r : availableRooms) {
                roomNumbers.add(r.getRoomNumber());
            }
            if (!roomNumbers.contains(room.getRoomNumber())) {
                availableRooms.add(room);
                return true;
            }
        }
        return false;
    }

    public HotelRoom bookARoom(HotelRoom room) {
        List<HotelRoom> newRooms = new ArrayList<>();
        HotelRoom correctRoom = null;
        for (HotelRoom r : availableRooms) {
            if (r.getType() == room.getType() && r.getSize() == room.getSize()) {
                correctRoom = r;
                bookedRooms.add(r);
            } else {
                newRooms.add(r);
            }
        }
        availableRooms = newRooms;
        return correctRoom;
    }

    public void unBookARoom(int roomNumber) {
        List<HotelRoom> newRooms = new ArrayList<>();
        for (HotelRoom r : bookedRooms) {
            if (r.getRoomNumber() == roomNumber && r.getType() != RoomType.SUITE) {
                availableRooms.add(r);
            } else {
                newRooms.add(r);
            }
        }
        bookedRooms = newRooms;
    }

    public HotelRoom lookForARoom(int size) {
        HotelRoom correctRoom = null;
        for (HotelRoom r : availableRooms) {
            if (r.getSize() > size) {
                correctRoom = r;
            }
        }
        for (HotelRoom r : bookedRooms) {
            if (correctRoom == null && r.getSize() > size) {
                System.out.println("This size rooms are all booked!");
                return null;
            }
        }
        if (correctRoom == null) {
            System.out.println("We do not have this sized rooms!");
        }
        return correctRoom;
    }

    public List<HotelRoom> getAvailableRooms() {
        return availableRooms;
    }

    public List<HotelRoom> getBookedRooms() {
        return bookedRooms;
    }
}
