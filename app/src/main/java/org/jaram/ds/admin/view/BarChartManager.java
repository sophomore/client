package org.jaram.ds.admin.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Menu;
import org.jaram.ds.data.struct.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by ka123ak on 2015-07-09.
 */
public class BarChartManager implements OnChartGestureListener {

    Typeface tf;
    boolean analysisType;
    ArrayList<String> menuList;
    int unitType;
    String start;
    String end;
    private BarChart mChart;
    Activity activity;
    int[] price = new int[3];
    public static int cartPrice = 0;
    public static int cashPrice = 0;

    public BarChartManager(Activity activity, boolean analysisType, ArrayList<String> menuList, int unitType, String start, String end) {
        this.analysisType = analysisType;
        this.menuList = menuList;
        this.unitType = unitType;
        this.start = start;
        this.end = end;
        this.activity = activity;
    }

    public BarChart getChart() {
        return mChart;
    }

    public void setChart(BarChart barChart) {
        this.mChart = barChart;
    }

    public long lengthOfDate(String startDate, String finishDate) throws ParseException {


        Date start = getStartDate(startDate);
        Date finish = getFinishDate(finishDate);
        long diff = finish.getTime() - start.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return diffDays;
    }


    public int getQuarter(int month){
        int quarter =0;
        if(month>=0 && month<=2){
            quarter = 0;
        } else if(month>=3 && month<=5){
            quarter = 1;
        } else if(month>=6 && month<=8){
            quarter = 2;
        } else{
            quarter = 3;
        }
        return quarter;
    }

    public BarData getData(ArrayList<String> menuList, int unitType, String startDate, String finishDate) {

        switch (unitType){
            case 0:

                return generateTimeChart(menuList);

            case 1:
                try {
                    int diffDays = (int) lengthOfDate(startDate, finishDate);

                    return generateDateChart(menuList,diffDays);
                } catch (ParseException e) {
                    e.printStackTrace();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                    dialog.setTitle("날짜 오류");
                    dialog.setMessage("날짜를 제대로 입력하였는지 확인해주세요.");
                    dialog.setPositiveButton("Ok",null);
                    dialog.show();
                }

                break;
            case 2:

                return generateDayChart(menuList);

            case 3:

                return generateMonthChart(menuList);

            case 4:

                return generateQuarterChart(menuList);

            case 5:

                return generateYearChart(menuList,startDate,finishDate);


        }

        return null;
    }

    public ArrayList<String> getMenuNameList() {
        ArrayList<String> menuName = new ArrayList<String>();
        for (Menu i : Data.menuList.values()) {
            menuName.add(i.name);
        }
        return menuName;
    }

    public Calendar getStartDate() throws ParseException {
        Calendar cal = Calendar.getInstance();
        String[] date = start.split("-");
        cal.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        return cal;
    }

    public Calendar getFinishDate() {
        Calendar cal = Calendar.getInstance();
        String[] date = end.split("-");
        cal.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        return cal;
    }

    private Date getStartDate(String startDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(startDate);
    }

    private Date getFinishDate(String finishDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(finishDate);
    }

    public BarData generateDateChart(ArrayList<String> menuName, int diffDays) {
        Calendar date = null;

        try {
            date = getStartDate();

        } catch (ParseException e) {

            e.printStackTrace();
        }
        if (date != null) {
            Calendar date2;

            ArrayList<Order> orderList = Data.orderList;
            int totalPricePerMenu[] = new int[diffDays];
            for (Order i : orderList) {
                Calendar orderDate = Calendar.getInstance();
                orderDate.setTime(i.date);
                date2 = (Calendar) date.clone();

                for (int l = 0; l < diffDays; l++) {
                    if (date2.get(Calendar.YEAR) == orderDate.get(Calendar.YEAR) && date2.get(Calendar.MONTH) == orderDate.get(Calendar.MONTH) && date2.get(Calendar.DATE) == orderDate.get(Calendar.DATE)) {
                        totalPricePerMenu[l] += totalPricePerMenu[l] + i.totalPrice;
                    } else {
                        date2.add(Calendar.DATE, 1);
                    }
                }

            }
            ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();

            ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
            for (int k = 0; k < diffDays; k++) {

                entries.add(new BarEntry(totalPricePerMenu[k], k));

            }

            BarDataSet barDataSet = new BarDataSet(entries, "매출액");
            barDataSet.setColor(Color.rgb(255, 128, 128));
            barDataSets.add(barDataSet);


            String xVals[] = new String[diffDays];

            for (int i = 0; i < diffDays; i++) {

                xVals[i] = date.getTime().getMonth() + "/" + date.getTime().getDate() + "";
                Log.d("date", date.getTime().getMonth() + "");
                date.add(Calendar.DATE, 1);

            }
            BarData barData = new BarData(xVals, barDataSets);
            return barData;
        } else {
            Log.d("asd", "실패");
            return null;
        }
    }

