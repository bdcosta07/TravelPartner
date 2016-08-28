package com.kichukkhon.android.travelpartner.Activity;

import android.os.Bundle;

import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.Constants;

public class TourDetailsActivity extends BaseDrawerActivity {

    int currentTourId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_details);

        InitCommonUIElements();

        currentTourId = getIntent().getIntExtra(Constants.CURRENT_TOUR_ID_KEY, 1);
    }
}
