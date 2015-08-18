package org.jaram.ds.admin.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.jaram.ds.R;

/**
 * Created by KimMyoungSoo on 2015. 7. 15..
 */
public class MenuListFrag extends Fragment{
    public MenuListFrag() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu_list,container,false);
        MenuListAdapter adapter = new MenuListAdapter(this.getActivity());
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        return view;

    }
}
