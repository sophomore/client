package org.jaram.ds.admin.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.jaram.ds.R;
import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Menu;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ka123ak on 2015-07-09.
 */

public class DrawerFrag extends Fragment {

    String unit[] = {"시간","일","요일","월","분기","년"};
    View view;
    OnAnalysisListener onAnalysisListener;
    boolean checkedType = true;
    int unitType = 0;
    ArrayList<String> selectedMenu = new ArrayList<String>();

    public static interface OnAnalysisListener{
        void createLineChart(boolean analysisType, ArrayList<String> menuList, int unitType);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_drawer, container, false);
        final ExpandableHeightGridView gridView = (ExpandableHeightGridView) view.findViewById(R.id.gridView);
        gridView.setExpanded(true);
        SetGridView(gridView, view);


        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.analysis_type_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.countbtn || checkedId != R.id.salesbtn){
                    checkedType = false;
                } else if(checkedId  == R.id.salesbtn || checkedId != R.id.countbtn){
                    checkedType = true;
                }
            }
        });

        Button analysisbtn = (Button) view.findViewById(R.id.analysis);
        analysisbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onAnalysisListener.createLineChart(checkedType,selectedMenu,unitType);


            }
        });
        final String[] items = getMenuList();
        final boolean mbIsSelect[] = new boolean[items.length];
        final ExpandableHeightGridView gridView1 = (ExpandableHeightGridView)view.findViewById(R.id.gridView2);
        gridView1.setAdapter(new mSelectedMenuListAdapter(setSelectedMenuList(mbIsSelect,items),getActivity()));

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
        Button selectedCancelbtn = (Button) view.findViewById(R.id.cancel_selected_menu);
        selectedCancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedMenuListAdapter adapter =(mSelectedMenuListAdapter) gridView1.getAdapter();
                adapter.menuList.clear();
                adapter.notifyDataSetChanged();
                for(int i=0;i<mbIsSelect.length;i++){
                    mbIsSelect[i]=false;
                }
            }
        });

        return view;
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
                textView.setTextSize(11);
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

                //set value into Button

                radioButton = (RadioButton) gridView.findViewById(R.id.radio);
                radioButton.setText(unit[position]);
            } else {
                gridView = convertView;
            }
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


            return gridView;
        }
    }
    private class Holder{
        RadioButton radioButton;
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
