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
import java.util.Iterator;


/**
 * Created by ohyongtaek on 15. 7. 18..
 */


public class LineChartManager implements OnChartGestureListener {


    Typeface tf;
    protected LineChart mChart;
    Date startDate;
    boolean analysisType;
    ArrayList<String> menuList;
    int unitType;
    String start;
    String end;

    public LineChartManager() {
    }

    public LineChart getChart() {
        return mChart;
    }

    public void setChart(LineChart lineChart) {
        this.mChart = lineChart;
    }

    public ArrayList<String> getMenuNameList() {
        return menuList;
    }

    public Calendar getStartDate() throws ParseException {
        Calendar cal = Calendar.getInstance();
        String[] date = start.split("-");
        cal.set(Integer.parseInt(date[0]), Integer.parseInt(date[1])-1, Integer.parseInt(date[2]));
        return cal;
    }

    public Calendar getFinishDate() {
        Calendar cal = Calendar.getInstance();
        String[] date = end.split("-");
        cal.set(Integer.parseInt(date[0]), Integer.parseInt(date[1])-1, Integer.parseInt(date[2]));
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
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return diffDays;
    }

    public int getQuarter(int month) {
        int quarter = 0;
        if (month >= 0 && month <= 2) {
            quarter = 0;
        } else if (month >= 3 && month <= 5) {
            quarter = 1;
        } else if (month >= 6 && month <= 8) {
            quarter = 2;
        } else {
            quarter = 3;
        }
        return quarter;
    }

