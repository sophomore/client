package org.jaram.ds;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.jaram.ds.admin.view.ManagementIntro;
import org.jaram.ds.data.Data;
import org.jaram.ds.order.OrderManager;
import org.jaram.ds.util.MenuAysncTask;
import org.jaram.ds.util.MenuManageAsyncTask;

public class Intro extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        //TestData testData = new TestData();

        final MenuManageAsyncTask asyncTask = new MenuManageAsyncTask(Intro.this);
        asyncTask.execute();

        Log.d("tstett", Data.orderList+"");
        /*if(TestCategory.findAll(TestCategory.class)==null){
            TestCategory.deleteAll(TestCategory.class);
        }*/
        MenuAysncTask menuAysncTask = new MenuAysncTask(Intro.this);


        Button orderButton = (Button) findViewById(R.id.orderBtn);
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

                startActivity(new Intent(Intro.this, ManagementIntro.class));

            }
        });

    }

}