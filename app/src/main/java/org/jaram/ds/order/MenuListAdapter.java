package org.jaram.ds.order;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jaram.ds.R;
import org.jaram.ds.data.struct.OrderMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by kjydiary on 15. 7. 10..
 */
public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MenuViewHolder> {

    private HashMap<OrderMenu, Integer> menus;
    private ArrayList<OrderMenu> orderMenus;
    public MenuListAdapter(HashMap<OrderMenu, Integer> menus) {
        if (menus == null) {
            throw new IllegalArgumentException("list null");
        }
        this.menus = menus;
        orderMenus = new ArrayList<OrderMenu>();
        for (OrderMenu menu : menus.keySet()) {
            orderMenus.add(menu);
        }

        Log.d("MenuListAdapter", "adapter");
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.menulist_item, parent, false);
        Log.d("MenuListAdapter", "onCreateViewHolder");
        return new MenuViewHolder(item);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        holder.nameView.setText(orderMenus.get(position).menu.name);
        holder.priceView.setText(orderMenus.get(position).menu.price+"원");
        holder.countView.setText(menus.get(orderMenus.get(position))+"개");
        holder.totalPriceView.setText("총 "+orderMenus.get(position).menu.price * menus.get(orderMenus.get(position))+"원");
        Log.d("MenuListAdapter", "onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return menus.size();
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
            Log.d("MenuListAdapter", "ViewHolder");
        }
    }
}
