package org.jaram.ds.admin.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.jaram.ds.R;
import org.jaram.ds.data.struct.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


/**
 * Created by cheonyujung on 15. 7. 23..
 */
public class OrderlistAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context mcontext = null;
    private ArrayList<Order> data;
    private int layout;

    public OrderlistAdapter(Context mcontext, int layout ,ArrayList<Order> data){
        super();
        this.data = data;
        this.inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;

    }

    @Override
    public int getCount(){return data.size();}

    public String getDate(int position){
        return data.get(position).date.getYear()+"-"+data.get(position).date.getMonth()+"-"+data.get(position).date.getDay()
                +" "+data.get(position).date.getHours()+":"+data.get(position).date.getMinutes();
    }

    @Override
    public HashMap<String, Integer> getItem(int position){
        HashMap<String, Integer> menus = new HashMap<String, Integer>();
        for(int i = 0; i <data.get(position).menuList.size();i++){
            String temp = data.get(position).menuList.get(i).menu.name;
            if(menus.keySet().contains(data.get(position).menuList.get(i).menu)){
                int plus = menus.get(temp) + 1;
                menus.put(temp, plus);
            }else{
                menus.put(temp, 1);
            }
        }
        return menus;
    }
    public int getTotal(int position){ return data.get(position).totalPrice;}
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
        d.setText(getDate(position));
        Set menu1 = getItem(position).keySet();
        String menu2 = "";
        for(Object i : menu1){
            menu2 = menu2 + i+" "+getItem(position).get(i)+"개, ";
        }
        menu2 = menu2.substring(0, menu2.length()-2);
        o.setText(menu2);
        ol.setText(String.format("%d",getTotal(position)));

        return convertView;
    }
}

