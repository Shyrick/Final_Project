package crazyjedi;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by Vlad on 30.04.2017.
 */
public class HotelManager {

    private List<Hotel> hotels;
    private Set<City> cities;
    private AtomicInteger hotelMaxId = new AtomicInteger(0);
    private AtomicInteger roomMaxId = new AtomicInteger(0);
    private AtomicInteger cityMaxId = new AtomicInteger(0);
    private DBManager dbm = new DBManager();

    public HotelManager() {
        this.setHotels(new ArrayList<>());
        List<Hotel>tmp=dbm.readHotelsDB();
        try{

            this.setHotels(dbm.readHotelsDB());

            try {
                roomMaxId.set(dbm.readRoomsDB().stream().map(Room::getId).max(Integer::compareTo).get()+1);
            } catch(Exception ex) {
                roomMaxId.set(0);
            }
            try {
                hotelMaxId.set(dbm.readHotelsDB().stream().map(Hotel::getId).max(Integer::compareTo).get()+1);
            } catch(Exception ex) {
                hotelMaxId.set(0);
            }
        }catch (Exception ex){

        }
        try{
            setCities(dbm.readCitiesDB());
            try {
                cityMaxId.set(dbm.readCitiesDB().stream().map(City::getId).max(Integer::compareTo).get()+1);
            } catch(Exception ex) {
                cityMaxId.set(0);
            }
        }catch (Exception ex){
            this.cities = new HashSet<>();
        }

    }

    private void setHotels(List<Hotel> hotels){
        this.hotels = hotels;
    }


    private void addHotel(Hotel hotel){
        hotels.add(hotel);
        this.dbm.dumpHotelDB(this.getHotels());
    }


    public void addCity(City city){
        /**
         * Adds a city to a cities list.
         * @param   city    city to add.
         */
        if(!cities.contains(city)){
            cities.add(city);
        }
        dbm.dumpCityDB(this.getCities());
    }

    public void addCity(String cityName) throws IllegalArgumentException{
        /**
         * adds a city to the cities list.
         * @param   cityName    a name of a city.
         */
        if(cityName.isEmpty()||cityName.matches("[\\W\\d]")){
           throw new IllegalArgumentException("Insert legal city Name!");
        }
        City tempCity = new City(cityMaxId.getAndIncrement(),cityName);
        if(!cities.contains(tempCity)){
            cities.add(tempCity);
        }
        dbm.dumpCityDB(this.getCities());
    }


    private void addCity(int id, String name){
        /**
         * Adds a city to the cities list.
         * @param   id      city id.
         * @param   name    city name.
         */
        if(!cities.stream().allMatch(city -> city.getId()==id)){
            cities.add(new City(id,name));
        }
        dbm.dumpCityDB(this.getCities());
    }

    public void removeCity(City city){
        /**
         * Removes a city from the cities list.
         * @param   city    city to be removed.
         */
        Iterator<City> iter = cities.iterator();
        while(iter.hasNext()){
            City curCity = iter.next();
            if(curCity==city){
                cities.remove(curCity);
            }
        }
        dbm.dumpCityDB(this.getCities());
    }

    public List<Hotel> getHotels() {
        return hotels;
    }


    public Set<City> getCities() {
        return cities;
    }

    private void setCities(Set<City> cities) {
        this.cities = cities;
    }

    public void removeHotel(int id){
        /**
         * Removes a hotel from the hotels list.
         * @param   id  id of a hotel to be removed.
         */
        hotels.removeIf(curHotel -> curHotel.getId() == id);
        dbm.dumpRoomDB(this.getRooms());
        dbm.dumpHotelDB(this.getHotels());
    }

    //Deleting hotels
    public void removeHotel(Hotel hotelToRemove){
        /**
         * Removes a hotel from the hotels list.
         * @param   hotelToRemove  a hotel to be removed.
         */
        this.setHotels(this.hotels.stream()
                .filter(hotel -> hotel.getId()==hotelToRemove.getId())
                .collect(Collectors.toList()));
        dbm.dumpRoomDB(this.getRooms());
        dbm.dumpHotelDB(this.getHotels());
    }

    public void updateHotel (int id, Hotel hotel) throws IllegalArgumentException{
        /**
         * Updates a hotel. In fact, drops a hotel and inserts a new one with the same id.
         * Don't forget to reassign rooms and write then read bookings from DB to set actual values.
         * @param   id      id of a hotel to be updated.
         * @param   hotel   a hotel object to be inserted with id.
         */
        //Обновляя отель, не забудьте обновить Bookings. Просто запишите и прочитайте их из базы и в бонирования
        // подтянутся новые данные.
        removeHotel(id);
        City curCity = cities.stream()
                            .filter(city -> hotel.getName().equals(city.getName()))
                            .findFirst()
                            .orElse(null);
        if(curCity!=null) {
            createHotel(id, curCity.getId(), hotel.getName());
        }else {
            throw new IllegalArgumentException("No city with such id!");
        }
        dbm.dumpHotelDB(this.hotels);
    }

    public Room findRoomById(int roomId){
        /**
         * Looks for a room by id.
         * @param   roomId  room id.
         * @return          room object.
         */
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
        /**
         * Looks for a hotel by id.
         * @param   hotelId  hotel id.
         * @return           hotel object.
         */
        for (Hotel hotel : hotels) {
            if(hotel.getId()==hotelId){
                return hotel;
            }
        }
        return null;
    }

