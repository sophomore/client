package org.jaram.ds.admin.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by cheonyujung on 15. 7. 23..
 */
public class Search_orderFrag extends Fragment {
    private TextView mDateDisplay;
    private TextView mDateDisplay2;
    private int mYear, mMonth, mDay;
    static final int DATE_DIALOG_ID = 0;
    private int mode = 0;
    View view;
    public Search_orderFrag(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, true);
        //FrameLayout frameLayout  = (FrameLayout) view.findViewById(R.id.search);
        super.onCreate(savedInstanceState);
        //setView(R.layout.activity_ordermanager);
        mDateDisplay = (TextView) view.findViewById(R.id.edit_time1);
        mDateDisplay2 = (TextView) view.findViewById(R.id.edit_time2);
        mDateDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDialog(DATE_DIALOG_ID);
                mode = 1;
            }
        });
        mDateDisplay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDialog(DATE_DIALOG_ID);
                mode = 2;
            }
        });
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth= c.get(Calendar.MONTH);
        mDay  = c.get(Calendar.DAY_OF_MONTH);
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
        }
    }

}
