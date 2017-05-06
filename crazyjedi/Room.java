package crazyjedi;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Vlad on 30.04.2017.
 */
public class Room {

    private int id;
    private byte person;
    private BigDecimal price;

    public Room(int id,byte person, BigDecimal price) {
        this.id=id;
        this.person = person;
        this.price = price;
    }

    public byte getPerson() {
        return person;
    }

    public void setPerson(byte person) {
        this.person = person;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getId(){
        return id;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", person=" + person +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (getId() != room.getId()) return false;
        if (getPerson() != room.getPerson()) return false;
        return getPrice() != null ? getPrice().equals(room.getPrice()) : room.getPrice() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (int) getPerson();
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        return result;
    }
}
