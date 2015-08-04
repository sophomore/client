package org.jaram.ds.admin.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import org.jaram.ds.R;

import java.util.Calendar;

/**
 * Created by cheonyujung on 15. 7. 23..
 */
public class Search_orderFrag extends Fragment {
    private TextView mDateDisplay;
    private TextView mDateDisplay2;
    private TextView mDateDisplay3;
    private TextView mDateDisplay4;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton;
    private Button search_start;
    private int mYear, mMonth, mDay, mHour, mMinute;
    static final int DATE_DIALOG_ID = 0;
    private int mode = 0;
    View view;
    public Search_orderFrag(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, true);
        //FrameLayout frameLayout  = (FrameLayout) view.findViewById(R.id.search);
        super.onCreate(savedInstanceState);
        mDateDisplay = (TextView) view.findViewById(R.id.edit_time1);
        mDateDisplay2 = (TextView) view.findViewById(R.id.edit_time2);
        mDateDisplay3 = (TextView) view.findViewById(R.id.edit_time3);
        mDateDisplay4 = (TextView) view.findViewById(R.id.edit_time4);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.radiogroup);
        mRadioButton = (RadioButton) view.findViewById(mRadioGroup.getCheckedRadioButtonId());
        search_start = (Button) view.findViewById(R.id.search_button);
        search_start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String checktool = mRadioButton.getText().toString();
                
            }
        });
        mDateDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog DatePicker = new DatePickerDialog(view.getContext(), mDateSetListener, mYear, mMonth, mDay);
                DatePicker.show();
                mode = 1;
            }
        });
        mDateDisplay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog DatePicker = new DatePickerDialog(view.getContext(), mDateSetListener, mYear, mMonth, mDay);
                DatePicker.show();
                mode = 2;
            }
        });
        mDateDisplay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog TimePicker = new TimePickerDialog(view.getContext(), mTimeSetListener, mHour, mMinute, false);
                TimePicker.show();
                mode = 3;
            }
        });
        mDateDisplay4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog TimePicker = new TimePickerDialog(view.getContext(), mTimeSetListener, mHour, mMinute, false);
                TimePicker.show();
                mode = 4;
            }
        });
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth= c.get(Calendar.MONTH);
        mDay  = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mDateDisplay.setText(
                new StringBuilder()
                        .append(mYear).append(".")
                        .append(mMonth + 1).append(".")
                        .append(mDay).append(" ")
        );
        mDateDisplay2.setText(
                new StringBuilder()
                        .append(mYear).append(".")
                        .append(mMonth + 1).append(".")
                        .append(mDay+1).append(" ")
        );
        mDateDisplay3.setText(
                new StringBuilder()
                        .append(mHour).append(":")
                        .append(mMinute)
        );
        mDateDisplay4.setText(
                new StringBuilder()
                        .append(mHour).append(":")
                        .append(mMinute)
        );
        return view;
    }
    protected Dialog onCreateDialog(int id) {
        switch(id){
            case DATE_DIALOG_ID : return new DatePickerDialog(view.getContext(),mDateSetListener,mYear,mMonth,mDay);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth=monthOfYear;
            mDay=dayOfMonth;
            updateDisplay();
        }
    };
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mHour = hourOfDay;
            mMinute = minute;
            updateDisplay();
        }
    };
    private void updateDisplay() {
        if(mode == 1) {
            mDateDisplay.setText(
                    new StringBuilder()
                            .append(mYear).append(".")
                            .append(mMonth + 1).append(".")
                            .append(mDay).append(" ")
            );
        }else if(mode == 2){
            mDateDisplay2.setText(
                    new StringBuilder()
                            .append(mYear).append(".")
                            .append(mMonth + 1).append(".")
                            .append(mDay).append(" ")
            );
        }else if(mode == 3){
            mDateDisplay3.setText(
                    new StringBuilder()
                            .append(mHour).append(":")
                            .append(mMinute)
            );
        }else if(mode == 4){
            mDateDisplay4.setText(
                    new StringBuilder()
                            .append(mHour).append(":")
                            .append(mMinute)
            );
        }
    }

}
