package net.passerat.weatherforecast;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import net.passerat.weatherforecast.data.WeatherElement;

import java.util.ArrayList;

/**
 * Created by bpasserat on 24/09/2017.
 */

public class WeatherProvider extends ContentProvider {

    public static final String AUTHORITY = "net.passerat.weatherforecast.provider";

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static ArrayList<WeatherElement> sWeatherElements = new ArrayList<>();

    public interface WeatherColumns extends BaseColumns {
        String CITY = "city";
        String STATE = "state";
        String TEMPERATURE = "temperature";
        String PRESSURE = "pressure";
        String WIND_SPEED = "windSpeedKmHour";
        String WIND_ORIENTATION = "windOrientationDegree";
    }

    static {

        sUriMatcher.addURI("net.passerat.weatherforecast.provider", "weather", 1);
        sUriMatcher.addURI("net.passerat.weatherforecast.provider", "weather/*", 2);
    }

    private static int MATCH_WEATHER = 1 ;
    private static int MATCH_CITY = 1 ;


    @Override
    public boolean onCreate() {
        return false;
    }



    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        switch (sUriMatcher.match(uri)) {


            // If the incoming URI was for all of table3
            case 1:

                if (TextUtils.isEmpty(s1)) s1 = "_ID ASC";
                break;

            // If the incoming URI was for a single row
            case 2:

                /*
                 * Because this URI was for a single row, the _ID value part is
                 * present. Get the last path segment from the URI; this is the _ID value.
                 * Then, append the value to the WHERE clause for the query
                 */
                s = s + "_ID = " + uri.getLastPathSegment();
                break;

            default:
                break;
                // If the URI is not recognized, you should do some error handling here.
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
