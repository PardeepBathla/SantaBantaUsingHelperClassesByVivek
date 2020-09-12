package com.app.santabanta.Helper;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.Activites.MainActivity;
import com.app.santabanta.Adapter.SectionsPagerAdapter;
import com.app.santabanta.Adapter.SideMenuAdapter;
import com.app.santabanta.AppController;
import com.app.santabanta.Callbacks.CategoriesCallback;
import com.app.santabanta.Modals.NavMenuResponse;
import com.app.santabanta.R;
import com.app.santabanta.RestClient.Webservices;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.NonSwipeableViewPager;
import com.app.santabanta.Utils.ResUtils;
import com.app.santabanta.Utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityHelper {

    private MainActivity mActivity;
    public DrawerLayout drawer;
    private TabLayout mTabLayout;
    private NavigationView navigationView;
    public SharedPreferences pref;
    private NonSwipeableViewPager mViewPager;
    private SectionsPagerAdapter mSectionPagerAdapter;
    private Webservices mInterface_method = AppController.getRetroInstance().create(Webservices.class);
    private ImageView ivMenu;

    public MainActivityHelper(MainActivity mActivity) {
        this.mActivity = mActivity;
        pref = Utils.getSharedPref(mActivity);
        findViews();
        setupViewPager();
    }

    private void setupViewPager(){
        mSectionPagerAdapter = new SectionsPagerAdapter(mActivity.getSupportFragmentManager());

    }

    private void findViews() {

        ivMenu = mActivity.findViewById(R.id.iv_hamburger);
        mViewPager = mActivity.findViewById(R.id.viewPager);
        mTabLayout = mActivity.findViewById(R.id.tabLayout);
        drawer = mActivity.findViewById(R.id.drawer_layout);
        navigationView = mActivity.findViewById(R.id.navigationView);
        initDrawer();
        ivMenu.setOnClickListener(view -> drawer.openDrawer(Gravity.LEFT));

    }

    private void initDrawer() {

        TextView name = navigationView.findViewById(R.id.name);
        ImageView ivChangeMode = navigationView.findViewById(R.id.ivChangeMode);
        RecyclerView recyclerViewItems = navigationView.findViewById(R.id.recyclerViewItems);

        name.setText(iSelectedThemeLight() ? ResUtils.getString(R.string.light) : ResUtils.getString(R.string.dark));

        getHomeCategories(response -> {
            recyclerViewItems.setLayoutManager(new LinearLayoutManager(mActivity));
            recyclerViewItems.setAdapter(new SideMenuAdapter(mActivity,response.getData()));
        });

    }

    public boolean iSelectedThemeLight() {
        return pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false);
    }

    private void getHomeCategories(CategoriesCallback categoriesCallback){
        Call<NavMenuResponse> call  = mInterface_method.getNavList("English");
        call.enqueue(new Callback<NavMenuResponse>() {
            @Override
            public void onResponse(Call<NavMenuResponse> call, Response<NavMenuResponse> response) {
                if (response.isSuccessful())
                    categoriesCallback.onCategoriesFetched(response.body());
            }

            @Override
            public void onFailure(Call<NavMenuResponse> call, Throwable t) {
                Log.e("onFailure","onFailure");
            }
        });
    }
}
