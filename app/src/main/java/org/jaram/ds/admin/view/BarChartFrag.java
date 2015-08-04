package org.jaram.ds.admin.view;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import org.jaram.ds.R;
import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Menu;
import org.jaram.ds.data.struct.Order;
import org.jaram.ds.data.struct.OrderMenu;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by ka123ak on 2015-07-09.
 */
public class BarChartFrag extends Fragment implements OnChartGestureListener {

    private HashMap<Menu, Integer> menuCount;
    private ArrayList<OrderMenu> orderMenus;
    private ArrayList<Order> orderArrayList;
    Typeface tf;

    public static Fragment newInstance() { return new BarChartFrag();}

    private BarChart mChart;

    public BarChartFrag() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bar,container,false);

//
//        //차트 객체 구현
//        mChart = new BarChart(getActivity());
//        mChart.setDescription("");
//
//        mChart.setHighlightEnabled(false);
//        mChart.setDrawBarShadow(false);
//        mChart.setDrawValueAboveBar(true);
//
//        tf = Typeface.createFromAsset(getActivity().getAssets(),"OpenSans-Light.ttf");
//
//        mChart.setData(generateBarData());
//        Legend l = mChart.getLegend();
//        l.setTypeface(tf);
//
//        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setTypeface(tf);
//
//        mChart.getAxisRight().setEnabled(false);
//
//        XAxis xAxis = mChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setTypeface(tf);
//        xAxis.setDrawGridLines(false);
//        xAxis.setSpaceBetweenLabels(1);
//
//        mChart.setBackgroundColor(Color.BLUE);
//        FrameLayout parent = (FrameLayout) view.findViewById(R.id.parentLayout);
//        parent.addView(mChart);


        return view;

    }
    protected BarData generateBarData(){
        ArrayList<BarDataSet> sets = new ArrayList<BarDataSet>();
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();


        ArrayList<String>menuName = getMenuNameList();


        String d [] = new String [15];
        for(int i =0 ;i<15;i++){
            d[i]=String.valueOf(i+1);
        }
        BarData data = setEntries(menuName);
        data.setValueTextSize(10f);
        data.setValueTypeface(tf);
        return data;
    }

    public ArrayList<String> getMenuNameList(){
        ArrayList<String> menuName = new ArrayList<String>();
        for(Menu i : Data.menuList){
            menuName.add(i.name);
        }
        return menuName;
    }

    public BarData setEntries(ArrayList<String> menuName){

        ArrayList<Order> orderList = Data.orderList;
        HashMap<String, Integer> totalCountPerMenu = new HashMap<String, Integer>();
        for(Order i : orderList){
            for(OrderMenu j : i.menuList){
                if(totalCountPerMenu.containsKey(j.menu.name)){
                    totalCountPerMenu.put(j.menu.name,totalCountPerMenu.get(j.menu.name)+ j.menu.price);
                } else{
                    totalCountPerMenu.put(j.menu.name,j.menu.price);
                }
            }
        }
        ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();
        for(int i=0;i<menuName.size();i++){
            ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
            for(int j=0;j<7;j++){
                entries.add(new BarEntry(j+i,j));
            }
            BarDataSet barDataSet = new BarDataSet(entries,menuName.get(i));
            barDataSet.setColor(Color.rgb(255,128,128));
            barDataSets.add(barDataSet);

        }
        String days[] = {"월","화","수","목","금","토","일"};

        BarData barData = new BarData(days,barDataSets);

        return barData;

    }
    @Override
    public void onChartLongPressed(MotionEvent me) {
        Toast.makeText(getActivity(),"onChartLongPressed 실행됨",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Toast.makeText(getActivity(),"onChartDoubleTapped 실행됨",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Toast.makeText(getActivity(),"onChartSingleTapped 실행됨",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Toast.makeText(getActivity(),"onChartFling 실행됨",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Toast.makeText(getActivity(),"onChartScale 실행됨",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Toast.makeText(getActivity(),"onChartTranslate 실행됨",Toast.LENGTH_LONG).show();
    }
}
