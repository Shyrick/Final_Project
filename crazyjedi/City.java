package crazyjedi;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Vlad on 03.05.2017.
 */
public class City {

    private AtomicInteger counter = new AtomicInteger(0);
    private int id;
    private String name;

    public City(String name) throws IllegalArgumentException{
        if(name==null||name.length()==0){
            throw new IllegalArgumentException("City name must be filled.");
        }
        this.id = counter.getAndIncrement();
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        return getName().equals(city.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }


}
