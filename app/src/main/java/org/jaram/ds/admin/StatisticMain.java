package org.jaram.ds.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jaram.ds.Intro;
import org.jaram.ds.R;
import org.jaram.ds.admin.view.DatePickerFrag;
import org.jaram.ds.admin.view.DrawerFrag;
import org.jaram.ds.admin.view.ProgressChartFrag;
import org.jaram.ds.admin.view.SummaryFrag;
import org.jaram.ds.order.OrderManager;

import java.util.ArrayList;

/**
 * Created by cheonyujung on 15. 8. 22..
 */
public class StatisticMain extends FragmentActivity implements DrawerFrag.OnAnalysisListener,ProgressChartFrag.Callbacks {

    Typeface tf;
    DrawerFrag drawer;
    SummaryFrag summaryFrag;
    TextView startText;
    TextView endText;
    ProgressChartFrag progressChartFrag;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_statistic);
        if (savedInstanceState == null) {


//            tf = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
            summaryFrag = new SummaryFrag();
            drawer = new DrawerFrag();
            getSupportFragmentManager().beginTransaction().add(R.id.drawer,drawer).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.chartFrag, summaryFrag).commit();

            startText = (TextView)findViewById(R.id.startText);
            endText = (TextView)findViewById(R.id.endText);
            Button startButton = (Button)findViewById(R.id.startPeriod);
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatePickerFrag startPicker = new DatePickerFrag(startText);
                    startPicker.show(getFragmentManager(), "startPicker");
                }
            });
            Button endButton = (Button) findViewById(R.id.endPeriod);
            endButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatePickerFrag endPicker = new DatePickerFrag(endText);
                    endPicker.show(getFragmentManager(), "endPicker");
                }
            });


            Button analysis = (Button) findViewById(R.id.analysisBtn);
            analysis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String start = (String) startText.getText();
                    String end = (String) endText.getText();
                    summaryFrag.createChart(start, end);

                }
            });

            Button refund = (Button) findViewById(R.id.refund);
            refund.setText("주문");
            refund.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(StatisticMain.this, OrderManager.class));
                    finish();
                }
            });
            Button statistic = (Button) findViewById(R.id.DrawerStat);
            statistic.setTextColor(Color.parseColor("#E8704C"));
            GradientDrawable bgShape = (GradientDrawable)statistic.getBackground();
            bgShape.setColorFilter(Color.parseColor("#E8704C"), PorterDuff.Mode.MULTIPLY);
            statistic.setClickable(false);
            Button order =(Button) findViewById(R.id.DrawerOrder);
            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(StatisticMain.this, OrderManagerMain.class));
                    finish();
                }
            });
            Button menuManager = (Button) findViewById(R.id.DrawerMenu);
            menuManager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(StatisticMain.this, MenuManagementMain.class));
                    finish();
                }
            });
            Button exit = (Button) findViewById(R.id.DrawerExit);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(StatisticMain.this,Intro.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    moveTaskToBack(true);
                }
            });
        }
    }

    @Override
    public void createLineChart(boolean analysisType, ArrayList<String> menuList, int unitType) {
        String start = (String) startText.getText();
        String end = (String) endText.getText();
        Bundle bundle = new Bundle();
        bundle.putBoolean("analysisType", analysisType);
        bundle.putStringArrayList("menuList", menuList);
        bundle.putInt("unitType", unitType);
        bundle.putString("start", start);
        bundle.putString("end",end);
        progressChartFrag = new ProgressChartFrag();
        progressChartFrag.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.chartFrag, progressChartFrag).commit();

    }

    @Override
    public void configChart() {
        progressChartFrag.createChart();
    }


}
