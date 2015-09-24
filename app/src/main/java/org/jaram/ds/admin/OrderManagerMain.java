package org.jaram.ds.admin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import org.jaram.ds.Intro;
import org.jaram.ds.R;
import org.jaram.ds.admin.view.Order_viewFrag;
import org.jaram.ds.admin.view.OrderlistAdapter;
import org.jaram.ds.admin.view.Search_orderFrag;
import org.jaram.ds.order.OrderManager;
import org.jaram.ds.util.SearchOrderAsyncTask;

import java.util.ArrayList;

/**
 * Created by cheonyujung on 15. 7. 23..
 */

public class OrderManagerMain extends FragmentActivity implements Search_orderFrag.OnSearchListener{
    Search_orderFrag search_orderFrag;
    Order_viewFrag order_viewFrag;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ordermanager);
        if (savedInstanceState == null) {
            FrameLayout detail_order = (FrameLayout)findViewById(R.id.detail_order);
            search_orderFrag = new Search_orderFrag();
            order_viewFrag = new Order_viewFrag(detail_order);

            getSupportFragmentManager().beginTransaction().add(R.id.order_search,search_orderFrag).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.order_view, order_viewFrag).commit();

            if(detail_order == null){
                Log.d("null", "detail_order1");
            }
            Button refund = (Button) findViewById(R.id.refund);
            refund.setText("주문");
            refund.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(OrderManagerMain.this, OrderManager.class));
                    finish();
                }
            });
            Button statistic = (Button) findViewById(R.id.DrawerStat);

            statistic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(OrderManagerMain.this, StatisticMain.class));
                    finish();
                }
            });

            Button order =(Button) findViewById(R.id.DrawerOrder);
            order.setTextColor(Color.parseColor("#E8704C"));
            GradientDrawable bgShape = (GradientDrawable)order.getBackground();
            bgShape.setColorFilter(Color.parseColor("#E8704C"), PorterDuff.Mode.MULTIPLY);
//            order.setClickable(false);

            Button menuManager = (Button) findViewById(R.id.DrawerMenu);
            menuManager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(OrderManagerMain.this, MenuManagementMain.class));
                    finish();
                }
            });
            Button exit = (Button) findViewById(R.id.DrawerExit);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OrderManagerMain.this,Intro.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    moveTaskToBack(true);
                }
            });
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


    @Override
    public void createListview(Context mcontext, String startDate, String endDate, ArrayList<Integer> menus, int pay) {

        ListView orderList = Order_viewFrag.getList();
        OrderlistAdapter adapter = (OrderlistAdapter) orderList.getAdapter();
        adapter.getData().clear();
        SearchOrderAsyncTask asyncTask1 = new SearchOrderAsyncTask(OrderManagerMain.this, startDate, endDate, menus ,pay);
        asyncTask1.setAdapter(adapter);
        asyncTask1.execute();

    }
}


