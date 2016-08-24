package com.kichukkhon.android.travelpartner.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.DateSet;

public class TourEntryDetailsActivity extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener {

    EditText txtTourName;
    TextView tvShowStartDate;
    TextView tvShowEndDate;
    DatePicker startDateSet;
    DatePicker endDateSet;
    DateSet dateSet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_entry_details);

        txtTourName=(EditText)findViewById(R.id.txtTourName);
        tvShowStartDate=(TextView)findViewById(R.id.tvShowStartDate);
        tvShowEndDate=(TextView)findViewById(R.id.tvShowEndDate);
        startDateSet = (DatePicker) findViewById(R.id.startDatePicker);
        endDateSet = (DatePicker) findViewById(R.id.endDatePicker);



        final Calendar calendar=Calendar.getInstance();

        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        tvShowStartDate.setText(year+"-"+(month+1)+"-"+day);
        tvShowEndDate.setText(year+"-"+(month+1)+"-"+day);

        tvShowStartDate.setOnClickListener(this);
        tvShowEndDate.setOnClickListener(this);
    }

    public void showDatePicker(){
        dateSet=new DateSet();
        dateSet.show(getFragmentManager(),"datePicker");
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
        tvShowStartDate.setText(new StringBuilder().append(year+"-"+(monthOfYear+1)+"-"+dayOfMonth));
        datePicker.init(year,monthOfYear,dayOfMonth,null);

        tvShowEndDate.setText(new StringBuilder().append(year+"-"+(monthOfYear+1)+"-"+dayOfMonth));
        datePicker.init(year,monthOfYear,dayOfMonth,null);
    }

}
