package crazyjedi;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Vlad on 30.04.2017.
 */
public class HotelManager {

    public List<Hotel> hotels;
    public Set<City> cities;

    public HotelManager() {
        this.hotels = new ArrayList<>();
        this.cities = new HashSet<>();
    }

    public void addHotel(Hotel hotel){
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


}