package net.passerat.weatherforecast.data;

/**
 * Created by bpasserat on 22/09/2017.
 *
 * This class implement the information to describe a city element
 *
 */

public class CityElement {

    /**
     * Constructor for the CityElement class
     * @param name the city name
     * @param latitude the latitude  of the city
     * @param longitude  the longitude of the city
     */
    public CityElement(String name , float latitude , float longitude) {
        this.name = name ;
        this.latitude = latitude ;
        this.longitude = longitude ;
    }


    public float longitude ;
    public float latitude ;
    public String name ;

    /**
     *
     * @return String with the name of the city
     */

    public String toString() {
        return name;
    }
}
