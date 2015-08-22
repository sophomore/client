package org.jaram.ds.admin;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jaram.ds.R;
import org.jaram.ds.admin.ChartListItem.BarChartItem;
import org.jaram.ds.admin.ChartListItem.ChartItem;
import org.jaram.ds.admin.ChartListItem.LineChartItem;
import org.jaram.ds.admin.view.BarChartManager;
import org.jaram.ds.admin.view.DrawerFrag;
import org.jaram.ds.admin.view.LineChartManager;

import java.util.ArrayList;
import java.util.List;


public class StatisticMain extends ActionBarActivity implements DrawerFrag.OnAnalysisListener{


    DrawerFrag drawerFrag;
    BarChartManager barChartManager;
    LineChartManager lineChartManager;
    ListView chartContainer;
    ArrayList<ChartItem> list;
    ChartDataAdapter cda;
    float startX;
    float startY;
    float endX;
    float endY;
    float diffX, diffY;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_statistic);
        if (savedInstanceState == null) {

            chartContainer = (ListView)findViewById(R.id.chart_container);
            list = new ArrayList<ChartItem>();
            cda = new ChartDataAdapter(getApplicationContext(), list);
            chartContainer.setAdapter(cda);
            drawerFrag = new DrawerFrag();
            getSupportFragmentManager().beginTransaction().add(R.id.drawer,drawerFrag).commit();
        }

    }
    private class ChartDataAdapter extends ArrayAdapter<ChartItem> {

        public ChartDataAdapter(Context context, List<ChartItem> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getItem(position).getView(position, convertView, getContext());
        }

        @Override
        public int getItemViewType(int position) {
            // return the views type
            return getItem(position).getItemType();
        }

        @Override
        public int getViewTypeCount() {
            return 2; // we have 3 different item-types
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
    public void createLineChart(boolean analysisType, ArrayList<String> menuList, int unitType, String start, String end) {
        list.clear();
        lineChartManager = new LineChartManager(this,analysisType, menuList, unitType, start, end);

        LineChartItem lineChartItem = new LineChartItem(lineChartManager.getData(menuList,unitType,start,end),getApplicationContext());
        list.add(lineChartItem);
        barChartManager = new BarChartManager(this,analysisType,menuList,unitType,start,end);
        BarChartItem barChartItem = new BarChartItem(barChartManager.getData(menuList,unitType,start,end),getApplicationContext());
        list.add(barChartItem);
        cda.notifyDataSetChanged();

    }

}