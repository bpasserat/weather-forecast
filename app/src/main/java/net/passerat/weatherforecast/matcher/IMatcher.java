package net.passerat.weatherforecast.matcher;

/**
 * Created by bpasserat on 22/09/2017.
 *
 * This interface is done to implement matchers for the find function of IStore interface
 *
 */

public interface IMatcher<T> {

    /**
     * This function test an object to against some criteria define in the class which implement this interface
     * @param elem The current element to be tested
     * @return true if the element match , false if do not match
     */
    boolean match( T elem);
}
