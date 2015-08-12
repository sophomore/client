package org.jaram.ds.admin;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.jaram.ds.R;
import org.jaram.ds.admin.view.Order_viewFrag;
import org.jaram.ds.admin.view.Search_orderFrag;

/**
 * Created by cheonyujung on 15. 7. 23..
 */
public class OrderManagerMain extends FragmentActivity {
    Search_orderFrag search_orderFrag;
    Order_viewFrag order_viewFrag;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ordermanager);
        if (savedInstanceState == null) {
            search_orderFrag = new Search_orderFrag();
            order_viewFrag = new Order_viewFrag();
            getSupportFragmentManager().beginTransaction().add(R.id.order_search,search_orderFrag).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.order_view, order_viewFrag).commit();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intro, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


