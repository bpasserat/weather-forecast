package net.passerat.weatherforecast;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by bpasserat on 22/09/2017.
 */

public class CityStore extends AStore<CityElement> {

    ArrayList<String> mCityList= new ArrayList<>(Arrays.asList(
            "Paris",
            "Lyon",
            "Marseille",
            "Toulouse",
            "Grenoble",
            "Montpelier",
            "Dijon",
            "Lille",
            "Brest",
            "Le Mans",
            "Rennes",
            "Bordeaux",
            "Auxerre",
            "Monaco"
    ));

    private ArrayList<CityElement> getCities() {
        ArrayList<CityElement> cities =  new ArrayList<>();
        for ( String name : mCityList) {
            float longitude  = (ThreadLocalRandom.current().nextFloat() - 0.5f) * 4.0f  ;
            float latitude = ThreadLocalRandom.current().nextFloat() * 9.0f + 40.0f ;
            cities.add(new CityElement(name , latitude , longitude));
        }
        return cities ;
    }


    public void fetch(String url ) {
        //TODO : chargement depuis le réseau
        // cette fonction est asynchrone et son retour doit déclencher onChange
        AsyncTask<Void , Void , ArrayList<CityElement>> asyncTask =
                new AsyncTask<Void, Void, ArrayList<CityElement>>() {
                    @Override
                    protected ArrayList<CityElement> doInBackground(Void... voids) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            //La precision du sleep n'est pas importante
                        }
                        return getCities();
                    }

                    @Override
                    protected void onPostExecute(ArrayList<CityElement> res) {
                        mElements.clear();
                        mElements.addAll(res);
                        if (onFetchListener!=null) {
                            onFetchListener.onFetch();
                        }
                    }

                };
        asyncTask.execute();
    }


}
