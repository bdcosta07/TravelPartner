package com.kichukkhon.android.travelpartner.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.kichukkhon.android.travelpartner.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

<<<<<<< HEAD
    public void btnTourEntryOnClick(View view) {
        Intent intent=new Intent(this,TourEntryDetailsActivity.class);
=======
    public void btnTourHomeClick(View view) {
        Intent intent = new Intent(this, TourHomeActivity.class);
>>>>>>> 6fb3bd1c0ebf798b730d74c9f68252302b7938f2
        startActivity(intent);
    }
}
