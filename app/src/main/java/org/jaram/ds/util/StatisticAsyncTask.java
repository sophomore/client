package org.jaram.ds.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.jaram.ds.admin.view.ProgressChartFrag;
import org.jaram.ds.admin.view.SummaryFrag;
import org.jaram.ds.data.Data;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

/**
 * Created by ohyongtaek on 15. 9. 16..
 */
public class StatisticAsyncTask extends AsyncTask<Void, Void, Void> {
    public static final String SERVER_URL = "http://61.77.77.20";
    Context mContext;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
    String startDate, endDate;
    SummaryFrag summaryFrag;
    ProgressChartFrag progressChartFrag;
    boolean aSwitch;
    ArrayList<Integer> menus;
    int unit;

    public StatisticAsyncTask(Context context, String startDate, String endDate, int unit, SummaryFrag summaryFrag, boolean aSwitch) {
        this.mContext = context;
        this.startDate = startDate;
        this.endDate = endDate;
        this.summaryFrag = summaryFrag;
        this.aSwitch = aSwitch;
        Iterator<Integer> iterator = Data.menuList.keySet().iterator();
        menus = new ArrayList<>();
        while (iterator.hasNext()) {

            menus.add(iterator.next());
        }
        this.unit = unit;
    }

    public StatisticAsyncTask(Context context,ProgressChartFrag progressChartFrag,boolean aSwitch) {
        this.mContext = context;
        this.progressChartFrag = progressChartFrag;
        Bundle bundle = progressChartFrag.getArguments();
        this.startDate = bundle.getString("start");
        this.endDate = bundle.getString("end");
        this.menus = bundle.getIntegerArrayList("menuIds");
        this.aSwitch = aSwitch;
        this.unit = bundle.getInt("unitType")+1;
    }

