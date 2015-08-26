package org.jaram.ds.admin.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.jaram.ds.R;
import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Menu;

import java.util.ArrayList;

/**
 * Created by KimMyoungSoo on 2015. 7. 15..
 */
public class MenuListFrag extends Fragment {

    ArrayList<Menu> menuList;
    MenuListAdapter adapter;
    public MenuListFrag() {

        menuList = Data.menuList;
    }
    public void setAdapter(MenuListAdapter adapter){
        this.adapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,  final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu_list,container,false);
        final String[] categorys = {"category 1","category 2","category 3","category 4","category 5"};

        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                final Spinner categorySpinner;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                final View dialogView = inflater.inflate(R.layout.fragment_menu_dialog, container,false);
                categorySpinner = (Spinner) dialogView.findViewById(R.id.categorySpinner);

                ArrayAdapter<String> spiAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, categorys);
                spiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                categorySpinner.setAdapter(spiAdapter);

                builder.setTitle("메뉴 수정");
                builder.setView(dialogView);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText nameText = (EditText) dialogView.findViewById(R.id.menuModify);
                        EditText priceText = (EditText) dialogView.findViewById(R.id.priceModify);
                        final String menu = String.valueOf(nameText.getText());
                        int price = Integer.parseInt(String.valueOf(priceText.getText()));

                        menuList.get(position)._id = position;
                        menuList.get(position).name = menu;
                        menuList.get(position).price = price;
                        menuList.get(position).category.name = String.valueOf(categorySpinner.getSelectedItem());;

                        adapter.notifyDataSetChanged();

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("삭제 하시겠습니까?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "deleted", Toast.LENGTH_LONG).show();
                        menuList.remove(position);

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
                return false;
            }
        });

        return view;

    }

}
