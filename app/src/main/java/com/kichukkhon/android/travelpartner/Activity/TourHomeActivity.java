package com.kichukkhon.android.travelpartner.Activity;

import android.os.Bundle;

import com.kichukkhon.android.travelpartner.R;

public class TourHomeActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_home);

        InitCommonUIElements();
    }
}
