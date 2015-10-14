package org.jaram.ds.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Order;
import org.jaram.ds.data.struct.OrderMenu;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by ohyongtaek on 15. 9. 9..
 */
public class AddOrderAsyncTask extends AsyncTask<Order, Integer, Void> {
    public static final String SERVER_URL = "http://61.77.77.20";
    Context mContext;

    public AddOrderAsyncTask(Context context) {
        this.mContext = context;
    }

    ProgressDialog dialog;

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(mContext, "", "추가중입니다.", true);
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Order... params) {

//        HashMap<String,Object> hashMap = new HashMap<>();

        HashMap<Integer, Integer> hashMap = new HashMap<>();
        HashMap<String,Object> billMap = new HashMap<>();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:mm:ss", Locale.KOREA);
            Date date = new Date();
            String date2 = format.format(date);
            String st = "";
            int curry = 0;
            int twice = 0;
            Order orderlist = params[0];
            for (int i = 0; i < orderlist.menuList.size(); i++) {
                if (orderlist.menuList.get(i).curry) {
                    curry++;
                }
                if (orderlist.menuList.get(i).doublei) {
                    twice++;
                }
                if (hashMap.containsKey(orderlist.menuList.get(i).menu.id)) {
                    int count = hashMap.get(orderlist.menuList.get(i).menu.id);
                    hashMap.put(orderlist.menuList.get(i).menu.id, count + 1);
                } else {
                    hashMap.put(orderlist.menuList.get(i).menu.id, 1);
                }
            }
            Iterator<Integer> key = hashMap.keySet().iterator();
            while (key.hasNext()) {
                int key2 = key.next();
                st += Data.menuList.get(key2).name;
                st += "\\x09"+hashMap.get(key2);
                st += "\\x09"+Data.menuList.get(key2).price;
                st += "\\x09"+Data.menuList.get(key2).price * hashMap.get(key2)+"\\n";
            }
            if (curry > 0) {
                st += "카레추가\\x09"+curry+"\\x2500\\x"+curry * 2500+"\\n";
            }
            if (twice > 0) {
                st += "곱추가\\x09"+twice+"\\x2500\\x"+twice * 2500+"\\n";
            }
            billMap.put("order",st);
            billMap.put("date",date);
            Log.d("string",st);
            String responses = Http.post(SERVER_URL+"/util/print_statement",billMap);
        }catch (Exception e){}
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
