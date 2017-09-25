package net.passerat.weatherforecast.data;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bpasserat on 22/09/2017.
 *
 * This class implement the information to describe a city element
 *
 */

public class CityElement {

    private static final String KEY_NAME = "name" ;
    private static final String KEY_LONGITUDE = "longitude" ;
    private static final String KEY_LATITUDE = "latitude" ;

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

    /**
     * Constructor from a JSONObject that can be received from the network !
     * @param obj JSONObject matching to a CityElement
     * @throws Exception If the object do not have all properties
     */
    public CityElement(JSONObject obj) throws  JSONException {
        this.name = obj.getString(KEY_NAME);
        this.longitude = (float)obj.getDouble(KEY_LONGITUDE);
        this.latitude = (float)obj.getDouble(KEY_LATITUDE);
    }

    public float longitude ;
    public float latitude ;
    public String name ;

    /**
     *  Convert a CityElement to a JSONObject
     * @return JSONObject
     * @throws JSONException
     */
    public String toString() {
        return name;
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject obj = new JSONObject() ;
        obj.put(KEY_NAME , this.name);
        obj.put(KEY_LATITUDE , (double)this.latitude) ;
        obj.put(KEY_LONGITUDE , (double)this.longitude);
        return obj;
    }
}
