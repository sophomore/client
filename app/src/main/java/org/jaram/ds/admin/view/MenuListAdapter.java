package org.jaram.ds.admin.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.jaram.ds.R;
import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Menu;

import java.util.ArrayList;

/**
 * Created by KimMyoungSoo on 2015. 8. 18..
 */
public class MenuListAdapter extends BaseAdapter{

    ArrayList<Menu> menuList;
    LayoutInflater inflater;

    MenuListAdapter(Context context){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        menuList = Data.menuList;

    }


    @Override
    public int getCount() {
        return menuList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.menu_management_list_item,parent,false);
        }
        Menu menu = (Menu)getItem(position);
        TextView nameText = (TextView) convertView.findViewById(R.id.menu_name_text);
        TextView priceText = (TextView) convertView.findViewById(R.id.menu_price_text);
        TextView categoryText = (TextView) convertView.findViewById(R.id.menu_category_text);

        nameText.setText(menu.name);
        priceText.setText(String.valueOf(menu.price));
        categoryText.setText(menu.category.name);

        return convertView;
    }
}
