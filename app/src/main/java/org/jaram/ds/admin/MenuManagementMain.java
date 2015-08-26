package org.jaram.ds.admin;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import org.jaram.ds.R;
import org.jaram.ds.admin.view.MenuFrag;
import org.jaram.ds.admin.view.MenuListAdapter;
import org.jaram.ds.admin.view.MenuListFrag;

/**
 * Created by KimMyoungSoo on 2015. 7. 15..
 */

//메뉴추가 취소 다이얼로그 작성//
public class MenuManagementMain extends ActionBarActivity {

    MenuListFrag menuListFrag;
    MenuFrag menuFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null) {
            setContentView(R.layout.activity_menu_management);
            MenuListAdapter adapter = new MenuListAdapter(this);
            menuListFrag = new MenuListFrag();
            menuListFrag.setAdapter(adapter);
            menuFrag = new MenuFrag();
            menuFrag.setAdapter(adapter);
            getSupportFragmentManager().beginTransaction().add(R.id.listContainer, menuListFrag).commit();
            getSupportFragmentManager().beginTransaction().add(R.id.management,menuFrag).commit();
        }

    }


}
