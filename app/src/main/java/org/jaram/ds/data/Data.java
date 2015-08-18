package org.jaram.ds.data;

import android.util.Log;

import org.jaram.ds.data.struct.Category;
import org.jaram.ds.data.struct.Menu;
import org.jaram.ds.data.struct.Order;
import org.jaram.ds.data.struct.OrderMenu;

import java.util.ArrayList;
import java.util.Calendar;
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
        for (int i=0; i<30; i++) {
            Menu menu = new Menu();
            menu._id = i;
            menu.category = categoryList.get(random.nextInt(5));
            menu.name = "Menu "+(i+1);
            menu.price = 5000+1000*(random.nextInt(3));
            menuList.add(menu);
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
            Log.d("testDate",order.date.getMonth()+"");
            orderList.add(order);
        }
    }
}