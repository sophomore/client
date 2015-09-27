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

    public int getXvalue(Date date, int unit) {
        if (unit == 2) {
            return date.getDate();
        } else if (unit == 4) {
            return date.getMonth() + 1;
        } else {
            return 1;
        }
    }
    public void setHourData(JSONObject jsonObject) throws JSONException{
        ArrayList<Integer> totals = Data.totals;
        ArrayList<Integer> cards = Data.cards;
        ArrayList<Integer> cashs = Data.cashs;
        ArrayList<HashMap> menus = Data.menus;
        Iterator<String> keys = jsonObject.keys();
        while(keys.hasNext()){
            JSONObject object = jsonObject.getJSONObject(keys.next());
            JSONObject menuObject = object.getJSONObject("menu");
            Iterator<String> menuKeys = menuObject.keys();
            HashMap<String,Integer> hashMap = new HashMap<>();
            while(menuKeys.hasNext()){
                String id =menuKeys.next();
                int price = menuObject.getInt(id);
                hashMap.put(id,price);
            }
            menus.add(hashMap);
            int cardtotal = object.getInt("cardtotal");
            int cashtotal = object.getInt("cashtotal");
            int total = cardtotal+cashtotal;
            totals.add(total);
            cards.add(cardtotal);
            cashs.add(cashtotal);
        }
    }
    public void setMonthData(JSONObject jsonObject, Date start, Date end) throws JSONException {
        ArrayList<Integer> totals = Data.totals;
        ArrayList<Integer> cards = Data.cards;
        ArrayList<Integer> cashs = Data.cashs;
        ArrayList<HashMap> menus = Data.menus;
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            JSONArray jsonArray = jsonObject.getJSONArray(keys.next());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                int value = start.getMonth() + 1;

                if (object.has(String.valueOf(value))) {
                    JSONObject jsonObject1 = object.getJSONObject(String.valueOf(value));

                        int cardTotal = jsonObject1.getInt("cardtotal");
                        int cashTotal = jsonObject1.getInt("cashtotal");
                        int total = cardTotal + cashTotal;
                        Data.totalCard += cardTotal;
                        Data.totalCash += cashTotal;
                        Data.totalPrice += total;

                        HashMap<Integer, Float> hashMap1 = new HashMap<>();
                        JSONObject object1;
                        object1 = object.getJSONObject("menu");

                        Iterator<String> keys2 = object1.keys();
                        while (keys2.hasNext()) {
                            String menuId = keys2.next();
                            int id = Integer.parseInt(menuId);
                            float price = (float) object1.getInt(menuId);
                            hashMap1.put(id, price);
                        }
                        menus.add(hashMap1);
                        totals.add(total);
                        cards.add(cardTotal);
                        cashs.add(cashTotal);


                }
                start.setMonth(start.getMonth() + 1);


            }

        }
    }

    public void setDateData(JSONObject jsonObject, Date start, Date end) throws JSONException {
        ArrayList<Integer> totals = Data.totals;
        ArrayList<Integer> cards = Data.cards;
        ArrayList<Integer> cashs = Data.cashs;
        ArrayList<HashMap> menus = Data.menus;
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Iterator<String> iterator = object.keys();
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
                            JSONObject object1;
                            object1 = days.getJSONObject("menu");

                            Iterator<String> keys2 = object1.keys();
                            while (keys2.hasNext()) {
                                String menuId = keys2.next();
                                int id = Integer.parseInt(menuId);
                                float price = (float) object1.getInt(menuId);
                                hashMap1.put(id, price);
                            }
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
                } else if(unit == 4){
                    setMonthData(jsonObject,start,end);
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
