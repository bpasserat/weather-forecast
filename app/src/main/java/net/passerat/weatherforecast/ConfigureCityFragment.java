package net.passerat.weatherforecast;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by bpasserat on 22/09/2017.
 */

public class ConfigureCityFragment extends DialogFragment {

    private int mSelectedItem;

    public static ConfigureCityFragment newInstance(int item) {
        ConfigureCityFragment frag = new ConfigureCityFragment();
        Bundle args = new Bundle();
        args.putInt("selected", item);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Bundle args = getArguments();
        mSelectedItem = args.getInt("selected",-1);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(R.string.configure_city)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setSingleChoiceItems(((WeatherActivity)getActivity()).mCityAdapter, mSelectedItem ,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which ) {
                        mSelectedItem = which ;
                    }
                })
                // Set the action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        ((WeatherActivity)getActivity()).onSelectCity( mSelectedItem);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder.create();
    }
}
