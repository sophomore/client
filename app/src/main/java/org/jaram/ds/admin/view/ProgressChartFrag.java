package org.jaram.ds.admin.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;

import org.jaram.ds.R;

import java.util.ArrayList;

/**
 * Created by ohyongtaek on 15. 8. 24..
 */

public class ProgressChartFrag extends Fragment {
    LineChartManager lineChartManager;
    Callbacks callbacks;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sales_progress,container,false);
        lineChartManager = new LineChartManager();
        lineChartManager.setChart((LineChart) view.findViewById(R.id.progressChart));
        callbacks.configChart();
        return view;
    }

    public LineChartManager getLineChartManager(){
        return this.lineChartManager;
    }
    public void createChart(){
        boolean anaylsisType = getArguments().getBoolean("analysisType");
        ArrayList<String> menuList = getArguments().getStringArrayList("menuList");
        int unitType = getArguments().getInt("unitType");
        String start = getArguments().getString("start");
        String end = getArguments().getString("end");
        lineChartManager.getChart().setData(lineChartManager.getData(anaylsisType,menuList,unitType,start,end));
        lineChartManager.getChart().notifyDataSetChanged();
    }

    public static interface Callbacks{
        void configChart();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
           callbacks  = (Callbacks) getActivity();
        }catch (Exception e){
            throw new ClassCastException(activity.toString() + " must implement OnAnalysisListener");
        }

    }
}