    ProgressDialog dialog;

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(mContext, "", "분석중입니다.", true);
        super.onPreExecute();
    }

    public void setHourData(JSONObject jsonObject) throws JSONException{
        ArrayList<Integer> totals = Data.totals;
        ArrayList<Integer> cards = Data.cards;
        ArrayList<Integer> cashs = Data.cashs;
        ArrayList<HashMap> menus = Data.menus;
        ArrayList<HashMap> counts = Data.counts;
        Iterator<String> keys = jsonObject.keys();
        while(keys.hasNext()){
            String hour = keys.next();
            JSONObject object = jsonObject.getJSONObject(hour);
            JSONObject menuObject = object.getJSONObject("menu");
            Iterator<String> menuKeys = menuObject.keys();
            HashMap<String,Float> priceMap = new HashMap<>();
            HashMap<String,Float> countMap = new HashMap<>();
            while(menuKeys.hasNext()){
                String id =menuKeys.next();
                float price = (float)menuObject.getJSONObject(id).getInt("price");
                float count = (float)menuObject.getJSONObject(id).getInt("count");
                priceMap.put(id, price);
                countMap.put(id,count);
            }
            counts.add(countMap);
            menus.add(priceMap);
            int cardtotal = object.getInt("cardtotal");
            int cashtotal = object.getInt("cashtotal");
            int total = cardtotal+cashtotal;
            totals.add(total);
            cards.add(cardtotal);
            cashs.add(cashtotal);
        }
    }
    public void setMonthData(JSONObject jsonObject) throws JSONException {
        ArrayList<Integer> totals = Data.totals;
        ArrayList<Integer> cards = Data.cards;
        ArrayList<Integer> cashs = Data.cashs;
        ArrayList<HashMap> menus = Data.menus;
        ArrayList<HashMap> counts = Data.counts;
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            JSONArray jsonArray = jsonObject.getJSONArray(keys.next());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Iterator<String> months = object.keys();
                while(months.hasNext()) {
                    String month = months.next();
                    JSONObject jsonObject1 = object.getJSONObject(month);
                    int cardTotal = jsonObject1.getInt("cardtotal");
                    int cashTotal = jsonObject1.getInt("cashtotal");
                    int total = cardTotal + cashTotal;
                    Data.totalCard += cardTotal;
                    Data.totalCash += cashTotal;
                    Data.totalPrice += total;
                    HashMap<Integer, Float> hashMap1 = new HashMap<>();
                    HashMap<Integer, Float> countMap = new HashMap<>();
                    JSONObject object1 = jsonObject1.getJSONObject("menu");
                    JSONObject countObject = jsonObject1.getJSONObject("count");
                    Iterator<String> keys2 = object1.keys();
                    while (keys2.hasNext()) {
                        String menuId = keys2.next();
                        int id = Integer.parseInt(menuId);
                        float count = (float) countObject.getInt(menuId);
                        float price = (float) object1.getInt(menuId);
                        hashMap1.put(id, price);
                        countMap.put(id,count);
                    }
                    menus.add(hashMap1);
                    counts.add(countMap);
                    totals.add(total);
                    cards.add(cardTotal);
                    cashs.add(cashTotal);
                }

            }

        }
    }
    public void setQuarterData(JSONObject jsonObject) throws JSONException{
        ArrayList<Integer> totals = Data.totals;
        ArrayList<Integer> cards = Data.cards;
        ArrayList<Integer> cashs = Data.cashs;
        ArrayList<HashMap> menus = Data.menus;
        ArrayList<HashMap> counts = Data.counts;
        Iterator<String> keys = jsonObject.keys();
        while(keys.hasNext()){
            String key = keys.next();
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for(int i =0; i< jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                Iterator<String> quarters = object.keys();
                while(quarters.hasNext()) {
                    String quarter = quarters.next();
                    JSONObject jsonObject1 = object.getJSONObject(quarter);
                    int cashtotal = jsonObject1.getInt("cashtotal");
                    int cardtotal = jsonObject1.getInt("cardtotal");
                    int total = cashtotal + cardtotal;
                    Data.totalCash += cashtotal;
                    Data.totalCard += cardtotal;
                    Data.totalPrice += total;
                    JSONObject menuObject = jsonObject1.getJSONObject("menu");
                    JSONObject countObject = jsonObject1.getJSONObject("count");
                    Iterator<String> keys2 = countObject.keys();
                    HashMap<Integer, Float> hashMap = new HashMap<>();
                    HashMap<Integer, Float> countMap = new HashMap<>();
                    while (keys2.hasNext()) {
                        String menu = keys2.next();
                        int id = Integer.parseInt(menu);
                        float count = (float) countObject.getInt(menu);
                        float price = (float) menuObject.getInt(menu);
                        hashMap.put(id, price);
                        countMap.put(id,count);
                    }
                    counts.add(countMap);
                    cards.add(cardtotal);
                    cashs.add(cashtotal);
                    menus.add(hashMap);
                    totals.add(total);
                }
            }
        }
    }
    public void setDateData(JSONObject jsonObject, Date start, Date end) throws JSONException {
        ArrayList<Integer> totals = Data.totals;
        ArrayList<Integer> cards = Data.cards;
        ArrayList<Integer> cashs = Data.cashs;
        ArrayList<HashMap> menus = Data.menus;
        ArrayList<HashMap> counts = Data.counts;
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                int value = start.getMonth()+1;
                if(object.has(String.valueOf(value))){
                    JSONObject jsonObject1 = object.getJSONObject(String.valueOf(value));
                    while(start.getMonth()+1 == value && start.getTime()<=end.getTime()) {
                        JSONObject days = jsonObject1.getJSONObject(String.valueOf(start.getDate()));
                        if (days.length() != 0) {
                            int cardTotal = days.getInt("cardtotal");
                            int cashTotal = days.getInt("cashtotal");
                            int total = cardTotal + cashTotal;
                            Data.totalCard += cardTotal;
                            Data.totalCash += cashTotal;
                            Data.totalPrice += total;

                            HashMap<Integer, Float> hashMap1 = new HashMap<>();
                            HashMap<Integer, Float> countMap = new HashMap<>();
                            JSONObject object1 = days.getJSONObject("menu");
                            JSONObject countObject = days.getJSONObject("count");
                            Iterator<String> keys2 = object1.keys();
                            while (keys2.hasNext()) {
                                String menuId = keys2.next();
                                int id = Integer.parseInt(menuId);
                                float count = (float) countObject.getInt(menuId);
                                float price = (float) object1.getInt(menuId);
                                hashMap1.put(id, price);
                                countMap.put(id,price);
                            }
                            counts.add(countMap);
                            menus.add(hashMap1);
                            totals.add(total);
                            cards.add(cardTotal);
                            cashs.add(cashTotal);
                        }
                        start.setDate(start.getDate() + 1);
                    }

                }
            }
        }
    }
    public void setYearData(JSONObject jsonObject) throws JSONException {
        ArrayList<Integer> totals = Data.totals;
        ArrayList<Integer> cards = Data.cards;
        ArrayList<Integer> cashs = Data.cashs;
        ArrayList<HashMap> menus = Data.menus;
        ArrayList<HashMap> counts = Data.counts;
        Iterator<String> keys = jsonObject.keys();
        while(keys.hasNext()){
            JSONObject object = jsonObject.getJSONObject(keys.next());
            int cardtotal = object.getInt("cardtotal");
            int cashtotal = object.getInt("cashtotal");
            int total = cardtotal+cashtotal;
            Data.totalCard = cardtotal;
            Data.totalCash = cashtotal;
            Data.totalPrice = total;
            JSONObject menuObject = object.getJSONObject("menu");
            JSONObject countObject = object.getJSONObject("count");
            Iterator<String> menusIds = menuObject.keys();
            HashMap<Integer,Float> hashMap = new HashMap<>();
            HashMap<Integer,Float> countMap = new HashMap<>();
            while(menusIds.hasNext()){
                String menu = menusIds.next();
                int id = Integer.parseInt(menu);
                float count = (float) countObject.getInt(menu);
                float price = (float)menuObject.getInt(menu);
                hashMap.put(id,price);
                countMap.put(id,count);
            }
            counts.add(countMap);
            menus.add(hashMap);
            totals.add(total);
            cards.add(cardtotal);
            cashs.add(cashtotal);
        }
    }
    public void setDayData(JSONObject jsonObject) throws JSONException {
        ArrayList<Integer> totals = Data.totals;
        ArrayList<Integer> cards = Data.cards;
        ArrayList<Integer> cashs = Data.cashs;
        ArrayList<HashMap> menus = Data.menus;
        ArrayList<HashMap> counts = Data.counts;
        Iterator<String> keys = jsonObject.keys();
        while(keys.hasNext()){
            JSONObject object = jsonObject.getJSONObject(keys.next());
            int cardtotal = object.getInt("cardtotal");
            int cashtotal = object.getInt("cashtotal");
            int total = cardtotal + cashtotal;
            Data.totalCard = cardtotal;
            Data.totalCash = cashtotal;
            Data.totalPrice = total;
            JSONObject menuObject = object.getJSONObject("menu");
            JSONObject countObject = object.getJSONObject("count");
            Iterator<String> menusIds = menuObject.keys();
            HashMap<Integer,Float> hashMap = new HashMap<>();
            HashMap<Integer,Float> countMap = new HashMap<>();

            while(menusIds.hasNext()){
                String menu = menusIds.next();
                int id = Integer.parseInt(menu);
                float price = (float)menuObject.getInt(menu);
                float count = (float)countObject.getInt(menu);
                hashMap.put(id,price);
                countMap.put(id,count);
            }
            counts.add(countMap);
            menus.add(hashMap);
            totals.add(total);
            cards.add(cardtotal);
            cashs.add(cashtotal);
        }
    }
    @Override
    protected Void doInBackground(Void... params) {

        HashMap<String, Object> hashMap = new HashMap<>();

        Date start = null;
        Date end = null;
        try {
            start = format.parse(startDate);
            end = format.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (start.getTime() > end.getTime()) {
            Toast.makeText(mContext, "기간을 잘못 입력하셨습니다.", Toast.LENGTH_LONG).show();
        } else {
            Data.reDataForStatistic();
            JSONArray js = new JSONArray();
            for (Integer i : menus) {
                js.put(i);
            }
            hashMap.put("startDate", startDate);
            hashMap.put("endDate", endDate);
            hashMap.put("unit", unit);
            hashMap.put("menus", js);
            String responses = Http.post(SERVER_URL + "/statistic/unit_menu_sum", hashMap);
            Log.d("responces",responses);
            try {
                JSONObject jsonObject = new JSONObject(responses);
                if(unit == 1){
                    setHourData(jsonObject);
                } else if(unit == 2){
                    setDateData(jsonObject,start,end);
                } else if(unit == 3){
                    setDayData(jsonObject);
                } else if(unit == 4){
                    setMonthData(jsonObject);
                } else if(unit == 5){
                    setQuarterData(jsonObject);
                } else if(unit == 6){
                    setYearData(jsonObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (aSwitch) {
                summaryFrag.createChart(startDate, endDate);
            } else {
                progressChartFrag.createChart();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        dialog.dismiss();
        if (aSwitch) {
            summaryFrag.setListener();
        }
        super.onPostExecute(aVoid);
    }
}
