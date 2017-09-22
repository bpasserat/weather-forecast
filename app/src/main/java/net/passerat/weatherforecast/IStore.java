package net.passerat.weatherforecast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by bpasserat on 21/09/2017.
 */

public interface IStore<T> {


    interface OnFetchListener {
        void onFetch( ) ;
    }

    void insert( T obj);
    void delete( T obj ) ;
    ArrayList<T> find (IMatcher<T> matcher );
    void load( File file);
    void save( File file );
    void fetch(String url);
    ArrayList<T> getAll();

}
