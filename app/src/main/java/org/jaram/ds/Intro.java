package org.jaram.ds;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Intro extends FragmentActivity {

    BarChartFrag barChartFrag;
    DrawerFrag drawerFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);
        if(savedInstanceState == null) {
            barChartFrag = new BarChartFrag();
            drawerFrag = new DrawerFrag();
            getSupportFragmentManager().beginTransaction().add(R.id.chart,barChartFrag).commit();

        }
    }
    float startX;
    float startY;
    float endX;
    float endY;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();

        switch(action) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                Toast.makeText(getApplicationContext(),"X : "+startX+" Y : "+startY,Toast.LENGTH_LONG).show();
                break;
            case MotionEvent.ACTION_UP:
                endX= ev.getX();
                endY = ev.getY();
                Toast.makeText(getApplicationContext(),"X : "+endX+" Y : "+endY,Toast.LENGTH_LONG).show();

                float sensitivity = 5;
                // From left to right
                if((endX - startX) >=sensitivity){
                    Toast.makeText(getApplicationContext(),"슬라이딩됨",Toast.LENGTH_LONG).show();
                    DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
                    LinearLayout linear = (LinearLayout) findViewById(R.id.drawer);
                    drawerLayout.openDrawer(linear);
                } else{
                    DrawerLayout drawLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
                    LinearLayout linear = (LinearLayout) findViewById(R.id.drawer);
                    drawLayout.closeDrawer(linear);
                }
                break;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
