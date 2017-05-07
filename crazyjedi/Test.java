package crazyjedi;

import Shyrick.User;
import Shyrick.UserController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Vlad on 03.05.2017.
 */
public class Test {
    public static void main(String[] args) {

        DBManager dbm = new DBManager();
        dbm.truncateDB(); //Clear the DB before test
        HotelManager hotelManager = new HotelManager();



        System.out.println("InitializingTest");

        for (Hotel hotel : hotelManager.getHotels()) {
            System.out.println(hotel);
        }
        System.out.println("-----------");

//        List<Room> newRooms = dbm.readRoomsDB();
//        System.out.println(newRooms.toString());

        try {
            hotelManager.addCity("Kiev");
            hotelManager.addCity("New York");
            hotelManager.addCity("Tokyo");
            hotelManager.addCity("Moskow");
            hotelManager.addCity("Paris");
        } catch(IllegalArgumentException ex){
            ex.printStackTrace();
        }

        System.out.println(hotelManager.getCities());
        hotelManager.createHotel(1,"Hayatt");
        hotelManager.createHotel(4,"Inn");
        hotelManager.createHotel(2,"Palace");
        hotelManager.createHotel(2,"Moe's");

        System.out.println("\nREAD-WRITE CITIES\n");
        dbm.dumpCityDB(hotelManager.getCities());
        Set<City> newCities = dbm.readCitiesDB();
        for (City newCity : newCities) {
            System.out.println(newCity);
        }
        System.out.println("\n");

//
//
        hotelManager.createRoom(0,(byte)2,new BigDecimal(400));
        hotelManager.createRoom(0,(byte)2,new BigDecimal(300));
        hotelManager.createRoom(0,(byte)2,new BigDecimal(500));
        hotelManager.createRoom(1,(byte)2,new BigDecimal(400));
        hotelManager.createRoom(1,(byte)3,new BigDecimal(400));
        hotelManager.createRoom(1,(byte)4,new BigDecimal(500));
        hotelManager.createRoom(2,(byte)2,new BigDecimal(400));
        hotelManager.createRoom(3,(byte)3,new BigDecimal(400));
        hotelManager.createRoom(3,(byte)4,new BigDecimal(500));

        for(Hotel hotel:hotelManager.getHotels()){
            System.out.println(hotel);
        }

        System.out.println("\n hotels by city \n");
        for(Hotel hotel:hotelManager.getHotelsByCity(2)){
            System.out.println(hotel);
        }

//
        System.out.println("\nREAD-WRITE\n");
//
        dbm.dumpRoomDB(hotelManager.getRooms());
        dbm.dumpHotelDB(hotelManager.getHotels());
//
        List<Hotel> newHotels = dbm.readHotelsDB();
        for (Hotel newHotel : newHotels) {
            System.out.println(newHotel);
        }
        System.out.println("------------------------");


        System.out.println("\nTest hotel removement\n");

        hotelManager.removeHotel(0);
        for(Hotel hotel:hotelManager.getHotels()){
            System.out.println(hotel);
        }
        System.out.println("Read from DB");
        newHotels = dbm.readHotelsDB();
        for (Hotel newHotel : newHotels) {
            System.out.println(newHotel);
        }

        System.out.println("\nTest hotel added\n");
        hotelManager.createHotel(2,"Hotello");
        Hotel lastHotel = hotelManager.getLastAddedHotel();
        hotelManager.createRoom(lastHotel,(byte)3,new BigDecimal(700));
        for(Hotel hotel:hotelManager.getHotels()){
            System.out.println(hotel);
        }
        System.out.println("Read from DB");
        newHotels = dbm.readHotelsDB();
        for (Hotel newHotel : newHotels) {
            System.out.println(newHotel);
        }


        //NEVER JUST NEVER CREATE ONE MORE HOTEM MENAGER
        //TESTING READING FROM DB ON INITIALIZATION STEP
        System.out.println("\nINITIAL READING\n");
        HotelManager newHotelManager = new HotelManager();

        for (Hotel hotel : newHotelManager.getHotels()) {
            System.out.println(hotel);
        }

        //TEST BOOKINGS
//        UserController uc = new UserController();
//        User u1 = new User(1,"login1","User1", "User1", false);
//        User u2 = new User(2,"login2","User2", "User2", false);
//        User u3 = new User(3,"login3","User3", "User3", false);
//        User u4 = new User(4,"login4","User4", "User4", false);
//        User u5 = new User(5,"login5","User5", "User5", false);
//
//        BookingManager bm = new BookingManager(hotelManager, uc);
//
//        bm.addBooking(new Date(2017,5,1),new Date(2017,5,9),u1,2,3);
//
//        System.out.println(bm.getByRoom(hotelManager.findRoomById(3)));

    }
}
