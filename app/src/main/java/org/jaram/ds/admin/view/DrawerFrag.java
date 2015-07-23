package org.jaram.ds.admin.view;

import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.jaram.ds.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by ka123ak on 2015-07-09.
 */
public class DrawerFrag extends Fragment {

    String unit[] = {"시간","일","요일","월","분기","년"};
    TextView byear,bmonth,bday, lyear,lmonth,lday;
    int year, month,day;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_drawer, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        SetGridView(gridView, view);

        Button beforeBtn = (Button) view.findViewById(R.id.beforeBtn);
        final int year,month,day;
        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        final Button countbtn = (Button) view.findViewById(R.id.countbtn);
        final Button salesbtn = (Button) view.findViewById(R.id.salesbtn);
        countbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        beforeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byear = (TextView)view.findViewById(R.id.ByearText);
                bmonth = (TextView)view.findViewById(R.id.BmonthText);
                bday = (TextView) view.findViewById(R.id.BdayText);
                DialogFragment beforeDialog = new DatePickerFrag(byear,bmonth,bday,"startDate");
                beforeDialog.show(getActivity().getFragmentManager(),"datePicker");

            }
        });
        Button laterbtn = (Button)view.findViewById(R.id.laterBtn);
        laterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lyear = (TextView)view.findViewById(R.id.LyearText);
                lmonth = (TextView)view.findViewById(R.id.LmonthText);
                lday = (TextView) view.findViewById(R.id.LdayText);
                DialogFragment beforeDialog = new DatePickerFrag(lyear,lmonth,lday,"finishDate");
                beforeDialog.show(getActivity().getFragmentManager(),"datePicker");

            }
        });
        Button analysisbtn = (Button) view.findViewById(R.id.analysis);
        analysisbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int []startDate = getArguments().getIntArray("startDate");
                int []finishDate = getArguments().getIntArray("finishDate");

            }
        });
        Button resetbtn = (Button) view.findViewById(R.id.reset);
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byear.setText("");
                bmonth.setText("");
                bday.setText("");
                lyear.setText("");
                lmonth.setText("");
                lday.setText("");
            }
        });
        return view;
    }

    private void SetGridView(GridView gridView , View view){
        SimpleAdapter simpleAdapter = new SimpleAdapter();
        gridView.setAdapter(simpleAdapter);
    }
    class SimpleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return unit.length;
        }

        @Override
        public Object getItem(int position) {
            return unit[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Button btn = new Button(getActivity());
            btn.setText(unit[position]);
            btn.setTextSize(20.0f);
            btn.setTextColor(Color.BLACK);

            return btn;
        }
    }
}
