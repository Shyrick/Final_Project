package crazyjedi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by Vlad on 30.04.2017.
 */
public class Hotel {

    private int id;
    private int cityId;
    private String name;
    private List<Room> rooms = null;

    public Hotel(int id, int cityId, String name) {
        this.id=id;
        this.cityId = cityId;
        this.name = name;
        rooms=new ArrayList<>();
    }

    public List<String> getRoomIds(){
        List<String> res = new ArrayList<>();
        for (Room room : this.getRooms()) {
            res.add(Integer.toString(room.getId()));
        }
        return res;
    }

    public int getId(){
        return id;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
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
                ", cityId=" + cityId +
                ", name='" + name + '\'' +
                ", rooms=" + rooms +
                '}';
    }
}
