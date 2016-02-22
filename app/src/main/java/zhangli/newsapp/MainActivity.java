package zhangli.newsapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;

import com.scxh.slider.library.Tricks.ViewPagerEx;
import com.warmtel.slidingmenu.lib.SlidingMenu;
import com.warmtel.slidingmenu.lib.app.SlidingActivity;

import zhangli.newsapp.Activity.CollectActivity;
import zhangli.newsapp.Activity.LoginActivity;
import zhangli.newsapp.Activity.OutActivity;
import zhangli.newsapp.Activity.SetActivity;

public class MainActivity extends SlidingActivity implements MenuFragment.OnFragmentInterfaceListener {

    private  PopupWindow popupWindow;
    private int result = 0;
    private Button bt_frist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_layout);

        bt_frist= (Button) findViewById(R.id.bt_frist);
        findViewById(R.id.title_imageview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainactivity_layout, MainFragment_Most.newInstance()).commit();


        setBehindContentView(R.layout.sliding_menu_fragment);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.sliding_fragment,MenuFragment.newInstance()).commit();

        SlidingMenu slidingMenu=getSlidingMenu();
        // 设置滑动菜单视图的宽度
        slidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        //从左边滑动
        slidingMenu.setMode(SlidingMenu.LEFT);
        // 设置渐入渐出效果的值
        slidingMenu.setFadeDegree(0.35f);
        //边缘触摸滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
    }

    @Override
    public void toLogin() {
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
        toggle();
    }

    @Override
    public void toCollect() {
        Intent intent=new Intent(this, CollectActivity.class);
        startActivity(intent);
        toggle();
    }

    @Override
    public void toSet() {
        Intent intent=new Intent(this, SetActivity.class);
        startActivity(intent);
        toggle();
    }

    @Override
    public void toOut() {
        Intent intent=new Intent(this, OutActivity.class);
        startActivity(intent);
        toggle();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void Dakai(View v){
        //在Activity里取得屏幕大小
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        getStatusBarHeight();

//        bt_frist.setBackgroundColor(getResources().getColor(R.color.red));

        LayoutInflater mLayoutInflater = getLayoutInflater();
        View view = mLayoutInflater.inflate(R.layout.item_more_layout, null);
        view.setBackgroundColor(Color.WHITE);
        //设置背景
//        Drawable drawable = getResources().getDrawable(R.drawable.button_shape_change);
//        bt_frist.setBackground(drawable);

        popupWindow = new PopupWindow(view, ViewPagerEx.LayoutParams.WRAP_CONTENT, ViewPagerEx.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(width);
        popupWindow.setHeight(height - result);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0,0);

    }

    public void guanbi(View v) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
        }
    }
    //得到状态栏的高度
    public int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}

