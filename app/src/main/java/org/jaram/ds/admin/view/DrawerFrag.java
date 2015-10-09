package org.jaram.ds.admin.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import org.jaram.ds.R;
import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by ka123ak on 2015-07-09.
 */

public class DrawerFrag extends Fragment {

    String unit[] = {"시간","일","요일","월","분기","년"};
    View view;
    OnAnalysisListener onAnalysisListener;
    boolean checkedType = true;
    int unitType = 0;
    Switch aSwitch;
    ArrayList<Integer> menuIds = new ArrayList<>();
    ArrayList<String> selectedMenu = new ArrayList<String>();
    ArrayList<Integer> selectedMenuId = new ArrayList<>();
    SharedPreferences pref;
    public static interface OnAnalysisListener{
        void createLineChart(boolean analysisType, ArrayList<String> menuList, ArrayList<Integer> menuIds,int unitType);
        void createBarChart();
    }
    public void getMenuIds(){
        Iterator<Integer> iterator= Data.menuList.keySet().iterator();
        while(iterator.hasNext()){
            menuIds.add(iterator.next());
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_drawer, container, false);
        aSwitch = (Switch) view.findViewById(R.id.statistic_switch);
        aSwitch.setTextOff("꺽은선");
        aSwitch.setTextOn("막대");

        //단위 1.시간 2.일 3.요일 4.월 5.분기 6.년
        final ExpandableHeightGridView gridView = (ExpandableHeightGridView) view.findViewById(R.id.gridView);
        gridView.setExpanded(true);
        SetGridView(gridView, view);
        final ScrollView scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        getMenuIds();
        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.analysis_type_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.countbtn || checkedId != R.id.salesbtn) {
                    checkedType = false;
                } else if (checkedId == R.id.salesbtn || checkedId != R.id.countbtn) {
                    checkedType = true;

                }
            }
        });

        Button analysisbtn = (Button) view.findViewById(R.id.analysis);
        analysisbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aSwitch.isChecked()) {
                    onAnalysisListener.createBarChart();
                } else{
                    Data.reDataForStatistic();
                    onAnalysisListener.createLineChart(checkedType,selectedMenu, selectedMenuId, unitType);
                }
            }
        });

        final String[] items = getMenuList();
        final boolean mbIsSelect[] = new boolean[items.length];
        final ExpandableHeightGridView gridView1 = (ExpandableHeightGridView)view.findViewById(R.id.gridView2);
        gridView1.setAdapter(new mSelectedMenuListAdapter(setSelectedMenuList(mbIsSelect, items), getActivity()));
        final Button selectMenubtn = (Button) view.findViewById(R.id.select_menu);
        selectMenubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View checkMenuView = (View) inflater.inflate(R.layout.statistic_checkbox, null);

                new AlertDialog.Builder(getActivity())
                        .setTitle("Select Colors")
                        .setView(checkMenuView)
                        .setMultiChoiceItems(items, mbIsSelect, new DialogInterface.OnMultiChoiceClickListener() {
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                mbIsSelect[which] = isChecked;
                                if(isChecked) {
                                    selectedMenuId.add(menuIds.get(which));
                                }else{
                                    selectedMenuId.remove(menuIds.get(which));
                                }
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                gridView1.setExpanded(true);
                                mSelectedMenuListAdapter adapter = (mSelectedMenuListAdapter) gridView1.getAdapter();
                                adapter.menuList.clear();
                                adapter.notifyDataSetChanged();
                                SetSelectedMenuAdapter(gridView1, view, setSelectedMenuList(mbIsSelect, items), mbIsSelect);

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
        final View view1 = view.findViewById(R.id.statistic_div);
        final Button selectedCancelbtn = (Button) view.findViewById(R.id.cancel_selected_menu);
        selectedCancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedMenuListAdapter adapter = (mSelectedMenuListAdapter) gridView1.getAdapter();
                adapter.menuList.clear();
                adapter.notifyDataSetChanged();
                for (int i = 0; i < mbIsSelect.length; i++) {
                    mbIsSelect[i] = false;
                    selectedMenuId.remove(i);
                }
            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    scrollView.setVisibility(View.INVISIBLE);

                } else {
                    scrollView.setVisibility(View.VISIBLE);
                    gridView.setBackgroundColor(Color.parseColor("#F5F6F7"));
                    scrollView.setBackgroundColor(Color.parseColor("#F5F6F7"));
                    gridView1.setBackgroundColor(Color.parseColor("#F5F6F7"));
                    radioGroup.setBackgroundColor(Color.parseColor("#F5F6F7"));
                    selectMenubtn.setBackgroundColor(Color.parseColor("#FF7F66"));
                    selectedCancelbtn.setBackgroundColor(Color.parseColor("#FF7F66"));
                    selectMenubtn.setTextColor(Color.parseColor("#FFFFFF"));
                    selectedCancelbtn.setTextColor(Color.parseColor("#FFFFFF"));
                    view1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    SimpleAdapter simpleAdapter = (SimpleAdapter) gridView.getAdapter();
                    simpleAdapter.setCheck(false);
                    simpleAdapter.notifyDataSetChanged();
                    radioGroup.getChildAt(0).setClickable(true);
                    radioGroup.getChildAt(1).setClickable(true);

                }
            }
        });
        aSwitch.setChecked(true);
        return view;
    }

    public boolean getChecked(){
        return aSwitch.isChecked();
    }

    private ArrayList<String> setSelectedMenuList(boolean[] mbisSelect, String[] menuList){

        for(int i=0;i<mbisSelect.length;i++){
            if(mbisSelect[i] == true){
                selectedMenu.add(menuList[i]);

            }
        }
        return selectedMenu;
    }
    private String[] getMenuList() {
        HashMap<Integer, Menu> list = Data.menuList;
        String[] menuList = new String[list.size()];
        int i=0;
        for(Menu menu : list.values()) {
            menuList[i] = menu.name;
            i++;
        }
        return menuList;
    }


    private void SetGridView(ExpandableHeightGridView gridView , View view){
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity());
        gridView.setAdapter(simpleAdapter);
    }


    private void SetSelectedMenuAdapter(ExpandableHeightGridView gridView, View view, ArrayList<String> menuList, final boolean[] mbIsSelect){
        final mSelectedMenuListAdapter adapter = new mSelectedMenuListAdapter(menuList,getActivity());
        gridView.setAdapter(adapter);

    }
    class mSelectedMenuListAdapter extends BaseAdapter{

        private Context mContext;
        ArrayList<String> menuList;
        boolean check = true;
        mSelectedMenuListAdapter(ArrayList<String> menuList,Context c){
            this.mContext = c;
            this.menuList = menuList;

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
        public View getView(final int position, View convertView, ViewGroup parent) {
            TextView textView;
            if(convertView ==null){
                textView = new TextView(getActivity());
                textView.setText(menuList.get(position));
                textView.setTextSize(20);
            } else{
                textView =  (TextView)convertView;
            }
            return textView;
        }
    }
    class SimpleAdapter extends BaseAdapter {

        private Context mContext;
        private int mSelectedPosition = -1;
        private RadioButton mSelectRadiobtn = null;
        boolean check = true;

        public void setCheck(boolean check) {
            this.check = check;
        }

        SimpleAdapter(Context c){
            mContext = c;
        }
        @Override
        public int getCount() {
            return unit.length;
        }

        @Override
        public Object getItem(int position) {
            return unit[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView = null;

            RadioButton radioButton = null;
            if(gridView == null) {
                gridView = (View) inflater.inflate(R.layout.fragment_radiobutton, null);

                radioButton = (RadioButton) gridView.findViewById(R.id.radio);
                radioButton.setText(unit[position]);
            } else {
                gridView = convertView;
            }
            if(check){
                radioButton.setClickable(false);
            } else{
                radioButton.setClickable(true);
                radioButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if ((position != mSelectedPosition && mSelectRadiobtn != null)) {
                            mSelectRadiobtn.setChecked(false);
                        }
                        unitType = position;
                        mSelectedPosition = position;
                        mSelectRadiobtn = (RadioButton) v;
                    }
                });

                if (mSelectedPosition != position) {
                    radioButton.setChecked(false);
                } else {
                    radioButton.setChecked(true);
                    if (mSelectRadiobtn != null && radioButton != mSelectRadiobtn) {
                        mSelectRadiobtn = radioButton;
                    }
                }
            }



            return gridView;
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onAnalysisListener = (OnAnalysisListener) getActivity();
        }catch (Exception e){
            throw new ClassCastException(activity.toString() + " must implement OnAnalysisListener");
        }

    }
}
