package org.jaram.ds.admin.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.jaram.ds.R;
import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Category;
import org.jaram.ds.data.struct.Menu;

import java.util.ArrayList;

/**
 * Created by KimMyoungSoo on 2015. 7. 15..
 */
public class MenuFrag extends Fragment {
    ArrayList<Menu> menuList;
    MenuListAdapter adapter;
    public MenuFrag() {
//        menuList = Data.menuList;
    }
    public void setAdapter(MenuListAdapter adapter){
        this.adapter = adapter;
    }
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu_management,container,false);
        View liview = inflater.inflate(R.layout.fragment_menu_list,container,false);
        final View addView = inflater.inflate(R.layout.fragment_menu_add,container,false);

        final String[] categorys = {"category 1","category 2","category 3","category 4","category 5"};

        final ListView listview = (ListView)liview.findViewById(R.id.listView);
        listview.setAdapter(adapter);


        Button addButton = (Button) view.findViewById(R.id.addmenu);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Spinner categorySpinner;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final String[] categorys = {"category 1","category 2","category 3","category 4","category 5"};

                final View addView = inflater.inflate(R.layout.fragment_menu_add, container,false);
                categorySpinner = (Spinner) addView.findViewById(R.id.cateSpinner);

                final ArrayAdapter<String> spiAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, categorys);
                spiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                categorySpinner.setAdapter(spiAdapter);

                builder.setTitle("메뉴 추가");
                builder.setView(addView);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText menuText = (EditText) addView.findViewById(R.id.menuAdd);
                        EditText priceText = (EditText) addView.findViewById(R.id.priceAdd);

                        final String menu = String.valueOf(menuText.getText());
                        int price = Integer.parseInt(String.valueOf(priceText.getText()));
//                        final ArrayList<Category> category = Data.categoryList;
//                        final Menu temp = new Menu();
//
//                        temp.id = menuList.size();
//                        temp.price = price;
//                        temp.name = menu;
////                        temp.category = category.get(categorySpinner.getSelectedItemPosition());
//                        menuList.add(temp);


                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();

            }
        });

        return view;
    }
}
