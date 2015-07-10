package org.jaram.ds.statistic;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import org.jaram.ds.R;


/**
 * Created by ka123ak on 2015-07-09.
 */
public class BarChartFrag extends Fragment implements OnChartGestureListener {
    public static Fragment newInstance() { return new BarChartFrag();}

    private BarChart mChart;

    public BarChartFrag() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_bar,container,false);

        //차트 객체 구현
        mChart = new BarChart(getActivity());
        mChart.setDescription("");

        mChart.setHighlightEnabled(false);
        mChart.setDrawBarShadow(false);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"OpenSans-Light.ttf");

        Legend l = mChart.getLegend();
        l.setTypeface(tf);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(tf);

        mChart.getAxisRight().setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(false);

        mChart.setBackgroundColor(Color.BLUE);
        FrameLayout parent = (FrameLayout) view.findViewById(R.id.parentLayout);
        parent.addView(mChart);


        return view;

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Toast.makeText(getActivity(),"onChartLongPressed 실행됨",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Toast.makeText(getActivity(),"onChartDoubleTapped 실행됨",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Toast.makeText(getActivity(),"onChartSingleTapped 실행됨",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Toast.makeText(getActivity(),"onChartFling 실행됨",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Toast.makeText(getActivity(),"onChartScale 실행됨",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Toast.makeText(getActivity(),"onChartTranslate 실행됨",Toast.LENGTH_LONG).show();
    }
}
