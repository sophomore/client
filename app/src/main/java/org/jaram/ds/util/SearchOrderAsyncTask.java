package org.jaram.ds.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

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
    protected void onPreExecute() {
        dialog = ProgressDialog.show(mContext,"","검색중입니다.",true);
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(URL... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        dialog.dismiss();
        super.onPostExecute(aVoid);
    }
}
