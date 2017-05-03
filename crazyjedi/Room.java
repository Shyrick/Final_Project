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
}
