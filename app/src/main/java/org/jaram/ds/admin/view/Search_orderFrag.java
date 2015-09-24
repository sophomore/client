package org.jaram.ds.admin.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.jaram.ds.R;
import org.jaram.ds.data.Data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Created by cheonyujung on 15. 7. 23..
 */
public class Search_orderFrag extends Fragment {
    private TextView mDateDisplay;
    private TextView mDateDisplay2;
    private TextView mDateDisplay3;
    private TextView mDateDisplay4;
    private TextView mMenuDisplay;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton;
    private Button search_start;
    private ArrayList<Integer> selectmenu = new ArrayList<Integer>();
    private int mYear, mMonth, mDay, mHour, mMinute;
    static final int DATE_DIALOG_ID = 0;
    private int mode = 0;
    private String selectedIndex;
    private OnSearchListener onSearchListener;
    private ArrayList<Integer> selectMenuId = new ArrayList<>();
    View view;
    public Search_orderFrag(){

    }

    public static interface OnSearchListener{
        void createListview(Context mcontext, String startDate, String endDate, ArrayList<Integer> menus, int pay);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search, container, false);

        mDateDisplay = (TextView) view.findViewById(R.id.edit_time1);
        mDateDisplay2 = (TextView) view.findViewById(R.id.edit_time2);
        mDateDisplay3 = (TextView) view.findViewById(R.id.edit_time3);
        mDateDisplay4 = (TextView) view.findViewById(R.id.edit_time4);
        mMenuDisplay = (TextView) view.findViewById(R.id.menublank);

        mRadioGroup = (RadioGroup) view.findViewById(R.id.radiogroup);

        mRadioButton = (RadioButton) view.findViewById(mRadioGroup.getCheckedRadioButtonId());
        search_start = (Button) view.findViewById(R.id.search_button);
        search_start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int radioButtonID = mRadioGroup.getCheckedRadioButtonId();
                View radioButton = mRadioGroup.findViewById(radioButtonID);
                int checkway = mRadioGroup.indexOfChild(radioButton)+1;

                String date1 = String.valueOf(mDateDisplay.getText());
                String date2 = String.valueOf(mDateDisplay2.getText());
                String time1 = String.valueOf(mDateDisplay3.getText());
                String time2 = String.valueOf(mDateDisplay4.getText());
                String startTime = date1+time1+":00";
                String endTime = date2+time2+":00";


                Toast.makeText(view.getContext(), String.valueOf(checkway), Toast.LENGTH_LONG).show();
                onSearchListener.createListview(view.getContext(), startTime, endTime, selectMenuId, checkway);
                //Toast.makeText(view.getContext(), time2, Toast.LENGTH_LONG).show();
            }
        });
        mMenuDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = Data.menuList.size();
                String[] menuitem = new String[size];
                final int[] menu_id=  new int[size];

                selectmenu.clear();
                Iterator<Integer> interator = Data.menuList.keySet().iterator();
                int count = 0;
                while(interator.hasNext()){
                    int menu = interator.next();
                    menuitem[count] = Data.menuList.get(menu).name;
                    menu_id[count] = Data.menuList.get(menu).id;

                    count++;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("메뉴 선택");
                builder.setMultiChoiceItems(menuitem, null, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        //체크박스 선택 시
                        if (isChecked) {
                            // if the user checked the item, add it to the selected items
                            selectmenu.add(which);
                            Log.d("menu_id", menu_id[which] + "");
                            selectMenuId.add(menu_id[which]);
                        }
                        else if (selectmenu.contains(which)) {
                            // else if the item is already in the array, remove it
                            selectmenu.remove(Integer.valueOf(which));
                            selectMenuId.remove(which); // 오류
                        }
                    }
                }).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //확인 버튼 누르고
                        selectedIndex = "";//선택된 메뉴 String
                        String selectedText = "";//edittext에 보여줄 메뉴

                        for(Integer i : selectMenuId){
//                            selectedIndex += Data.menuList.get(i).name + "!";
                            selectedText += Data.menuList.get(i).name+ ", ";
                        }

                        if(selectedText.equals("")){

                        }else {
                            selectedText = selectedText.substring(0, selectedText.length() - 2);
                            mMenuDisplay.setText(selectedText);
                        }
                        Toast.makeText(view.getContext(), selectedText, Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //취소 버튼 누르고
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        mDateDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog DatePicker = new DatePickerDialog(view.getContext(), mDateSetListener, mYear, mMonth, mDay);
                DatePicker.show();
                mode = 1;
            }
        });
        mDateDisplay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog DatePicker = new DatePickerDialog(view.getContext(), mDateSetListener, mYear, mMonth, mDay);
                DatePicker.show();
                mode = 2;
            }
        });
        mDateDisplay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog TimePicker = new TimePickerDialog(view.getContext(), mTimeSetListener, mHour, mMinute, false);
                TimePicker.show();
                mode = 3;
            }
        });
        mDateDisplay4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog TimePicker = new TimePickerDialog(view.getContext(), mTimeSetListener, mHour, mMinute, false);
                TimePicker.show();
                mode = 4;
            }
        });
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth= c.get(Calendar.MONTH);
        mDay  = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        mDateDisplay.setText(
                new StringBuilder()
                        .append(mYear).append("-")
                        .append(mMonth + 1).append("-")
                        .append(mDay).append(" ")
        );
        mDateDisplay2.setText(
                new StringBuilder()
                        .append(mYear).append("-")
                        .append(mMonth + 1).append("-")
                        .append(mDay+1).append(" ")
        );
        mDateDisplay3.setText(
                new StringBuilder()
                        .append(mHour).append(":")
                        .append(mMinute)
        );
        mDateDisplay4.setText(
                new StringBuilder()
                        .append(mHour).append(":")
                        .append(mMinute)
        );
        return view;
    }
    protected Dialog onCreateDialog(int id) {
        switch(id){
            case DATE_DIALOG_ID : return new DatePickerDialog(view.getContext(),mDateSetListener,mYear,mMonth,mDay);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth=monthOfYear;
            mDay=dayOfMonth;
            updateDisplay();
        }
    };
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mHour = hourOfDay;
            mMinute = minute;
            updateDisplay();
        }
    };
    private void updateDisplay() {
        if(mode == 1) {
            mDateDisplay.setText(
                    new StringBuilder()
                            .append(mYear).append("-")
                            .append(mMonth + 1).append("-")
                            .append(mDay).append(" ")
            );
        }else if(mode == 2){
            mDateDisplay2.setText(
                    new StringBuilder()
                            .append(mYear).append("-")
                            .append(mMonth + 1).append("-")
                            .append(mDay).append(" ")
            );
        }else if(mode == 3){
            mDateDisplay3.setText(
                    new StringBuilder()
                            .append(mHour).append(":")
                            .append(mMinute)
            );
        }else if(mode == 4){
            mDateDisplay4.setText(
                    new StringBuilder()
                            .append(mHour).append(":")
                            .append(mMinute)
            );
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            onSearchListener = (OnSearchListener) getActivity();
        } catch (Exception e){
            throw new ClassCastException(activity.toString() +" must implement OnSearchListener");
        }
    }
}
