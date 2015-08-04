package org.jaram.ds.admin.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.jaram.ds.R;
import org.jaram.ds.data.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;


/**
 * Created by cheonyujung on 15. 7. 23..
 */
public class OrderlistAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Data> data;
    private int layout;
    public OrderlistAdapter(Context context, int layout, ArrayList<Data> data){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout=layout;
    }

    @Override
    public int getCount(){return data.size();}
    public Date getDate(int position){
        return Data.orderList.get(position).date;
    }
    @Override
    public HashMap<String, Integer> getItem(int position){
        HashMap<String, Integer> menus = new HashMap<String, Integer>();
        for(int i = 0; i <Data.orderList.get(position).menuList.size();i++){
            String temp = Data.orderList.get(position).menuList.get(i).menu.name;
            if(menus.keySet().contains(Data.orderList.get(position).menuList.get(i).menu)){
                int plus = menus.get(temp) + 1;
                menus.put(temp, plus);
            }else{
                menus.put(temp, 1);
            }
        }
        return menus;
    }
    public int getTotal(int position){
        return Data.orderList.get(position).totalPrice;
    }
    @Override
    public long getItemId(int position){return position;}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }
        TextView d = (TextView) convertView.findViewById(R.id.date);
        TextView o = (TextView) convertView.findViewById(R.id.order);
        TextView ol = (TextView) convertView.findViewById(R.id.orderprice);
        d.setText((CharSequence) getDate(position));
        Set menu1 = getItem(position).keySet();
        for(Object i : menu1){
            o.setText(i+" "+getItem(position).get(i));
        }
        /*Listviewitem listviewitem=data.get(position);
        name.setText(listviewitem.getName());*/
        return convertView;
    }
}
