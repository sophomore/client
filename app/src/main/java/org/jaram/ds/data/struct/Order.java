package org.jaram.ds.data.struct;

import org.jaram.ds.exeption.OrderException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by kjydiary on 15. 7. 10..
 */
public class Order {
    public int _id;
    public Date date;
    public ArrayList<OrderMenu> menuList = new ArrayList<OrderMenu>();
    //public int totalPrice;

    public void addMenu(Menu menu, OrderMenu.Pay pay) {
        menuList.add(new OrderMenu(menu, pay));
    }

    public void removeMenu(Menu menu) throws OrderException {
        for (int i=0; i<menuList.size(); i++) {
            OrderMenu orderMenu = menuList.get(i);
            if (orderMenu.menu.equals(menu) && orderMenu.pay == OrderMenu.Pay.CREDIT) {
                menuList.remove(i);
                return;
            }
        }
        throw new OrderException("Not exist menu");
    }
}
