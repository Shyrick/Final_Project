package crazyjedi;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Vlad on 30.04.2017.
 */
public class Room {

    private int id;
    private static AtomicInteger counter=new AtomicInteger(0);
    private byte person;
    private BigDecimal price;

    public Room(byte person, BigDecimal price) {
        id=counter.getAndIncrement();
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
}
