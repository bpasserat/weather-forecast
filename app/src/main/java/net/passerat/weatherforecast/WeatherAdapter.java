package net.passerat.weatherforecast;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.passerat.weatherforecast.data.WeatherElement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by bpasserat on 25/09/2017.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    ArrayList<WeatherElement> mDataset ;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvCity ;
        public ImageView weatherImage ;
        public TextView tvWeatherLabel;
        public TextView tvTemperature;
        public TextView tvPressure;
        public TextView tvWindSpeed ;
        public TextView tvDate;

        public ViewHolder(View v) {
            super(v);
            weatherImage = (ImageView) v.findViewById(R.id.weather_icon);
            tvWeatherLabel = (TextView) v.findViewById(R.id.weather_label);
            tvTemperature = (TextView) v.findViewById(R.id.temperature);
            tvPressure = (TextView) v.findViewById(R.id.pressure);
            tvWindSpeed = (TextView) v.findViewById(R.id.wind);
            tvDate = (TextView) v.findViewById(R.id.date);
        }

        private void updateWeatherView(WeatherElement elem ) {

            switch (elem.weatherState) {
                case CLOUD:
                    weatherImage.setImageResource(R.drawable.weather_icons_cloud);
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
            tvTemperature.setText(String.format("%.1f °C", elem.temperatureCelsius));
            tvPressure.setText(String.format("%.0f Pa", elem.pressurePa));
            tvWindSpeed.setText(String.format("%.1f km/h", elem.windSpeedKmHour));
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            tvDate.setText(dateFormat.format(elem.date));
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public WeatherAdapter(ArrayList<WeatherElement> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_weather, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.updateWeatherView(mDataset.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
