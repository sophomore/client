package org.jaram.ds.data.struct;

import android.util.Log;

import org.jaram.ds.data.Data;
import org.jaram.ds.exeption.OrderException;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kjydiary on 15. 7. 10..
 */
public class Order {
    public int _id;
    public Date date;
    public ArrayList<OrderMenu> menuList = new ArrayList<OrderMenu>();
    public int totalPrice = 0;
    public int server;

    public void addMenu(Menu menu, int pay) {
        menuList.add(new OrderMenu(menu, pay));
    }
    public int getTotalPrice(){
        totalPrice = 0;
        for(int i=0;i<menuList.size();i++) {
            if (!menuList.get(i).complete) {
                totalPrice += menuList.get(i).totalprice;
            }
        }
        return totalPrice;
    }
    public int getTotalForServer(){
        server =0;
        for(int i=0; i<menuList.size(); i++){
            server += menuList.get(i).totalprice;
        }
        return server;
    }

    public void removeMenu(Menu menu) throws OrderException {
        for (int i=0; i<menuList.size(); i++) {
            OrderMenu orderMenu = menuList.get(i);
            if (orderMenu.menu.equals(menu) && orderMenu.pay == Data.PAY_CREDIT) {
                menuList.remove(i);
                return;
            }
        }
        throw new OrderException("Not exist menu");
    }


}
