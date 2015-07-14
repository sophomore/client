package org.jaram.ds.statistic.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.Toast;

import org.jaram.ds.R;

/**
 * Created by ka123ak on 2015-07-09.
 */
public class DrawerFrag extends Fragment {

    String unit[] = {"시간","일","요일","월","분기","년"};
    GridView gridView;
    DatePicker datePicker;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer,container,false);
        gridView = (GridView) view.findViewById(R.id.gridView);
        SimpleAdapter simpleAdapter = new SimpleAdapter();
        gridView.setAdapter(simpleAdapter);
        Button btn1 = (Button) view.findViewById(R.id.button);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("button","버튼눌림");

                Toast.makeText(getActivity(),"버튼눌림",Toast.LENGTH_LONG).show();
            }
        });
        datePicker = (DatePicker) view.findViewById(R.id.datepic);
        datePicker.init(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth(),
                new DatePicker.OnDateChangedListener(){

                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String msg = String.format("%d /%d /%d",year,monthOfYear,dayOfMonth);
                        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT);
                    }
                });
        return view;
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
