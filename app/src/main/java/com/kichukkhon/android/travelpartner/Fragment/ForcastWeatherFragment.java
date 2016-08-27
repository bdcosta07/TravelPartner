package com.kichukkhon.android.travelpartner.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kichukkhon.android.travelpartner.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForcastWeatherFragment extends Fragment {


    public ForcastWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forcast_weather, container, false);
    }

}
