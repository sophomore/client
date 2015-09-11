package org.jaram.ds.data.struct;

import java.util.ArrayList;

/**
 * Created by kjydiary on 15. 7. 8..
 */
public class Category {
    public int id;
    public String name;
    public ArrayList<Menu> menus = new ArrayList<Menu>();
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addMenu(Menu menu) {
        menus.add(menu);
    }
}