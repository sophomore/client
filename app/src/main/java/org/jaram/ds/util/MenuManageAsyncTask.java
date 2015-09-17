package org.jaram.ds.util;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Menu;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ohyongtaek on 15. 9. 9..
 */
public class MenuManageAsyncTask extends AsyncTask<Void, Integer, Void> {
    public static final String SERVER_URL = "http://61.77.77.20";
    Context mContext;


    public MenuManageAsyncTask(Context context) {
        this.mContext = context;
    }

    ProgressDialog dialog;

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(mContext, "", "추가중입니다.", true);

        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {

        String responses = Http.get(SERVER_URL+"/menu",null);
        Log.d("testt",responses);
        Data.menuList.clear();

        try {
            JSONArray jsonArray = new JSONArray(responses);
            for(int j =0 ; j<jsonArray.length();j++){
                JSONObject jsonObject = jsonArray.getJSONObject(j);
                String name = jsonObject.getString("name");
                int price = jsonObject.getInt("price");
                int category_id = jsonObject.getInt("category_id");
                Menu menu = new Menu(j, Data.categoryList.get(category_id),name,price);
                Data.menuList.put(jsonObject.getInt("id"),menu);
                Log.d("testt",Data.menuList.size()+"@");

            }
        } catch (JSONException e) {
            Log.d("testt", "실패");
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        dialog.dismiss();
        super.onPostExecute(aVoid);
    }
}