package org.jaram.ds.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.jaram.ds.R;

/**
 * Created by KimMyoungSoo on 2015. 7. 15..
 */

//메뉴추가 취소 다이얼로그 작성//
public class MenuManagementMain extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_management);

        Button deletebutton = (Button) findViewById(R.id.deletemenu);
        deletebutton.setOnClickListener(new View.OnClickListener() {

            AlertDialog.Builder builder = new AlertDialog.Builder(MenuManagementMain.this);
            public void onClick(View v) {
                builder.setMessage("삭제 하시겠습니까?")
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "삭제되었습니", Toast.LENGTH_LONG).show();
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();

            }
        });

    }


}
