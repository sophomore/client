package org.jaram.ds.order;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jaram.ds.R;

import java.util.List;

/**
 * Created by vmffkxlgnqh1 on 15. 7. 12..
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<Order_object> mOrderList;

    public OrderAdapter(List<Order_object> list){
        mOrderList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_orderitem_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.menuName.setText(mOrderList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView menuName;

        public ViewHolder(View itemView){

            super(itemView);

            menuName = (TextView)itemView.findViewById(R.id.menuname);

        }

    }
}
