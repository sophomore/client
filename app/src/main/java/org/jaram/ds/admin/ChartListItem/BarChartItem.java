package org.jaram.ds.admin.ChartListItem;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.ChartData;

import org.jaram.ds.R;

/**
 * Created by ohyongtaek on 15. 8. 14..
 */
public class BarChartItem extends ChartItem {

    Typeface tf;
    ViewHolder holder = null;
    public BarChartItem(ChartData<?> cd, Context c) {
        super(cd);
        tf = Typeface.createFromAsset(c.getAssets(), "OpenSans-Light.ttf");
    }

    @Override
    public int getItemType() {
        return TYPE_BARCHART;
    }

    @Override
    public View getView(int position, View convertView, Context c) {

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.list_item_barchart, null);
            holder.chart = (BarChart) convertView.findViewById(R.id.chart);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.chart.setDescription("");
        holder.chart.setHighlightEnabled(false);
        holder.chart.setDrawBarShadow(false);
        holder.chart.setDrawValueAboveBar(true);

        holder.chart.setData((BarData) mChartData);
        Legend l = holder.chart.getLegend();
        l.setTypeface(tf);

        YAxis leftAxis = holder.chart.getAxisLeft();
        leftAxis.setTypeface(tf);

        holder.chart.getAxisRight().setEnabled(false);

        XAxis xAxis = holder.chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tf);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(1);

        holder.chart.setBackgroundColor(Color.BLUE);

        return convertView;
    }
    public BarChart getChart(){
        return holder.chart;
    }
    public void setChart(BarChart barChart){
        this.holder.chart = barChart;
    }
    private static class ViewHolder {
        BarChart chart;
    }
}
