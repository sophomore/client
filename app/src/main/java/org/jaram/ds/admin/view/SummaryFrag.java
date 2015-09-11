package org.jaram.ds.admin.view;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;

import org.jaram.ds.R;
import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Menu;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ohyongtaek on 15. 8. 24..
 */


public class SummaryFrag extends Fragment {
    BarChartManager barChartManager;
    View view;
    ArrayList<String> menuList = new ArrayList<String>();
    Typeface tf;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.summary_chart,container,false);
        for(Menu i : Data.menuList.values()){
            menuList.add(i.name);
        }

        return view;
    }

    public void createChart(String start,String end){

        barChartManager = new BarChartManager(getActivity(),false,menuList,3,start,end);
        BarChart barChart = (BarChart) view.findViewById(R.id.chart_container2);
        barChartManager.setChart(barChart);
        barChartManager.getChart().setData(barChartManager.getData(menuList,3,start,end));
        barChart.notifyDataSetChanged();
        TextView moneydata = (TextView) view.findViewById(R.id.totalCash);
        moneydata.setText("10000원");
        TextView creditdata = (TextView) view.findViewById(R.id.totalCard);
        creditdata.setText("10000원");
        TextView totaldata = (TextView) view.findViewById(R.id.totalPrice);
        totaldata.setText("10000원");

//        barChartManager.getChart().setBackgroundColor(Color.BLUE);

        barChartManager.getChart().setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Random r = new Random();
                Toast.makeText(barChartManager.getChart().getContext(), e.getXIndex() + "", Toast.LENGTH_SHORT).show();
                TextView click_moneydata = (TextView) view.findViewById(R.id.monthCash);
                TextView click_creditdata = (TextView) view.findViewById(R.id.monthCard);
                TextView click_totaldata = (TextView) view.findViewById(R.id.monthPrice);

                click_moneydata.setText(e.getVal() + "원");
                click_creditdata.setText(r.nextInt(45) * 10000 + "원");
                click_totaldata.setText(r.nextInt(45) * 10000 + "원");
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }
    public BarChart getChart(){
        return this.barChartManager.getChart();
    }

}
