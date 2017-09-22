package net.passerat.weatherforecast.store;


import net.passerat.weatherforecast.matcher.IMatcher;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by bpasserat on 22/09/2017.
 */

public abstract class AStore<T> implements IStore<T> {
    protected ArrayList<T> mElements= new ArrayList<>();
    protected IStore.OnFetchListener onFetchListener = null ;

    @Override
    public void insert(T obj) {
        mElements.add(obj);
    }

    @Override
    public void delete(T obj) {
        mElements.remove(obj);
    }

    @Override
    public ArrayList<T> find(IMatcher<T> matcher) {
        ArrayList<T> ret = new ArrayList<>();
        for ( T elem : mElements ) {
            if (matcher.match(elem)) {
                ret.add(elem);
            }
        }
        return ret;
    }

    @Override
    public void load(File file) {
        //TODO
    }

    @Override
    public void save(File file) {
        //TODO
    }

    public void setOnFetchListener( IStore.OnFetchListener onFetchListener ) {
        this.onFetchListener = onFetchListener ;
    }

    public T get(int idx) {
        return mElements.get(idx);
    }

    public ArrayList<T> getAll() {
        return mElements;
    }
}
