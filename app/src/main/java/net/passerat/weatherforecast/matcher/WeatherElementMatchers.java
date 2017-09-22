package net.passerat.weatherforecast.matcher;

import net.passerat.weatherforecast.data.WeatherElement;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bpasserat on 22/09/2017.
 */

public class WeatherElementMatchers {

    public static class MatchDateAndCity implements IMatcher<WeatherElement> {

        private Date date ;
        private String city ;

        public MatchDateAndCity(Date date  , String city) {
            this.date= date ;
            this.city = city ;
        }

        @Override
        public boolean match(WeatherElement elem) {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
            if ( elem.city.equals(city) && fmt.format(elem.date).equals(fmt.format(this.date) )) {
                return true ;
            }
            return false ;
        }
    }


}
