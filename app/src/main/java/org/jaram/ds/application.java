package org.jaram.ds;

import android.content.Context;

/**
 * Created by ohyongtaek on 15. 8. 18..
 */
public class application extends com.orm.SugarApp {
    private static Context instance;

    public static Context get() {
        return application.instance;
    }
}