package com.kichukkhon.android.travelpartner.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.kichukkhon.android.travelpartner.Fragment.UpcomingTourListFragment;
import com.kichukkhon.android.travelpartner.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnTourEntryOnClick(View view) {
        Intent intent = new Intent(this, TourEntryDetailsActivity.class);
        startActivity(intent);
    }

    public void btnTourHomeClick(View view) {
        Intent intent = new Intent(this, TourHomeActivity.class);
        startActivity(intent);
    }

    public void btnDestinationClick(View view) {
        Intent intent = new Intent(this, TourEntryDestinationActivity.class);
        startActivity(intent);
    }


    public void btnTourListClick(View view) {
        Intent intent = new Intent(this, TourListActivity.class);
        startActivity(intent);
    }
}
