package com.kichukkhon.android.travelpartner.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kichukkhon.android.travelpartner.Adapter.TourAdapter;
import com.kichukkhon.android.travelpartner.Class.Tour;
import com.kichukkhon.android.travelpartner.Database.TourDBManager;
import com.kichukkhon.android.travelpartner.R;

import java.util.ArrayList;

public class TourListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TourAdapter tourAdapter;
    ArrayList<Tour> tourList;
    TourDBManager tourDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_list);

        recyclerView=(RecyclerView)findViewById(R.id.rvTourList);

        tourDBManager=new TourDBManager(this);
    }

    public void getTourData(){
        tourList=tourDBManager.getAllTourInfo();

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        tourAdapter=new TourAdapter(this,tourList);
        tourAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(tourAdapter);
    }
}
