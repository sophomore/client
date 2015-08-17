package org.jaram.ds.order.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jaram.ds.R;
import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Menu;
import org.jaram.ds.data.struct.Order;
import org.jaram.ds.data.struct.OrderMenu;
import org.jaram.ds.order.MenuListAdapter;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by kjydiary on 15. 7. 8..
 */
public class OrderView extends Fragment {

    Callbacks callbacks = null;
    Button paybtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        RecyclerView menuListView = (RecyclerView)view.findViewById(R.id.menuListView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        menuListView.setLayoutManager(layoutManager);

//        Order order = new Order();
//        Order order = Data.orderList.get(new Random().nextInt(Data.orderList.size()-1));
//        MenuListAdapter adapter = new MenuListAdapter(order.menuList);
        ArrayList<OrderMenu> orderMenus = Data.orderList.get(0).menuList;
//        orderMenus.add();
        MenuListAdapter adapter = new MenuListAdapter(orderMenus);

        menuListView.setAdapter(adapter);
        menuListView.setItemAnimator(new DefaultItemAnimator());


        //TODO: 메뉴 목록과 메뉴 선택 fragment 분리해야함. : 결제화면과 메뉴목록 통일
        ArrayList<Menu> menuBtns = new ArrayList<Menu>();
        menuBtns.addAll(Data.menuList);
        RecyclerView menuBtnListView = (RecyclerView)view.findViewById(R.id.menuBtnListView);
        MenuSelectBtnAdapter menuBtnAdapter = new MenuSelectBtnAdapter(menuBtns);
        menuBtnListView.setAdapter(menuBtnAdapter);
        menuBtnListView.setLayoutManager(new GridLayoutManager(getActivity(), 5, GridLayoutManager.VERTICAL, false));

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callbacks = (Callbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement OrderView.Callbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    private class MenuSelectBtnAdapter extends RecyclerView.Adapter<MenuSelectBtnAdapter.MenuSelectBtnViewHolder> {

        ArrayList<Menu> menuList = null;
        public MenuSelectBtnAdapter(ArrayList<Menu> menuList) {
            this.menuList = menuList;
        }

        @Override
        public MenuSelectBtnViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MenuSelectBtnViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.menubtn_item, parent, false));
        }

        @Override
        public void onBindViewHolder(MenuSelectBtnViewHolder holder, int position) {
            final int i = position;
            holder.name.setText(menuList.get(position).name);
            holder.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callbacks.selectMenu(menuList.get(i));
                }
            });
        }

        @Override
        public int getItemCount() {
            return menuList.size();
        }

        public class MenuSelectBtnViewHolder extends RecyclerView.ViewHolder {

            public View menu;
            public TextView name;
            public MenuSelectBtnViewHolder(View item) {
                super(item);
                name = (TextView)item.findViewById(R.id.menu_name);
                menu = item;
            }
        }
    }

    public interface Callbacks {
        void selectMenu(Menu menu);
    }
}