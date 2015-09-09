package org.jaram.ds.util;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;

/**
 * Created by ohyongtaek on 15. 9. 9..
 */
public class MenuAysncTask extends AsyncTask<URL,Integer,Void> {

    Context mContext;
    public MenuAysncTask(Context context){
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
        hashMap.put("name","히레까스");
        hashMap.put("price", 7000);
        hashMap.put("category", 1);
        Log.d("testGG", "123");

        String responses = Http.get("http://61.77.77.20/menu",null);
        try {
            JSONArray jsonArray = new JSONArray(responses);
            for(int i =0; i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                int price = jsonObject.getInt("price");
                int category = jsonObject.getInt("category_id");
                Log.d("testGet",name);
            }
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
//        dialog.dismiss();
        super.onPostExecute(aVoid);
    }
}