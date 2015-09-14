package org.jaram.ds.admin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jaram.ds.Intro;
import org.jaram.ds.R;
import org.jaram.ds.data.Data;
import org.jaram.ds.data.struct.Menu;
import org.jaram.ds.order.OrderManager;

import java.util.ArrayList;

/**
 * Created by KimMyoungSoo on 2015. 7. 15..
 */

//메뉴추가 취소 다이얼로그 작성//
public class MenuManagementMain extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_management2);


        RecyclerView cutletList = (RecyclerView)findViewById(R.id.DonMenuList);
        MenuSelectBtnAdapter menuBtnAdapterDon = new MenuSelectBtnAdapter(Data.categoryList.get(1).menus);
        cutletList.setAdapter(menuBtnAdapterDon);
        cutletList.setLayoutManager(new LinearLayoutManager(MenuManagementMain.this,LinearLayoutManager.VERTICAL,false));

        RecyclerView riceList = (RecyclerView)findViewById(R.id.DupMenuList);
        MenuSelectBtnAdapter menuBtnAdapterDup = new MenuSelectBtnAdapter(Data.categoryList.get(2).menus);
        riceList.setAdapter(menuBtnAdapterDup);
        riceList.setLayoutManager(new LinearLayoutManager(MenuManagementMain.this,LinearLayoutManager.VERTICAL,false));

        RecyclerView noodleList = (RecyclerView)findViewById(R.id.NoodleMenuList);
        MenuSelectBtnAdapter menuBtnAdapterNoodle = new MenuSelectBtnAdapter(Data.categoryList.get(3).menus);
        noodleList.setAdapter(menuBtnAdapterNoodle);
        noodleList.setLayoutManager(new LinearLayoutManager(MenuManagementMain.this, LinearLayoutManager.VERTICAL, false));

        RecyclerView etcList = (RecyclerView)findViewById(R.id.DrinkAndAdd);
        MenuSelectBtnAdapter menuBtnAdapterLast = new MenuSelectBtnAdapter(Data.categoryList.get(4).menus);
        etcList.setAdapter(menuBtnAdapterLast);
        etcList.setLayoutManager(new LinearLayoutManager(MenuManagementMain.this, LinearLayoutManager.VERTICAL, false));



        TextView don = (TextView)findViewById(R.id.DonGgas);
        don.setOnClickListener(new View.OnClickListener() {

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
        TextView dup = (TextView)findViewById(R.id.DupBbap);
        dup.setOnClickListener(new View.OnClickListener() {

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
        TextView noo = (TextView)findViewById(R.id.Noodles);
        noo.setOnClickListener(new View.OnClickListener() {

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

        TextView drink = (TextView)findViewById(R.id.DrinkandSpecial);
        drink.setOnClickListener(new View.OnClickListener() {

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

//
//        if(savedInstanceState==null) {
//            setContentView(R.layout.activity_menu_management);
//            MenuListAdapter adapter = new MenuListAdapter(this);
//            menuListFrag = new MenuListFrag();
//            menuListFrag.setAdapter(adapter);
//            menuFrag = new MenuFrag();
//            menuFrag.setAdapter(adapter);
//            getSupportFragmentManager().beginTransaction().add(R.id.listContainer, menuListFrag).commit();
//            getSupportFragmentManager().beginTransaction().add(R.id.management,menuFrag).commit();
//        }




//        setContentView(R.layout.activity_menu_management);
//
//        Button deletebutton = (Button) findViewById(R.id.deletemenu);
//        deletebutton.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//
//                Builder builder = new Builder(MenuManagementMain.this);
//                builder.setMessage("삭제 하시겠습니까?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(getApplicationContext(), "삭제되었습니다", Toast.LENGTH_LONG).show();
//                            }
//                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                builder.create().show();
//
//            }
//        });
//
//        Button categorybutton = (Button) findViewById(R.id.categorizemenu);
//        categorybutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MenuManagementMain.this, CategoryMenagement.class));
//            }
//        });
//
//        Button modifybutton = (Button) findViewById(R.id.modifymenu);
//        modifybutton.setOnClickListener(new View.OnClickListener() {
//
//            Context mcontext = getApplicationContext();
//            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(LAYOUT_INFLATER_SERVICE);
//            View layout = inflater.inflate(R.layout.fragment_menu_dialog, (ViewGroup) findViewById(R.id.layout_root));
//
//            @Override
//            public void onClick(View v) {
//                Builder builder = new Builder(MenuManagementMain.this);
//                builder.setTitle("메뉴 수정");
//                builder.setView(layout);
//
//                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getApplicationContext(), "수정되었습니다.", Toast.LENGTH_LONG).show();
//                    }
//                });
//                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                builder.create().show();
//
//
//            }
//        });
//
//        Button addbutton = (Button) findViewById(R.id.addmenu);
//        addbutton.setOnClickListener(new View.OnClickListener() {
//
//            Context mcontext = getApplicationContext();
//            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(LAYOUT_INFLATER_SERVICE);
//            View layout = inflater.inflate(R.layout.fragment_menu_add, (ViewGroup) findViewById(R.id.layout_root_add));
//
//            Builder builder = new Builder(MenuManagementMain.this);
//
//            @Override
//            public void onClick(View v) {
//                builder.setTitle("메뉴 추가");
//                builder.setView(layout);
//
//                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getApplicationContext(), "추가되었습니다.", Toast.LENGTH_LONG).show();
//                    }
//                });
//
//                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                builder.create().show();
//
//            }
//        });
        Button refund = (Button) findViewById(R.id.refund);
        refund.setText("주문");
        refund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuManagementMain.this, OrderManager.class));
                finish();
            }
        });
        Button statistic = (Button) findViewById(R.id.DrawerStat);

        statistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuManagementMain.this, StatisticMain.class));
                finish();
            }
        });

        Button order =(Button) findViewById(R.id.DrawerOrder);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuManagementMain.this, OrderManagerMain.class));
                finish();
            }
        });
        Button menuManager = (Button) findViewById(R.id.DrawerMenu);
        menuManager.setTextColor(Color.parseColor("#E8704C"));
        GradientDrawable bgShape = (GradientDrawable)menuManager.getBackground();
        bgShape.setColorFilter(Color.parseColor("#E8704C"), PorterDuff.Mode.MULTIPLY);

        Button exit = (Button) findViewById(R.id.DrawerExit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuManagementMain.this,Intro.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                moveTaskToBack(true);
            }
        });
    }

    public class MenuSelectBtnAdapter extends RecyclerView.Adapter<MenuSelectBtnAdapter.MenuSelectBtnViewHolder> {

        ArrayList<Menu> menuList = null;
        public MenuSelectBtnAdapter(ArrayList<Menu> menuList) {
            this.menuList = menuList;
        }

        public void setmenuList(ArrayList<Menu> menuList){
            this.menuList = menuList;
        }

        @Override
        public MenuSelectBtnViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MenuSelectBtnViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.menubtn_item, parent, false));
        }

        @Override
        public void onBindViewHolder(MenuSelectBtnViewHolder holder, int position) {
            final int i =position;

            holder.name.setText(menuList.get(position).name);
            holder.price.setText(menuList.get(position).price+"");
        }

        @Override
        public int getItemCount() {
            return menuList.size();
        }

        public class MenuSelectBtnViewHolder extends RecyclerView.ViewHolder {

            public TextView name;
            public TextView price;
            public MenuSelectBtnViewHolder(View item) {
                super(item);
                price = (TextView)item.findViewById(R.id.PriceOfMenu);

                name = (TextView)item.findViewById(R.id.NameOfMenu);

                item.setOnClickListener(new View.OnClickListener() {

                    Context mcontext = getApplicationContext();
                    LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(LAYOUT_INFLATER_SERVICE);
                    View layout = inflater.inflate(R.layout.fragment_menu_dialog, (ViewGroup) findViewById(R.id.layout_root));

                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(MenuManagementMain.this);
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
            }
        }
    }
}
