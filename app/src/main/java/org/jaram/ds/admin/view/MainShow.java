package org.jaram.ds.admin.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import org.jaram.ds.R;

/**
 * Created by cheonyujung on 15. 9. 14..
 */
public class MainShow extends FragmentActivity{
    Order_viewFrag order_viewFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainshow);

        //order_viewFrag = new Order_viewFrag();
        getSupportFragmentManager().beginTransaction().add(R.id.order_view1, order_viewFrag).commit();

    }
}
