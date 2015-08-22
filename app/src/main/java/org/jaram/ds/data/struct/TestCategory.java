package org.jaram.ds.data.struct;

import com.orm.SugarRecord;

/**
 * Created by ohyongtaek on 15. 8. 20..
 */
public class TestCategory extends SugarRecord<TestCategory> {

    private static Integer increment = 0;
    private long _id;

    private String name;


    public TestCategory(){

    }
    public TestCategory(String name){
        super();
        this.name = name;
        this._id = ++increment;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public long get_id() {
        return _id;
    }
}
