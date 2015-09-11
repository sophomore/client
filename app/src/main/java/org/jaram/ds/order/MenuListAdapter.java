package org.jaram.ds.order;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jaram.ds.R;
import org.jaram.ds.data.struct.Menu;
import org.jaram.ds.data.struct.OrderMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kjydiary on 15. 7. 10..
 */
interface ItemTouchHelperAdapter{
    void onItemDismiss(int position,TextView textView);
}
public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MenuViewHolder> implements ItemTouchHelperAdapter{
    private ArrayList<OrderMenu> curry;
    private ArrayList<OrderMenu> doublei;
    private ArrayList<OrderMenu> orderMenus;
    private ArrayList<Menu> menuList;
    private HashMap<Menu, Integer> menuCount;
    private ArrayList<OrderMenu> selectedMenus;

    public MenuListAdapter(ArrayList<OrderMenu> orderMenus) {
        if (orderMenus == null) {
            throw new IllegalArgumentException("list null");
        }
        this.orderMenus = orderMenus;
        this.selectedMenus = new ArrayList<>();
        this.curry = new ArrayList<>();
        this.doublei = new ArrayList<>();
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.menulist_item, parent, false);
        return new MenuViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final MenuViewHolder holder, int position) {
        final int pos = position;
        holder.nameView.setText(orderMenus.get(position).menu.name);
        holder.priceView.setText(orderMenus.get(position).menu.price+"");
        if(selectedMenus.contains(orderMenus.get(position))){
            holder.menu.setBackgroundColor(Color.parseColor("#2185c5"));
            holder.curryBtn.setTextColor(Color.parseColor("#ffffff"));
            holder.doubleBtn.setTextColor(Color.parseColor("#ffffff"));
            holder.nameView.setTextColor(Color.parseColor("#ffffff"));
        }
        else{
            holder.menu.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.curryBtn.setTextColor(Color.parseColor("#2185C5"));
            holder.doubleBtn.setTextColor(Color.parseColor("#2185C5"));
            holder.nameView.setTextColor(Color.parseColor("#3E454C"));
        }

        holder.curryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!curry.contains(orderMenus.get(pos))) {
                    holder.priceView.setText((Integer.parseInt((String) holder.priceView.getText()) + 1000) + "");
                    curry.add(orderMenus.get(pos));
                } else {
                    holder.priceView.setText((Integer.parseInt((String) holder.priceView.getText()) - 1000) + "");
                    curry.remove(orderMenus.get(pos));
                }
            }
        });
        holder.doubleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!doublei.contains(orderMenus.get(pos))){
                    holder.priceView.setText((Integer.parseInt((String) holder.priceView.getText()) + 500) + "");
                    doublei.add(orderMenus.get(pos));
                } else {
                    holder.priceView.setText((Integer.parseInt((String) holder.priceView.getText()) - 500) + "");
                    doublei.remove(orderMenus.get(pos));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderMenus.size();
    }

    @Override
    public void onItemDismiss(int position,TextView textView) {
        textView.setText((Integer.parseInt((String)textView.getText())-orderMenus.get(position).menu.price)+"");
        orderMenus.remove(position);
        notifyDataSetChanged();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        public TextView nameView, priceView;
        Button curryBtn, doubleBtn;
        View menu;
        public MenuViewHolder(final View item) {
            super(item);
            menu = item;
            curryBtn = (Button)item.findViewById(R.id.Curry);
            doubleBtn = (Button)item.findViewById(R.id.Double);
            nameView = (TextView)item.findViewById(R.id.menu_name);
            priceView = (TextView)item.findViewById(R.id.menu_price);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedMenus.contains(orderMenus.get(getLayoutPosition()))) {
                        selectedMenus.remove(orderMenus.get(getLayoutPosition()));
                    } else {
                        selectedMenus.add(orderMenus.get(getLayoutPosition()));
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }
}
