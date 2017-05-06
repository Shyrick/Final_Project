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
        Hotel tempHotel = hotelManager.findHotelById(hotelId);
        Room tempRoom = hotelManager.findRoomById(roomId);
        if(tempHotel!=null&&tempRoom!=null){
            Booking tempBooking = new Booking(user,dateBegin,dateEnd,tempHotel,tempRoom);
            this.bookingList.add(tempBooking);
        }else {
            throw new IllegalArgumentException("No room or hotel found!");
        }
    }

    public void addBooking(Date dateBegin, Date dateEnd, User user, int hotelId, Room room) throws IllegalArgumentException{
        Hotel tempHotel = hotelManager.findHotelById(hotelId);
        if(tempHotel!=null&&tempHotel.getRooms().contains(room)){
            Booking tempBooking = new Booking(user,dateBegin,dateEnd,tempHotel,room);
            this.bookingList.add(tempBooking);
        }else {
            throw new IllegalArgumentException("No room or hotel found!");
        }
    }

    public void addBooking(Date dateBegin, Date dateEnd, User user, Hotel hotel, Room room) throws IllegalArgumentException{
        if(hotelManager.getHotels().contains(hotel)&&hotel.getRooms().contains(room)){
            Booking tempBooking = new Booking(user,dateBegin,dateEnd,hotel,room);
            this.bookingList.add(tempBooking);
        }else {
            throw new IllegalArgumentException("No room or hotel found!");
        }
    }

    //REMOVING BOOKING
    //todo реализовать со списком
    public void removeBooking(Booking booking){
        bookingList.remove(booking);
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

    //GET USERS FROM BOOKINGS

}
