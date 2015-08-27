package org.jaram.ds.order.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jaram.ds.R;
import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Menu;
import org.jaram.ds.data.struct.OrderMenu;
import org.jaram.ds.order.MenuListAdapter;
import org.jaram.ds.order.SimpleItemTouchHelper;

import java.util.ArrayList;

/**
 * Created by kjydiary on 15. 7. 8..
 */
public class OrderView extends Fragment {

    Callbacks callbacks = null;
    MenuListAdapter adapter;
    TextView totalprice;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        totalprice = (TextView)view.findViewById(R.id.TotalPay);
        totalprice.setText(Data.orderList.get(0).totalPrice+"");

        RecyclerView menuListView = (RecyclerView)view.findViewById(R.id.menuListView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        menuListView.setLayoutManager(layoutManager);

//        Order order = new Order();
//        Order order = Data.orderList.get(new Random().nextInt(Data.orderList.size()-1));
//        MenuListAdapter adapter = new MenuListAdapter(order.menuList);
        ArrayList<OrderMenu> orderMenus = Data.orderList.get(0).menuList;
//        orderMenus.add();


        adapter = new MenuListAdapter(orderMenus);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelper(adapter,totalprice);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(menuListView);
        menuListView.setAdapter(adapter);
        menuListView.setItemAnimator(new DefaultItemAnimator());

        //TODO: 메뉴 목록과 메뉴 선택 fragment 분리해야함. : 결제화면과 메뉴목록 통일
        ArrayList<Menu> menuBtns = new ArrayList<Menu>();
        menuBtns.addAll(Data.menuList);
        RecyclerView menuBtnListViewDon = (RecyclerView)view.findViewById(R.id.DonMenuList);
        MenuSelectBtnAdapter menuBtnAdapterDon = new MenuSelectBtnAdapter(menuBtns);
        menuBtnListViewDon.setAdapter(menuBtnAdapterDon);
        menuBtnListViewDon.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        RecyclerView menuBtnListViewDup = (RecyclerView)view.findViewById(R.id.DupMenuList);
        MenuSelectBtnAdapter menuBtnAdapterDup = new MenuSelectBtnAdapter(menuBtns);
        menuBtnListViewDup.setAdapter(menuBtnAdapterDup);
        menuBtnListViewDup.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        RecyclerView menuBtnListViewNoodle = (RecyclerView)view.findViewById(R.id.NoodleMenuList);
        MenuSelectBtnAdapter menuBtnAdapterNoodle = new MenuSelectBtnAdapter(menuBtns);
        menuBtnListViewNoodle.setAdapter(menuBtnAdapterNoodle);
        menuBtnListViewNoodle.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        RecyclerView menuBtnListViewLast = (RecyclerView)view.findViewById(R.id.DrinkAndAdd);
        MenuSelectBtnAdapter menuBtnAdapterLast = new MenuSelectBtnAdapter(menuBtns);
        menuBtnListViewLast.setAdapter(menuBtnAdapterLast);
        menuBtnListViewLast.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));


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
        public void onBindViewHolder(final MenuSelectBtnViewHolder holder, int position) {
            final int i =position;

            holder.name.setText(menuList.get(position).name);
            holder.price.setText(menuList.get(position).price+"");
            holder.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Data.orderList.get(0).addMenu(menuList.get(i),OrderMenu.Pay.CREDIT);
                    totalprice.setText((Integer.parseInt((String)totalprice.getText())+menuList.get(i).price)+"");
                    adapter.notifyDataSetChanged();
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
            public TextView price;

            public MenuSelectBtnViewHolder(View item) {
                super(item);
                menu = item;

                price = (TextView)item.findViewById(R.id.PriceOfMenu);
                name = (TextView)item.findViewById(R.id.NameOfMenu);
            }
        }
    }

    public interface Callbacks {
        void selectMenu(Menu menu);
    }
}