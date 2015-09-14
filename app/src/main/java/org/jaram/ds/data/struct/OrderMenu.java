package org.jaram.ds.data.struct;

import org.jaram.ds.data.Data;

/**
 * Created by kjydiary on 15. 7. 10..
 */
public class OrderMenu {
    public Menu menu;
    public boolean curry;
    public boolean doublei;

    public void setCurry(boolean curry) {
        this.curry = curry;
    }

    public void setDoublei(boolean doublei) {
        this.doublei = doublei;
    }

    public boolean isCurry() {
        return curry;
    }

    public boolean isDoublei() {
        return doublei;
    }

    public Menu getMenu() {
        return menu;
    }
    public int pay = Data.PAY_CREDIT;

    public OrderMenu(Menu menu, int pay) {
        this.menu = menu;
        this.pay = pay;
    }
}