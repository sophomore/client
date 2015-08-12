package org.jaram.ds;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.jaram.ds.admin.OrderManagerMain;
import org.jaram.ds.order.OrderManager;

public class Intro extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Button orderButton = (Button)findViewById(R.id.orderBtn);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intro.this, OrderManager.class));
            }
        });
        Button adminButton = (Button)findViewById(R.id.adminBtn);
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intro.this, OrderManagerMain.class));
            }
        });
    }
}