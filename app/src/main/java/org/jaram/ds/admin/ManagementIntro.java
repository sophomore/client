package org.jaram.ds.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import org.jaram.ds.R;

/**
 * Created by KimMyoungSoo on 2015. 8. 4..
 */
public class ManagementIntro extends ActionBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_intro);

        Button menuButton = (Button)findViewById(R.id.menu_management_btn);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManagementIntro.this, MenuManagementMain.class));
            }
        });
        Button orderButton = (Button)findViewById(R.id.order_management_btn);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManagementIntro.this, OrderManagerMain.class));
            }
        });
        Button statisticButton = (Button)findViewById(R.id.statistic_management_btn);
        statisticButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManagementIntro.this, StatisticMain.class));
            }
        });
    }

}
