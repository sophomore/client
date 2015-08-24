package org.jaram.ds.admin;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jaram.ds.R;
import org.jaram.ds.admin.view.DatePickerFrag;
import org.jaram.ds.admin.view.DrawerFrag;
import org.jaram.ds.admin.view.ProgressChartFrag;
import org.jaram.ds.admin.view.SummaryFrag;

import java.util.ArrayList;

/**
 * Created by cheonyujung on 15. 8. 22..
 */
public class Statistic2 extends FragmentActivity implements DrawerFrag.OnAnalysisListener,ProgressChartFrag.Callbacks {

    Typeface tf;
    DrawerFrag drawer;
    SummaryFrag summaryFrag;
    TextView startText;
    TextView endText;
    ProgressChartFrag progressChartFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_statistic2);
        if (savedInstanceState == null) {
            tf = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
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
                    summaryFrag.createChart(start,end);
                    summaryFrag.getChart().notifyDataSetChanged();
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
