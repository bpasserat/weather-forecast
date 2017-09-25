package net.passerat.weatherforecast;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class WeatherActivity extends AppCompatActivity implements
            SharedPreferences.OnSharedPreferenceChangeListener {


    private static final String CITY = "city" ;
    private static final String CITY_DEFAULT = "Toulouse" ;

    private int mSelectedCityIdx = 0 ;

    private CityElement mSelectedCityElem ;

    private WeatherStore mWeatherStore = new WeatherStore() ;
    private CityStore mCityStore = new CityStore();

    public ArrayAdapter<CityElement> mCityAdapter ;

    // Constants
    // The authority for the sync adapter's content provider
    public static final String AUTHORITY = "net.passerat.weatherforcast.provider";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "passerat.net";
    // The account name
    public static final String ACCOUNT = "dummyaccount";
    // Instance fields
    Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCityStore.setOnFetchListener( cityListener);
        mCityStore.fetch("/myapi");

        mCityAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_activated_1,mCityStore.getAll() );
        mWeatherStore.setOnFetchListener( weatherListener );

        mAccount = CreateSyncAccount(this);
    }

    public static Account CreateSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }
        return newAccount ;
    }

    private IStore.OnFetchListener cityListener = new IStore.OnFetchListener() {
        @Override
        public void onFetch() {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);
            sharedPref.registerOnSharedPreferenceChangeListener(WeatherActivity.this);
            String city = sharedPref.getString( CITY  , CITY_DEFAULT ) ;
            /*Initialisation de la liste statique des villes */
            try {
                mSelectedCityElem = mCityStore.find(new CityElementMatcher.MatchName(city)).get(0);
                mSelectedCityIdx = mCityStore.getAll().indexOf(mSelectedCityElem);
            } catch (Exception e) {
                //TODO city Unknown should not happen
                Toast toast = Toast.makeText(WeatherActivity.this, R.string.unknown_city , Toast.LENGTH_LONG);
                toast.show();
            }

            TextView tvCity = (TextView) findViewById(R.id.city) ;
            tvCity.setText(city);

            fetchData();
        }
    };

    private IStore.OnFetchListener weatherListener = new IStore.OnFetchListener() {
        @Override
        public void onFetch() {
            WeatherElementMatchers.MatchDateAndCity matcher = new WeatherElementMatchers.MatchDateAndCity(
                    Calendar.getInstance().getTime(),
                    mSelectedCityElem.name);
            ArrayList<WeatherElement> elements = mWeatherStore.find( matcher );
            if (elements.size() == 0 ) {
                Toast toast = Toast.makeText(WeatherActivity.this ,R.string.data_not_matching, Toast.LENGTH_LONG);
                toast.show();
            } else {
                updateWeatherView(elements.get(0));
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
                // Pass the settings flags by inserting them in a bundle
                Bundle settingsBundle = new Bundle();
                settingsBundle.putBoolean(
                        ContentResolver.SYNC_EXTRAS_MANUAL, true);
                settingsBundle.putBoolean(
                        ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        /*
         * Request the sync for the default account, authority, and
         * manual sync settings
         */
                ContentResolver.requestSync(mAccount, AUTHORITY, settingsBundle);
                fetchData();
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateWeatherView(WeatherElement elem ) {
        ImageView weatherImage = (ImageView) findViewById(R.id.weather_icon);
        TextView tvWeatherLabel = (TextView ) findViewById(R.id.weather_label);
        switch (elem.weatherState) {
            case CLOUD:
                weatherImage.setImageResource(R.drawable.weather_icons_rain);
                tvWeatherLabel.setText(R.string.cloud);
                break;
            case SUN :
                weatherImage.setImageResource(R.drawable.weather_icons_sunny);
                tvWeatherLabel.setText(R.string.sun);
                break;
            case RAIN :
                weatherImage.setImageResource(R.drawable.weather_icons_rain);
                tvWeatherLabel.setText(R.string.rain);
                break;
            case THUNDER:
                weatherImage.setImageResource(R.drawable.weather_icons_thunderstorms);
                tvWeatherLabel.setText(R.string.thunder);
                break;
            case SNOW:
                weatherImage.setImageResource(R.drawable.weather_icons_snow);
                tvWeatherLabel.setText(R.string.snow);
                break;
        }
        TextView tvTemperature = (TextView ) findViewById(R.id.temperature);
        TextView tvPressure = (TextView ) findViewById(R.id.pressure);
        TextView tvWindSpeed = (TextView ) findViewById(R.id.wind);
        TextView tvDate = (TextView) findViewById(R.id.date);

        tvTemperature.setText(String.format("%.1f °C" , elem.temperatureCelsius));
        tvPressure.setText(String.format("%.0f Pa" , elem.pressurePa));
        tvWindSpeed.setText(String.format("%.1f km/h" , elem.windSpeedKmHour));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        tvDate.setText(dateFormat.format(elem.date));
    }

    /* onSelectCity met à jour les préférences ce qui déclenche l'évènement onSharedPreference
     Cette fonction sert uniquement à mettre à jour les préférences. La mise à jour de l'UI ets déclenchée par
     onSharedPreference
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


    public Cursor retrieveCities() {

        Cursor cursor = null;
        return cursor ;
    }
}
