package com.kichukkhon.android.travelpartner.Util;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Bundle;

/**
 * Created by Bridget on 8/25/2016.
 */
public class DateSet extends DialogFragment {
    private OnDateSetListener onDateSetListener;
    int year, month, day;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog =
                new DatePickerDialog(getActivity(), onDateSetListener, year, month, day);
        return datePickerDialog;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnDateSetListener) {
            onDateSetListener = (OnDateSetListener) activity;
        }
    }
}
