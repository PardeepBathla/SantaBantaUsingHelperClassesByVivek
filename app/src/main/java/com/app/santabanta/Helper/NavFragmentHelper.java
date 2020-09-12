package com.app.santabanta.Helper;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.santabanta.Activites.MainActivity;
import com.app.santabanta.Adapter.ViewPagerAdapter;
import com.app.santabanta.AppController;
import com.app.santabanta.Fragment.NavFragment;
import com.app.santabanta.R;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.NonSwipeableViewPager;
import com.app.santabanta.Utils.ResUtils;
import com.app.santabanta.Utils.Utils;
import com.google.android.material.tabs.TabLayout;

public class NavFragmentHelper {

    private Activity mActivity;
    private NavFragment fragment;
    private ViewPagerAdapter adapter;
    private NonSwipeableViewPager mPager;
    private TabLayout mTabLayout;
    private RelativeLayout profileLayout;
    private ImageView iv_hamburger;

    public NavFragmentHelper(NavFragment fragment, NonSwipeableViewPager mPager, TabLayout mTabLayout, Activity mActivity) {
        this.fragment = fragment;
        this.mActivity = mActivity;
        this.mPager = mPager;
        this.mTabLayout = mTabLayout;
        adapter = new ViewPagerAdapter(mActivity.getResources(), fragment.getChildFragmentManager());
        initViews();
    }

    private void initViews() {

        mPager.setAdapter(adapter);
        mPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mPager);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){

                    ((ImageView) tab.getCustomView().findViewById(R.id.IconImageView)).setImageResource(R.drawable.ic_home);
                }
                ((TextView) tab.getCustomView().findViewById(R.id.tvMenuTitle)).setTextColor(ResUtils.getColor(R.color.light_mode_title_color));

                switch (tab.getPosition()){
                    case 0:
                        ((MainActivity)mActivity).getTitleView().setText(ResUtils.getString(R.string.HomeTab));
                        break;

                    case 1:
                        ((MainActivity)mActivity).getTitleView().setText(ResUtils.getString(R.string.SmsTab));
                        break;

                    case 2:
                        ((MainActivity)mActivity).getTitleView().setText(ResUtils.getString(R.string.JokesTab));
                        break;

                    case 3:
                        ((MainActivity)mActivity).getTitleView().setText(ResUtils.getString(R.string.MemesTab));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0){

                    if (Utils.getSharedPref(AppController.getInstance()).getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false)) {
                        ((ImageView) tab.getCustomView().findViewById(R.id.IconImageView)).setImageResource(R.drawable.ic_home_black);
                    } else {
                        ((ImageView) tab.getCustomView().findViewById(R.id.IconImageView)).setImageResource(R.drawable.ic_home_white);
                    }

                }
                if (Utils.getSharedPref(AppController.getInstance()).getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false)) {
                    ((TextView) tab.getCustomView().findViewById(R.id.tvMenuTitle)).setTextColor(ResUtils.getColor(R.color.black));
                } else {
                    ((TextView) tab.getCustomView().findViewById(R.id.tvMenuTitle)).setTextColor(ResUtils.getColor(R.color.white));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_home);
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_home);
        mTabLayout.getTabAt(3).setIcon(R.drawable.ic_home);
        for (int i = 0; i < 4; i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            View v;
            v = i == 0 ? this.getTabView(i, true) : this.getTabView(i, false);
            if (tab != null) tab.setCustomView(v);
        }

        setTabLayoutMatchParent(mTabLayout);
    }
    public ViewPagerAdapter getAdapter() {
        return adapter;
    }

    private View getTabView(int position, boolean isSelected) {
        View v = LayoutInflater.from(mActivity).inflate(R.layout.custom_tab, null);
        ImageView mTabNameTV = v.findViewById(R.id.IconImageView);
        TextView tvMenuTitle = v.findViewById(R.id.tvMenuTitle);

        if (position == 0){
            mTabNameTV.setVisibility(View.VISIBLE);
            tvMenuTitle.setVisibility(View.GONE);
        }else {
            mTabNameTV.setVisibility(View.GONE);
            tvMenuTitle.setVisibility(View.VISIBLE);
        }

        switch (position){
            case 1:
                tvMenuTitle.setText(ResUtils.getString(R.string.SmsTab));
                break;

            case 2:
                tvMenuTitle.setText(ResUtils.getString(R.string.JokesTab));
                break;

            case 3:
                tvMenuTitle.setText(ResUtils.getString(R.string.MemesTab));
                break;
        }

        return v;
    }


    private static void setTabLayoutMatchParent(TabLayout tabLayout) {
        final ViewGroup tabLayoutChild = (ViewGroup) (tabLayout.getChildAt(0));
        int tabLen = tabLayoutChild.getChildCount();

        for (int j = 0; j < tabLen; j++) {
            View v = tabLayoutChild.getChildAt(j);
            v.setPadding(0, 0, 0, 0);
        }
    }

}
