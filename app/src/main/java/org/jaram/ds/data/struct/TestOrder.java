package org.jaram.ds.data.struct;

import com.orm.SugarRecord;

/**
 * Created by ohyongtaek on 15. 8. 20..
 */
public class TestOrder extends SugarRecord<TestOrder> {

    private static int increment = 0;
    private String date;
    private Integer totalPrice;
    private long _id;

    public TestOrder(){

    }
    public TestOrder(String date, Integer totalPrice){
        this.date = date;
        this.totalPrice = totalPrice;
        this._id = ++increment;
    }


    public void set_id(long _id) {
        this._id = _id;
    }

    public long get_id() {
        return _id;

    }

    public String getDate() {
        return date;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void addTotalPrice(Integer price){
        this.totalPrice += price;
    }
}
