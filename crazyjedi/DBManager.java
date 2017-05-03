package crazyjedi;
import java.io.*;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Vlad on 03.05.2017.
 */
public class DBManager{

    String currentDir = System.getProperty("user.dir")+"/crazyjedi/db/";
    private String roomFilePath;
    private String hotelFilePath;

    public DBManager(String roomFilePath, String hotelFilePath) {
        this.roomFilePath = currentDir+roomFilePath;
        this.hotelFilePath = currentDir+hotelFilePath;
    }

    public String getRoomFilePath() {
        return roomFilePath;
    }

    public void setRoomFilePath(String roomFilePath) {
        this.roomFilePath = roomFilePath;
    }

    public String getHotelFilePath() {
        return hotelFilePath;
    }

    public void setHotelFilePath(String hotelFilePath) {
        this.hotelFilePath = hotelFilePath;
    }

    private String roomStringJoin(Room room){
        return Integer.toString(room.getId())+
                    "\t"+Byte.toString(room.getPerson())+
                    "\t"+room.getPrice().toString();
    }

    //READ-WRITE ROOM
    public void dumpRoomDB(List<Room> rooms) {
        File roomFile = new File(this.roomFilePath);
        try{
            roomFile.getParentFile().mkdirs();
            roomFile.createNewFile();
            Function<Room,String> roomStringJoin = room -> Integer.toString(room.getId())+
                                                            "\t"+Byte.toString(room.getPerson())+
                                                            "\t"+room.getPrice().toString()+"\n";
            BufferedWriter writer = new BufferedWriter(new FileWriter(roomFile));
            for (String roomString : rooms.stream().map(roomStringJoin).collect(Collectors.toList())) {
                writer.write(roomString);
            }
            writer.close();
        } catch (IOException ex) {
            throw new Error("Can't write room table!");
        }
    }

    public List<Room> readRoomsDB() {
        String line;
        List<String> roomBlueprints = new ArrayList<>();
        BufferedReader reader = null;
        List<Room> rooms = new ArrayList<>();
        try{
            reader = new BufferedReader(new FileReader(this.roomFilePath));
            while((line = reader.readLine()) != null){
                roomBlueprints.add(line);
            }
        } catch (IOException ex){
            throw new Error("Can't read rooms table!");
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (String roomBlueprint : roomBlueprints) {
            ArrayList<String> tempRoom = new ArrayList<>();
            Scanner sc = new Scanner(roomBlueprint);
            sc.useDelimiter("\t");
            while(sc.hasNext()){
                tempRoom.add(sc.next());
            }
            int id = Integer.parseInt(tempRoom.get(0));
            byte person = Byte.parseByte(tempRoom.get(1));
            BigDecimal price = new BigDecimal(tempRoom.get(2));
            rooms.add(new Room(id,person,price));
        }
        return rooms;
    }

    //READ-WRITE HOTEL

    public void dumpHotelDB(List<Hotel> hotels) {
        File hotelFile = new File(this.hotelFilePath);
        try{
            hotelFile.getParentFile().mkdirs();
            hotelFile.createNewFile();

            Function<Hotel,String> hotelStringJoin = hotel -> Integer.toString(hotel.getId())+
                    "\t"+Integer.toString(hotel.getCityId())+
                    "\t"+hotel.getName()+
                    "\t"+String.join(";",hotel.getRoomIds().stream().collect(Collectors.toList()))+"\n";
            BufferedWriter writer = new BufferedWriter(new FileWriter(hotelFile));
            for (String hotelString : hotels.stream().map(hotelStringJoin).collect(Collectors.toList())) {
                writer.write(hotelString);
            }
            writer.close();
        } catch (IOException ex) {
            throw new Error("Can't write hotel table!");
        }
    }

    public List<Hotel> readHotelsDB(){

        List<Room> tempRooms = readRoomsDB();

        String line;
        List<String> hotelBlueprints = new ArrayList<>();
        BufferedReader reader = null;
        List<Hotel> hotels = new ArrayList<>();
        try{
            reader = new BufferedReader(new FileReader(this.hotelFilePath));
            while((line = reader.readLine()) != null){
                hotelBlueprints.add(line);
            }
        } catch (IOException ex){
            throw new Error("Can't read hotel table!");
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (String hotelBlueprint : hotelBlueprints) {
            ArrayList<String> tempHotels = new ArrayList<>();
            Scanner sc = new Scanner(hotelBlueprint);
            sc.useDelimiter("\t");

            while(sc.hasNext()){
                tempHotels.add(sc.next());
            }
            int id = Integer.parseInt(tempHotels.get(0));
            int cityId = Integer.parseInt(tempHotels.get(1));
            String name = tempHotels.get(2);
            Scanner roomSc = new Scanner(tempHotels.get(3));
            roomSc.useDelimiter(";");
            Set<Integer> roomIds = new HashSet<>();
            while(roomSc.hasNext()){
                roomIds.add(Integer.parseInt(roomSc.next()));
            }

            Hotel tempHotel = new Hotel(id,cityId,name);


            List<Room> thisHotelRooms = tempRooms.stream()
                    .filter(room -> roomIds.contains(room.getId()))
                    .collect(Collectors.toList());
            for (Room r: thisHotelRooms) {
                tempHotel.addRoom(r);
            }
            hotels.add(tempHotel);
        }
        return hotels;

    }

}
