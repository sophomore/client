package org.jaram.ds.data;

import android.util.Log;

import org.jaram.ds.data.struct.Category;
import org.jaram.ds.data.struct.Menu;
import org.jaram.ds.data.struct.Order;
import org.jaram.ds.data.struct.OrderMenu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by kjydiary on 15. 7. 8..
 */
public class Data {
    public static ArrayList<Menu> menuList = new ArrayList<Menu>();
    public static ArrayList<Category> categoryList = new ArrayList<Category>();

    /*Test Data*/
    static Random random = new Random();
    public static ArrayList<Order> orderList = new ArrayList<Order>();

    static {
        for (int i=0; i<6; i++) {
            Category category = new Category();
            category._id = i;
            category.name = "category "+(i+1);
            categoryList.add(category);
        }
        for (int i=0; i<15; i++) {
            Menu menu = new Menu();
            menu._id = i;
            menu.category = categoryList.get(random.nextInt(5));
            menu.name = "Menu "+(i+1);
            menu.price = 5000+1000*(random.nextInt(3));
            menuList.add(menu);
        }
        for (int i=0; i<8; i++) {
            Order order = new Order();
            order._id = i;
            for (int j=0; j<menuList.size(); j++) {
                OrderMenu menu = new OrderMenu();
                menu.menu = menuList.get(j);
                int count = random.nextInt(6);
                if (count != 0) {
                    order.menus.put(menu, count);
                }
            }
            order.date = new Date();
            orderList.add(order);
        }
        Log.d("Data", "Static constructor");
    }
}