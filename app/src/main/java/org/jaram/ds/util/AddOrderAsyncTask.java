package org.jaram.ds.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jaram.ds.data.struct.Order;
import org.jaram.ds.data.struct.OrderMenu;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by ohyongtaek on 15. 9. 9..
 */
public class AddOrderAsyncTask extends AsyncTask<Order,Integer,Void> {
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
    protected Void doInBackground(Order... params) {

        HashMap<String,Object> hashMap = new HashMap<>();

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:mm:ss", Locale.KOREA);

            Date date = new Date();
            String date2 = format.format(date);
            JSONArray array = new JSONArray();
            for(int i=0; i<params[0].menuList.size(); i++){
                JSONObject object = new JSONObject();
                OrderMenu ordermenu = params[0].menuList.get(i);
                object.put("id", ordermenu.menu.id);
                object.put("pay", ordermenu.pay);
                object.put("curry", ordermenu.curry);
                object.put("twice", ordermenu.doublei);
                array.put(object);
            }
            hashMap.put("totalprice", params[0].getTotalForServer());
            hashMap.put("ordermenus", array);

            Log.d("testJsonArray", array + "");

            String responses = Http.post(SERVER_URL+"/order",hashMap);

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
