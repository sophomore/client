package org.jaram.ds.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by ohyongtaek on 15. 9. 9..
 */
public class AddOrderAsyncTask extends AsyncTask<URL,Integer,Void> {
    public static final String SERVER_URL = "http://61.77.77.20";
    Context mContext;
    public AddOrderAsyncTask(Context context){
        this.mContext  = context;
    }
    ProgressDialog dialog;
    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(mContext, "", "추가중입니다.", true);
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(URL... params) {

        HashMap<String,Object> hashMap = new HashMap<>();


        try {


            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String date2 = format.format(date);
            hashMap.put("time",date2);
            String orderMenu = "[{\"id\":"+1+",\"curry\" :"+true+", \"double\" : "+true+"}]";
            JSONArray jsonArray = new JSONArray(orderMenu);
            hashMap.put("totalprice",5000);
            hashMap.put("ordermenus",jsonArray);
            Log.d("testJsonArray",jsonArray+"");
            String responses = Http.get(SERVER_URL+"/order",hashMap);
            Log.d("testResponse",responses+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer[] values) {

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        dialog.dismiss();
        super.onPostExecute(aVoid);
    }
}
