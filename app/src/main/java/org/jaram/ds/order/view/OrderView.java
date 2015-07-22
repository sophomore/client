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
import android.widget.Button;

import org.jaram.ds.R;
import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Order;
import org.jaram.ds.order.MenuListAdapter;

import java.util.ArrayList;
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


        //TODO: 메뉴 목록과 메뉴 선택 fragment 분리해야함. : 결제화면과 메뉴목록 통일
        ArrayList<Button> menuBtns = new ArrayList<Button>();
        

        return view;
    }

    private class MenuSelectBtnListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

        }
    }
}