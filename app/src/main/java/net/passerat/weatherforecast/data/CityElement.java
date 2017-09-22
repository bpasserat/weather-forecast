package net.passerat.weatherforecast.data;

/**
 * Created by bpasserat on 22/09/2017.
 */

public class CityElement {

    public CityElement(String name , float latitude , float longitude) {
        this.name = name ;
        this.latitude = latitude ;
        this.longitude = longitude ;
    }

    public float longitude ;
    public float latitude ;
    public String name ;

    public String toString() {
        return name;
    }
}
