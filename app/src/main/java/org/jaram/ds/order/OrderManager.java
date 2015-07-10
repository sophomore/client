package org.jaram.ds.order;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import org.jaram.ds.R;
import org.jaram.ds.order.view.OrderView;

/**
 * Created by kjydiary on 15. 7. 8..
 */
public class OrderManager extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        OrderView orderView = new OrderView();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment, orderView)
                .commit();
    }
}