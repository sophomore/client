package org.jaram.ds.admin.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.jaram.ds.data.struct.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cheonyujung on 15. 7. 23..
 */
public class OrderlistAdapter extends BaseAdapter {
    private Context mContext;

    private List<Order> mOrderList = new ArrayList<Order>();

    public OrderlistAdapter(List<Order> list) {
        mOrderList = list;
    }

    @Override
    public int getCount() {
        return mOrderList.size();
    }

    @Override
    public Object getItem(int position) {
        return mOrderList.get(position).menuList;
    }
    //@Override
    public Date getDate(int position) {
        return mOrderList.get(position).date;
    }

    @Override
    public long getItemId(int position) {//int를 long으로 반환시키는 이유 32bit 특성 타기위해
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position; //상수화 이유? 콜백메소드 호출과 관련~!!!
        if (convertView == null) {
            //convertView = inflater.inflate(layout, parent, false);
        }
        return convertView;
    }
}
