package org.jaram.ds.util;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import java.util.HashMap;

/**
 * Created by ohyongtaek on 15. 9. 9..
 */
public class MenuAysncTask extends AsyncTask<RecyclerView, Integer, Void> {
    public static final String SERVER_URL = "http://61.77.77.20";
    Context mContext;

    public MenuAysncTask(Context context) {
        this.mContext = context;
    }

    ProgressDialog dialog;

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(mContext, "", "추가중입니다.", true);

        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(RecyclerView... params) {
        int count = params.length;
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("name","메뉴 1");
        hashMap.put("price", 7000);
        hashMap.put("category",1);
        Http.post(SERVER_URL+"/menu",hashMap);
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