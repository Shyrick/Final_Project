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
        for (int i = 0; i < rooms.size(); i++) {
            if(rooms.get(i).getId() == roomId){rooms.remove(i);}
        }
    }

    public List<Room> getRooms(){
        return rooms;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("");
        sb.append("Отель #");
        sb.append(id);
        sb.append(", Город ");
        sb.append(city);
        sb.append(", Название ");
        sb.append(name);
        sb.append("\t\t");
        sb.append("Комнаты: ");
        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            sb.append("\t" + room);
            if(i%2==0){
                sb.append("\t");
            } else{
                sb.append("\t");
            }
        }
        return  sb.toString();
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
        /**
         *  Gets the largest room id from the hotel's rooms list.
         *  @return largest id.
         */
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
