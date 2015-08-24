package org.jaram.ds.admin;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;

import org.jaram.ds.R;
import org.jaram.ds.admin.ChartListItem.ChartItem;
import org.jaram.ds.admin.view.BarChartManager;
import org.jaram.ds.admin.view.DatePickerFrag;
import org.jaram.ds.admin.view.DrawerFrag;
import org.jaram.ds.admin.view.LineChartManager;
import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Menu;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by cheonyujung on 15. 8. 22..
 */
public class Statistic2 extends ActionBarActivity implements DrawerFrag.OnAnalysisListener{
    LineChartManager lineChartManager;
    ListView chartContainer;
    ArrayList<ChartItem> list;
    //ChartDataAdapter cda;
    DrawerFrag drawerFrag;
    BarChart barChart;
    ArrayList<String> menuList = new ArrayList<String>();
    Typeface tf;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_statistic2);
        if (savedInstanceState == null) {
            tf = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
//            barChartManager = new BarChartManager(this,false,menuList,1,"2014/8/30","2015/8/30");
//            barChartManager.setChart((BarChart) findViewById(R.id.chart_container2));
//            barChartManager.getChart().setData(barChartManager.getData(menuList, 1, "2014/8/30", "2015/8/30"));
//            //barChart = (BarChart) findViewById(R.id.chart_container2);
//            barChartManager.getChart().setDescription("");
//            barChartManager.getChart().setHighlightEnabled(false);
//            barChartManager.getChart().setDrawBarShadow(false);
//            barChartManager.getChart().setDrawValueAboveBar(true);

            for(Menu i : Data.menuList){
                menuList.add(i.name);
            }

            Toast.makeText(getApplicationContext(), menuList.size()+"@", Toast.LENGTH_LONG).show();


//            chartContainer = (ListView)findViewById(R.id.chart_container1);
//            list = new ArrayList<ChartItem>();
//            cda = new ChartDataAdapter(getApplicationContext(), list);
//            chartContainer.setAdapter(cda);
//            drawerFrag = new DrawerFrag();
//            getSupportFragmentManager().beginTransaction().add(R.id.drawer2,drawerFrag).commit();
        }
        final TextView startText = (TextView) findViewById(R.id.startText);
        final TextView endText = (TextView) findViewById(R.id.endText);
        Button startButton = (Button)findViewById(R.id.startPeriod);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerFrag startPicker = new DatePickerFrag(startText);
                startPicker.show(getFragmentManager(),"startPicker");
            }
        });
        Button endButton = (Button) findViewById(R.id.endPeriod);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerFrag endPicker = new DatePickerFrag(endText);
                endPicker.show(getFragmentManager(),"endPicker");
            }
        });
        final Activity statisticActivity = this;
        Button analysis = (Button) findViewById(R.id.analysisBtn);
        analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start = (String) startText.getText();
                String end = (String) endText.getText();
                BarChartManager barChartManager = new BarChartManager(statisticActivity,false,menuList,3,start,end);
                barChartManager.setChart((BarChart)findViewById(R.id.chart_container2));
                barChartManager.getChart().setData(barChartManager.getData(menuList,3,start,end));
                setChartConfig(barChartManager);
            }
        });
    }

    public void setChartConfig(final BarChartManager barChartManager){
        Legend l = barChartManager.getChart().getLegend();
        l.setTypeface(tf);

        YAxis leftAxis = barChartManager.getChart().getAxisLeft();
        leftAxis.setTypeface(tf);

        barChartManager.getChart().getAxisRight().setEnabled(false);

        XAxis xAxis = barChartManager.getChart().getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tf);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(1);

        TextView moneydata = (TextView) findViewById(R.id.moneydata);
        moneydata.setText("10000원");
        TextView creditdata = (TextView) findViewById(R.id.creditdata);
        moneydata.setText("10000원");
        TextView totaldata = (TextView) findViewById(R.id.totaldata);
        totaldata.setText("10000원");

        barChartManager.getChart().setBackgroundColor(Color.BLUE);

        barChartManager.getChart().setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Random r = new Random();
                Toast.makeText(barChartManager.getChart().getContext(), e.getXIndex()+"", Toast.LENGTH_SHORT).show();
                TextView click_moneydata = (TextView) findViewById(R.id.click_moneydata);
                TextView click_creditdata = (TextView) findViewById(R.id.click_creditdata);
                TextView click_totaldata = (TextView) findViewById(R.id.click_totaldata);
                click_moneydata.setText(r.nextInt(45) * 10000 + "원");
                click_creditdata.setText(r.nextInt(45) * 10000 + "원");
                click_totaldata.setText(r.nextInt(45) * 10000 + "원");
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }
    @Override
    public void createLineChart(boolean analysisType, ArrayList<String> menuList, int unitType, String start, String end) {

    }

}
