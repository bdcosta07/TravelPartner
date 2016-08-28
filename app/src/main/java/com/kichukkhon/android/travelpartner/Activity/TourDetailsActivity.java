package com.kichukkhon.android.travelpartner.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.kichukkhon.android.travelpartner.Class.Tour;
import com.kichukkhon.android.travelpartner.Database.TourDBManager;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.AppUtils;
import com.kichukkhon.android.travelpartner.Util.Preference;

public class TourDetailsActivity extends BaseDrawerActivity {
    TextView tvDestination;
    TextView tvStartDate;
    TextView tvEndDate;
    TextView tvBudget;
    TourDBManager tourDBManager;
    Toolbar toolbar;
    Tour tourInfo;
    Preference preference;
    int curentTourId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_details);

        InitCommonUIElements();
        preference = new Preference(this);

        curentTourId = preference.getCurrentlySelectedTourId();

        tvDestination = (TextView) findViewById(R.id.tvDestination);
        tvStartDate = (TextView) findViewById(R.id.tvShowStartDate);
        tvEndDate = (TextView) findViewById(R.id.tvShowEndDate);
        tvBudget = (TextView) findViewById(R.id.tvCurrentBudget);

        tourDBManager = new TourDBManager(this);
        tourInfo = tourDBManager.getTourInfoById(curentTourId);
        getTourData();

    }

    public void getTourData() {
        String destination = tourInfo.getDestination();
        String startDate = AppUtils.getFormattedDate(this, tourInfo.getStartDateTime());
        String endDate=AppUtils.getFormattedDate(this,tourInfo.getEndDateTime());
        double budget=tourInfo.getBudget();

        tvDestination.setText(destination);
        tvStartDate.setText(startDate);
        tvEndDate.setText(endDate);
        tvBudget.setText(String.valueOf("BDT "+budget));
    }
}
