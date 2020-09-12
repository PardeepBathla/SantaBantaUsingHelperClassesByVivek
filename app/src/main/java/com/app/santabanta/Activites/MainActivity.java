package com.app.santabanta.Activites;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;

import com.app.santabanta.Fragment.NavFragment;
import com.app.santabanta.Helper.MainActivityHelper;
import com.app.santabanta.R;
import com.app.santabanta.base.BaseActivity;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.rvBottom)
    RelativeLayout rvBottom;
    @BindView(R.id.tv_selected_module)
    TextView tvTitle;

    public NavFragment navFragment;
    public MainActivityHelper mHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initActivity();
        if (savedInstanceState == null) {
            initScreen();
        } else {
            try {
                if (getSupportFragmentManager().getFragments().get(0) instanceof NavFragment)
                    navFragment = (NavFragment) getSupportFragmentManager().getFragments().get(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public TextView getTitleView(){
        return tvTitle;
    }

    private void initActivity() {
        mHelper = new MainActivityHelper(MainActivity.this);
    }

    private void initScreen() {

        navFragment = new NavFragment();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, navFragment)
                .commit();

    }

    @Override
    public void onBackPressed() {
        if (navFragment!=null){
            if (!navFragment.onBackPressed()) {
                if (mHelper.drawer.isDrawerOpen(GravityCompat.END))
                    mHelper.drawer.closeDrawer(GravityCompat.END);
                else {
                   finish();
                }

            }
        }else {
            super.onBackPressed();
        }
    }

    public void showCurrentPage(int pos) {
        if (navFragment != null)
            navFragment.setCurrentPage(pos);
    }
}