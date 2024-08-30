package com.example.quickcash.ui_elements;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;

public class DatePicker extends DialogFragment {

    @Override
    @Deprecated
    public Dialog onCreateDialog(Bundle savedInstances){
        Calendar c=Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);

    }

}
