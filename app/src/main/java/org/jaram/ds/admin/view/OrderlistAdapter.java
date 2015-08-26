package org.jaram.ds.admin.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.jaram.ds.R;
import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Order;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;


/**
 * Created by cheonyujung on 15. 7. 23..
 */
public class OrderlistAdapter extends BaseAdapter implements View.OnClickListener {
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
        String day[] = {"일", "월", "화", "수", "목", "금", "토"};
        Calendar c = Calendar.getInstance();
        c.setTime(data.get(position).date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        String curDate = year+"-"+month+"-"+date+" "+day[dayOfWeek-1]+" "+hour+":"+minute+":"+second;
        return curDate;
    }

    @Override
    public HashMap<String, Integer> getItem(int position){
        HashMap<String, Integer> menus = new HashMap<String, Integer>();
        for(int i = 0; i <data.get(position).menuList.size();i++){
            String temp = data.get(position).menuList.get(i).menu.name;
            if(menus.keySet().contains(data.get(position).menuList.get(i).menu.name)){
                Log.d("Ok", "~~~~~~~~");
                int plus = menus.get(temp) + 1;
                menus.put(temp, plus);
                Log.d("num", plus+"@");
            }else{
                menus.put(temp, 1);
            }
        }
        return menus;
    }
    public int getTotal(int position){ return data.get(position).totalPrice;}
    @Override
    public long getItemId(int position){return position;}


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
            Log.d("get", getItem(position).get(i)+"");
        }
        menu2 = menu2.substring(0, menu2.length()-2);
        o.setText(menu2);
        ol.setText(String.format("%d", getTotal(position)));
        convertView.setTag(position);
        convertView.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer)v.getTag();
        Toast.makeText(v.getContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
        String text = getDate(position)+"\n\n";
        for(int i=0; i<Data.orderList.get(position).menuList.size();i++) {
            text = text + Data.orderList.get(position).menuList.get(i).menu.name +"\t"+
                    Data.orderList.get(position).menuList.get(i).pay+"\t"+Data.orderList.get(position).menuList.get(i).menu.price+"\n";
        }
        text = text + "\n총 금액 : "+getTotal(position)+"\n";
        new AlertDialog.Builder(v.getContext()).setTitle("주문 정보")
                .setMessage(text)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();

        }
    }

