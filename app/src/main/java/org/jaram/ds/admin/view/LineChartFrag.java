package org.jaram.ds.admin.view;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import org.jaram.ds.R;
import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Menu;
import org.jaram.ds.data.struct.Order;
import org.jaram.ds.data.struct.OrderMenu;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ohyongtaek on 15. 7. 18..
 */
public class LineChartFrag extends Fragment implements OnChartGestureListener{


    Typeface tf;
    private LineChart mChart;
    public static Fragment newInstance() { return new LineChartFrag();}

    public LineChartFrag(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bar,container,false);


        mChart = new LineChart(getActivity());
        mChart.setDescription("");

        mChart.setHighlightEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.setTouchEnabled(true);

        mChart.setData(generateChart());
        tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");

        Legend l = mChart.getLegend();
        l.setTypeface(tf);


        mChart.getAxisRight().setEnabled(false);

        LimitLine ll1 = new LimitLine(100000f, "Upper Limit");
        ll1.setLineWidth(2f);
        ll1.enableDashedLine(5f, 5f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.POS_RIGHT);
        ll1.setTextSize(3f);

        LimitLine ll2 = new LimitLine(-30000f, "Lower Limit");
        ll2.setLineWidth(2f);
        ll2.enableDashedLine(5f, 5f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.POS_RIGHT);
        ll2.setTextSize(3f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaxValue(3000000f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.setStartAtZero(false);
        leftAxis.enableGridDashedLine(5f, 5f, 0f);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);
        FrameLayout parent = (FrameLayout) view.findViewById(R.id.parentLayout);
        parent.addView(mChart);

        return view;

    }
    public String[] getMenuNameList(){
        String[] menuName = new String[Data.menuList.size()];
        int j=0;
        for(Menu i : Data.menuList){
            menuName[j]=i.name;
            j++;
        }
        return menuName;
    }

    public LineData setLineData(String[] menuName){
        ArrayList<Order> orderList = Data.orderList;
        int totalPricePerMenu[] = new int[menuName.length];
        for(Order i : orderList){
            for(OrderMenu j : i.menuList){
                for(int k=0;k<menuName.length;k++) {
                    if (j.menu.name == menuName[k]) {
                        totalPricePerMenu[k] += totalPricePerMenu[k] + j.menu.price;
                    }
                }

            }
        }
        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();

        ArrayList<Entry> entries = new ArrayList<Entry>();
        for(int j=0;j<menuName.length;j++) {

            entries.add(new Entry(totalPricePerMenu[j], j));
        }

        LineDataSet lineDataSet = new LineDataSet(entries,"MenuCount");
        lineDataSet.enableDashedLine(10f, 5f, 0f);
        lineDataSet.setColor(Color.BLACK);
        lineDataSet.setCircleColor(Color.BLACK);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleSize(3f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(9f);
        lineDataSet.setFillAlpha(65);
        lineDataSet.setFillColor(Color.BLACK);
        lineDataSets.add(lineDataSet);




        String xVals[] = new String[menuName.length];

        for(int i=0;i<menuName.length;i++){

            xVals[i] =menuName[i];

        }
        Log.d("checkCount",entries.size()+"");
        Log.d("checkCount",menuName.length+"");
        LineData lineData = new LineData(xVals,lineDataSets);

        return lineData;
    }
    protected LineData generateChart(){


        String[] menuName = getMenuNameList();
        LineData data = setLineData(menuName);

        return data;
    }

    @Override
    public void onChartLongPressed(MotionEvent motionEvent) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent motionEvent) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent motionEvent) {

    }

    @Override
    public void onChartFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

    }

    @Override
    public void onChartScale(MotionEvent motionEvent, float v, float v1) {

    }

    @Override
    public void onChartTranslate(MotionEvent motionEvent, float v, float v1) {

    }
}
