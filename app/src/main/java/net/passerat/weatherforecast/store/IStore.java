package net.passerat.weatherforecast.store;

import net.passerat.weatherforecast.matcher.IMatcher;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by bpasserat on 21/09/2017.
 *
 * This interfce is to be used to implement different kinds of data store
 *
 */

public interface IStore<T> {

    /**
     *  This interface is done to implement listener when the fetch function of IStore returned
     */
    interface OnFetchListener {
        void onFetch( ) ;
    }

    /**
     * This function insert an object in the store
     * @param obj insert object  obj in the store
     */
    void insert( T obj);

    /**
     * This function delete an object in the store
     * @param obj delete object obj from the store
     */
    void delete( T obj ) ;

    /**
     * This function find objects in the store
     * @param matcher the matcher to check if elem match to criteria
     * @return an ArrayList of object that returned true with matcher test
     */
    ArrayList<T> find (IMatcher<T> matcher );

    /**
     * This function load data from file to the store
     * @param file File which store the data
     */
    void load( File file);

    /**
     * Save the data to the file
     * @param file File to save the data
     */
    void save( File file );

    /**
     * Fetch data from an URL
     * @param url url parameter where are the datas
     */
    void fetch(String url);

    /**
     * Return all the elements from the store
     * @return return the complete list of elements in the store
     */
    ArrayList<T> getAll();

}
