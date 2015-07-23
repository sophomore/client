package org.jaram.ds.order;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jaram.ds.R;
import org.jaram.ds.data.struct.Menu;
import org.jaram.ds.data.struct.OrderMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by kjydiary on 15. 7. 10..
 */
public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MenuViewHolder> {

    private ArrayList<OrderMenu> orderMenus;
    private ArrayList<Menu> menuList;
    private HashMap<Menu, Integer> menuCount;
    public MenuListAdapter(ArrayList<OrderMenu> orderMenus) {
        if (menuList == null) {
            throw new IllegalArgumentException("list null");
        }
        this.orderMenus = orderMenus;
        menuCount = new HashMap<Menu, Integer>();
        for (int i=0; i<orderMenus.size(); i++) {
            Menu menu = orderMenus.get(i).menu;
            int count = 0;
            if (menuCount.containsKey(menu)) {
                count = menuCount.get(menu);
            }
            count++;
            menuCount.put(menu, count);
        }
        this.menuList = new ArrayList<Menu>();
        for (Menu menu : menuCount.keySet()) {
            menuList.add(menu);
        }
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.menulist_item, parent, false);
        return new MenuViewHolder(item);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        holder.nameView.setText(menuList.get(position).name);
        holder.priceView.setText(menuList.get(position).price+"원");
        holder.countView.setText(menuCount.get(menuList.get(position))+"개");
        holder.totalPriceView.setText("총 "+orderMenus.get(position).menu.price * menuCount.get(menuList.get(position))+"원");
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        public TextView nameView, countView, priceView, totalPriceView;
        public MenuViewHolder(View item) {
            super(item);
            nameView = (TextView)item.findViewById(R.id.menu_name);
            countView = (TextView)item.findViewById(R.id.menu_count);
            priceView = (TextView)item.findViewById(R.id.menu_price);
            totalPriceView = (TextView)item.findViewById(R.id.menu_all_price);
            item.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                }
            });
        }
    }
}
