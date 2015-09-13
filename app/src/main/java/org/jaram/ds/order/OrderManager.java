package org.jaram.ds.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.jaram.ds.Intro;
import org.jaram.ds.R;
import org.jaram.ds.admin.MenuManagementMain;
import org.jaram.ds.admin.OrderManagerMain;
import org.jaram.ds.admin.StatisticMain;
import org.jaram.ds.data.struct.Menu;
import org.jaram.ds.order.view.OrderView;

/**
 * Created by kjydiary on 15. 7. 8..
 */
public class OrderManager extends ActionBarActivity implements OrderView.Callbacks
{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        OrderView orderView = new OrderView();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment, orderView)
                .commit();
        Button refund = (Button) findViewById(R.id.refund);

        refund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button statistic = (Button) findViewById(R.id.DrawerStat);

        statistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderManager.this, StatisticMain.class));
                finish();
            }
        });

        Button order =(Button) findViewById(R.id.DrawerOrder);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderManager.this, OrderManagerMain.class));
                finish();
            }
        });
        Button menuManager = (Button) findViewById(R.id.DrawerMenu);
        menuManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderManager.this, MenuManagementMain.class));
                finish();
            }
        });
//        menuManager.setTextColor(Color.parseColor("#E8704C"));
//        GradientDrawable bgShape = (GradientDrawable)menuManager.getBackground();
//        bgShape.setColorFilter(Color.parseColor("#E8704C"), PorterDuff.Mode.MULTIPLY);

        Button exit = (Button) findViewById(R.id.DrawerExit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderManager.this,Intro.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                moveTaskToBack(true);
            }
        });
    }

    @Override
    public void selectMenu(Menu menu) {
        Toast.makeText(OrderManager.this, "Select Menu : "+menu.name, Toast.LENGTH_SHORT).show();
    }
}