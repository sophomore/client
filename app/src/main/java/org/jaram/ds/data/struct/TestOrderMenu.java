package org.jaram.ds.data.struct;

import com.orm.SugarRecord;

/**
 * Created by ohyongtaek on 15. 8. 20..
 */
enum Pay{CASH(0), CARD(1), SERVICE(2), CREDIT(3);

    int index;
     Pay(int i) {
         this.index = i;
    }

};

public class TestOrderMenu extends SugarRecord<TestOrderMenu> {

    private TestMenu menu;
    private Integer pay;
    private TestOrder testOrder;

    public TestOrderMenu(){

    }
    public TestOrderMenu(TestMenu menu, Integer pay, TestOrder order){
        this.menu = menu;
        this.pay = pay;
        this.testOrder = order;
    }

    public TestMenu getMenu(){
        return this.menu;
    }
    public Integer getPay(){
        return this.pay;
    }
    public void setMenu(TestMenu menu){
        this.menu = menu;
    }
    public void setPay(Integer pay){
        this.pay = pay;
    }

    public void setOrder(TestOrder order) {
        this.testOrder = order;
    }

    public TestOrder getOrder() {

        return this.testOrder;
    }
}
