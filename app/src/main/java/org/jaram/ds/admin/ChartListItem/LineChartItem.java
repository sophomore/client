package org.jaram.ds.admin.ChartListItem;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.LineData;

import org.jaram.ds.R;

/**
 * Created by ohyongtaek on 15. 8. 14..
 */
public class LineChartItem extends ChartItem {

    Typeface tf;
    public LineChartItem(ChartData<?> cd, Context c) {
        super(cd);
        tf = Typeface.createFromAsset(c.getAssets(), "OpenSans-Light.ttf");
    }

    @Override
    public int getItemType() {
        return TYPE_LINECHART;
    }

    @Override
    public View getView(int position, View convertView, Context c) {
        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.list_item_linechart, null);
            holder.chart = (LineChart) convertView.findViewById(R.id.chart);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.chart.setDescription("");

        holder.chart.setHighlightEnabled(false);
        holder.chart.setDrawGridBackground(false);
        holder.chart.setTouchEnabled(true);
        holder.chart.setData((LineData) mChartData);


        Legend l = holder.chart.getLegend();
        l.setTypeface(tf);


        holder.chart.getAxisRight().setEnabled(false);



        LimitLine ll1 = new LimitLine(100000f, "Upper Limit");
        ll1.setLineWidth(2f);
        ll1.enableDashedLine(5f, 5f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.POS_RIGHT);
        ll1.setTextSize(3f);

        LimitLine ll2 = new LimitLine(-30000f, "Lower Limit");
        ll2.setLineWidth(2f);
        ll2.enableDashedLine(5f, 5f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.POS_RIGHT);
        ll2.setTextSize(3f);

        YAxis leftAxis = holder.chart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaxValue(300000);
        leftAxis.setAxisMinValue(0f);
        leftAxis.setStartAtZero(false);
        leftAxis.enableGridDashedLine(5f, 5f, 0f);

        leftAxis.setDrawLimitLinesBehindData(true);

        return convertView;
    }


    private static class ViewHolder {
        LineChart chart;
    }
}
