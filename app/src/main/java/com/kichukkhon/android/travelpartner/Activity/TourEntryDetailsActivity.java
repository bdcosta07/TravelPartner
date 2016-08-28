package com.kichukkhon.android.travelpartner.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kichukkhon.android.travelpartner.Class.Tour;
import com.kichukkhon.android.travelpartner.Database.TourDBManager;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.AppUtils;
import com.kichukkhon.android.travelpartner.Util.Constants;
import com.kichukkhon.android.travelpartner.Util.DateSet;

public class TourEntryDetailsActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    EditText txtTourName;
    TextView tvShowStartDate;
    TextView tvShowEndDate;
    EditText txtBudget;
    DatePicker startDateSet;
    DatePicker endDateSet;
    DateSet dateSet;
    private Toolbar toolbar;
    Tour tourInfo;
    TourDBManager tourDBManager;
    int selectedDatePickerCallerId;
    String placeName, placeId;
    Bundle extras;
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_entry_details);

        txtTourName = (EditText) findViewById(R.id.txtTourName);
        tvShowStartDate = (TextView) findViewById(R.id.tvShowStartDate);
        tvShowEndDate = (TextView) findViewById(R.id.tvShowEndDate);
        startDateSet = (DatePicker) findViewById(R.id.startDatePicker);
        endDateSet = (DatePicker) findViewById(R.id.endDatePicker);
        txtBudget = (EditText) findViewById(R.id.txtBudget);

        toolbar = (Toolbar) findViewById(R.id.toolbarWithAppbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("New Tour");
        actionBar.setDisplayHomeAsUpEnabled(true);

        final Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        tvShowStartDate.setText(year + "-" + (month + 1) + "-" + day);
        tvShowEndDate.setText(year + "-" + (month + 1) + "-" + day);

        extrasBundleInten();

        txtTourName.setText("Tour to " + placeName);
        tvShowStartDate.setOnClickListener(this);
        tvShowEndDate.setOnClickListener(this);
    }

    public void showDatePicker() {
        dateSet = new DateSet();
        dateSet.show(getFragmentManager(), "datePicker");
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        selectedDatePickerCallerId = id;
        showDatePicker();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        if (selectedDatePickerCallerId == R.id.tvShowStartDate) {
            tvShowStartDate.setText(new StringBuilder().append(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth));
            datePicker.init(year, monthOfYear, dayOfMonth, null);
        } else {
            tvShowEndDate.setText(new StringBuilder().append(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth));
            datePicker.init(year, monthOfYear, dayOfMonth, null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.icon_save) {
            saveTourInfo();
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveTourInfo() {

        String tourName = txtTourName.getText().toString();
        double budget = Double.parseDouble(txtBudget.getText().toString());

        String startDateStr = tvShowStartDate.getText().toString();
        String endDateStr = tvShowEndDate.getText().toString();

        long startDate = AppUtils.convertDateStringToMillis(startDateStr, Constants.DEFAULT_DATE_FORMAT);
        long endDate = AppUtils.convertDateStringToMillis(endDateStr, Constants.DEFAULT_DATE_FORMAT);

        tourInfo = new Tour();
        tourDBManager = new TourDBManager(this);

        extrasBundleInten();

        tourInfo.setTourName(tourName);
        tourInfo.setBudget(budget);
        tourInfo.setStartDateTime(startDate);
        tourInfo.setEndDateTime(endDate);
        tourInfo.setPlaceId(placeId);
        tourInfo.setDestination(placeName);
        tourInfo.setDestLat(latitude);
        tourInfo.setDestLon(longitude);

        boolean inserted = tourDBManager.addTour(tourInfo);
        if (inserted) {
            Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "Data not Inserted", Toast.LENGTH_SHORT).show();
    }

    public void extrasBundleInten() {
        extras = getIntent().getExtras();
        placeId = extras.getString(Constants.PLACE_ID_KEY);
        placeName = extras.getString(Constants.PLACE_NAME_KEY);
        latitude = extras.getDouble(Constants.PLACE_LATITUDE_KEY);
        longitude = extras.getDouble(Constants.PLACE_LONGITUDE_KEY);

    }

    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem saveItem=menu.findItem(R.id.icon_save);
        saveItem.setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }*/


}
