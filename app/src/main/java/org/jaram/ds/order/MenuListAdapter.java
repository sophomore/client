package org.jaram.ds.order;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.jaram.ds.R;
import org.jaram.ds.data.struct.Order;

import javax.security.auth.callback.Callback;

/**
 * Created by kjydiary on 15. 7. 10..
 */

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MenuViewHolder> implements ItemTouchHelperAdapter {
    private Order orderMenus;
    private Order selectedMenus;
    private TextView totalPrice;

    public MenuListAdapter(Order orderMenus, TextView totalPrice) {
        if (orderMenus == null) {
            throw new IllegalArgumentException("list null");
        }
        this.orderMenus = orderMenus;
        this.selectedMenus = new Order();
        this.totalPrice = totalPrice;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.menulist_item, parent, false);
        return new MenuViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final MenuViewHolder holder, int position) {
        final int pos = position;
        holder.nameView.setText(orderMenus.menuList.get(position).menu.name);
        holder.priceView.setText(orderMenus.menuList.get(position).totalprice + "");
        if (selectedMenus.menuList.contains(orderMenus.menuList.get(position))) {
            holder.menu.setBackgroundColor(Color.parseColor("#2185c5"));
            holder.curryBtn.setTextColor(Color.parseColor("#ffffff"));
            holder.doubleBtn.setTextColor(Color.parseColor("#ffffff"));
            holder.nameView.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.menu.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.curryBtn.setTextColor(Color.parseColor("#2185C5"));
            holder.doubleBtn.setTextColor(Color.parseColor("#2185C5"));
            holder.nameView.setTextColor(Color.parseColor("#3E454C"));
        }
        if (orderMenus.menuList.get(position).getComplete()){
            holder.menu.setBackgroundColor(Color.parseColor("#A6A6A6"));
            holder.curryBtn.setTextColor(Color.parseColor("#ffffff"));
            holder.doubleBtn.setTextColor(Color.parseColor("#ffffff"));
            holder.nameView.setTextColor(Color.parseColor("#4c4c4c"));
        }

        holder.curryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!orderMenus.menuList.get(pos).curry) {
                    holder.priceView.setText(orderMenus.menuList.get(pos).setCurry() + "");
                    totalPrice.setText(orderMenus.getTotalPrice() + "");
                } else {
                    holder.priceView.setText(orderMenus.menuList.get(pos).resetCurry() + "");
                    totalPrice.setText(orderMenus.getTotalPrice() + "");
                }
            }
        });
        holder.doubleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!orderMenus.menuList.get(pos).doublei) {
                    holder.priceView.setText(orderMenus.menuList.get(pos).setDoublei() + "");
                    totalPrice.setText(orderMenus.getTotalPrice() + "");
                } else {
                    holder.priceView.setText(orderMenus.menuList.get(pos).resetDoublei() + "");
                    totalPrice.setText(orderMenus.getTotalPrice() + "");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderMenus.menuList.size();
    }



    @Override
    public void onItemDismiss(int position, TextView textView) {
        orderMenus.menuList.remove(position);
        notifyDataSetChanged();
        textView.setText(orderMenus.getTotalPrice() + "");
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        public TextView nameView, priceView;
        Button curryBtn, doubleBtn;
        View menu;

        public MenuViewHolder(final View item) {
            super(item);
            menu = item;
            curryBtn = (Button) item.findViewById(R.id.Curry);
            doubleBtn = (Button) item.findViewById(R.id.Double);
            nameView = (TextView) item.findViewById(R.id.menu_name);
            priceView = (TextView) item.findViewById(R.id.menu_price);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedMenus.menuList.contains(orderMenus.menuList.get(getLayoutPosition()))) {
                        selectedMenus.menuList.remove(orderMenus.menuList.get(getLayoutPosition()));
                    } else {
                        selectedMenus.menuList.add(orderMenus.menuList.get(getLayoutPosition()));
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }
    public void setpay(int pay){
        for (int i=0; i<selectedMenus.menuList.size();i++){
            selectedMenus.menuList.get(i).setPay(pay);
            selectedMenus.menuList.get(i).setComplete();
        }
    }

}
