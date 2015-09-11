package org.jaram.ds.data;

import android.util.Log;

import org.jaram.ds.data.struct.Category;
import org.jaram.ds.data.struct.Menu;
import org.jaram.ds.data.struct.Order;
import org.jaram.ds.data.struct.OrderMenu;
import org.jaram.ds.util.Http;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by kjydiary on 15. 7. 8..
 */
public class Data {
    public static final String SERVER_URL = "http://61.77.77.20";
    public static HashMap<Integer, Menu> menuList = new HashMap<Integer, Menu>();
    public static HashMap<Integer, Category> categoryList = new HashMap<Integer, Category>();

    static {
        categoryList.put(1, new Category(1, "돈까스"));
        categoryList.put(2, new Category(2, "덥밥"));
        categoryList.put(3, new Category(3, "면류"));
        categoryList.put(4, new Category(4, "기타"));
    }

    /*Test Data*/
    static Random random = new Random();
    public static ArrayList<Order> orderList = new ArrayList<Order>();

    static {
        try {
            JSONArray menuJson = new JSONArray(Http.get(SERVER_URL+"/menu", null));
            Log.d("menuJson", menuJson.toString());
            for (int i=0; i<menuJson.length(); i++) {
                JSONObject jo = menuJson.getJSONObject(i);
                menuList.put(jo.getInt("id"),  new Menu(jo.getInt("id"), categoryList.get(jo.getInt("category_id")), jo.getString("name"), jo.getInt("price")));
                Log.d("testMenu",menuList.get(jo.getInt("id")).name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i=0; i<300; i++) {
            Order order = new Order();
            order._id = i;
            for (int j = random.nextInt(5); j<7; j++) {
                order.menuList.add(new OrderMenu(menuList.get(random.nextInt(menuList.size()-1)), OrderMenu.Pay.CREDIT));
            }
            Calendar cal = Calendar.getInstance();
            cal.set(2015-random.nextInt(2), 12-random.nextInt(11), 30-random.nextInt(29), 24-random.nextInt(24), 60-random.nextInt(60), 60-random.nextInt(60));
            order.date = cal.getTime();
            orderList.add(order);
        }
    }
}