    public LineData getData(boolean analysisType, ArrayList<String> menuList, int unitType, String startDate, String finishDate) {

        this.analysisType = analysisType;
        this.menuList = menuList;
        this.unitType = unitType;
        this.start = startDate;
        this.end = finishDate;

        switch (unitType) {
            case 0:

                return generateTimeChart(menuList);

            case 1:
                try {
                    int diffDays = (int) lengthOfDate(startDate, finishDate);

                    return generateDateChart(menuList, diffDays);
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

                return generateYearChart(menuList);


        }

        return null;
    }


    public LineData generateDateChart(ArrayList<String> menuList, int diffDays) {
        Calendar startDate = null;
        Calendar endDate = null;
        Calendar cloneStart = null;
        ArrayList<String> dates = new ArrayList<>();
        try {
            startDate = getStartDate();
            endDate = getFinishDate();
        } catch (ParseException e) {

            e.printStackTrace();
        }
        if (startDate != null) {

            cloneStart = (Calendar) startDate.clone();
            Date clone = cloneStart.getTime();
            Log.d("ttss", String.valueOf(cloneStart.getMinimum(Calendar.DATE)));
            Log.d("ttsss",cloneStart.get(Calendar.YEAR)+"."+cloneStart.get(Calendar.MONTH)+"."+cloneStart.get(Calendar.DATE));
            while (!((cloneStart.get(Calendar.YEAR) == endDate.get(Calendar.YEAR)) && (cloneStart.get(Calendar.MONTH) == endDate.get(Calendar.MONTH)) && cloneStart.get(Calendar.DATE) == endDate.get(Calendar.DATE))) {
                String date_unit = cloneStart.get(Calendar.YEAR) + "." + (cloneStart.get(Calendar.MONTH)+1) + "." + cloneStart.get(Calendar.DATE);
                cloneStart.add(Calendar.DATE, 1);
                Log.d("tstss", String.valueOf(cloneStart.get(Calendar.DATE)));
                dates.add(date_unit);
                Log.d("ttsss", date_unit);


            }
            String date_unit = cloneStart.get(Calendar.YEAR) + "." + cloneStart.get(Calendar.MONTH)+"."+cloneStart.get(Calendar.DATE);
            Log.d("ttsss",date_unit);
            dates.add(date_unit);
            Log.d("ttsst",dates.size()+"");

            ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
            Entry[][] entries = new Entry[menuList.size()][dates.size()];
            Log.d("ttsst",entries.length+"");
            Log.d("ttsst",dates.size()+"");
            for (int i = 0; i < Data.menus.size(); i++) {
                Iterator<Integer> iterator = Data.menus.get(i).keySet().iterator();
                int menu_index = 0;

                while(iterator.hasNext()){
                    int menu_id = iterator.next();
                    Log.d("ttsst",menu_id+"@");
                    float data = (float) Data.menus.get(i).get(menu_id);
                    entries[menu_index][i] = new Entry(data,i);
                    Log.d("ttee",entries[menu_index][i]+"");
                    menu_index++;
                }


            }
            for(int  j = 0; j< entries.length;j++){

                ArrayList<Entry> entries1 = new ArrayList<>();
                for(int i =0; i< entries[j].length;i++){
                    entries1.add(entries[j][i]);

                }

                LineDataSet lineDataSet = new LineDataSet(entries1,menuList.get(j));
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


            LineData lineData = new LineData(dates, lineDataSets);
            return lineData;
        } else {
            Log.d("asd", "실패");
            return null;
        }
    }

    public LineData generateTimeChart(ArrayList<String> menuList) {
        Calendar date = null;
        int timeList[] = {1,2,3,4,5,6,7,8,9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22,23,24};
        try {
            date = getStartDate();

        } catch (ParseException e) {

            e.printStackTrace();
        }
        if (date != null) {



            Entry entries[][] = new Entry[menuList.size()][timeList.length];
            for (int i = 0; i < Data.menus.size(); i++) {
                Iterator<String> iterator = Data.menus.get(i).keySet().iterator();
                int menu_index = 0;

                while(iterator.hasNext()){
                    String menu_id = iterator.next();
                    Log.d("menu_id", menu_id + "!@");
                    float data = (float) Data.menus.get(i).get(menu_id);
                    entries[menu_index][i] = new Entry(data,i);
                    Log.d("ttsss",entries[menu_index][i]+"%%"+menu_index+"#"+i);
                    menu_index++;
                }
                Log.d("menu_id","next");
            }

            ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
            for(int  j = 0; j< entries.length;j++){
                Log.d("ttsss",j+"");
                ArrayList<Entry> entries1 = new ArrayList<>();
                for(int i =0; i< entries[j].length;i++){
                    entries1.add(entries[j][i]);
                    Log.d("ttsss",entries[j][i]+"$");
                }
                Log.d("ttsss",entries1.size()+"!"+menuList.get(j));
                LineDataSet lineDataSet = new LineDataSet(entries1,menuList.get(j));
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

                xVals[i] = String.format("%s", timeList[i]);
            }
            LineData lineData = new LineData(xVals, lineDataSets);
            return lineData;
        } else {
            Log.d("asd", "실패");
            return null;
        }
    }

    public LineData generateDayChart(ArrayList<String> menuList) {
        Calendar date = null;

        try {
            date = getStartDate();

        } catch (ParseException e) {

            e.printStackTrace();
        }
        if (date != null) {


            String Days[] = {"월", "화", "수", "목", "금", "토", "일"};

            ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
            Entry[][] entries = new Entry[menuList.size()][Days.length];
            for (int i = 0; i < Data.menus.size(); i++) {
                Iterator<Integer> iterator = Data.menus.get(i).keySet().iterator();
                int menu_index = 0;

                while(iterator.hasNext()){
                    int menu_id = iterator.next();
                    float data = (float) Data.menus.get(i).get(menu_id);
                    entries[menu_index][i] = new Entry(data,i);
                    menu_index++;
                }


            }
            for(int  j = 0; j< entries.length;j++){

                ArrayList<Entry> entries1 = new ArrayList<>();
                for(int i =0; i< entries[j].length;i++){
                    entries1.add(entries[j][i]);

                }
                LineDataSet lineDataSet = new LineDataSet(entries1,menuList.get(j));
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
        } else {
            Log.d("asd", "실패");
            return null;
        }
    }

    public LineData generateMonthChart(ArrayList<String> menuList) {
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

            cloneStart = (Calendar) startDate.clone();
            while (!((cloneStart.get(Calendar.YEAR) == endDate.get(Calendar.YEAR)) && (cloneStart.get(Calendar.MONTH) == endDate.get(Calendar.MONTH)))) {
                String month = cloneStart.get(Calendar.YEAR) + "." +(cloneStart.get(Calendar.MONTH)+1);
                months.add(month);
                cloneStart.add(Calendar.MONTH, 1);
            }
            String month = cloneStart.get(Calendar.YEAR) + "." + (cloneStart.get(Calendar.MONTH)+1);
            months.add(month);


            ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
            Entry[][] entries = new Entry[menuList.size()][months.size()];

            for (int i = 0; i < Data.menus.size(); i++) {
                Iterator<Integer> iterator = Data.menus.get(i).keySet().iterator();
                int menu_index = 0;

                while(iterator.hasNext()){
                    int menu_id = iterator.next();
                    Log.d("menu_id",menu_id+"!@");
                    float data = (float) Data.menus.get(i).get(menu_id);
                    entries[menu_index][i] = new Entry(data,i);
                    Log.d("ttsss",entries[menu_index][i]+"%%"+menu_index+"#"+i);
                    menu_index++;
                }
                Log.d("menu_id","next");

            }
            for(int  j = 0; j< entries.length;j++){
                Log.d("ttsss",j+"");
                ArrayList<Entry> entries1 = new ArrayList<>();
                for(int i =0; i< entries[j].length;i++){
                    entries1.add(entries[j][i]);
                    Log.d("ttsss", entries[j][i] + "$");
                }
                Log.d("ttsss",entries1.size()+"!"+menuList.get(j));
                LineDataSet lineDataSet = new LineDataSet(entries1,menuList.get(j));
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

            LineData lineData = new LineData(months, lineDataSets);
            Log.d("lineData",lineData+"");
            return lineData;
        } else {
            Log.d("asd", "실패");
            return null;
        }
    }

    public LineData generateQuarterChart(ArrayList<String> menuList) {
        Calendar startDate = null;
        Calendar endDate = null;
        Calendar cloneStart = null;
        ArrayList<String> quarters = new ArrayList<>();

        try {
            startDate = getStartDate();
            endDate = getFinishDate();

        } catch (ParseException e) {

            e.printStackTrace();
        }
        if (startDate != null) {


            cloneStart = (Calendar) startDate.clone();
            while (!((cloneStart.get(Calendar.YEAR) == endDate.get(Calendar.YEAR)) && (cloneStart.get(Calendar.MONTH) == endDate.get(Calendar.MONTH)))) {
                int month = cloneStart.get(Calendar.MONTH)+1;
                if(month>=1 && month<=3){
                    String quarter = cloneStart.get(Calendar.YEAR)+".1분기";
                    if(!quarters.contains(quarter)){
                        quarters.add(quarter);
                    }
                } else if(month>=4 && month<=6){
                    String quarter = cloneStart.get(Calendar.YEAR)+".2분기";
                    if(!quarters.contains(quarter)){
                        quarters.add(quarter);
                    }
                } else if(month>=7 && month<=9){
                    String quarter = cloneStart.get(Calendar.YEAR)+".3분기";
                    if(!quarters.contains(quarter)){
                        quarters.add(quarter);
                    }
                } else if(month>=10 && month<=12){
                    String quarter = cloneStart.get(Calendar.YEAR)+".4분기";
                    if(!quarters.contains(quarter)){
                        quarters.add(quarter);
                    }
                }
                cloneStart.add(Calendar.MONTH, 1);
            }
            int month = cloneStart.get(Calendar.MONTH)+1;
            if(month>=1 && month<=3){
                String quarter = cloneStart.get(Calendar.YEAR)+".1분기";
                if(!quarters.contains(quarter)){
                    quarters.add(quarter);
                }
            } else if(month>=4 && month<=6){
                String quarter = cloneStart.get(Calendar.YEAR)+".2분기";
                if(!quarters.contains(quarter)){
                    quarters.add(quarter);
                }
            } else if(month>=7 && month<=9){
                String quarter = cloneStart.get(Calendar.YEAR)+".3분기";
                if(!quarters.contains(quarter)){
                    quarters.add(quarter);
                }
            } else if(month>=10 && month<=12){
                String quarter = cloneStart.get(Calendar.YEAR)+".4분기";
                if(!quarters.contains(quarter)){
                    quarters.add(quarter);
                }
            }


            ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
            Entry[][] entries = new Entry[menuList.size()][quarters.size()];
            for (int i = 0; i < Data.menus.size(); i++) {
                Iterator<Integer> iterator = Data.menus.get(i).keySet().iterator();
                int menu_index = 0;

                while(iterator.hasNext()){
                    int menu_id = iterator.next();
                    Log.d("menu_id",menu_id+"!@");
                    float data = (float) Data.menus.get(i).get(menu_id);
                    entries[menu_index][i] = new Entry(data,i);
                    Log.d("ttsss",entries[menu_index][i]+"%%"+menu_index+"#"+i);
                    menu_index++;
                }
                Log.d("menu_id","next");

            }
            for(int  j = 0; j< entries.length;j++){

                ArrayList<Entry> entries1 = new ArrayList<>();
                for(int i =0; i< entries[j].length;i++){
                    entries1.add(entries[j][i]);
                }
                LineDataSet lineDataSet = new LineDataSet(entries1,menuList.get(j));
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
        } else {
            Log.d("asd", "실패");
            return null;
        }
    }

    public LineData generateYearChart(ArrayList<String> menuList) {
        Calendar startDate = null;
        Calendar endDate = null;
        Calendar cloneStart = null;
        ArrayList<String> years = new ArrayList<>();
        try {
            startDate = getStartDate();
            endDate = getFinishDate();
        } catch (ParseException e) {

            e.printStackTrace();
        }
        if (startDate != null) {

            cloneStart = (Calendar) startDate.clone();
            while (!(cloneStart.get(Calendar.YEAR) == endDate.get(Calendar.YEAR))) {
                String year = String.valueOf(cloneStart.get(Calendar.YEAR));
                years.add(year);
                cloneStart.add(Calendar.YEAR,1);
            }
            String year = String.valueOf(cloneStart.get(Calendar.YEAR));
            years.add(year);


            ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
            Entry[][] entries = new Entry[menuList.size()][years.size()];

            for (int i = 0; i < Data.menus.size(); i++) {
                Iterator<Integer> iterator = Data.menus.get(i).keySet().iterator();
                int menu_index = 0;

                while(iterator.hasNext()){
                    int menu_id = iterator.next();

                    float data = (float) Data.menus.get(i).get(menu_id);
                    entries[menu_index][i] = new Entry(data,i);

                    menu_index++;
                }


            }
            for(int  j = 0; j< entries.length;j++){

                ArrayList<Entry> entries1 = new ArrayList<>();
                for(int i =0; i< entries[j].length;i++){
                    entries1.add(entries[j][i]);

                }
                LineDataSet lineDataSet = new LineDataSet(entries1,menuList.get(j));
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

            LineData lineData = new LineData(years, lineDataSets);
            return lineData;
        } else {
            Log.d("asd", "실패");
            return null;
        }
    }

    public LineData generateChart(ArrayList<String> menuName, int diffDays) {
        Calendar date = null;

        try {
            date = getStartDate();

        } catch (ParseException e) {

            e.printStackTrace();
        }
        if (date != null) {
            Calendar date2;

            ArrayList<Order> orderList = Data.orderList;
            int totalPricePerMenu[][] = new int[menuName.size()][diffDays];
            for (Order i : orderList) {
                for (OrderMenu j : i.menuList) {
                    date2 = (Calendar) date.clone();
                    for (int l = 0; l < diffDays; l++) {

                        Calendar orderDate = Calendar.getInstance();

                        if (date2.get(Calendar.YEAR) == orderDate.get(Calendar.YEAR) && date2.get(Calendar.MONTH) == orderDate.get(Calendar.MONTH) && date2.get(Calendar.DATE) == orderDate.get(Calendar.DATE)) {
                            Log.d("suc", "성공");
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
                Log.d("date", (date.getTime().getMonth() + 1) + "");
                date.add(Calendar.DATE, 1);

            }
            LineData lineData = new LineData(xVals, lineDataSets);
            return lineData;
        } else {
            Log.d("asd", "실패");
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