    public BarData generateTimeChart(ArrayList<String> menuList) {
        Calendar date = null;
        int timeList[] = {9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22};
        try {
            date = getStartDate();

        } catch (ParseException e) {

            e.printStackTrace();
        }
        if (date != null) {
            Calendar date2;

            ArrayList<Order> orderList = Data.orderList;
            int totalPricePerMenu[] = new int[timeList.length];
            for (Order i : orderList) {
                Calendar orderDate = Calendar.getInstance();
                orderDate.setTime(i.date);

                Log.d("time", orderDate.get(Calendar.HOUR_OF_DAY) + "");
                date2 = (Calendar) date.clone();
                for (int l = 0; l < timeList.length; l++) {
                    if (orderDate.get(Calendar.HOUR_OF_DAY) == timeList[l]) {
                        totalPricePerMenu[l] += totalPricePerMenu[l] + i.totalPrice;
                    } else {
                        date2.add(Calendar.DATE, 1);
                    }
                }

            }


            ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();

            ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
            for (int k = 0; k < timeList.length; k++) {

                entries.add(new BarEntry(totalPricePerMenu[k], k));

            }


            BarDataSet barDataSet = new BarDataSet(entries, "매출액");
            barDataSet.setColor(Color.rgb(255, 128, 128));
            barDataSets.add(barDataSet);

            String xVals[] = new String[timeList.length];

            for (int i = 0; i < timeList.length; i++) {

                xVals[i] = String.format("%s", timeList[i]);
            }
            BarData barData = new BarData(xVals, barDataSets);
            return barData;
        } else {
            Log.d("asd", "실패");
            return null;
        }
    }

    public BarData generateDayChart(ArrayList<String> menuList) {
        Calendar date = null;

        try {
            date = getStartDate();

        } catch (ParseException e) {

            e.printStackTrace();
        }
        if (date != null) {
            Calendar date2;

            String Days[] = {"일", "화", "수", "목", "금", "토", "월"};

            ArrayList<Order> orderList = Data.orderList;
            int totalPricePerMenu[] = new int[Days.length];
            for (Order i : orderList) {
                date2 = (Calendar) date.clone();

                Calendar orderDate = Calendar.getInstance();
                orderDate.setTime(i.date);
                for (int l = 0; l < Days.length; l++) {

                    if (orderDate.get(Calendar.DAY_OF_WEEK) == l + 1) {
                        totalPricePerMenu[l] += totalPricePerMenu[l] + i.totalPrice;
                    } else {
                        date2.add(Calendar.DATE, 1);
                    }
                }
            }
            ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();

            ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
            for (int k = 0; k < Days.length; k++) {

                entries.add(new BarEntry(totalPricePerMenu[k], k));

            }

            BarDataSet barDataSet = new BarDataSet(entries, "매출액");
            barDataSet.setColor(Color.rgb(255, 128, 128));
            barDataSets.add(barDataSet);

            BarData barData = new BarData(Days, barDataSets);
            return barData;
        } else {
            Log.d("asd", "실패");
            return null;
        }
    }

    public BarData generateMonthChart(ArrayList<String> menuList) {
        Calendar startDate = null;
        Calendar endDate = null;
        Calendar cloneStart = null;
        ArrayList<String> months = new ArrayList<String>();

        try {
            startDate = getStartDate();
            endDate = getFinishDate();
        } catch (ParseException e) {

            e.printStackTrace();
        }
        if (startDate != null) {
            Calendar date2;
            cloneStart = (Calendar) startDate.clone();
            while(!((cloneStart.get(Calendar.YEAR) == endDate.get(Calendar.YEAR)) && (cloneStart.get(Calendar.MONTH) == endDate.get(Calendar.MONTH)))){
                String month = cloneStart.get(Calendar.YEAR)+"."+cloneStart.get(Calendar.MONTH);
                months.add(month);
                cloneStart.add(Calendar.MONTH,1);
            }
            String month = cloneStart.get(Calendar.YEAR)+"."+cloneStart.get(Calendar.MONTH);
            months.add(month);

            ArrayList<Order> orderList = Data.orderList;
            int totalPricePerMenu[] = new int[months.size()];
            for (Order i : orderList) {
                date2 = (Calendar) startDate.clone();

                Calendar orderDate = Calendar.getInstance();
                orderDate.setTime(i.date);
                for (int l = 0; l < months.size(); l++) {

                    if ((orderDate.get(Calendar.YEAR)+"."+orderDate.get(Calendar.MONTH)).equals(months.get(l))) {
                        totalPricePerMenu[l] += totalPricePerMenu[l] + i.totalPrice;

                    } else {
                        date2.add(Calendar.DATE, 1);
                    }
                }
            }

            ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();

            ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
            for (int k = 0; k < months.size(); k++) {

                entries.add(new BarEntry(totalPricePerMenu[k], k));

            }

            BarDataSet barDataSet = new BarDataSet(entries, "매출액");
//            barDataSet.setColor(Color.rgb(255, 128, 128));
            barDataSets.add(barDataSet);

            String xVals[] = new String[months.size()];

            for (int i = 0; i < months.size(); i++) {

                xVals[i] = String.format("%s", months.get(i));
                startDate.add(Calendar.DATE, 1);

            }
            BarData barData = new BarData(xVals, barDataSets);
            return barData;
        }

        else{
            Log.d("asd","실패");
            return null;
        }
    }

