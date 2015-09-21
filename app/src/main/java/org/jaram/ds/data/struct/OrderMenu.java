package org.jaram.ds.data.struct;

import org.jaram.ds.data.Data;

/**
 * Created by kjydiary on 15. 7. 10..
 */
public class OrderMenu {
    public Menu menu;
    public boolean curry = false;
    public boolean doublei = false;
    public int totalprice = 0;
    public int pay = Data.PAY_CREDIT;
    public boolean complete = false;

    public OrderMenu(Menu menu, int pay) {
        this.menu = menu;
        this.pay = pay;
        this.totalprice = menu.price;
    }

    public int setCurry() {
        this.curry = true;
        totalprice += Data.CURRY;
        return totalprice;
    }

    public int setDoublei() {
        this.doublei = true;
        totalprice += Data.DOULBEI;
        return totalprice;
    }

    public int resetCurry() {
        this.curry = false;
        totalprice -= Data.CURRY;
        return totalprice;
    }

    public int resetDoublei() {
        this.doublei = false;
        totalprice -= Data.DOULBEI;
        return totalprice;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public int getTotalprice() {
        return totalprice;
    }
    public void setComplete(){
        this.complete = true;
    }
    public boolean getComplete(){
        return complete;
    }
}