package org.jaram.ds.data;

import android.util.Log;

import org.jaram.ds.data.struct.Category;
import org.jaram.ds.data.struct.Menu;
import org.jaram.ds.data.struct.Order;
import org.jaram.ds.data.struct.OrderMenu;
import org.jaram.ds.data.struct.TestCategory;
import org.jaram.ds.data.struct.TestMenu;
import org.jaram.ds.data.struct.TestOrder;
import org.jaram.ds.data.struct.TestOrderMenu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by ohyongtaek on 15. 8. 18..
 */
public class TestData{
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
            category.menu = null;
            categoryList.add(category);
            TestCategory testCategory = new TestCategory("category"+(i+1));
            testCategory.setName(category.name);
            testCategory.save();
        }
        List<TestCategory> a = TestCategory.listAll(TestCategory.class);
        Log.d("asdfg",a.size()+"@");
        Log.d("asdfg", String.valueOf(a.get(0).get_id()));
        for (int i=0; i<30; i++) {

            Menu menu = new Menu();
            menu._id = i;
            menu.category = categoryList.get(random.nextInt(5));
            menu.name = "Menu "+(i+1);
            menu.price = 5000+1000*(random.nextInt(3));
            List<TestCategory> categories = TestCategory.find(TestCategory.class,"_id = ?",1+"");
            TestMenu testMenu = new TestMenu(menu.name,menu.price,categories.get(0),true);
            testMenu.save();
            menuList.add(menu);
        }
        for (int i=0; i<300; i++) {
            Order order = new Order();
            order._id = i;
            Calendar cal = Calendar.getInstance();
            cal.set(2015-random.nextInt(2), 12-random.nextInt(11), 30-random.nextInt(29), 24-random.nextInt(24), 60-random.nextInt(60), 60-random.nextInt(60));
            String testDate = (2015-random.nextInt(2))+"-"+(12-random.nextInt(11))+"-"+(30-random.nextInt(29))+" "+(24-random.nextInt(24))+":"+(60-random.nextInt(60))+":"+(60-random.nextInt(60));
            TestOrder testOrder = new TestOrder();
            testOrder.setDate(testDate);
            testOrder.setTotalPrice(0);
            testOrder.save();

            for (int j = random.nextInt(5); j<7; j++) {
                List<TestMenu> testMenus = TestMenu.listAll(TestMenu.class);
                TestOrderMenu testOrderMenu = new TestOrderMenu(testMenus.get(random.nextInt(testMenus.size()-1)),random.nextInt(3),null);
                testOrderMenu.save();
                order.menuList.add(new OrderMenu(menuList.get(random.nextInt(menuList.size()-1)), OrderMenu.Pay.CREDIT));
            }
            Log.d("testOrder",TestOrderMenu.listAll(TestOrderMenu.class).get(0).getMenu().getName());
            order.date = cal.getTime();
            Log.d("testDate", order.date.getMonth() + "");
            orderList.add(order);
        }
    }
}
