package crazyjedi;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import Final_Project.Users.UserController.User;

/**
 * Created by Vlad on 30.04.2017.
 */
public class Booking {

    private static AtomicLong counter = new AtomicLong(0);
    private long id;
    private Room room;
    private User user;
    private Date dateBegin;
    private Date dateEnd;
    private Hotel hotel;

    public Booking(User user, Date dateBegin, Date dateEnd, Hotel hotel, Room room) throws IllegalArgumentException{

        if(user==null||dateBegin==null||dateEnd==null||hotel==null||room==null){
            throw new IllegalArgumentException("No null field allowed");
        }

        id=counter.incrementAndGet();
        this.user = user;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.hotel = hotel;
    }

    public long getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public User getUser() {
        return user;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public Hotel getHotel() {
        return hotel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Booking booking = (Booking) o;

        if (getId() != booking.getId()) return false;
        if (!getRoom().equals(booking.getRoom())) return false;
        if (!getUser().equals(booking.getUser())) return false;
        if (!getDateBegin().equals(booking.getDateBegin())) return false;
        if (!getDateEnd().equals(booking.getDateEnd())) return false;
        return getHotel().equals(booking.getHotel());
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getRoom().hashCode();
        result = 31 * result + getUser().hashCode();
        result = 31 * result + getDateBegin().hashCode();
        result = 31 * result + getDateEnd().hashCode();
        result = 31 * result + getHotel().hashCode();
        return result;
    }
}
