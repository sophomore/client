package org.jaram.ds.order.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.jaram.ds.R;
import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Menu;
import org.jaram.ds.data.struct.Order;
import org.jaram.ds.order.MenuListAdapter;
import org.jaram.ds.order.SimpleItemTouchHelper;
import org.jaram.ds.util.AddOrderAsyncTask;

import java.util.ArrayList;

/**
 * Created by kjydiary on 15. 7. 8..
 */
public class OrderView extends Fragment implements View.OnClickListener {

    Callbacks callbacks = null;
    MenuListAdapter adapter;
    TextView totalprice;
    Order orderList;
    TextView empty;
    RecyclerView menuListView;
    RelativeLayout frame;
    LinearLayout buttonsframe;
    int payway;
    boolean doingpay = false;
    MenuSelectBtnAdapter menuBtnAdapterDon;
    MenuSelectBtnAdapter menuBtnAdapterDup;
    MenuSelectBtnAdapter menuBtnAdapterNoodle;
    MenuSelectBtnAdapter menuBtnAdapterLast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        totalprice = (TextView)view.findViewById(R.id.TotalPay);
        buttonsframe = (LinearLayout)view.findViewById(R.id.buttons);
        frame = (RelativeLayout)view.findViewById(R.id.isitOK);
        frame.setVisibility(RelativeLayout.INVISIBLE);
//        totalprice.setText(Data.orderList.get(0).totalPrice+"");

        menuListView = (RecyclerView)view.findViewById(R.id.menuListView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        menuListView.setLayoutManager(layoutManager);

        empty = (TextView)view.findViewById(R.id.list_empty);

        Button cash = (Button)view.findViewById(R.id.Cash);
        cash.setOnClickListener(this);
        Button card = (Button)view.findViewById(R.id.Card);
        card.setOnClickListener(this);
        Button service = (Button)view.findViewById(R.id.Service);
        service.setOnClickListener(this);
        Button credit = (Button)view.findViewById(R.id.Credit);
        credit.setOnClickListener(this);

        Button Ok = (Button)view.findViewById(R.id.accept);
        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setpay(payway);
                frame.setVisibility(RelativeLayout.INVISIBLE);
                adapter.resetDoingpay();
                resetDoingPay();
                adapter.notifyDataSetChanged();
                totalprice.setText(orderList.getTotalPrice()+"");
            }
        });
        Button cancel = (Button)view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frame.setVisibility(RelativeLayout.INVISIBLE);
                adapter.resetDoingpay();
                resetDoingPay();
                if(adapter.selectedMenus == adapter.orderMenus){
                    adapter.selectedMenus = new Order();
                }
            }
        });

        Button paybtn = (Button)view.findViewById(R.id.payBtn);
        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(orderList.totalPrice>0){
                    new AlertDialog.Builder(getActivity())
                            .setMessage("모든 결제를 완료해주세요.")
                            .setNeutralButton("확인",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                }
                else{
                    AddOrderAsyncTask task = new AddOrderAsyncTask(getActivity());
                    task.execute(orderList);
                    orderList = new Order();
                    adapter = new MenuListAdapter(orderList,totalprice);
                    menuListView.setAdapter(adapter);
                }

            }
        });


//        Order order = new Order();
//        Order order = Data.orderList.get(new Random().nextInt(Data.orderList.size()-1));
//        MenuListAdapter adapter = new MenuListAdapter(order.menuList);
//        ArrayList<OrderMenu> orderMenus = Data.orderList.get(0).menuList;
//        orderMenus.add();

        orderList = new Order();

        adapter = new MenuListAdapter(orderList, totalprice);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelper(adapter,totalprice);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(menuListView);
        menuListView.setAdapter(adapter);
        menuListView.setItemAnimator(new DefaultItemAnimator());

        //TODO: 메뉴 목록과 메뉴 선택 fragment 분리해야함. : 결제화면과 메뉴목록 통일
        RecyclerView cutletList = (RecyclerView)view.findViewById(R.id.DonMenuList);

        menuBtnAdapterDon = new MenuSelectBtnAdapter(Data.categoryList.get(1).menus);
        cutletList.setAdapter(menuBtnAdapterDon);
        cutletList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        RecyclerView riceList = (RecyclerView)view.findViewById(R.id.DupMenuList);
        menuBtnAdapterDup = new MenuSelectBtnAdapter(Data.categoryList.get(2).menus);
        riceList.setAdapter(menuBtnAdapterDup);
        riceList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        RecyclerView noodleList = (RecyclerView)view.findViewById(R.id.NoodleMenuList);
        menuBtnAdapterNoodle = new MenuSelectBtnAdapter(Data.categoryList.get(3).menus);
        noodleList.setAdapter(menuBtnAdapterNoodle);
        noodleList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        RecyclerView etcList = (RecyclerView)view.findViewById(R.id.DrinkAndAdd);
        menuBtnAdapterLast = new MenuSelectBtnAdapter(Data.categoryList.get(4).menus);
        etcList.setAdapter(menuBtnAdapterLast);
        etcList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


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

    public class MenuSelectBtnAdapter extends RecyclerView.Adapter<MenuSelectBtnAdapter.MenuSelectBtnViewHolder> {

        ArrayList<Menu> menuList = null;
        public MenuSelectBtnAdapter(ArrayList<Menu> menuList) {
            this.menuList = menuList;
        }
        public void setmenuList(ArrayList<Menu> menuList){
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
                    orderList.addMenu(menuList.get(i), Data.PAY_CREDIT);
                    totalprice.setText(orderList.getTotalPrice()+"");
                    adapter.notifyDataSetChanged();
                    callbacks.selectMenu(menuList.get(i));

                    if (orderList.menuList.size() == 0) {
                        menuListView.setVisibility(View.INVISIBLE);
                        empty.setVisibility(View.VISIBLE);
                    }
                    else {
                        menuListView.setVisibility(View.VISIBLE);
                        empty.setVisibility(View.INVISIBLE);
                    }
                }
            });
            if(doingpay){
                holder.menu.setClickable(false);
            }
            else{
                holder.menu.setClickable(true);
            }
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
                item.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            v.setBackgroundColor(Color.parseColor("#2185C5"));
                        }
                        else {
                            v.setBackgroundColor(Color.parseColor("#3E454C"));
                        }
                        return false;
                    }
                });
            }
        }
    }

    public interface Callbacks {
        void selectMenu(Menu menu);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        frame.setVisibility(RelativeLayout.VISIBLE);
        adapter.setDoingpay();
        setDoingPay();
        if(adapter.selectedMenus.menuList.size() == 0){
            adapter.selectedMenus = adapter.orderMenus;
        }
        if (id == R.id.Cash) {
            payway = Data.PAY_CASH;
        }
        else if (id == R.id.Card) {
            payway = Data.PAY_CARD;
        }
        else if (id == R.id.Service) {
            payway = Data.PAY_SERVICE;
        }
        else {
            payway = Data.PAY_CREDIT;
        }
    }
    public void setDoingPay(){
        doingpay = true;
        menuBtnAdapterDon.notifyDataSetChanged();
        menuBtnAdapterDup.notifyDataSetChanged();
        menuBtnAdapterNoodle.notifyDataSetChanged();
        menuBtnAdapterLast.notifyDataSetChanged();
    }
    public void resetDoingPay(){
        doingpay = false;
        menuBtnAdapterDon.notifyDataSetChanged();
        menuBtnAdapterDup.notifyDataSetChanged();
        menuBtnAdapterNoodle.notifyDataSetChanged();
        menuBtnAdapterLast.notifyDataSetChanged();
    }
}