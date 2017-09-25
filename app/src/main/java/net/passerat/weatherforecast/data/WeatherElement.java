package net.passerat.weatherforecast.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by bpasserat on 21/09/2017.
 *
 * This class describe a weather element
 *
 */

public class WeatherElement implements Serializable{

    /**
     *  This enum gives the differents state of the sky
     */
    public enum WeatherState  {
        SUN  ,
        CLOUD ,
        RAIN  ,
        SNOW ,
        THUNDER
    }

    /**
     *  Weather sky state
     */
    public WeatherState weatherState ;


    /**
     *  Temperature in °C
     */
    public float temperatureCelsius ;

    /**
     *  Pressure in Pascal
     */
    public float pressurePa ;

    /**
     *  Wind speed km/h
     */
    public float windSpeedKmHour ;

    /**
     * Wind Orientation
     */
    public float windOrientationDegree ;

    /**
     * Date for the forecast
     */
    public Date date ;

    /**
     * City for the forecast
     */
    public String city ;


    /**
     *  Constructor for a city element
     * @param city city name where this weather
     * @param date date of the forecast
     * @param weatherState state of the sky
     * @param temperatureCelsius Temperature in °C
     * @param pressurePa Pressure in Pascal
     * @param windSpeedKmHour Wind speed in km/h
     * @param windOrientationDegree Wind orientation in km/h
     */
    public WeatherElement( String  city , Date date , WeatherState weatherState , float temperatureCelsius , float pressurePa ,
                           float windSpeedKmHour , float windOrientationDegree ) {
        this.city = city ;
        this.date = date ;
        this.weatherState = weatherState ; // TODO : check state and throw error if state unknown
        this.temperatureCelsius = temperatureCelsius;
        this.pressurePa = pressurePa ;
        this.windSpeedKmHour = windSpeedKmHour ;
        this.windOrientationDegree = windOrientationDegree ;
    }

    private static final String KEY_CITY = "city" ;
    private static final String KEY_STATE = "state" ;
    private static final String KEY_PRESSURE = "pressure" ;
    private static final String KEY_TEMPERATURE = "temperature" ;
    private static final String KEY_WIND_SPEED = "windSpeed" ;
    private static final String KEY_WIND_ORIENTATION = "windOrientation" ;

    /**
     * Constructor from a JSONObject that can be received from the network !
     * @param obj JSONObject matching to a WeatherElement
     * @throws Exception If the object do not have all properties
     */
    public WeatherElement(JSONObject obj) throws  Exception {
        this.city = obj.getString("city");
        int state = obj.getInt("state");
        switch (state) {
            case 0 :
                this.weatherState = WeatherState.SUN;
                break;
            case 1 :
                this.weatherState = WeatherState.THUNDER;
                break;
            case 2 :
                this.weatherState = WeatherState.CLOUD;
                break ;
            case 3 :
                this.weatherState = WeatherState.RAIN;
                break;
            case 4 :
                 this.weatherState = WeatherState.SNOW;
                 break ;
            default:
                throw new Exception("Unknown weather state") ;
        }
        this.pressurePa = (float) obj.getDouble("pressure");
        this.temperatureCelsius = (float) obj.getDouble("temperature");
        this.windSpeedKmHour = (float) obj.getDouble("windSpeed");
        this.windOrientationDegree = (float) obj.getDouble("windOrientation");
    }

    private int getWeatherStateIntValue( WeatherState state) {
        switch (state) {
            case SUN:
                return 0 ;
            case CLOUD:
                return 2 ;
            case RAIN:
                return 3;
            case SNOW:
                return 4;
            case THUNDER:
                return 1;
        }
        return -1;
    }

    /**
     *  Convert a WeatherElement to a JSONObject
     * @return JSONObject
     * @throws JSONException
     */
    public JSONObject toJSON() throws JSONException {
        JSONObject obj = new JSONObject( ) ;
        obj.put(KEY_CITY , this.city );
        obj.put(KEY_TEMPERATURE , this.temperatureCelsius );
        obj.put(KEY_PRESSURE , this.pressurePa );
        obj.put(KEY_STATE , getWeatherStateIntValue(this.weatherState) );
        obj.put(KEY_WIND_ORIENTATION , this.windOrientationDegree);
        obj.put(KEY_WIND_SPEED , this.windSpeedKmHour );
        return obj;
    }

    /**
     * Convert temperature from °C to °F
     * @return temperature in °F
     */
    public float temperatureToFarenheit( ) {
        return 1.8f*this.temperatureCelsius + 32 ;
    }

    /**
     * Convert Wind Speed from km/h to miles/h
     * @return wind speed in miles/h
     */
    public float windSpeedToMilePerHour() {
        return this.windSpeedKmHour / 1.6034f ;
    }



}
