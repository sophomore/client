package org.jaram.ds.admin.view;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.Date;


/**
 * Created by ohyongtaek on 15. 7. 18..
 */


public class LineChartFrag extends Fragment implements OnChartGestureListener{


    Typeface tf;
    protected LineChart mChart;
    Date startDate;


    public static LineChartFrag newInstance(boolean analysisType, ArrayList<String> menuList, int unitType, String start, String end) {
        LineChartFrag lineChartFrag = new LineChartFrag();
        Bundle argument = new Bundle();
        argument.putString("start",start);
        argument.putString("finish",end);
        argument.putStringArrayList("menuList", menuList);
        argument.putInt("unitType", unitType);
        argument.putBoolean("analysisType",analysisType);
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

        String startDate = getArguments().getString("start");
        String finishDate = getArguments().getString("finish");

        int unitType = getArguments().getInt("unitType");
        ArrayList<String> menuList = getMenuNameList();
        setData(menuList,unitType,startDate,finishDate);

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
        leftAxis.setAxisMaxValue(100000);
        leftAxis.setAxisMinValue(0f);
        leftAxis.setStartAtZero(false);
        leftAxis.enableGridDashedLine(5f, 5f, 0f);

        leftAxis.setDrawLimitLinesBehindData(true);


        mChart.getAxisRight().setEnabled(false);
        FrameLayout parent = (FrameLayout) view.findViewById(R.id.parentLayout);
        parent.addView(mChart);

        return view;

    }
    public ArrayList<String> getMenuNameList(){
        ArrayList<String> menuName = getArguments().getStringArrayList("menuList");
        for(int i=0;i<menuName.size();i++){
            Log.d("asd",menuName.get(i));
        }
        return menuName;
    }

    public Calendar getStartDate() throws ParseException {
        String start = getArguments().getString("start");
        Calendar cal = Calendar.getInstance();
        String []date = start.split("/");
        cal.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        return cal;
    }
    private Date getStartDate(String startDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return formatter.parse(startDate);
    }
    private Date getFinishDate(String finishDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return formatter.parse(finishDate);
    }
    public long lengthOfDate(String startDate, String finishDate) throws ParseException {


        Date start = getStartDate(startDate);
        Date finish = getFinishDate(finishDate);
        long diff = finish.getTime() - start.getTime();
        long diffDays = diff/(24*60*60*1000);

        return diffDays;
    }

    public void setData(ArrayList<String> menuList,int unitType,String startDate,String finishDate){

        ArrayList<String> selectList = menuList;

        switch (unitType){
            case 0:

                mChart.setData(generateTimeChart(menuList));
                break;
            case 1:
                try {
                    int diffDays = (int) lengthOfDate(startDate, finishDate);

                    mChart.setData(generateDateChart(menuList,diffDays));
                } catch (ParseException e) {
                    e.printStackTrace();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("날짜 오류");
                    dialog.setMessage("날짜를 제대로 입력하였는지 확인해주세요.");
                    dialog.setPositiveButton("Ok",null);
                    dialog.show();
                }

                break;
            case 2:

                mChart.setData(generateDayChart(menuList));
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;

        }
    }


