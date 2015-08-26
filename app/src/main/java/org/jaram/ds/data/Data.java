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
        Category category1 = new Category();
        category1._id = 1;
        category1.name = "돈까스";
        category1.menu = null;
        categoryList.add(category1);
        Category category2 = new Category();
        category2._id = 2;
        category2.name = "덮밥";
        category2.menu = null;
        categoryList.add(category2);
        Category category3 = new Category();
        category3._id = 3;
        category3.name = "면";
        category3.menu = null;
        categoryList.add(category3);
        Category category4 = new Category();
        category4._id = 4;
        category4.name = "음료 및 스페셜";
        category4.menu = null;
        categoryList.add(category4);
        for (int i=0; i<30; i++) {
            Menu menu = new Menu();
            menu._id = i;
            menu.category = categoryList.get(random.nextInt(3));
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