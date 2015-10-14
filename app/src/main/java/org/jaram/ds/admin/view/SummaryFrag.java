package org.jaram.ds.admin.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;

import org.jaram.ds.R;
import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Menu;

import java.util.ArrayList;

/**
 * Created by ohyongtaek on 15. 8. 24..
 */


public class SummaryFrag extends Fragment {
    BarChartManager barChartManager;
    View view;
    ArrayList<String> menuList = new ArrayList<String>();
    TextView click_totaldata;
    TextView click_carddata;
    TextView click_moneydata;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.summary_chart,container,false);
        for(Menu i : Data.menuList.values()){
            menuList.add(i.name);
        }
        click_moneydata = (TextView) view.findViewById(R.id.monthCash);
        click_carddata = (TextView) view.findViewById(R.id.monthCard);
        click_totaldata = (TextView) view.findViewById(R.id.monthPrice);
        return view;
    }

    public void createChart(String start,String end){

        barChartManager = new BarChartManager(getActivity(),false,menuList,3,start,end);
        barChartManager.setData();
        BarChart barChart = (BarChart) view.findViewById(R.id.chart_container2);
        barChartManager.setChart(barChart);
        barChartManager.getChart().setData(barChartManager.getData(menuList, 3, start, end));
        barChart.notifyDataSetChanged();


    }
    public void setListener(){

        TextView moneydata = (TextView) view.findViewById(R.id.totalCash);
        moneydata.setText(Data.totalPrice+"원");
        TextView carddata = (TextView) view.findViewById(R.id.totalCard);
        carddata.setText(Data.totalCard+"원");
        TextView totaldata = (TextView) view.findViewById(R.id.totalPrice);
        totaldata.setText(Data.totalPrice+"원");



        barChartManager.getChart().setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {



                click_moneydata.setText(Data.cashs.get(e.getXIndex()) + "원");
                click_carddata.setText(Data.cards.get(e.getXIndex()) + "원");
                click_totaldata.setText(Data.totals.get(e.getXIndex()) + "원");
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }
    public void resetTextView(){
        click_moneydata.setText(0+"");
        click_carddata.setText(0+"");
        click_totaldata.setText(0+"");
    }
    public BarChart getChart(){
        return this.barChartManager.getChart();
    }

}
