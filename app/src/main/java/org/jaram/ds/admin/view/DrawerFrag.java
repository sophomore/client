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

import org.jaram.ds.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by ka123ak on 2015-07-09.
 */
public class DrawerFrag extends Fragment {

    String unit[] = {"시간","일","요일","월","분기","년"};

    int year, month,day;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_drawer,container,false);
        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        SetGridView(gridView, view);

        Button beforeBtn = (Button) view.findViewById(R.id.beforeBtn);
        final int year,month,day;
        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        beforeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView year = (TextView)view.findViewById(R.id.ByearText);
                TextView month = (TextView)view.findViewById(R.id.BmonthText);
                TextView day = (TextView) view.findViewById(R.id.BdayText);
                DialogFragment beforeDialog = new DatePickerFrag(year,month,day);
                beforeDialog.show(getActivity().getFragmentManager(),"datePicker");
            }
        });
        Button laterbtn = (Button)view.findViewById(R.id.laterBtn);
        laterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView year = (TextView)view.findViewById(R.id.LyearText);
                TextView month = (TextView)view.findViewById(R.id.LmonthText);
                TextView day = (TextView) view.findViewById(R.id.LdayText);
                DialogFragment beforeDialog = new DatePickerFrag(year,month,day);
                beforeDialog.show(getActivity().getFragmentManager(),"datePicker");
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
