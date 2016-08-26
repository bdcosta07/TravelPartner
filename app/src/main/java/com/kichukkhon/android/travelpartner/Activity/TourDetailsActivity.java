package com.kichukkhon.android.travelpartner.Activity;

import android.os.Bundle;

import com.kichukkhon.android.travelpartner.R;

public class TourDetailsActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_details);

        InitCommonUIElements();
    }
}
