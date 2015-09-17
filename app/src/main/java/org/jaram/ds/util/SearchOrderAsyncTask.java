package org.jaram.ds.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Menu;
import org.jaram.ds.data.struct.Order;
import org.jaram.ds.data.struct.OrderMenu;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ohyongtaek on 15. 9. 13..
 */
public class SearchOrderAsyncTask extends AsyncTask<URL, Integer, Void> {

    public static final String SERVER_URL = "http://61.77.77.20";
    Context mContext;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:mm:ss", Locale.KOREA);

    public SearchOrderAsyncTask(Context context) {
        this.mContext = context;
    }

    ProgressDialog dialog;

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(mContext, "", "추가중입니다.", true);
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    @Override
    protected Void doInBackground(URL... params) {

        String responses = Http.get(SERVER_URL + "/order", null);

        try {
            JSONArray jsonArray = new JSONArray(responses);

            for (int i = 0; i < jsonArray.length(); i++) {
                Order order = new Order();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int totalprice = jsonObject.getInt("totalprice");
                String orderDate = jsonObject.getString("time");
                JSONArray jsonArray1 = jsonObject.getJSONArray("ordermenus");
                for (int j = 0; j < jsonArray1.length(); j++) {
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                    Boolean doublei = jsonObject1.getBoolean("double");
                    int pay = jsonObject1.getInt("pay");
                    Boolean curry = jsonObject1.getBoolean("curry");
                    int menu_id = jsonObject1.getInt("menu_id");
                    Menu menu = Data.menuList.get(menu_id);

                    OrderMenu orderMenu = new OrderMenu(menu, pay);
                    orderMenu.totalprice = jsonObject1.getInt("totalprice");

                    if (doublei == true) {
                        orderMenu.setDoublei();
                    }
                    if (curry == true) {
                        orderMenu.setCurry();
                    }
                    order.menuList.add(orderMenu);

                }
                order.totalPrice = totalprice;
                Date date = format.parse(orderDate);
                order.date = date;
                Data.orderList.add(order);

            }

        } catch (JSONException e) {
            Log.d("teststt", "JSon Fail");
            e.printStackTrace();
        } catch (ParseException e) {
            Log.d("teststt", "Date Fail");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        dialog.dismiss();
        super.onPreExecute();
    }
}
