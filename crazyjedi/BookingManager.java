package crazyjedi;

import Shyrick.User;
import Shyrick.UserController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Created by Vlad on 30.04.2017.
 */
public class BookingManager {

    private List<Booking> bookingList = null;
    private HotelManager hotelManager = null;
    private DBManager dbm = new DBManager();
    private AtomicLong bookingMaxId = new AtomicLong(0);
    private UserController userController = null;
    public BookingManager(HotelManager hotelManager, UserController userController) {
        this.hotelManager = hotelManager;
        this.userController = userController;
        loadBookings();
    }

    //READING AND WRITING BOOKING LIST


    //todo Implement methods in BookingReader
    public void loadBookings(){
        List<List<String>> blueprints = dbm.readBookingDB();
        List<Booking> bookingsRead = new ArrayList<>();
        for (List<String> blueprint : blueprints) {
            long id = Long.parseLong(blueprint.get(0));
            Date dBeg=null;
            Date dEnd=null;
            try{
                dBeg = dbm.getDf().parse(blueprint.get(1));
                dEnd = dbm.getDf().parse(blueprint.get(2));
            }catch (Exception ex){
                throw new Error("Can't read bookings dates");
            }
            User us = userController.findById(Integer.parseInt(blueprint.get(3)));
            Hotel hot = hotelManager.findHotelById(Integer.parseInt(blueprint.get(4)));
            Room r = null;
            try{
                r = hot.getRooms().stream()
                        .filter(room -> room.getId()==Integer.parseInt(blueprint.get(5)))
                        .findAny()
                        .orElseGet(null);

            }catch (Exception ex){
                throw new IllegalArgumentException("No such Room in Hotel");
            }
            bookingsRead.add(new Booking(id,us,dBeg,dEnd,hot,r));
        }
        if(!bookingsRead.isEmpty()){
            this.bookingList=bookingsRead;
            long mid = bookingsRead.stream().map(Booking::getId).max(Long::compareTo).get();
            this.bookingMaxId.set(mid+1);
        }
    }

    public void dumpBookings(){
        dbm.dumpBookingsDB(this.bookingList);
    }

    //FIND BOOKING

    public Booking findById(long id){

        for (Booking currentBooking : bookingList) {
            if (currentBooking.getId() == id) {
                return currentBooking;
            }
        }
        return null;
    }

    public List<Booking> getByUser(User user){
        return bookingList.stream().filter(booking -> booking.getUser().equals(user)).collect(Collectors.toList());
    }

    public List<Booking> getByUserId(int userId){
        return bookingList.stream().filter(booking -> booking.getUser().getId()==userId).collect(Collectors.toList());
    }

    public List<Booking> getByRoom(Room room){
        if(hotelManager.getHotels().stream().anyMatch(hotel -> hotel.getRooms().contains(room))){
            return bookingList.stream().filter(booking -> booking.getRoom().equals(room)).collect(Collectors.toList());
        }
        else return null;
    }

    public List<Booking> getByRoomId(int roomId){
        Room room = hotelManager.findRoomById(roomId);
        if(room==null) return null;
        return bookingList.stream().filter(booking -> booking.getRoom().getId()==roomId).collect(Collectors.toList());
    }

    //ADDING NEW BOOKING

    public void addBooking(Date dateBegin, Date dateEnd, User user, int hotelId, int roomId) throws IllegalArgumentException{
        long id = bookingMaxId.getAndIncrement();
        Hotel tempHotel = hotelManager.findHotelById(hotelId);
        Room tempRoom = hotelManager.findRoomById(roomId);
        if(tempHotel!=null&&tempRoom!=null){
            Booking tempBooking = new Booking(id,user,dateBegin,dateEnd,tempHotel,tempRoom);
            this.bookingList.add(tempBooking);
        }else {
            throw new IllegalArgumentException("No room or hotel found!");
        }
        dumpBookings();
    }

    public void addBooking(Date dateBegin, Date dateEnd, int userId, int hotelId, int roomId) throws IllegalArgumentException{
        long id = bookingMaxId.getAndIncrement();
        Hotel tempHotel = hotelManager.findHotelById(hotelId);
        Room tempRoom = hotelManager.findRoomById(roomId);
        User user = userController.findById(userId);
        if(tempHotel!=null&&tempRoom!=null){
            Booking tempBooking = new Booking(id,user,dateBegin,dateEnd,tempHotel,tempRoom);
            this.bookingList.add(tempBooking);
        }else {
            throw new IllegalArgumentException("No room or hotel found!");
        }
        dumpBookings();
    }

    public void addBooking(Date dateBegin, Date dateEnd, User user, int hotelId, Room room) throws IllegalArgumentException{
        long id = bookingMaxId.getAndIncrement();
        Hotel tempHotel = hotelManager.findHotelById(hotelId);
        if(tempHotel!=null&&tempHotel.getRooms().contains(room)){
            Booking tempBooking = new Booking(id,user,dateBegin,dateEnd,tempHotel,room);
            this.bookingList.add(tempBooking);
        }else {
            throw new IllegalArgumentException("No room or hotel found!");
        }
        dumpBookings();
    }

    public void addBooking(Date dateBegin, Date dateEnd, User user, Hotel hotel, Room room) throws IllegalArgumentException{
        long id = bookingMaxId.getAndIncrement();
        if(hotelManager.getHotels().contains(hotel)&&hotel.getRooms().contains(room)){
            Booking tempBooking = new Booking(id,user,dateBegin,dateEnd,hotel,room);
            this.bookingList.add(tempBooking);
        }else {
            throw new IllegalArgumentException("No room or hotel found!");
        }
        dumpBookings();
    }

    //REMOVING BOOKING
    //todo реализовать со списком
    public void removeBooking(Booking booking){
        bookingList.remove(booking);
        dumpBookings();
    }

    public void removeBooking(List<Booking> bookings){
        for (Booking booking : bookings) {
            bookingList.remove(booking);
        }
        dumpBookings();
    }

    //CHECK IF BOOKING IS POSSIBLE

    public  boolean checkBookingPossible(Date dateBegin, Date dateEnd, Room room){
        //Если дата начала и дата конца не попадают ни в один из существующих промежутков бронирования, вернуть true
        List<Booking> bookingsOfRoom = this.getByRoom(room);
        if(bookingsOfRoom==null) return false;
        for (Booking booking:bookingsOfRoom) {
            if(booking.getDateBegin().compareTo(dateBegin)>=0&&booking.getDateBegin().compareTo(dateEnd)<=0){
                return false;
            }
            if(booking.getDateEnd().compareTo(dateBegin)>=0&&booking.getDateEnd().compareTo(dateEnd)<=0){
                return false;
            }
            if(dateBegin.compareTo(dateEnd)>0){
                return false;
            }

        }
        return true;
    }

    public  boolean checkBookingPossible(Date dateBegin, Date dateEnd, int roomId){
        //Если дата начала и дата конца не попадают ни в один из существующих промежутков бронирования, вернуть true
        List<Booking> bookingsOfRoom = this.getByRoomId(roomId);
        if(bookingsOfRoom==null) return false;
        for (Booking booking:bookingsOfRoom) {
            if(booking.getDateBegin().compareTo(dateBegin)>=0&&booking.getDateBegin().compareTo(dateEnd)<=0){
                return false;
            }
            if(booking.getDateEnd().compareTo(dateBegin)>=0&&booking.getDateEnd().compareTo(dateEnd)<=0){
                return false;
            }
        }
        return true;
    }

    public HotelManager getHotelManager() {
        return hotelManager;
    }

    //GET USERS FROM BOOKINGS

}
