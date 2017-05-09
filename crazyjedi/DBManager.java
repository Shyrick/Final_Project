package crazyjedi;
import java.awt.print.Book;
import java.io.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Vlad on 03.05.2017.
 */
public class DBManager{

    String currentDir = System.getProperty("user.dir")+"/crazyjedi/db/";
    private String roomFilePath;
    private String hotelFilePath;
    private String cityFilePath;
    private String bookingsFilePath;
    private DateFormat df;
    public DBManager() {
        this.cityFilePath = currentDir+"cityDB.tsv";
        this.roomFilePath = currentDir+"roomDB.tsv";
        this.hotelFilePath = currentDir+"hotelDB.tsv";
        this.bookingsFilePath = currentDir+"bookingsDB.tsv";
        df = new SimpleDateFormat("dd/MM/yyyy");
        createDBStructure();

    }

    public void createDBStructure(){
        /**
         * Creates a structure of DB:
         * 1) bookings table;
         * 2) city table;
         * 3) hotel table;
         * 4) room table;
         */
        try{
            File f = new File(cityFilePath);
            f.createNewFile();
            f = new File(roomFilePath);
            f.createNewFile();
            f = new File(hotelFilePath);
            f.createNewFile();
            f = new File(bookingsFilePath);
            f.createNewFile();
        } catch (IOException ex)
        {
            throw new Error("Can't initialize the DB");
        }
    }

    //READ-WRITE CITIES

    public void dumpCityDB(Set<City> cities) {
        /**
         * Dumps a list of cities to the DB.
         * @param   cities  a list of cities.
         */
        File cityFile = new File(this.cityFilePath);
        try{
            cityFile.getParentFile().mkdirs();
            cityFile.createNewFile();
            Function<City,String> cityStringJoin = city -> Integer.toString(city.getId())+
                    "\t"+city.getName()+"\n";
            BufferedWriter writer = new BufferedWriter(new FileWriter(cityFile));
            for (String cityString : cities.stream().map(cityStringJoin).collect(Collectors.toList())) {
                writer.write(cityString);
            }
            writer.close();
        } catch (IOException ex) {
            throw new Error("Can't write cities table!");
        }
    }

