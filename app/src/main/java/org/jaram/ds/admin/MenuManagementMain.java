package org.jaram.ds.admin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MenuManagementMain.this);
                builder.setMessage("삭제 하시겠습니까?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "삭제되었습니다", Toast.LENGTH_LONG).show();
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();

            }
        });

        Button categorybutton = (Button) findViewById(R.id.categorizemenu);
        categorybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuManagementMain.this, CategoryMenagement.class));
            }
        });

        Button modifybutton = (Button) findViewById(R.id.modifymenu);
        modifybutton.setOnClickListener(new View.OnClickListener() {

            Context mcontext = getApplicationContext();
            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.fragment_menu_dialog, (ViewGroup) findViewById(R.id.layout_root));

            AlertDialog.Builder builder = new AlertDialog.Builder(MenuManagementMain.this);

            @Override
            public void onClick(View v) {
                builder.setTitle("메뉴 수정");
                builder.setView(layout);

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "수정되었습니다.", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();


            }
        });

        Button addbutton = (Button) findViewById(R.id.addmenu);
        addbutton.setOnClickListener(new View.OnClickListener() {

            Context mcontext = getApplicationContext();
            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.fragment_menu_add, (ViewGroup) findViewById(R.id.layout_root_add));

            AlertDialog.Builder builder = new AlertDialog.Builder(MenuManagementMain.this);

            @Override
            public void onClick(View v) {
                builder.setTitle("메뉴 추가");
                builder.setView(layout);

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "추가되었습니다.", Toast.LENGTH_LONG).show();
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
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
