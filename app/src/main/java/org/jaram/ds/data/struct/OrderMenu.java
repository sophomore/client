package org.jaram.ds.data.struct;

/**
 * Created by kjydiary on 15. 7. 10..
 */
public class OrderMenu {
    public Menu menu;
    public enum Pay{CASH, CARD, SERVICE, CREDIT};
    public Pay pay = Pay.CREDIT;

    public OrderMenu(Menu menu, Pay pay) {
        this.menu = menu;
        this.pay = pay;
    }
}