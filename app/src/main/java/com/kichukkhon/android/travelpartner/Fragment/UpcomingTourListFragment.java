package com.kichukkhon.android.travelpartner.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kichukkhon.android.travelpartner.Adapter.TourAdapter;
import com.kichukkhon.android.travelpartner.Class.Tour;
import com.kichukkhon.android.travelpartner.Database.TourDBManager;
import com.kichukkhon.android.travelpartner.R;

import java.util.ArrayList;

public class UpcomingTourListFragment extends Fragment {
    RecyclerView recyclerView;
    TourAdapter tourAdapter;
    TextView emptyView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        emptyView=(TextView) inflater.inflate(
                R.layout.empty_view_for_recyclerview, container, false);

        if (getTourData().isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);

            tourAdapter = new TourAdapter(recyclerView.getContext(), getTourData());
            recyclerView.setAdapter(tourAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        return recyclerView;
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_upcomin_tour_list);

        recyclerView=(RecyclerView)findViewById(R.id.rvTourList);

        tourDBManager=new TourDBManager(this);
    }*/

    public ArrayList<Tour> getTourData() {
        TourDBManager tourDBManager = new TourDBManager(getContext());
        ArrayList<Tour> tourList = tourDBManager.getAllTourInfo();

        return tourList;

    }
}
