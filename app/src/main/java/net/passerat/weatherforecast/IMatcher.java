package net.passerat.weatherforecast;

/**
 * Created by bpasserat on 22/09/2017.
 */

public interface IMatcher<T> {

    boolean match( T elem);
}
