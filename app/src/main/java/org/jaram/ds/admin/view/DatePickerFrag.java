package org.jaram.ds.admin.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by ka123ak on 2015-07-15.
 */


public class DatePickerFrag extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    TextView yearText,monthText,dayText;
    public DatePickerFrag(TextView yearText,TextView monthText, TextView dayText){
        this.yearText = yearText;
        this.monthText = monthText;
        this.dayText = dayText;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year,month,day;
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(getActivity(),this,year,month,day);

        return datePicker;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.yearText.setText(String.format("%d",year));
        this.monthText.setText(String.format("%d",monthOfYear+1));
        this.dayText.setText(String.format("%d",dayOfMonth));

    }

}
