package org.jaram.ds.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by ohyongtaek on 15. 9. 16..
 */
public class StatisticAsyncTask extends AsyncTask<Void,Void, Void> {
    public static final String SERVER_URL = "http://61.77.77.20";
    Context mContext;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:mm:ss", Locale.KOREA);
    String startDate, endDate;
    public StatisticAsyncTask(Context context,String startDate, String endDate) {
        this.mContext = context;
        this.startDate = startDate;
        this.endDate  = endDate;
    }

    ProgressDialog dialog;
    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(mContext,"","분석중입니다.",true);
        super.onPreExecute();
    }


    @Override
    protected Void doInBackground(Void... params) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("startDate",startDate);
        hashMap.put("endDate",endDate);
        String responses = Http.post(SERVER_URL + "/statistic/month_money_sum", hashMap);
        Log.d("ttess", responses);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        dialog.dismiss();
        super.onPostExecute(aVoid);
    }
}