    public Set<City> readCitiesDB() {
        /**
         * Reads a list of cities from the DB.
         * @return  set of cities.
         */
        String line;
        List<String> cityBlueprints = new ArrayList<>();
        BufferedReader reader = null;
        Set<City> cities = new HashSet<>();
        try{
            reader = new BufferedReader(new FileReader(this.cityFilePath));
            while((line = reader.readLine()) != null){
                cityBlueprints.add(line);
            }
        } catch (IOException ex){
            throw new Error("Can't read cities table!");
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (String cityBlueprint: cityBlueprints) {
            ArrayList<String> tempCity = new ArrayList<>();
            Scanner sc = new Scanner(cityBlueprint);
            sc.useDelimiter("\t");
            while(sc.hasNext()){
                tempCity.add(sc.next());
            }
            int id = Integer.parseInt(tempCity.get(0));
            String name = tempCity.get(1);
            cities.add(new City(id,name));
        }
        return cities;
    }

    //READ-WRITE ROOM
    public void dumpRoomDB(List<Room> rooms) {
        /**
         * Dumps rooms list into the DB.
         * @param   rooms   a list of rooms.
         */
        File roomFile = new File(this.roomFilePath);
        try{
            roomFile.getParentFile().mkdirs();
            roomFile.createNewFile();
            Function<Room,String> roomStringJoin = room -> Integer.toString(room.getId())+
                                                            "\t"+Byte.toString(room.getPerson())+
                                                            "\t"+room.getPrice().toString()+"\n";
            BufferedWriter writer = new BufferedWriter(new FileWriter(roomFile));
            for (String roomString : rooms.stream().map(roomStringJoin).collect(Collectors.toList())) {
                writer.write(roomString);
            }
            writer.close();
        } catch (IOException ex) {
            throw new Error("Can't write room table!");
        }
    }

    public List<Room> readRoomsDB() {
        /**
         * Reads a list of rooms from the DB.
         * @return a list of rooms.
         */
        String line;
        List<String> roomBlueprints = new ArrayList<>();
        BufferedReader reader = null;
        List<Room> rooms = new ArrayList<>();
        try{
            reader = new BufferedReader(new FileReader(this.roomFilePath));
            while((line = reader.readLine()) != null){
                roomBlueprints.add(line);
            }
        } catch (IOException ex){
            throw new Error("Can't read rooms table!");
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (String roomBlueprint : roomBlueprints) {
            ArrayList<String> tempRoom = new ArrayList<>();
            Scanner sc = new Scanner(roomBlueprint);
            sc.useDelimiter("\t");
            while(sc.hasNext()){
                tempRoom.add(sc.next());
            }
            int id = Integer.parseInt(tempRoom.get(0));
            byte person = Byte.parseByte(tempRoom.get(1));
            BigDecimal price = new BigDecimal(tempRoom.get(2));
            rooms.add(new Room(id,person,price));
        }
        return rooms;
    }

    //READ-WRITE HOTEL

    public void dumpHotelDB(List<Hotel> hotels) {
        /**
         * Dumps a list of hotels to the DB.
         * @param   hotels  a list of hotels.
         */
        File hotelFile = new File(this.hotelFilePath);
        try{
            hotelFile.getParentFile().mkdirs();
            hotelFile.createNewFile();

            Function<Hotel,String> hotelStringJoin = hotel -> Integer.toString(hotel.getId())+
                    "\t"+hotel.getCity()+
                    "\t"+hotel.getName()+
                    "\t"+String.join(";",hotel.getRoomStringIds().stream().collect(Collectors.toList()))+"\n";
            BufferedWriter writer = new BufferedWriter(new FileWriter(hotelFile));
            for (String hotelString : hotels.stream().map(hotelStringJoin).collect(Collectors.toList())) {
                writer.write(hotelString);
            }
            writer.close();
        } catch (IOException ex) {
            throw new Error("Can't write hotel table!");
        }
    }

    public List<Hotel> readHotelsDB(){
        /**
         * Reads a list of hotels from the DB.
         * @return  a list of hotels.
         */
        List<Room> tempRooms = readRoomsDB();

        String line;
        List<String> hotelBlueprints = new ArrayList<>();
        BufferedReader reader = null;
        List<Hotel> hotels = new ArrayList<>();
        try{
            reader = new BufferedReader(new FileReader(this.hotelFilePath));
            while((line = reader.readLine()) != null){
                hotelBlueprints.add(line);
            }
        } catch (IOException ex){
            throw new Error("Can't read hotel table!");
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (String hotelBlueprint : hotelBlueprints) {
            ArrayList<String> tempHotels = new ArrayList<>();
            Scanner sc = new Scanner(hotelBlueprint);
            sc.useDelimiter("\t");

            while(sc.hasNext()){
                tempHotels.add(sc.next());
            }
            int id = Integer.parseInt(tempHotels.get(0));
            String city = tempHotels.get(1);
            String name = tempHotels.get(2);
            Set<Integer> roomIds = new HashSet<>();
            if(tempHotels.size()==4){
                Scanner roomSc = new Scanner(tempHotels.get(3));
                roomSc.useDelimiter(";");
                while(roomSc.hasNext()){
                    roomIds.add(Integer.parseInt(roomSc.next()));
                }
            }

            Hotel tempHotel = new Hotel(id,city,name);
            List<Room> thisHotelRooms = tempRooms.stream()
                    .filter(room -> roomIds.contains(room.getId()))
                    .collect(Collectors.toList());
            for (Room r: thisHotelRooms) {
                tempHotel.addRoom(r);
            }
            hotels.add(tempHotel);
        }
        return hotels;

    }

    public void truncateDB(){
        /**
         * Truncates all tables in the DB.
         */
        File roomFile = new File(this.roomFilePath);
        if(roomFile.exists()) roomFile.delete();

        File hotelFile = new File(this.hotelFilePath);
        if(hotelFile.exists()) hotelFile.delete();

        File citiesFile = new File(this.cityFilePath);
        if(citiesFile.exists()) citiesFile.delete();

        File bookingsFile = new File(this.bookingsFilePath);
        if(bookingsFile.exists()) bookingsFile.delete();

        createDBStructure();
    }


    //LOAD/DUMP BOOKINGS

    public void dumpBookingsDB(List<Booking> bookings) {
        /**
         * Dumps bookings table into DB.
         * @param   bookings    a list of bookings.
         */
        File bookingsFile = new File(this.bookingsFilePath);
        try{
            bookingsFile.getParentFile().mkdirs();
            bookingsFile.createNewFile();

            Function<Booking,String> bookingStringJoin = booking -> Long.toString(booking.getId())+
                    "\t"+df.format(booking.getDateBegin())+
                    "\t"+df.format(booking.getDateEnd())+
                    "\t"+Integer.toString(booking.getUser().getId())+
                    "\t"+Integer.toString(booking.getHotel().getId())+
                    "\t"+Integer.toString(booking.getRoom().getId())
                    +"\n";
            BufferedWriter writer = new BufferedWriter(new FileWriter(bookingsFile));
            for (String bookingStream : bookings.stream().map(bookingStringJoin).collect(Collectors.toList())) {
                writer.write(bookingStream);
            }
            writer.close();
        } catch (IOException ex) {
            throw new Error("Can't write booking table!");
        }
    }

    public DateFormat getDf() {
        /**
         * @return date formatter to read/write dates into/from bookings DB.
         */
        return df;
    }

    public List<List<String>> readBookingDB(){
        /**
         * Reads bookings from a DB.
         * @return  bookings list.
         */
        List<List<String>> res = new ArrayList<>();

        String line;
        List<String> bookingBlueprints = new ArrayList<>();
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(this.bookingsFilePath));
            while((line = reader.readLine()) != null){
                bookingBlueprints.add(line);
            }
        } catch (IOException ex){
            throw new Error("Can't read rooms table!");
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (String bookingBlueprint : bookingBlueprints) {
            ArrayList<String> tempBooking = new ArrayList<>();
            Scanner sc = new Scanner(bookingBlueprint);
            sc.useDelimiter("\t");
            while(sc.hasNext()){
                tempBooking.add(sc.next());
            }
            res.add(tempBooking);

        }
        return res;

    }

}
