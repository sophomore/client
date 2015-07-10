package org.jaram.ds.data.struct;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by kjydiary on 15. 7. 10..
 */
public class Order {
    public int _id;
    public Date date;
    public HashMap<OrderMenu, Integer> menus = new HashMap<OrderMenu, Integer>();
    //public int totalPrice;
}
