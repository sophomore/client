package org.jaram.ds.data.struct;

import com.orm.SugarRecord;

/**
 * Created by ohyongtaek on 15. 8. 20..
 */
public class TestCategory extends SugarRecord<TestCategory> {

    private static Integer increment = 0;
    private Integer id;

    private String name;


    public TestCategory(int id,String name){
        super();
        this.name = name;
        this.id = id;
    }

    public String getName(){
        return this.name;
    }


}
