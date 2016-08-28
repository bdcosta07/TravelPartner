package com.kichukkhon.android.travelpartner.Activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.kichukkhon.android.travelpartner.Class.Tour;
import com.kichukkhon.android.travelpartner.Database.TourDBManager;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.AppUtils;

public class TourDetailsActivity extends BaseDrawerActivity {
    TextView tvDestination;
    TextView tvStartDate;
    TextView tvEndDate;
    TextView tvBudget;
    TourDBManager tourDBManager;
    Toolbar toolbar;
    Tour tourInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_details);

        InitCommonUIElements();

        tvDestination =(TextView)findViewById(R.id.tvDestination);
        tvStartDate=(TextView)findViewById(R.id.tvStartDate);
        tvEndDate=(TextView)findViewById(R.id.tvEndDate);
        tvBudget=(TextView)findViewById(R.id.tvBudget);

        tourDBManager=new TourDBManager(this);
        tourInfo=new Tour();

    }

    public void getTourData(){
        String destination=tourInfo.getDestination();


        //long startDate= AppUtils.getFriendlyDayString()
    }
}
