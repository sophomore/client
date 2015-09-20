package org.jaram.ds.admin.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import org.jaram.ds.R;
import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Order;

import java.util.ArrayList;

/**
 * Created by cheonyujung on 15. 7. 23..
 */
public class Order_viewFrag extends Fragment {
    View view;
    FrameLayout detail_order;
    public Order_viewFrag(FrameLayout detail_order){
        this.detail_order = detail_order;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_orderview, container, false);
        ArrayList<Order> orderlist;
        orderlist = getOrderList();

        ListView list = (ListView)view.findViewById(R.id.orderlist);

        final OrderlistAdapter Adapter = new OrderlistAdapter(view.getContext(),R.layout.orderlist_item,orderlist, detail_order);
        list.setAdapter(Adapter);
        return view;
    }

    public ArrayList<Order> getOrderList() {
        return Data.orderList;
    }
}

