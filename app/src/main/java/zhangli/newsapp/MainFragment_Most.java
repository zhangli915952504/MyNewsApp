package zhangli.newsapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zhangli.newsapp.fragment.CaiJingFragment;
import zhangli.newsapp.fragment.FristFragment;
import zhangli.newsapp.fragment.FunFragment;
import zhangli.newsapp.fragment.KeJiFragment;
import zhangli.newsapp.fragment.SportFragment;

public class MainFragment_Most extends Fragment implements View.OnClickListener {

    private ViewPager mViewPager;
    private TextView frist_text;
    private TextView fun_text;
    private TextView sport_text;
    private TextView hot_text;
    private TextView local_text;



    public static MainFragment_Most newInstance() {
        MainFragment_Most fragment = new MainFragment_Most();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.leibie_more_relativelayout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewPager = (ViewPager) getView().findViewById(R.id.main_viewpager);
        frist_text = (TextView) getView().findViewById(R.id.frist_text);
        fun_text = (TextView) getView().findViewById(R.id.fun_text);
        sport_text = (TextView) getView().findViewById(R.id.sport_text);
        local_text = (TextView) getView().findViewById(R.id.local_text);
        hot_text = (TextView) getView().findViewById(R.id.hot_text);

        frist_text.setOnClickListener(this);
        fun_text.setOnClickListener(this);
        hot_text.setOnClickListener(this);
        local_text.setOnClickListener(this);
        sport_text.setOnClickListener(this);



        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(FristFragment.newInstance());
        fragmentList.add(FunFragment.newInstance());
        fragmentList.add(CaiJingFragment.newInstance());
        fragmentList.add(SportFragment.newInstance());
        fragmentList.add(KeJiFragment.newInstance());

        //调用添加数据到adapter的方法
        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager(), fragmentList);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(4);

        frist_text.setTextColor(Color.RED);

        //给滑动界面增加监听事件
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                clickView();
                switch (position) {
                    case 0:
                        frist_text.setTextColor(Color.RED);
                        break;
                    case 1:
                        fun_text.setTextColor(Color.RED);
                        break;
                    case 2:
                        hot_text.setTextColor(Color.RED);
                        break;
                    case 3:
                        sport_text.setTextColor(Color.RED);
                        break;
                    case 4:
                        local_text.setTextColor(Color.RED);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void clickView() {
        frist_text.setTextColor(getResources().getColor(R.color.darkgrey));
        fun_text.setTextColor(getResources().getColor(R.color.darkgrey));
        hot_text.setTextColor(getResources().getColor(R.color.darkgrey));
        local_text.setTextColor(getResources().getColor(R.color.darkgrey));
        sport_text.setTextColor(getResources().getColor(R.color.darkgrey));
    }


    @Override
    public void onClick(View v) {
        clickView();
        switch (v.getId()) {
            case R.id.frist_text:
                mViewPager.setCurrentItem(0);
                frist_text.setTextColor(Color.RED);
                break;
            case R.id.fun_text:
                mViewPager.setCurrentItem(1);
                fun_text.setTextColor(Color.RED);
                break;
            case R.id.hot_text:
                mViewPager.setCurrentItem(2);
                hot_text.setTextColor(Color.RED);
                break;
            case R.id.sport_text:
                mViewPager.setCurrentItem(3);
                sport_text.setTextColor(Color.RED);
                break;
            case R.id.local_text:
                mViewPager.setCurrentItem(4);
                local_text.setTextColor(Color.RED);
                break;
        }

    }


    public static class PagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> list = new ArrayList<>();

        public PagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

}
