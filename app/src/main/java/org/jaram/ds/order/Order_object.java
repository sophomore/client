package org.jaram.ds.order;

/**
 * Created by vmffkxlgnqh1 on 15. 7. 12..
 */
public class Order_object {

    private String name;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }


    @Override
    public String toString(){
        return "Menu_object{"+"name"+name+'}';
    }
}
