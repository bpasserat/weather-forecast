package net.passerat.weatherforecast;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import net.passerat.weatherforecast.data.CityElement;
import net.passerat.weatherforecast.data.WeatherElement;
import net.passerat.weatherforecast.matcher.CityElementMatcher;
import net.passerat.weatherforecast.matcher.WeatherElementMatchers;
import net.passerat.weatherforecast.store.CityStore;
import net.passerat.weatherforecast.store.IStore;
import net.passerat.weatherforecast.store.WeatherStore;

import java.util.ArrayList;


public class WeatherActivity extends AppCompatActivity implements
            SharedPreferences.OnSharedPreferenceChangeListener {


    private static final String CITY = "city" ;
    private static final String CITY_DEFAULT = "Toulouse" ;

    private int mSelectedCityIdx = 0 ;

    private CityElement mSelectedCityElem ;

    private WeatherStore mWeatherStore = new WeatherStore() ;
    private CityStore mCityStore = new CityStore();

    public ArrayAdapter<CityElement> mCityAdapter ;

    WeatherAdapter mWeatherAdapter ;
    RecyclerView mRecyclerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String city = preferences.getString(CITY , CITY_DEFAULT);

        mCityStore.load(null);
        mCityStore.setOnFetchListener( cityListener);

        ArrayList<CityElement> cities = mCityStore.find(new CityElementMatcher.MatchName(city));
        if ( cities.size()==0) {
            mCityStore.fetch("/myapi");
        } else {
            mSelectedCityElem = cities.get(0);
            mSelectedCityIdx = mCityStore.getAll().indexOf(mSelectedCityElem);
            TextView tvCity = (TextView) findViewById(R.id.city);
            tvCity.setText(mSelectedCityElem.name);
            fetchData();
        }

        mCityAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_activated_1,mCityStore.getAll() );

        mWeatherStore.setOnFetchListener( weatherListener );
        mWeatherStore.load(null);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mWeatherAdapter = new WeatherAdapter(mWeatherStore.getAll());
        mRecyclerView.setAdapter(mWeatherAdapter);

    }



    private IStore.OnFetchListener cityListener = new IStore.OnFetchListener() {
        @Override
        public void onFetch() {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);
            sharedPref.registerOnSharedPreferenceChangeListener(WeatherActivity.this);
            String city = sharedPref.getString( CITY  , CITY_DEFAULT ) ;
            try {
                mSelectedCityElem = mCityStore.find(new CityElementMatcher.MatchName(city)).get(0);
                mSelectedCityIdx = mCityStore.getAll().indexOf(mSelectedCityElem);
                TextView tvCity = (TextView) findViewById(R.id.city);
                tvCity.setText(mSelectedCityElem.name);
            } catch (Exception e) {
                //TODO city Unknown should not happen
                Toast toast = Toast.makeText(WeatherActivity.this, R.string.unknown_city , Toast.LENGTH_LONG);
                toast.show();
            }
            mCityAdapter.notifyDataSetChanged();
            fetchData();
        }
    };

    private IStore.OnFetchListener weatherListener = new IStore.OnFetchListener() {
        @Override
        public void onFetch() {

            WeatherElementMatchers.MatchCity matcher = new WeatherElementMatchers.MatchCity(mSelectedCityElem.name);

            ArrayList<WeatherElement> elements = mWeatherStore.find( matcher );
            if (elements.size() == 0 ) {
                Toast toast = Toast.makeText(WeatherActivity.this ,R.string.data_not_matching, Toast.LENGTH_LONG);
                toast.show();
            } else {
                mWeatherAdapter.mDataset = elements;
                mWeatherAdapter.notifyDataSetChanged();
                mRecyclerView.invalidate();

                ProgressBar pBar = (ProgressBar) findViewById(R.id.progressBar);
                pBar.setVisibility(View.INVISIBLE);
            }
        }
    };

    private void fetchData() {
        ProgressBar pbar = (ProgressBar) findViewById(R.id.progressBar);
        pbar.setVisibility(View.VISIBLE);
        if ( mSelectedCityElem != null ) {
            mWeatherStore.fetch(mSelectedCityElem.name);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_weather, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings :
                DialogFragment dialogFragment = ConfigureCityFragment.newInstance(mSelectedCityIdx);
                dialogFragment.show(getSupportFragmentManager(), "my_city");
                return true;
            case R.id.action_update :
                fetchData();
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     *  Function called from Dialog when a city is selected .
     *  It will update the city in preferences
     * @param id Index or if of the city in the city store
     */
    public void onSelectCity(int id) {
        mSelectedCityIdx = id ;
        CityElement city = mCityStore.get(id) ; //TODO : check get return value
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CITY , city.name);
        editor.commit();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if ( s.equals(CITY)) {
            String cityName = sharedPreferences.getString(s , null);
            try {
                mSelectedCityElem = mCityStore.find(new CityElementMatcher.MatchName(cityName)).get(0);
                mSelectedCityIdx = mCityStore.getAll().indexOf(mSelectedCityElem);
                TextView tvCity = (TextView) findViewById(R.id.city);
                tvCity.setText(mSelectedCityElem.name);
                fetchData();
            } catch (Exception e) {
                Toast toast = Toast.makeText(this, R.string.unknown_city , Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCityStore.save(null);
        mWeatherStore.save(null);

    }
}
