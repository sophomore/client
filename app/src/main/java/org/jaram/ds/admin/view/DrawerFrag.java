package org.jaram.ds.admin.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.jaram.ds.R;
import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Menu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ka123ak on 2015-07-09.
 */
public class DrawerFrag extends Fragment {

    String unit[] = {"시간","일","요일","월","분기","년"};
    TextView byear,bmonth,bday, lyear,lmonth,lday;
    int year, month,day;
    View view;
    int chartKind = 0;
    OnAnalysisListener onAnalysisListener;
    public interface OnAnalysisListener{
        public void createChart(String start,String diffDays);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_drawer, container, false);
        final ExpandableHeightGridView gridView = (ExpandableHeightGridView) view.findViewById(R.id.gridView);
        gridView.setExpanded(true);
        SetGridView(gridView, view);

        Button beforeBtn = (Button) view.findViewById(R.id.beforeBtn);
        final int year,month,day;
        final GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        byear = (TextView)view.findViewById(R.id.ByearText);
        bmonth = (TextView)view.findViewById(R.id.BmonthText);
        bday = (TextView) view.findViewById(R.id.BdayText);

        lyear = (TextView)view.findViewById(R.id.LyearText);
        lmonth = (TextView)view.findViewById(R.id.LmonthText);
        lday = (TextView) view.findViewById(R.id.LdayText);

        final Button countbtn = (Button) view.findViewById(R.id.countbtn);
        final Button salesbtn = (Button) view.findViewById(R.id.salesbtn);
        countbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chartKind = 0;
            }
        });

        beforeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment beforeDialog = new DatePickerFrag(byear,bmonth,bday);
                beforeDialog.show(getActivity().getFragmentManager(), "datePicker");

            }
        });
        Button laterbtn = (Button)view.findViewById(R.id.laterBtn);
        laterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment beforeDialog = new DatePickerFrag(lyear,lmonth,lday);
                beforeDialog.show(getActivity().getFragmentManager(), "datePicker");

            }
        });
        //TODO: 데이터들을 가져와서 통계자료로 변환하는 작업을 해야됨
        Button analysisbtn = (Button) view.findViewById(R.id.analysis);
        analysisbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (byear.getText() == "" || lyear.getText() == "") {
                    Toast.makeText(getActivity(), "기간을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    String startDate = byear.getText() + "/" + (Integer.parseInt((String) bmonth.getText()) - 1) + "/" + bday.getText();
                    String finishDate = lyear.getText() + "/" + (Integer.parseInt((String) lmonth.getText()) - 1) + "/" + lday.getText();

                    long diffDays = 0;
                    try {
                        diffDays = lengthOfAnalaysis(startDate, finishDate);
                        Log.d("diff", diffDays + "");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (chartKind == 0) {

                        onAnalysisListener.createChart(startDate, diffDays + "");

                    }
                }

            }
        });
        Button resetbtn = (Button) view.findViewById(R.id.reset);
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byear.setText("");
                bmonth.setText("");
                bday.setText("");
                lyear.setText("");
                lmonth.setText("");
                lday.setText("");
            }
        });
        final String[] items = getMenuList();
        final boolean mbIsSelect[] = new boolean[items.length];
        final ExpandableHeightGridView gridView1 = (ExpandableHeightGridView)view.findViewById(R.id.gridView2);

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
                                SetSelectedMenuAdapter(gridView1, view, setSelectedMenuList(mbIsSelect, items),mbIsSelect);
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
        ArrayList<String> selectedMenu = new ArrayList<String>();
        for(int i=0;i<mbisSelect.length;i++){
            if(mbisSelect[i] == true){
                selectedMenu.add(menuList[i]);

            }
        }
        return selectedMenu;
    }
    private String[] getMenuList() {
        ArrayList<Menu>list = Data.menuList;
        String[] menuList = new String[list.size()];
        int i=0;
        for(Menu menu : list) {
            menuList[i] = menu.name;
            i++;
        }
        return menuList;
    }
    private Date getStartDate(String startDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return formatter.parse(startDate);
    }
    private Date getFinishDate(String finishDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return formatter.parse(finishDate);
    }
    private void SetGridView(ExpandableHeightGridView gridView , View view){
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity());
        gridView.setAdapter(simpleAdapter);
    }
    public long lengthOfAnalaysis(String startDate, String finishDate) throws ParseException {

        Date start = getStartDate(startDate);
        Date finish = getFinishDate(finishDate);
        long diff = finish.getTime() - start.getTime();
        long diffDays = diff/(24*60*60*1000);


        return diffDays;
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
