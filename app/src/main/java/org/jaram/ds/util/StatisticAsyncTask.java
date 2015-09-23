package org.jaram.ds.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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

    public StatisticAsyncTask(Context context, String startDate, String endDate, SummaryFrag summaryFrag, boolean aSwitch) {
        this.mContext = context;
        this.startDate = startDate;
        this.endDate = endDate;
        this.summaryFrag = summaryFrag;
        this.aSwitch = aSwitch;
        Iterator<Integer> iterator = Data.menuList.keySet().iterator();
        menus = new ArrayList<>();
        while(iterator.hasNext()){

            menus.add(iterator.next());
        }
    }

    public StatisticAsyncTask(Context context, String startDate, String endDate, ProgressChartFrag progressChartFrag, ArrayList<Integer> menus, boolean aSwitch) {
        this.mContext = context;
        this.startDate = startDate;
        this.endDate = endDate;
        this.progressChartFrag = progressChartFrag;
        this.aSwitch = aSwitch;
        this.menus = menus;

    }

    ProgressDialog dialog;

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(mContext, "", "분석중입니다.", true);
        super.onPreExecute();
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
            JSONArray js = new JSONArray();
            for(Integer i : menus){
                js.put(i);
            }
            hashMap.put("startDate", startDate);
            hashMap.put("endDate", endDate);
            hashMap.put("unit", 4);
            hashMap.put("menus", js);
            String responses = Http.post(SERVER_URL + "/statistic/unit_menu_sum", hashMap);


            ArrayList<Integer> totalOfMonth = Data.totalOfMonth;
            ArrayList<Integer> cardOfMonth = Data.cardOfMonth;
            ArrayList<Integer> cashOfMonth = Data.cashOfMonth;
            ArrayList<HashMap> menusOfMonth = Data.menusOfMonth;

            try {
                JSONObject jsonObject = new JSONObject(responses);
                Iterator<String> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    JSONArray jsonArray = jsonObject.getJSONArray(keys.next());


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        if (object.has(String.valueOf(start.getMonth() + 1))) {
                            JSONObject jsonObject1 = object.getJSONObject(String.valueOf(start.getMonth() + 1));
                            int cardTotal = jsonObject1.getInt("cardtotal");
                            int cashTotal = jsonObject1.getInt("cashtotal");
                            int total = cardTotal + cashTotal;
                            Data.totalCard += cardTotal;
                            Data.totalCash += cashTotal;
                            Data.totalPrice += total;
                            HashMap<Integer, Float> hashMap1 = new HashMap<>();
                            JSONObject object1 = object.getJSONObject("menu");
                            Iterator<String> keys2 = object1.keys();
                            while (keys2.hasNext()) {
                                String menuId = keys2.next();
                                int id = Integer.parseInt(menuId);
                                float price = (float)object1.getInt(menuId);
                                hashMap1.put(id, price);

                            }
                            menusOfMonth.add(hashMap1);
                            totalOfMonth.add(total);
                            cardOfMonth.add(cardTotal);
                            cashOfMonth.add(cashTotal);
                        }
                        start.setMonth(start.getMonth() + 1);

                    }
                    Log.d("ttss",menusOfMonth.size()+"@#%");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(aSwitch) {
                summaryFrag.createChart(startDate, endDate);
            } else{
                Log.d("ttss", "suc");
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
