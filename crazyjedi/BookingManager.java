package crazyjedi;

import Shyrick.User;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vlad on 30.04.2017.
 */
public class BookingManager {

    private List<Booking> bookingList = null;
    private HotelManager hotelManager = null;

    public BookingManager(HotelManager hotelManager) {
        this.hotelManager = hotelManager;
    }

    //READING AND WRITING BOOKING LIST

    //todo Implement methods in BookingReader
    public void loadBookings(){
        this.bookingList=BookingsReader.loadBookings();
    }

    public void dumpBookings(){
        BookingsReader.dumpBookings(this.bookingList);
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
        return bookingList.stream().filter(booking -> booking.getRoom().equals(room)).collect(Collectors.toList());
    }

    public List<Booking> getByRoomId(int roomId){
        return bookingList.stream().filter(booking -> booking.getRoom().getId()==roomId).collect(Collectors.toList());
    }

    //ADDING NEW BOOKING

    public void addBooking(Date dateBegin, Date dateEnd, User user, int hotelId, int roomId){
        Hotel tempHotel = hotelManager.findHotelById(hotelId);
        Room tempRoom = hotelManager.findRoomById(roomId);
        Booking tempBooking = new Booking(user,dateBegin,dateEnd,tempHotel,tempRoom);
    }

    public void addBooking(Date dateBegin, Date dateEnd, User user, int hotelId, Room room){
        Hotel tempHotel = hotelManager.findHotelById(hotelId);
        Booking tempBooking = new Booking(user,dateBegin,dateEnd,tempHotel,room);
    }

    public void addBooking(Date dateBegin, Date dateEnd, User user, Hotel hotel, Room room){
        Booking tempBooking = new Booking(user,dateBegin,dateEnd,hotel,room);
    }

    //REMOVING BOOKING

    public void removeBooking(Booking booking){
        /**
         *
         */
        bookingList.remove(booking);
    }

    //CHECK IF BOOKING IS POSSIBLE

    public  boolean checkBookingPossible(Date dateBegin, Date dateEnd, Room room){
        //Если дата начала и дата конца не попадают ни в один из существующих промежутков бронирования, вернуть true
        List<Booking> bookingsOfRoom = this.getByRoom(room);
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

    public  boolean checkBookingPossible(Date dateBegin, Date dateEnd, int roomId){
        //Если дата начала и дата конца не попадают ни в один из существующих промежутков бронирования, вернуть true
        List<Booking> bookingsOfRoom = this.getByRoomId(roomId);
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
