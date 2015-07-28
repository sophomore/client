package org.jaram.ds.admin.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.jaram.ds.R;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

/**
 * Created by cheonyujung on 15. 7. 23..
 */
public class Order_viewFrag extends Fragment {
    View view;
    public Order_viewFrag(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        LinearLayout linearLayout  = (LinearLayout) view.findViewById(R.id.orderview);
        ArrayList<String> orderlist = new ArrayList<String>();
        orderlist.add("날짜/ 메뉴들");



        ListView list = (ListView)view.findViewById(R.id.orderlist);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(view.getContext(), simple_list_item_1, orderlist);
        //list.setAdapter(Adapter);

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //listview 클릭 시
            }
        };


        return view;
    }

}

