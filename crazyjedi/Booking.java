package crazyjedi;

import Shyrick.User;
import Shyrick.UserController;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Created by Vlad on 30.04.2017.
 */
public class Booking {


    private long id;
    private Room room;
    private User user;
    private Date dateBegin;
    private Date dateEnd;
    private Hotel hotel;

    public Booking(long id, User user, Date dateBegin, Date dateEnd, Hotel hotel, Room room) throws IllegalArgumentException{

        if(user==null||dateBegin==null||dateEnd==null||hotel==null||room==null){
            throw new IllegalArgumentException("No null field allowed");
        }

        this.id=id;
        this.user = user;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.hotel = hotel;
        this.room = room;
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

    @Override
    public String toString() {
        return  "Бронирование Id - " + getId() + " пользователь " + getUser().getLogin() +
                "\nОтель: " + getHotel().getName() +
                " Комната: " + getRoom().getId() +
                " Город: " + getHotel().getCity() +
                "\nДата начала: " + getDateBegin() +
                "\nДата конца: " + getDateEnd() + "\n";
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}
