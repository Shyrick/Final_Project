package crazyjedi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Vlad on 30.04.2017.
 */
public class Hotel {

    private int id;
    private String city;
    private String name;
    private List<Room> rooms = null;

    public Hotel(int id, String city, String name) {
        this.id=id;
        this.city = city;
        this.name = name;
        rooms=new ArrayList<>();
    }

    public List<String> getRoomStringIds(){
        List<String> res = new ArrayList<>();
        for (Room room : this.getRooms()) {
            res.add(Integer.toString(room.getId()));
        }
        return res;
    }

    public int getId(){
        return id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addRoom(Room room){
        this.rooms.add(room);
    }

    //REMOVE ROOM
    public void removeRoom(Room room){
        rooms.remove(room);
    }

    public void removeRoom(int roomId){
        Iterator<Room> iter = rooms.iterator();
        while(iter.hasNext()){
            Room curRoom = iter.next();
            if(curRoom.getId()==roomId){
                rooms.remove(curRoom);
            }
        }
    }

    public List<Room> getRooms(){
        return rooms;
    }


    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", cityId=" + city +
                ", name='" + name + '\'' +
                ", rooms=" + rooms +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hotel hotel = (Hotel) o;

        if (getId() != hotel.getId()) return false;
        if (!city.equals(hotel.city)) return false;
        if (!getName().equals(hotel.getName())) return false;
        return getRooms().equals(hotel.getRooms());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + city.hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getRooms().hashCode();
        return result;
    }

    public int getMaxRoomId() {
        int maxId = 0;
        if (rooms != null || rooms.size()!=0) return 0;

        for (int i = 0; i < rooms.size(); i++) {
            if (maxId < rooms.get(i).getId()) {
                maxId = rooms.get(i).getId();
                break;
            }
        }
        return maxId;
    }
}
