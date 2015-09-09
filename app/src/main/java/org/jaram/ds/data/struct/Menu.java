package org.jaram.ds.data.struct;

/**
 * Created by kjydiary on 15. 7. 8..
 */
public class Menu {
    public int id;
    public Category category;
    public String name;
    public int price;
    public Menu(int id, Category category, String name, int price) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        category.addMenu(this);
    }
}