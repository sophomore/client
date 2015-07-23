package org.jaram.ds.admin.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

/**
 * Created by cheonyujung on 15. 7. 23..
 */
public class Order_viewFrag {
    View view;
    public Order_viewFrag(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, true);
        LinearLayout linearLayout  = (LinearLayout) view.findViewById(R.id.orderview);
        //setContentView(R.layout.activity_ordermanager);
        ArrayList<String> orderlist = new ArrayList<String>();
        orderlist.add("날짜/ 메뉴들");

        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, simple_list_item_1, orderlist);

        ListView list = new (ListView)view.findViewById(R.id.orderlist);
        list.setAdapter(Adapter);

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //listview 클릭 시
            }
        };


        return view;
    }

}

