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
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import org.jaram.ds.R;
import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Menu;
import org.jaram.ds.data.struct.Order;
import org.jaram.ds.data.struct.OrderMenu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//<<<<<<< HEAD
//=======
import java.util.Calendar;
import java.util.Date;

//>>>>>>> 8f7fd9f9fc3a8e4c25de33a5b12675c7e76c5a75

/**
 * Created by ohyongtaek on 15. 7. 18..
 */


public class LineChartFrag extends Fragment implements OnChartGestureListener{


    Typeface tf;
    protected LineChart mChart;
    Date startDate;


    public static LineChartFrag newInstance(String start,String diffDays) {
        LineChartFrag lineChartFrag = new LineChartFrag();
        Bundle argument = new Bundle();
        argument.putString("start",start);
        argument.putString("diff",diffDays);
        lineChartFrag.setArguments(argument);


        return lineChartFrag;
    }

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

        int diffDays = getDiffDays();
        mChart.setData(generateChart(LineChartFrag.getMenuNameList(),diffDays));


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
        leftAxis.setAxisMaxValue(300000f);
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
    public static String[] getMenuNameList(){
        String[] menuName = new String[Data.menuList.size()];
        int j=0;
        for(Menu i : Data.menuList){
            menuName[j]=i.name;
            j++;
        }
        return menuName;
    }

    public int getDiffDays(){
        return Integer.parseInt(getArguments().getString("diff"));
    }
    public Calendar getStartDate() throws ParseException {
        String start = getArguments().getString("start");
        Calendar cal = Calendar.getInstance();
        String []date = start.split("/");
        cal.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        return cal;
    }

    public LineData generateChart(String[] menuName,int diffDays){
        Calendar date = null;

        try {
            date = getStartDate();

        } catch (ParseException e) {

            e.printStackTrace();
        }
        if(date != null) {
            Calendar date2 = (Calendar) date.clone();

            ArrayList<Order> orderList = Data.orderList;
            int totalPricePerMenu[][] = new int[menuName.length][diffDays];
            for (Order i : orderList) {
                for (OrderMenu j : i.menuList) {
                    date2 = (Calendar) date.clone();
                    for (int l = 0; l < diffDays; l++) {

                        Calendar orderDate = Calendar.getInstance();
                        orderDate.setTime(i.date);
                        Log.d("time",orderDate.getTime()+"!");
                        Log.d("time",orderDate.get(Calendar.YEAR)+"");
                        Log.d("time",orderDate.get(Calendar.MONTH)+"");
                        Log.d("time",orderDate.get(Calendar.DATE)+ "");
                        if (date2.get(Calendar.YEAR) == orderDate.get(Calendar.YEAR) && date2.get(Calendar.MONTH)==orderDate.get(Calendar.MONTH) && date2.get(Calendar.DATE) == orderDate.get(Calendar.DATE)) {
                            Log.d("suc","성공");
                            for (int k = 0; k < menuName.length; k++) {
                                if (j.menu.name == menuName[k]) {
                                    totalPricePerMenu[k][l] += totalPricePerMenu[k][l] + j.menu.price;
                                }
                            }
                            break;
                        } else {
                            date2.add(Calendar.DATE, 1);
                        }
                    }
                }
            }
            ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();


            for (int j = 0; j < menuName.length; j++) {
                ArrayList<Entry> entries = new ArrayList<Entry>();
                for (int k = 0; k < diffDays; k++) {
                    entries.add(new Entry(totalPricePerMenu[j][k], k));
                }
                LineDataSet lineDataSet = new LineDataSet(entries, menuName[j]);
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
            }


            String xVals[] = new String[diffDays];

            for (int i = 0; i < diffDays; i++) {

                xVals[i] = date.getTime().getMonth()+"/"+date.getTime().getDate() + "";
                Log.d("date",date.getTime().getMonth()+"");
                date.add(Calendar.DATE,1);

            }
            LineData lineData = new LineData(xVals, lineDataSets);
            return lineData;
        }
        else{
            Log.d("asd","실패");
            return null;
        }
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
