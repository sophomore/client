package org.jaram.ds.data.struct;

import org.jaram.ds.data.Data;

/**
 * Created by kjydiary on 15. 7. 10..
 */
public class OrderMenu {
    public Menu menu;
    public int pay = Data.PAY_CREDIT;

    public OrderMenu(Menu menu, int pay) {
        this.menu = menu;
        this.pay = pay;
    }
}