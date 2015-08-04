package org.jaram.ds.admin.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jaram.ds.R;

/**
 * Created by KimMyoungSoo on 2015. 7. 15..
 */
public class MenuFrag extends Fragment{
    public MenuFrag (){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu_management,container,true);

        return view;
    }
}
