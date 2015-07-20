package org.jaram.ds.order.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jaram.ds.R;
import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Order;
import org.jaram.ds.order.MenuListAdapter;

import java.util.Random;

/**
 * Created by kjydiary on 15. 7. 8..
 */
public class OrderView extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        RecyclerView menuListView = (RecyclerView)view.findViewById(R.id.menuListView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        menuListView.setLayoutManager(layoutManager);

//        Order order = new Order();
        Order order = Data.orderList.get(new Random().nextInt(Data.orderList.size()-1));
        MenuListAdapter adapter = new MenuListAdapter(order.menuList);

        menuListView.setAdapter(adapter);
        menuListView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }
}