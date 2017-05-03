package crazyjedi;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Vlad on 30.04.2017.
 */
public class HotelManager {

    public List<Hotel> hotels;
    public Set<City> cities;
    private AtomicInteger hotelMaxId = new AtomicInteger(0);
    private AtomicInteger roomMaxId = new AtomicInteger(0);

    public HotelManager() {
        this.hotels = new ArrayList<>();
        this.cities = new HashSet<>();
    }

    private void addHotel(Hotel hotel){
        hotels.add(hotel);
    }

    public void addCity(City city){
        if(!cities.contains(city)){
            cities.add(city);
        }
    }

    public void removeCity(City city){
        Iterator<City> iter = cities.iterator();
        while(iter.hasNext()){
            City curCity = iter.next();
            if(curCity==city){
                cities.remove(curCity);
            }
        }
    }

    public void removeHotel(int id){
        Iterator<Hotel> iter = hotels.iterator();
        while(iter.hasNext()){
            Hotel curHotel = iter.next();
            if(curHotel.getId()==id){
                cities.remove(curHotel);
            }
        }
    }

    //todo реализовать
    public void updateHotel(int id, Hotel hotel){

    }

    public Room findRoomById(int roomId){

        for (Hotel hotel : hotels) {
            for (Room room : hotel.getRooms()) {
                if(room.getId()==roomId){
                    return room;
                }
            }
        }
        return null;
    }

    public Hotel findHotelById(int hotelId) {
        for (Hotel hotel : hotels) {
            if(hotel.getId()==hotelId){
                return hotel;
            }
        }
        return null;
    }

    public List<Hotel> getHotelsByCity(int cityId) {
        return hotels.stream().filter(hotel -> hotel.getCityId()==cityId).collect(Collectors.toList());
    }

    //Creating hotels
    public void createHotel(int hotelId, int cityId, String name){
        this.addHotel(new Hotel(this.hotelMaxId.incrementAndGet(),cityId,name));
    }
    //Creating rooms
    public void createRoom(Hotel hotel,int roomId, byte person, BigDecimal price){
        hotel.addRoom(new Room(this.roomMaxId.incrementAndGet(),person,price));
    }


}
