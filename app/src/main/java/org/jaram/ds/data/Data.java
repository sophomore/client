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
    public static final int PAY_CASH = 1;
    public static final int PAY_CARD = 2;
    public static final int PAY_SERVICE = 3;
    public static final int PAY_CREDIT = 4;
    public static final String SERVER_URL = "http://61.77.77.20";
    public static final int CURRY = 1000;
    public static final int DOULBEI = 500;
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
    public static ArrayList<Order> orderList1 = new ArrayList<Order>();

    static {
        try {
            JSONArray menuJson = new JSONArray(Http.get(SERVER_URL + "/menu", null));
            for (int i = 0; i < menuJson.length(); i++) {
                JSONObject jo = menuJson.getJSONObject(i);
                Menu menu = new Menu(jo.getInt("id"), categoryList.get(jo.getInt("category_id")), jo.getString("name"), jo.getInt("price"));
                menuList.put(jo.getInt("id"), menu);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 300; i++) {
            Order order = new Order();
            order._id = i;
            for (int j = random.nextInt(5); j < 7; j++) {
                Menu menu = menuList.get(random.nextInt(menuList.size() - 2) + 1);
                if (menu == null) {

                } else {
                    OrderMenu orderMenu = new OrderMenu(menu, Data.PAY_CREDIT);
                    order.menuList.add(orderMenu);

                }
                Calendar cal = Calendar.getInstance();
                cal.set(2015 - random.nextInt(2), 12 - random.nextInt(11), 30 - random.nextInt(29), 24 - random.nextInt(24), 60 - random.nextInt(60), 60 - random.nextInt(60));
                order.date = cal.getTime();
                orderList.add(order);
            }
        }
    }
}