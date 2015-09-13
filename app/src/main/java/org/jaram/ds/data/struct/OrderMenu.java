package org.jaram.ds.data.struct;

import org.jaram.ds.data.Data;

/**
 * Created by kjydiary on 15. 7. 10..
 */
public class OrderMenu {
    public Menu menu;
    public enum Pay{CASH, CARD, SERVICE, CREDIT};
    public Pay pay = Pay.CREDIT;
    public boolean curry = false;
    public boolean doublei = false;
    public int totalprice = menu.price;

    public OrderMenu(Menu menu, Pay pay) {
        this.menu = menu;
        this.pay = pay;
    }

    public int setCurry() {
        this.curry = true;
        totalprice += Data.CURRY;
        return totalprice;
    }
    public int setDoublei() {
        this.doublei = false;
        totalprice += Data.DOULBEI;
        return totalprice;
    }

    public int resetCurry(){
        this.curry = true;
        totalprice -= Data.CURRY;
        return totalprice;
    }

    public int resetDoublei(){
        this.curry = false;
        totalprice -= Data.DOULBEI;
        return totalprice;
    }

    public void setPay(Pay pay) {
        this.pay = pay;
    }

    public int getTotalprice(){
        return totalprice;
    }
}