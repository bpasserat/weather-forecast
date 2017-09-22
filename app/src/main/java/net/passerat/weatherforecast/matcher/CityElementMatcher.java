package net.passerat.weatherforecast.matcher;

import net.passerat.weatherforecast.data.CityElement;

/**
 * Created by bpasserat on 22/09/2017.
 */

public class CityElementMatcher {

    public static class MatchName implements IMatcher<CityElement> {

        String mName ;

        public MatchName(String name ) {
            this.mName = name ;
        }
        @Override
        public boolean match(CityElement elem) {
            if (mName.equals(elem.name)) {
                return true ;
            }
            return false;
        }
    }
}
