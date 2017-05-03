package crazyjedi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 03.05.2017.
 */
public class Test {
    public static void main(String[] args) {
        Room room1 = new Room(1,(byte)2,new BigDecimal(400));
        Room room2 = new Room(2,(byte)3,new BigDecimal(600));
        Room room3 = new Room(3,(byte)1,new BigDecimal(200));

        List<Room> rooms = new ArrayList<>();
        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);

        DBManager dbm = new DBManager("/roomDB.tsv","/hotelDB.tsv");
        dbm.dumpRoomDB(rooms);

        List<Room> newRooms = dbm.readRoomsDB();
        System.out.println(newRooms.toString());

        Hotel hotel1 = new Hotel(1,1,"Hayatt");
        Hotel hotel2 = new Hotel(2,4,"Inn");
        Hotel hotel3 = new Hotel(3,2,"Palace");

        hotel1.addRoom(room1);
        hotel1.addRoom(room2);
        hotel2.addRoom(room2);
        hotel2.addRoom(room3);
        hotel3.addRoom(room2);
        hotel3.addRoom(room2);

        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel1);
        hotels.add(hotel2);
        hotels.add(hotel3);

        dbm.dumpHotelDB(hotels);

        List<Hotel> newHotels = dbm.readHotelsDB();
        for (Hotel newHotel : newHotels) {
            System.out.println(newHotel);
        }
    }
}
