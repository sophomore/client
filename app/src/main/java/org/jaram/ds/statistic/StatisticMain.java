package org.jaram.ds.statistic;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.jaram.ds.R;
import org.jaram.ds.statistic.view.BarChartFrag;
import org.jaram.ds.statistic.view.DrawerFrag;


public class StatisticMain extends FragmentActivity {


    DrawerFrag drawerFrag;
    BarChartFrag barChartFrag;

    float startX;
    float startY;
    float endX;
    float endY;
    float diffX, diffY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_statistic);
        if (savedInstanceState == null) {
            barChartFrag = new BarChartFrag();
            getSupportFragmentManager().beginTransaction().add(R.id.chart, barChartFrag).commit();

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