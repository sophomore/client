package org.jaram.ds.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by ohyongtaek on 15. 9. 13..
 */
public class SearchOrderAsyncTask extends AsyncTask<URL,Integer,Void> {

    public static final String SERVER_URL = "http://61.77.77.20";
    Context mContext;
    public SearchOrderAsyncTask(Context context){
        this.mContext  = context;
    }
    ProgressDialog dialog;
    @Override
    protected void onPostExecute(Void aVoid) {
        dialog.dismiss();
        super.onPreExecute();
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(mContext, "", "추가중입니다.", true);
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

        String responses = Http.get(SERVER_URL+"/order",null);
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(responses);
            for(int i =0; i< jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                jsonObject.getInt("totalprice");
                jsonObject.getString("time");
                JSONArray jsonArray1 = jsonObject.getJSONArray("ordermenus");
                for(int j = 0; j<jsonArray1.length();j++){
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                    jsonObject1.getBoolean("double");
                    jsonObject1.getInt("pay");
                    jsonObject1.getBoolean("curry");
                    jsonObject1.getInt("menu_id");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onProgressUpdate(values);
    }

    @Override
    protected Void doInBackground(URL... params) {
        return null;
    }
}
