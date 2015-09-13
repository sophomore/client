package org.jaram.ds.admin.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.jaram.ds.R;
import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Order;

import java.util.ArrayList;

/**
 * Created by cheonyujung on 15. 7. 23..
 */
interface getOrderList{
    ArrayList<Order> getOrderList();
}
public class Order_viewFrag extends Fragment implements getOrderList{
    View view;
    public Order_viewFrag(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_orderview, container, false);
        ArrayList<Order> orderlist;
        orderlist = getOrderList();

        ListView list = (ListView)view.findViewById(R.id.orderlist);
        final OrderlistAdapter Adapter = new OrderlistAdapter(view.getContext(),R.layout.orderlist_item,orderlist);
        list.setAdapter(Adapter);
        //list.setAdapter(null);
        return view;
    }

    @Override
    public ArrayList<Order> getOrderList() {
        return Data.orderList;
    }
}