    public BarData generateQuarterChart(ArrayList<String> menuList){
        Calendar date = null;

        String []quarters = {"1분기","2분기","3분기","4분기"};
        try {
            date = getStartDate();

        } catch (ParseException e) {

            e.printStackTrace();
        }
        if(date != null) {


            ArrayList<Order> orderList = Data.orderList;
            int totalPricePerMenu[] = new int[quarters.length];
            for (Order i : orderList) {
                Calendar orderDate = Calendar.getInstance();
                orderDate.setTime(i.date);
                for (int l = 0; l < quarters.length; l++) {
                    int quarter  = getQuarter(orderDate.get(Calendar.MONTH));

                    totalPricePerMenu[quarter] += totalPricePerMenu[quarter] + i.totalPrice;



                }

            }

            ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();

            ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
            for (int k = 0; k < quarters.length; k++) {

                entries.add(new BarEntry(totalPricePerMenu[k], k));

            }

            BarDataSet barDataSet = new BarDataSet(entries, "매출액");
            barDataSet.setColor(Color.rgb(255, 128, 128));
            barDataSets.add(barDataSet);


            BarData barData = new BarData(quarters, barDataSets);
            return barData;
        }
        else{
            Log.d("asd","실패");
            return null;
        }
    }


    public BarData generateYearChart(ArrayList<String> menuList,String startDate, String finishDate){
        Calendar start = null;
        Calendar finish = null;
        try {
            start = getStartDate();
            finish = getFinishDate();
        } catch (ParseException e) {

            e.printStackTrace();
        }
        if(start != null) {
            Calendar date2;

            int diffYear = finish.get(Calendar.YEAR) - start.get(Calendar.YEAR)+1;
            ArrayList<Order> orderList = Data.orderList;
            int totalPricePerMenu[] = new int[diffYear];
            for (Order i : orderList) {

                date2 = (Calendar) start.clone();
                Calendar orderDate = Calendar.getInstance();
                orderDate.setTime(i.date);
                for (int l = 0; l < diffYear; l++) {


                    if (orderDate.get(Calendar.YEAR) == start.get(Calendar.YEAR)+ l) {

                        totalPricePerMenu[l] += totalPricePerMenu[l] + i.totalPrice;

                    } else {
                        date2.add(Calendar.DATE, 1);
                    }
                }

            }
            ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();


            ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();

            ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
            for (int k = 0; k < diffYear; k++) {

                entries.add(new BarEntry(totalPricePerMenu[k], k));

            }

            BarDataSet barDataSet = new BarDataSet(entries, "매출액");
            barDataSet.setColor(Color.rgb(255, 128, 128));
            barDataSets.add(barDataSet);

            String xVals[] = new String[diffYear];

            for (int i = 0; i < diffYear; i++) {

                xVals[i] = String.format("%d",start.get(Calendar.YEAR)+i);
            }
            BarData barData = new BarData(xVals, barDataSets);
            return barData;
        }
        else{
            Log.d("asd","실패");
            return null;
        }
    }



    @Override
    public void onChartLongPressed(MotionEvent me) {
        Toast.makeText(activity,"onChartLongPressed 실행됨",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Toast.makeText(activity,"onChartDoubleTapped 실행됨",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Toast.makeText(activity,"onChartSingleTapped 실행됨",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Toast.makeText(activity,"onChartFling 실행됨",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Toast.makeText(activity,"onChartScale 실행됨",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Toast.makeText(activity,"onChartTranslate 실행됨",Toast.LENGTH_LONG).show();
    }
}
