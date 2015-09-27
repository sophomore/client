package org.jaram.ds.admin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jaram.ds.Intro;
import org.jaram.ds.R;
import org.jaram.ds.admin.view.DatePickerFrag;
import org.jaram.ds.admin.view.DrawerFrag;
import org.jaram.ds.admin.view.ProgressChartFrag;
import org.jaram.ds.admin.view.SummaryFrag;
import org.jaram.ds.data.Data;
import org.jaram.ds.order.OrderManager;
import org.jaram.ds.util.StatisticAsyncTask;

import java.util.ArrayList;

/**
 * Created by cheonyujung on 15. 8. 22..
 */
public class StatisticMain extends FragmentActivity implements DrawerFrag.OnAnalysisListener,ProgressChartFrag.Callbacks {


    DrawerFrag drawer;
    SummaryFrag summaryFrag;
    TextView startText;
    TextView endText;
    ProgressChartFrag progressChartFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_statistic);
        if (savedInstanceState == null) {


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
                    Log.d("ttsssss", (String) startText.getText());
                    if(drawer.getChecked()) {
                        if(startText.getText().length()!=0 && endText.getText().length()!=0) {
                            createBarChart();
                        }
                    }else{
                        Bundle bundle = progressChartFrag.getArguments();
                        boolean analysisType = bundle.getBoolean("analysisType");
                        ArrayList<String> menuList = bundle.getStringArrayList("menuList");
                        int unitType = bundle.getInt("unitType");
                        ArrayList<Integer> menuIds = bundle.getIntegerArrayList("menuIds");
                        createLineChart(analysisType, menuList, menuIds,unitType);
                    }

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
    public void createLineChart(boolean analysisType, ArrayList<String> menuList,ArrayList<Integer> menuIds, int unitType) {

        String start = (String) startText.getText();
        String end = (String) endText.getText();
        if(start.length()!=0 && end.length()!=0){
            Bundle bundle = new Bundle();
            bundle.putBoolean("analysisType", analysisType);
            bundle.putStringArrayList("menuList", menuList);
            bundle.putInt("unitType", unitType);
            bundle.putString("start", start);
            bundle.putString("end", end);
            bundle.putIntegerArrayList("menuIds", menuIds);
            progressChartFrag = new ProgressChartFrag();
            progressChartFrag.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.chartFrag, progressChartFrag).commit();
            StatisticAsyncTask asyncTask = new StatisticAsyncTask(StatisticMain.this,progressChartFrag,false);
            asyncTask.execute();


        }
    }

    @Override
    public void createBarChart() {
        String start = (String) startText.getText();
        String end = (String) endText.getText();
        Data.reDataForStatistic();
        if(start.length()!=0 && end.length()!=0) {
            summaryFrag.resetTextView();
            StatisticAsyncTask statisticAsyncTask = new StatisticAsyncTask(StatisticMain.this, start, end,4, summaryFrag,true);
            statisticAsyncTask.execute();
        }
    }

    @Override
    public void configChart() {
        progressChartFrag.getLineChartManager().getChart().notifyDataSetChanged();
    }


}
