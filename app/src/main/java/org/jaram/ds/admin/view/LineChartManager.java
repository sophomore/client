package org.jaram.ds.admin.view;


import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import org.jaram.ds.data.Data;
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


public class LineChartManager implements OnChartGestureListener{


    Typeface tf;
    protected LineChart mChart;
    Date startDate;
    boolean analysisType;
    ArrayList<String> menuList;
    int unitType;
    String start;
    String end;

    public static int max = 0;

    public static int getMax(){
        return max;
    }
    public static void setMex(int num){
        max = num;
    }
    public LineChartManager(){
    }

    public LineChart getChart(){
        return mChart;
    }

    public void setChart(LineChart lineChart){
        this.mChart = lineChart;
    }

    public ArrayList<String> getMenuNameList(){
        return menuList;
    }

    public Calendar getStartDate() throws ParseException {
        Calendar cal = Calendar.getInstance();
        String []date = start.split("-");
        cal.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        return cal;
    }

    public Calendar getFinishDate(){
        Calendar cal = Calendar.getInstance();
        String []date = end.split("-");
        cal.set(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]));
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
    public long lengthOfDate(String startDate, String finishDate) throws ParseException {


        Date start = getStartDate(startDate);
        Date finish = getFinishDate(finishDate);
        long diff = finish.getTime() - start.getTime();
        long diffDays = diff/(24*60*60*1000);

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
    public LineData getData(boolean analysisType,ArrayList<String> menuList,int unitType,String startDate,String finishDate){

        this.analysisType = analysisType;
        this.menuList = menuList;
        this.unitType = unitType;
        this.start = startDate;
        this.end = finishDate;

        switch (unitType){
            case 0:

                return generateTimeChart(menuList);

            case 1:
                try {
                    int diffDays = (int) lengthOfDate(startDate, finishDate);

                    return generateDateChart(menuList,diffDays);
                } catch (ParseException e) {
                    e.printStackTrace();
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
                                    if(analysisType == true) {
                                        totalPricePerMenu[k][l] += totalPricePerMenu[k][l] + j.menu.price;
                                        if(max< totalPricePerMenu[k][l]){
                                            max = totalPricePerMenu[k][l];
                                        }
                                    } else if(analysisType == false){
                                        totalPricePerMenu[k][l] += totalPricePerMenu[k][l] +1;
                                        if(max< totalPricePerMenu[k][l]){
                                            max = totalPricePerMenu[k][l];
                                        }
                                    }
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
                                    if(analysisType == true) {
                                        totalPricePerMenu[k][l] += totalPricePerMenu[k][l] + j.menu.price;
                                        if(max< totalPricePerMenu[k][l]){
                                            max = totalPricePerMenu[k][l];
                                        }
                                    } else if(analysisType == false){
                                        totalPricePerMenu[k][l] += totalPricePerMenu[k][l] + 1;
                                        if(max< totalPricePerMenu[k][l]){
                                            max = totalPricePerMenu[k][l];
                                        }
                                    }
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
                                    if(analysisType == true) {
                                        totalPricePerMenu[k][l] += totalPricePerMenu[k][l] + j.menu.price;
                                        if(max< totalPricePerMenu[k][l]){
                                            max = totalPricePerMenu[k][l];
                                        }
                                    } else if(analysisType == false){
                                        totalPricePerMenu[k][l] += totalPricePerMenu[k][l] +1;
                                        if(max< totalPricePerMenu[k][l]){
                                            max = totalPricePerMenu[k][l];
                                        }
                                    }
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
    public LineData generateMonthChart(ArrayList<String> menuList){
        Calendar date = null;
        int months[]= {1,2,3,4,5,6,7,8,9,10,11,12};

        try {
            date = getStartDate();

        } catch (ParseException e) {

            e.printStackTrace();
        }
        if(date != null) {
            Calendar date2;

            ArrayList<Order> orderList = Data.orderList;
            int totalPricePerMenu[][] = new int[menuList.size()][months.length];
            for (Order i : orderList) {
                for (OrderMenu j : i.menuList) {
                    date2 = (Calendar) date.clone();
                    for (int l = 0; l < months.length; l++) {

                        Calendar orderDate = Calendar.getInstance();
                        orderDate.setTime(i.date);
                        Log.d("month", orderDate.get(Calendar.MONTH) + "");
                        if (orderDate.get(Calendar.MONTH)==l) {
                            Log.d("suc","성공");
                            for (int k = 0; k < menuList.size(); k++) {
                                if (j.menu.name == menuList.get(k)) {
                                    if(analysisType == true) {
                                        totalPricePerMenu[k][l] += totalPricePerMenu[k][l] + j.menu.price;
                                        if(max< totalPricePerMenu[k][l]){
                                            max = totalPricePerMenu[k][l];
                                        }
                                    } else if(analysisType == false){
                                        totalPricePerMenu[k][l] += totalPricePerMenu[k][l] + 1;
                                        if(max< totalPricePerMenu[k][l]){
                                            max = totalPricePerMenu[k][l];
                                        }
                                    }
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
                for (int k = 0; k < months.length; k++) {
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


            String xVals[] = new String[months.length];

            for (int i = 0; i < months.length; i++) {

                xVals[i] = String.format("%s",months[i]);
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
    public LineData generateQuarterChart(ArrayList<String> menuList){
        Calendar date = null;

        String []quarters = {"1분기","2분기","3분기","4분기"};
        try {
            date = getStartDate();

        } catch (ParseException e) {

            e.printStackTrace();
        }
        if(date != null) {
            Calendar date2;

            ArrayList<Order> orderList = Data.orderList;
            int totalPricePerMenu[][] = new int[menuList.size()][quarters.length];
            for (Order i : orderList) {
                for (OrderMenu j : i.menuList) {
                    date2 = (Calendar) date.clone();
                    for (int l = 0; l < quarters.length; l++) {
                        Calendar orderDate = Calendar.getInstance();
                        orderDate.setTime(i.date);
                        int quarter  = getQuarter(orderDate.get(Calendar.MONTH));
                        Log.d("quarter",quarter+"");
                        for (int k = 0; k < menuList.size(); k++) {
                            if (j.menu.name == menuList.get(k)) {
                                if(analysisType == true) {
                                    totalPricePerMenu[k][quarter] += totalPricePerMenu[k][quarter] + j.menu.price;
                                } else if(analysisType ==false){
                                    totalPricePerMenu[k][quarter] += totalPricePerMenu[k][quarter] + 1;
                                }
                            }
                        }

                    }
                }
            }
            ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();


            for (int j = 0; j < menuList.size(); j++) {
                ArrayList<Entry> entries = new ArrayList<Entry>();
                for (int k = 0; k < quarters.length; k++) {
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


            LineData lineData = new LineData(quarters, lineDataSets);
            return lineData;
        }
        else{
            Log.d("asd","실패");
            return null;
        }
    }
    public LineData generateYearChart(ArrayList<String> menuList,String startDate, String finishDate){
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
            int totalPricePerMenu[][] = new int[menuList.size()][diffYear];
            for (Order i : orderList) {
                for (OrderMenu j : i.menuList) {
                    date2 = (Calendar) start.clone();

                    for (int l = 0; l < diffYear; l++) {

                        Calendar orderDate = Calendar.getInstance();
                        orderDate.setTime(i.date);
                        if (orderDate.get(Calendar.YEAR) == start.get(Calendar.YEAR)+ l) {
                            Log.d("suc","성공");
                            for (int k = 0; k < menuList.size(); k++) {
                                if (j.menu.name == menuList.get(k)) {
                                    if(analysisType == true) {
                                        totalPricePerMenu[k][l] += totalPricePerMenu[k][l] + j.menu.price;
                                    } else if(analysisType ==false){
                                        totalPricePerMenu[k][l] += totalPricePerMenu[k][l] + 1;
                                    }
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
                for (int k = 0; k < diffYear; k++) {
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


            String xVals[] = new String[diffYear];

            for (int i = 0; i < diffYear; i++) {

                xVals[i] = String.format("%d",start.get(Calendar.YEAR)+i);
            }
            LineData lineData = new LineData(xVals, lineDataSets);
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
