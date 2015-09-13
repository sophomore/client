package org.jaram.ds.admin.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.jaram.ds.R;
import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Order;
import org.jaram.ds.data.struct.OrderMenu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;


/**
 * Created by cheonyujung on 15. 7. 23..
 */
public class OrderlistAdapter extends BaseAdapter implements View.OnClickListener, View.OnLongClickListener {
    private LayoutInflater inflater;
    private Context mcontext = null;
    private ArrayList<Order> data;
    private int layout;
    private View view = null;

    public OrderlistAdapter(Context mcontext, int layout, ArrayList<Order> data) {
        super();
        this.mcontext = mcontext;
        this.data = data;
        this.inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    public String getDate(int position) {
        String day[] = {"일", "월", "화", "수", "목", "금", "토"};
        Calendar c = Calendar.getInstance();
        c.setTime(data.get(position).date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        String curDate = year + "-" + month + "-" + date + " " + day[dayOfWeek - 1] + " " + hour + ":" + minute + ":" + second;
        return curDate;
    }

    @Override
    public HashMap<String, Integer> getItem(int position) {
        HashMap<String, Integer> menus = new HashMap<String, Integer>();
        for (int i = 0; i < data.get(position).menuList.size(); i++) {
            String temp = data.get(position).menuList.get(i).menu.name;
            Log.d("name", String.valueOf(menus.keySet().size()));
            if (menus.keySet().contains(data.get(position).menuList.get(i).menu.name)) {
                Log.d("Ok", "~~~~~~~~");
                int plus = menus.get(temp) + 1;
                menus.put(temp, plus);
                Log.d("num", plus + "@");
            } else {
                menus.put(temp, 1);
            }
        }
        return menus;
    }

    public int getTotal(int position) {
        return data.get(position).totalPrice;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        TextView d = (TextView) convertView.findViewById(R.id.date);
        TextView o = (TextView) convertView.findViewById(R.id.order);
        TextView ol = (TextView) convertView.findViewById(R.id.orderprice);
        d.setText(getDate(position));
        Set menu1 = getItem(position).keySet();
        String menu2 = "";
        for (Object i : menu1) {
            menu2 = menu2 + i + " " + getItem(position).get(i) + "개, ";
            Log.d("get", getItem(position).get(i) + "");
        }
        menu2 = menu2.substring(0, menu2.length() - 2);
        o.setText(menu2);
        ol.setText(String.format("%d", getTotal(position)));
        convertView.setTag(position);
        convertView.setOnClickListener(this);
        convertView.setOnLongClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();

        view = inflater.inflate(R.layout.orderlistmodify_container, null);

        TextView date = (TextView) view.findViewById(R.id.date_dialog);

        date.setText(getDate(position));

        GridView gridView = (GridView) view.findViewById(R.id.orderModify_list);

        OrderModifyAdapter orderModifyAdapter = new OrderModifyAdapter(Data.orderList.get(position).menuList);

        gridView.setAdapter(orderModifyAdapter);

        TextView total_price = (TextView) view.findViewById(R.id.order_price);

        total_price.setText(String.valueOf(getTotal(position)));

        AlertDialog.Builder ab = new AlertDialog.Builder(mcontext);
        ab.setTitle("상세 주문 보기");
        ab.setView(view);
        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //확인을 눌렀을 때
            }
        });
        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //취소를 눌렀을 때
            }
        });
        ab.create();
        ab.show();

    }

    @Override
    public boolean onLongClick(View v) {
        AlertDialog.Builder ab = new AlertDialog.Builder(mcontext);
        ab.setTitle("주문 삭제");
        ab.setMessage("삭제하시겠습니까?");
        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        ab.create();
        ab.show();
        return true;
    }

    class OrderModifyAdapter extends BaseAdapter {
        ArrayList<OrderMenu> orderMenu;
        public LinearLayout mspinner;
        public LinearLayout mtextview;

        OrderModifyAdapter(ArrayList<OrderMenu> orderMenu) {
            this.orderMenu = orderMenu;
        }

        @Override
        public int getCount() {
            return orderMenu.size();
        }

        @Override
        public Object getItem(int position) {
            return orderMenu.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public String getMenu(int position) {
            return orderMenu.get(position).menu.name;
        }

        public boolean checkcredit(int position){
            if(orderMenu.get(position).pay == OrderMenu.Pay.CREDIT){
                return true;
            }else{
                return false;
            }
        }

        public String koreaCredit(int position){
            if(orderMenu.get(position).pay == OrderMenu.Pay.CARD){
                return "카드";
            }else if(orderMenu.get(position).pay == OrderMenu.Pay.CASH){
                return "현금";
            }else if(orderMenu.get(position).pay == OrderMenu.Pay.SERVICE){
                return "서비스";
            }else{
                return "외상";
            }
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.orderlistmodify, null);
            }
            mspinner = (LinearLayout) convertView.findViewById(R.id.spinnerlayout);
            mtextview = (LinearLayout) convertView.findViewById(R.id.textviewlayout);
            String[] chooseitem = {"현금", "카드", "외상", "서비스"};
            TextView menuname = (TextView) convertView.findViewById(R.id.origincredit);
            menuname.setText(getMenu(position));
            if(checkcredit(position)){
                Spinner spinner = (Spinner) convertView.findViewById(R.id.changecredit);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(mcontext, R.layout.support_simple_spinner_dropdown_item, chooseitem);
                spinner.setAdapter(adapter);
                spinner.setSelection(2);
                mtextview.setVisibility(LinearLayout.INVISIBLE);
                mspinner.setVisibility(LinearLayout.VISIBLE);
            }else{
                TextView creditview = (TextView) convertView.findViewById(R.id.credit_view);
                creditview.setText(koreaCredit(position));
                mspinner.setVisibility(LinearLayout.INVISIBLE);
                mtextview.setVisibility(LinearLayout.VISIBLE);
            }
            TextView menu_price = (TextView) convertView.findViewById(R.id.menu_price);
            menu_price.setText(String.valueOf(orderMenu.get(position).menu.price));
            return convertView;
        }
    }

}



