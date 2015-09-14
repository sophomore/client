package org.jaram.ds.data.struct;

import android.util.Log;

import org.jaram.ds.data.Data;

/**
 * Created by kjydiary on 15. 7. 10..
 */
public class OrderMenu {
    public Menu menu;
<<<<<<< HEAD
    public boolean curry = false;
    public boolean doublei = false;
    public int totalprice = 0;
=======
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
>>>>>>> d1a65fc7fe137b751b5b483df24730a41f55f297
    public int pay = Data.PAY_CREDIT;

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
}