package com.kichukkhon.android.travelpartner.Activity;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
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
import com.kichukkhon.android.travelpartner.Util.DateSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_entry_details);

        txtTourName = (EditText) findViewById(R.id.txtTourName);
        tvShowStartDate = (TextView) findViewById(R.id.tvShowStartDate);
        tvShowEndDate = (TextView) findViewById(R.id.tvShowEndDate);
        startDateSet = (DatePicker) findViewById(R.id.startDatePicker);
        endDateSet = (DatePicker) findViewById(R.id.endDatePicker);
        txtBudget=(EditText) findViewById(R.id.txtBudget);

        toolbar = (Toolbar) findViewById(R.id.toolbarWithAppbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        final Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        tvShowStartDate.setText(year + "-" + (month + 1) + "-" + day);
        tvShowEndDate.setText(year + "-" + (month + 1) + "-" + day);

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

        switch (id) {
            case R.id.tvShowStartDate:
                showDatePicker();
                break;
        }
        switch (id) {
            case R.id.tvShowEndDate:
                showDatePicker();
                break;
        }

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        tvShowStartDate.setText(new StringBuilder().append(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth));
        datePicker.init(year, monthOfYear, dayOfMonth, null);

        tvShowEndDate.setText(new StringBuilder().append(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth));
        datePicker.init(year, monthOfYear, dayOfMonth, null);
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

    public void saveTourInfo(){

        String tourName=txtTourName.getText().toString();
        double budget=Double.parseDouble(txtBudget.getText().toString());

        String startDateStr=tvShowStartDate.getText().toString();
        String endDateStr=tvShowEndDate.getText().toString();

        long startDate= AppUtils.convertDateStringToMillis(startDateStr,"yyyy-MM-dd");
        long endDate=AppUtils.convertDateStringToMillis(endDateStr,"yyyy-MM-dd");

        tourInfo=new Tour();
        tourDBManager=new TourDBManager(this);

        tourInfo.setTourName(tourName);
        tourInfo.setBudget(budget);
        tourInfo.setStartDateTime(startDate);
        tourInfo.setEndDateTime(endDate);

        boolean inserted=tourDBManager.addTour(tourInfo);
        if (inserted){
            Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(this, "Data not Inserted", Toast.LENGTH_SHORT).show();
    }

    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem saveItem=menu.findItem(R.id.icon_save);
        saveItem.setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }*/


}
