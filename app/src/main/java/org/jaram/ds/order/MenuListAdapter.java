package org.jaram.ds.order;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.jaram.ds.R;
import org.jaram.ds.data.struct.Menu;
import org.jaram.ds.data.struct.OrderMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by kjydiary on 15. 7. 10..
 */
public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MenuViewHolder> {

    private SparseBooleanArray selectedItems;
    int curry = 0;
    int doublei = 0;
    private ArrayList<OrderMenu> orderMenus;
    private ArrayList<Menu> menuList;
    private HashMap<Menu, Integer> menuCount;

    public MenuListAdapter(ArrayList<OrderMenu> orderMenus) {
        if (orderMenus == null) {
            throw new IllegalArgumentException("list null");
        }
        this.orderMenus = orderMenus;

    }

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        } else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items =
                new ArrayList<Integer>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }


    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.menulist_item, parent, false);
        return new MenuViewHolder(item);
    }

    @Override
    public void onBindViewHolder( final MenuViewHolder holder, int position) {
        holder.nameView.setText(orderMenus.get(position).menu.name);
        holder.priceView.setText(orderMenus.get(position).menu.price+"");
        holder.curryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (curry == 0) {
                    holder.priceView.setText((Integer.parseInt((String) holder.priceView.getText()) + 1000)+"");
                    curry = 1;
                } else {
                    holder.priceView.setText((Integer.parseInt((String) holder.priceView.getText()) - 1000)+"");
                    curry = 0;
                }
            }
        });
        holder.doubleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(doublei == 0){
                    holder.priceView.setText((Integer.parseInt((String)holder.priceView.getText())+500)+"");
                    doublei = 1;
                }
                else{
                    holder.priceView.setText((Integer.parseInt((String)holder.priceView.getText())-500)+"");
                    doublei = 0;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderMenus.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        public TextView nameView, priceView;
        Button curryBtn, doubleBtn;
        public MenuViewHolder(View item) {
            super(item);
            curryBtn = (Button)item.findViewById(R.id.Curry);
            doubleBtn = (Button)item.findViewById(R.id.Double);
            nameView = (TextView)item.findViewById(R.id.menu_name);
            priceView = (TextView)item.findViewById(R.id.menu_price);
            item.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //클릭시 동작
                }
            });
        }
    }
}
