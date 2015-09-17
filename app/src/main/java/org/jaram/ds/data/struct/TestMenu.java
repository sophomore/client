package org.jaram.ds.data.struct;

import com.orm.SugarRecord;

/**
 * Created by ohyongtaek on 15. 8. 18..
 */
public class TestMenu extends SugarRecord<TestMenu>{
    private static Integer increment = 0;


    private TestCategory category;
    private String name;
    private Integer price;
    private Boolean available;

    private long _id;

    public TestMenu(){

    }

    public TestMenu(String name, int price,TestCategory category, boolean available){
        this.name = name;
        this.price = price;
        this._id = ++increment;
        this.category = category;
        this.available = available;
    }

    public String getName(){
        return name;
    }
    public TestCategory getCategory(){
        return this.category;
    }
    public int getPrice(){
        return price;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPrice(int price){
        this.price = price;
    }
    public void setId(long id){
        _id = id;
    }
    public void setCategory(TestCategory category){
        this.category = category;
    }

}
