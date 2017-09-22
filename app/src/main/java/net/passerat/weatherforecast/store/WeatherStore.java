package net.passerat.weatherforecast.store;

import android.os.AsyncTask;

import net.passerat.weatherforecast.matcher.IMatcher;
import net.passerat.weatherforecast.data.WeatherElement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Created by bpasserat on 21/09/2017.
 */

public class WeatherStore extends AStore<WeatherElement> {

    private static final int MAX_RANDOM_ELEMENT = 15 ;

    public WeatherStore() {

    }

    WeatherElement.WeatherState getRandomState() {
        WeatherElement.WeatherState state ;
        int min = 0 ;
        int max = 4 ;
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);


        switch (randomNum) {
            case 0 :
                state = WeatherElement.WeatherState.CLOUD;
                break;
            case 1 :
                state = WeatherElement.WeatherState.SUN;
                break;
            case 2 :
                state = WeatherElement.WeatherState.THUNDER;
                break ;
            case 3 :
                state = WeatherElement.WeatherState.SNOW;
                break ;
            case 4 :
                state = WeatherElement.WeatherState.RAIN;
                break;
            default :
                state = WeatherElement.WeatherState.SUN;
                break;
        }
        return state ;
    }

    String getRandomCity() {
        String city ;
        int min = 0 ;
        int max = 2 ;
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        switch (randomNum) {
            case 0 :
                city = "Toulouse";
                break ;
            case 1:
                city = "Paris" ;
                break;
            case 2 :
                city ="Lyon" ;
                break;
            default :
                city = "Toulouse" ;
                break ;
        }
        return city ;
    }

    private ArrayList<WeatherElement> randomWeatherElements(String city ) {
        ArrayList<WeatherElement> ret = new ArrayList<>();
        Date date = Calendar.getInstance().getTime();
        for( int i = 0 ; i < MAX_RANDOM_ELEMENT ; i++  ) {
            Date newDate = new Date( date.getTime() + TimeUnit.DAYS.toMillis(i));
            float temperature =  ThreadLocalRandom.current().nextFloat() * 20.0f  + 10.0f ;
            float pressure =  ThreadLocalRandom.current().nextFloat() * 200.0f  + 900.0f ;
            float wind =  ThreadLocalRandom.current().nextFloat() * 70.0f ;
            float windOrientation =  ThreadLocalRandom.current().nextFloat() * 360.0f ;
            WeatherElement elem = new WeatherElement(city ,
                                                    newDate ,
                                                    getRandomState(),
                                                    temperature ,
                                                    pressure ,
                                                    wind ,
                                                    windOrientation );
            ret.add(elem);
        }
        return ret ;
    }



    @Override
    public ArrayList<WeatherElement> find( IMatcher<WeatherElement> matcher) {
        ArrayList<WeatherElement> ret = new ArrayList<>();
        for ( WeatherElement elem : mElements) {
            if (matcher.match(elem)) {
                ret.add(elem);
            }
        }
        return ret ;
    }


    public void fetch(final String city ) {
        //TODO : chargement depuis le réseau
        // cette fonction est asynchrone et son retour doit déclencher onChange
        AsyncTask<Void , Void , ArrayList<WeatherElement>> asyncTask =
                new AsyncTask<Void, Void, ArrayList<WeatherElement>>() {
            @Override
            protected ArrayList<WeatherElement> doInBackground(Void... voids) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    //La precision du sleep n'est pas importante
                }
                return randomWeatherElements(city);
            }

            @Override
            protected void onPostExecute(ArrayList<WeatherElement> res) {
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