    public List<Hotel> getHotelsByCity(int cityId) {
        /**
         * Looks for hotels by city.
         * @param   cityId  city id.
         * @return          list of hotel objects.
         */
        String currCity = this.cities.stream().filter(city -> city.getId()==cityId).findFirst().orElse(null).getName();
        return hotels.stream().filter(hotel -> hotel.getCity().equals(currCity)).collect(Collectors.toList());
    }

    //Creating hotels
    public void createHotel(int cityId, String name) throws IllegalArgumentException{
        /**
         * Creates a new hotel object. You should use this function to create hotels.
         * @param   cityId  city id.
         * @param   name    hotel name
         */
        String currCity;
        try {
            currCity  = this.cities.stream().filter(city -> city.getId() == cityId).findFirst().orElse(null).getName();
        } catch(Exception ex){
            throw  new IllegalArgumentException("No such City found!");
        }
        if(this.hotels.stream().allMatch(hotel -> !hotel.getName().equals(name)||
                (!currCity.isEmpty()&&!hotel.getCity().equals(currCity)))) {
            this.addHotel(new Hotel(this.hotelMaxId.getAndIncrement(), currCity, name));
        }
        dbm.dumpHotelDB(this.getHotels());
    }

    public void createHotel(int hotelId, int cityId, String name) throws IllegalArgumentException{
        /**
         * Creates a new hotel object.
         * You should not use this function to create hotels unless you know what you are doing.
         * @param   hotelId the hotel's id.
         * @param   cityId  city id.
         * @param   name    hotel name
         */
        String currCity = null;
        try {
            currCity = this.cities.stream().filter(city -> city.getId()==cityId).findFirst().orElse(null).getName();
        } catch(Exception ex){
            throw  new IllegalArgumentException("No such City found!");
        }
        if(this.hotels.stream().allMatch(hotel -> hotel.getId()!=hotelId)){
            this.addHotel(new Hotel(hotelId,currCity,name));
        }
        if(hotelId>=this.hotelMaxId.get()){
            this.hotelMaxId.set(hotelId+1);
        }
        dbm.dumpHotelDB(this.getHotels());
    }
    //Creating rooms
    public void createRoom(Hotel hotel, byte person, BigDecimal price){
        /**
         * Creates a new room. You should use this function to create rooms.
         * @param   hotel   hotel where the room will be placed.
         * @param   preson  room catacity.
         * @param   price   room price per day.
         */
        hotel.addRoom(new Room(this.roomMaxId.getAndIncrement(),person,price));
        dbm.dumpRoomDB(this.getRooms());
        dbm.dumpHotelDB(this.getHotels());
    }

    public void createRoom(int hotelId, byte person, BigDecimal price) throws IllegalArgumentException{
        /**
         * Creates a new room. You should use this function to create rooms.
         * @param   hotelId id of a hotel where the room will be placed.
         * @param   preson  room catacity.
         * @param   price   room price per day.
         */
        Hotel resHotel=this.hotels.stream().filter(hotel -> hotel.getId()==hotelId).findFirst().orElse(null);
        if(resHotel!=null){
            resHotel.addRoom(new Room(this.roomMaxId.getAndIncrement(),person,price));

        }else {
            throw new IllegalArgumentException("No such Hotel found!");
        }
        dbm.dumpRoomDB(this.getRooms());
        dbm.dumpHotelDB(this.getHotels());
    }

    public void createRoom(Hotel hotel, int roomId, byte person, BigDecimal price) throws IllegalArgumentException{
        /**
         * Creates a new room. You should not use this function to create rooms unless you know what you are doing.
         * @param   hotel   hotel where the room will be placed
         * @param   roomId  id of the room.
         * @param   preson  room catacity.
         * @param   price   room price per day.
         */
        if(!this.hotels.contains(hotel)) throw new IllegalArgumentException("No such Hotel found!");
        hotel.addRoom(new Room(roomId,person,price));
        if(roomId>=this.roomMaxId.get()){
            this.roomMaxId.set(roomId+1);
        }
        dbm.dumpRoomDB(this.getRooms());
        dbm.dumpHotelDB(this.getHotels());
    }

    public void createRoom(int hotelId, int roomId, byte person, BigDecimal price) throws IllegalArgumentException{
        /**
         * Creates a new room. You should not use this function to create rooms unless you know what you are doing.
         * @param   hotelId id of a hotel where the room will be placed.
         * @param   roomId  id of the room.
         * @param   preson  room catacity.
         * @param   price   room price per day.
         */
        Hotel resHotel=this.hotels.stream().filter(hotel -> hotel.getId()==hotelId).findFirst().orElse(null);
        if(resHotel!=null){
            resHotel.addRoom(new Room(roomId,person,price));
        }else {
            throw new IllegalArgumentException("No such Hotel found!");
        }
        if(roomId>=this.roomMaxId.get()){
            this.roomMaxId.set(roomId+1);
        }
        dbm.dumpRoomDB(this.getRooms());
        dbm.dumpHotelDB(this.getHotels());
    }

    public List<Room> getRooms(){
        List<Room> rooms = new ArrayList<>();
        for (Hotel hotel : this.hotels) {
            rooms.addAll(hotel.getRooms());
        }
        return rooms;
    }

    public Hotel getLastAddedHotel(){
        /**
         * Gets the last created hotel.
         * @return          last created hotel
         */
        if(getHotels().isEmpty()) return null;
        return getHotels().get(getHotels().size()-1);
    }

    public Hotel findHotelByName(String hotelName) {
        /**
         * Gets a hotel by name.
         * @return          hotel
         */
        return hotels.stream().filter(h->h.getName().equals(hotelName)).findFirst().orElse(null);
    }
}
