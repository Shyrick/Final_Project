package crazyjedi;

/**
 * Created by Vlad on 30.04.2017.
 */
public interface HotelManager {

    abstract Room findRoomById(int roomId);
    abstract Hotel findHotelById(int hotelId);


}
