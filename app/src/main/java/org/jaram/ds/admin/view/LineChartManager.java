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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;


/**
 * Created by ohyongtaek on 15. 7. 18..
 */

/*꺽은선 그래프
    1. LineData 안에는 여러개의 LineDataSet이 들어갈 수 있다.
    2. LineDataSet은 하나의 꺽은선을 나타낸다.
    3. LineDataSet에는 ArrayList<Entry> 가 들어가는데 하나의 메뉴에 관한 데이터가 x축의 갯수만큼 들어가야한다.
    4. LineDataSets를 만들었으면 LineData에 x축 String list와 LineDataSets를 넣어 만들어준다.
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

    public Calendar getStartDate() throws ParseException {
        Calendar cal = Calendar.getInstance();
        String[] date = start.split("-");
        cal.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]));
        return cal;
    }

    public Calendar getFinishDate() {
        Calendar cal = Calendar.getInstance();
        String[] date = end.split("-");
        cal.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]));
        return cal;
    }


    public int getQuarter(int month) {
        int quarter;
        if (month >= 1 && month <= 3) {
            quarter = 1;
        } else if (month >= 4 && month <= 6) {
            quarter = 2;
        } else if (month >= 7 && month <= 9) {
            quarter = 3;
        } else {
            quarter = 4;
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
                return generateDateChart(menuList);
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


    public LineData generateDateChart(ArrayList<String> menuList) {
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
            while (cloneStart.getTime().getTime() <= endDate.getTime().getTime()) {
                String date_unit = cloneStart.get(Calendar.YEAR) + "." + (cloneStart.get(Calendar.MONTH) + 1) + "." + cloneStart.get(Calendar.DATE);
                dates.add(date_unit);
                cloneStart.add(Calendar.DATE, 1);
            }
            ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
            Entry[][] entries = new Entry[menuList.size()][dates.size()];
            ArrayList<HashMap> hashMaps = null;
            if(analysisType){
                hashMaps = Data.menus;
            } else{
                hashMaps = Data.counts;
            }
            for (int i = 0; i <hashMaps.size() ; i++) {
                Iterator<Integer> iterator = hashMaps.get(i).keySet().iterator();
                int menu_index = 0;

                while (iterator.hasNext()) {
                    int menu_id = iterator.next();

                    float data = (float) hashMaps.get(i).get(menu_id);
                    entries[menu_index][i] = new Entry(data, i);

                    menu_index++;
                }


            }
            for (int j = 0; j < entries.length; j++) {

                ArrayList<Entry> entries1 = new ArrayList<>();
                for (int i = 0; i < entries[j].length; i++) {
                    entries1.add(entries[j][i]);

                }

                LineDataSet lineDataSet = new LineDataSet(entries1, menuList.get(j));
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

            return null;
        }
    }

    public LineData generateTimeChart(ArrayList<String> menuList) {
        Calendar date = null;
        int timeList[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
        try {
            date = getStartDate();

        } catch (ParseException e) {

            e.printStackTrace();
        }
        if (date != null) {


            Entry entries[][] = new Entry[menuList.size()][timeList.length];
            ArrayList<HashMap> hashMaps = null;
            if(analysisType){
                hashMaps = Data.menus;
            } else{
                hashMaps = Data.counts;
            }
            for (int i = 0; i < hashMaps.size(); i++) {
                Iterator<String> iterator = hashMaps.get(i).keySet().iterator();
                int menu_index = 0;

                while (iterator.hasNext()) {
                    String menu_id = iterator.next();

                    float data = (float) hashMaps.get(i).get(menu_id);
                    entries[menu_index][i] = new Entry(data, i);

                    menu_index++;
                }

            }

            ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();

            for (int j = 0; j < entries.length; j++) {

                ArrayList<Entry> entries1 = new ArrayList<>();
                for (int i = 0; i < entries[j].length; i++) {
                    entries1.add(entries[j][i]);

                }
                LineDataSet lineDataSet = new LineDataSet(entries1, menuList.get(j));
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
            ArrayList<HashMap> hashMaps = null;
            if(analysisType){
                hashMaps = Data.menus;
            } else{
                hashMaps = Data.counts;
            }
            for (int i = 0; i < hashMaps.size(); i++) {
                Iterator<Integer> iterator = hashMaps.get(i).keySet().iterator();
                int menu_index = 0;

                while (iterator.hasNext()) {
                    int menu_id = iterator.next();
                    float data = (float) hashMaps.get(i).get(menu_id);
                    entries[menu_index][i] = new Entry(data, i);
                    menu_index++;
                }


            }
            for (int j = 0; j < entries.length; j++) {

                ArrayList<Entry> entries1 = new ArrayList<>();
                for (int i = 0; i < entries[j].length; i++) {
                    entries1.add(entries[j][i]);

                }
                LineDataSet lineDataSet = new LineDataSet(entries1, menuList.get(j));
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
            while (!((cloneStart.get(Calendar.YEAR) == endDate.get(Calendar.YEAR)) && (cloneStart.get(Calendar.MONTH) > endDate.get(Calendar.MONTH)))) {
                String month = cloneStart.get(Calendar.YEAR) + "." + (cloneStart.get(Calendar.MONTH) + 1);
                months.add(month);
                cloneStart.add(Calendar.MONTH, 1);
            }
            ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
            Entry[][] entries = new Entry[menuList.size()][months.size()];
            ArrayList<HashMap> hashMaps = null;
            if(analysisType){
                hashMaps = Data.menus;
            } else{
                hashMaps = Data.counts;
            }
            for (int i = 0; i < hashMaps.size(); i++) {
                Iterator<Integer> iterator = hashMaps.get(i).keySet().iterator();
                int menu_index = 0;
                while (iterator.hasNext()) {
                    int menu_id = iterator.next();
                    float data = (float) hashMaps.get(i).get(menu_id);
                    entries[menu_index][i] = new Entry(data, i);
                    menu_index++;
                }
            }
            for (int j = 0; j < entries.length; j++) {
                ArrayList<Entry> entries1 = new ArrayList<>();
                for (int i = 0; i < entries[j].length; i++) {
                    entries1.add(entries[j][i]);
                }
                LineDataSet lineDataSet = new LineDataSet(entries1, menuList.get(j));
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
            return lineData;
        } else {

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
            while (!((cloneStart.get(Calendar.YEAR) == endDate.get(Calendar.YEAR)) && (cloneStart.get(Calendar.MONTH) > endDate.get(Calendar.MONTH)))) {
                int month = cloneStart.get(Calendar.MONTH) + 1;
                String quarter = cloneStart.get(Calendar.YEAR) + "."+getQuarter(month)+"분기";
                Log.d("sstt",quarter);
                if(!quarters.contains(quarter)){
                    quarters.add(quarter);
                }
                cloneStart.add(Calendar.MONTH, 1);
            }

            ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
            Entry[][] entries = new Entry[menuList.size()][quarters.size()];
            ArrayList<HashMap> hashMaps = null;
            if(analysisType){
                hashMaps = Data.menus;
            } else{
                hashMaps = Data.counts;
            }
            for (int i = 0; i < hashMaps.size(); i++) {
                Iterator<Integer> iterator = hashMaps.get(i).keySet().iterator();
                int menu_index = 0;
                while (iterator.hasNext()) {
                    int menu_id = iterator.next();

                    float data = (float) hashMaps.get(i).get(menu_id);
                    entries[menu_index][i] = new Entry(data, i);

                    menu_index++;
                }

            }
            for (int j = 0; j < entries.length; j++) {

                ArrayList<Entry> entries1 = new ArrayList<>();
                for (int i = 0; i < entries[j].length; i++) {
                    entries1.add(entries[j][i]);
                }
                LineDataSet lineDataSet = new LineDataSet(entries1, menuList.get(j));
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
                cloneStart.add(Calendar.YEAR, 1);
            }
            String year = String.valueOf(cloneStart.get(Calendar.YEAR));
            years.add(year);


            ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
            Entry[][] entries = new Entry[menuList.size()][years.size()];
            ArrayList<HashMap> hashMaps = null;
            if(analysisType){
                hashMaps = Data.menus;
            } else{
                hashMaps = Data.counts;
            }
            for (int i = 0; i < hashMaps.size(); i++) {
                Iterator<Integer> iterator = hashMaps.get(i).keySet().iterator();
                int menu_index = 0;

                while (iterator.hasNext()) {
                    int menu_id = iterator.next();

                    float data = (float) hashMaps.get(i).get(menu_id);
                    entries[menu_index][i] = new Entry(data, i);

                    menu_index++;
                }


            }
            for (int j = 0; j < entries.length; j++) {

                ArrayList<Entry> entries1 = new ArrayList<>();
                for (int i = 0; i < entries[j].length; i++) {
                    entries1.add(entries[j][i]);

                }
                LineDataSet lineDataSet = new LineDataSet(entries1, menuList.get(j));
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