    public LineData generateDateChart(ArrayList<String> menuName, int diffDays){
        Calendar date = null;

        try {
            date = getStartDate();

        } catch (ParseException e) {

            e.printStackTrace();
        }
        if(date != null) {
            Calendar date2;

            ArrayList<Order> orderList = Data.orderList;
            int totalPricePerMenu[][] = new int[menuName.size()][diffDays];
            for (Order i : orderList) {
                for (OrderMenu j : i.menuList) {
                    Calendar orderDate = Calendar.getInstance();
                    orderDate.setTime(i.date);
                    date2 = (Calendar) date.clone();

                    for (int l = 0; l < diffDays; l++) {
                        Log.d("testOrderDate",orderDate.get(Calendar.MONTH)+"");
                        if (date2.get(Calendar.YEAR) == orderDate.get(Calendar.YEAR) && date2.get(Calendar.MONTH)==orderDate.get(Calendar.MONTH) && date2.get(Calendar.DATE) == orderDate.get(Calendar.DATE)) {
                            Log.d("suc","성공");
                            for (int k = 0; k < menuName.size(); k++) {
                                if (j.menu.name == menuName.get(k)) {
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


            for (int j = 0; j < menuName.size(); j++) {
                ArrayList<Entry> entries = new ArrayList<Entry>();
                for (int k = 0; k < diffDays; k++) {
                    entries.add(new Entry(totalPricePerMenu[j][k], k));
                }
                LineDataSet lineDataSet = new LineDataSet(entries, menuName.get(j));
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

    public LineData generateTimeChart(ArrayList<String> menuList){
        Calendar date = null;
        int timeList[] ={9,10,11,12,13,14,15,16,17,18,19,20,21,22};
        try {
            date = getStartDate();

        } catch (ParseException e) {

            e.printStackTrace();
        }
        if(date != null) {
            Calendar date2;

            ArrayList<Order> orderList = Data.orderList;
            int totalPricePerMenu[][] = new int[menuList.size()][timeList.length];
            for (Order i : orderList) {
                for (OrderMenu j : i.menuList) {
                    Calendar orderDate = Calendar.getInstance();
                    orderDate.setTime(i.date);
                    Log.d("time", orderDate.get(Calendar.HOUR_OF_DAY) + "");
                    date2 = (Calendar) date.clone();
                    for (int l = 0; l < timeList.length; l++) {
                        if (orderDate.get(Calendar.HOUR_OF_DAY) == timeList[l]) {

                            for (int k = 0; k < menuList.size(); k++) {
                                if (j.menu.name == menuList.get(k)) {
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


            for (int j = 0; j < menuList.size(); j++) {
                ArrayList<Entry> entries = new ArrayList<Entry>();
                for (int k = 0; k < timeList.length; k++) {
                    entries.add(new Entry(totalPricePerMenu[j][k], k));
                }
                LineDataSet lineDataSet = new LineDataSet(entries, menuList.get(j));
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


            String xVals[] = new String[timeList.length];

            for (int i = 0; i < timeList.length; i++) {

                xVals[i] = String.format("%s",timeList[i]);
            }
            LineData lineData = new LineData(xVals, lineDataSets);
            return lineData;
        }
        else{
            Log.d("asd","실패");
            return null;
        }
    }
    public LineData generateDayChart(ArrayList<String> menuList){
        Calendar date = null;

        try {
            date = getStartDate();

        } catch (ParseException e) {

            e.printStackTrace();
        }
        if(date != null) {
            Calendar date2;

            String Days[] = {"일","화","수","목","금","토","월"};

            ArrayList<Order> orderList = Data.orderList;
            int totalPricePerMenu[][] = new int[menuList.size()][Days.length];
            for (Order i : orderList) {
                for (OrderMenu j : i.menuList) {
                    date2 = (Calendar) date.clone();
                    for (int l = 0; l < Days.length; l++) {

                        Calendar orderDate = Calendar.getInstance();
                        orderDate.setTime(i.date);
                        Log.d("days",orderDate.get(Calendar.DAY_OF_WEEK)+"");
                        if (orderDate.get(Calendar.DAY_OF_WEEK) == l+1) {
                            Log.d("suc","성공");
                            for (int k = 0; k < menuList.size(); k++) {
                                if (j.menu.name == menuList.get(k)) {
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


            for (int j = 0; j < menuList.size(); j++) {
                ArrayList<Entry> entries = new ArrayList<Entry>();
                for (int k = 0; k < Days.length; k++) {
                    entries.add(new Entry(totalPricePerMenu[j][k], k));
                }
                LineDataSet lineDataSet = new LineDataSet(entries, menuList.get(j));
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

            LineData lineData = new LineData(Days, lineDataSets);
            return lineData;
        }
        else{
            Log.d("asd","실패");
            return null;
        }
    }
    public LineData generateChart(ArrayList<String> menuName,int diffDays){
        Calendar date = null;

        try {
            date = getStartDate();

        } catch (ParseException e) {

            e.printStackTrace();
        }
        if(date != null) {
            Calendar date2;

            ArrayList<Order> orderList = Data.orderList;
            int totalPricePerMenu[][] = new int[menuName.size()][diffDays];
            for (Order i : orderList) {
                for (OrderMenu j : i.menuList) {
                    date2 = (Calendar) date.clone();
                    for (int l = 0; l < diffDays; l++) {

                        Calendar orderDate = Calendar.getInstance();

                        if (date2.get(Calendar.YEAR) == orderDate.get(Calendar.YEAR) && date2.get(Calendar.MONTH)==orderDate.get(Calendar.MONTH) && date2.get(Calendar.DATE) == orderDate.get(Calendar.DATE)) {
                            Log.d("suc","성공");
                            for (int k = 0; k < menuName.size(); k++) {
                                if (j.menu.name == menuName.get(k)) {
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


            for (int j = 0; j < menuName.size(); j++) {
                ArrayList<Entry> entries = new ArrayList<Entry>();
                for (int k = 0; k < diffDays; k++) {
                    entries.add(new Entry(totalPricePerMenu[j][k], k));
                }
                LineDataSet lineDataSet = new LineDataSet(entries, menuName.get(j));
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

                xVals[i] = String.format("%d/%d", date.getTime().getMonth() + 1, date.getTime().getDate());
                Log.d("date",(date.getTime().getMonth()+1)+"");
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
