package net.passerat.weatherforecast;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by bpasserat on 21/09/2017.
 */

public class WeatherElement implements Serializable{

    public enum WeatherState  {
         SUN  ,
        CLOUD ,
        RAIN  ,
        SNOW ,
        THUNDER
    }


    public WeatherState weatherState ;
    public float temperatureCelsius ;
    public float pressurePa ;
    public float windSpeedKmHour ;
    public float windOrientationDegree ;
    public Date date ;
    public String city ;


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
