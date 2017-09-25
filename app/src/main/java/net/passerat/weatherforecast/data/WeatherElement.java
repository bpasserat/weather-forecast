package net.passerat.weatherforecast.data;

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

    public float temperatureToFarenheit( ) {
        return 1.8f*this.temperatureCelsius + 32 ;
    }

    public float windSpeedToMilePerHour() {
        return this.windSpeedKmHour / 1.6034f ;
    }

}
