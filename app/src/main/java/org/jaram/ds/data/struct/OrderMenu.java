package org.jaram.ds.data.struct;

/**
 * Created by kjydiary on 15. 7. 10..
 */
public class OrderMenu {
    public Menu menu;
    public enum Pay{CASH, CARD, SERVICE, CREDIT};
    public Pay pay = Pay.CREDIT;
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

    public Pay getPay() {
        return pay;
    }

    public void setPay(Pay pay) {
        this.pay = pay;
    }

    public OrderMenu(Menu menu, Pay pay) {
        this.menu = menu;
        this.pay = pay;
    }
}