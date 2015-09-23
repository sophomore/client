package org.jaram.ds.data;

import org.jaram.ds.data.struct.Category;
import org.jaram.ds.data.struct.Menu;
import org.jaram.ds.data.struct.Order;

import java.util.ArrayList;
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
    public static final int TWICE = 500;
    public static HashMap<Integer, Menu> menuList = new HashMap<Integer, Menu>();
    public static HashMap<Integer, Category> categoryList = new HashMap<Integer, Category>();
    public static ArrayList<Integer> totalOfMonth = new ArrayList<Integer>();
    public static ArrayList<Integer> cardOfMonth = new ArrayList<Integer>();
    public static ArrayList<Integer> cashOfMonth = new ArrayList<Integer>();
    public static ArrayList<HashMap> menusOfMonth = new ArrayList<>();
    public static int totalPrice = 0;
    public static int totalCard = 0;
    public static int totalCash = 0;

    static {
        categoryList.put(1, new Category(1, "돈까스"));
        categoryList.put(2, new Category(2, "덥밥"));
        categoryList.put(3, new Category(3, "면류"));
        categoryList.put(4, new Category(4, "기타"));
    }
    public static void reDataForStatistic(){
        totalOfMonth = new ArrayList<>();
        cashOfMonth = new ArrayList<>();
        cashOfMonth = new ArrayList<>();
        menusOfMonth = new ArrayList<>();
        totalPrice = 0;
        totalCash = 0;
        totalCard = 0;
    }
    /*Test Data*/
    static Random random = new Random();
    public static ArrayList<Order> orderList = new ArrayList<Order>();
    public static Order order = new Order();

}