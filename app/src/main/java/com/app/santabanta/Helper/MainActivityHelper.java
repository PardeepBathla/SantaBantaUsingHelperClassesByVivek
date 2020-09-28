package com.app.santabanta.Helper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.Activites.MainActivity;
import com.app.santabanta.Adapter.SearchAdapter;
import com.app.santabanta.Adapter.SectionsPagerAdapter;
import com.app.santabanta.Adapter.SideMenuAdapter;
import com.app.santabanta.AppController;
import com.app.santabanta.Callbacks.CategoriesCallback;
import com.app.santabanta.Callbacks.DrawerMenuClickListener;
import com.app.santabanta.Events.Events;
import com.app.santabanta.Events.GlobalBus;
import com.app.santabanta.Modals.NavMenuResponse;
import com.app.santabanta.Modals.SearchResponse;
import com.app.santabanta.R;
import com.app.santabanta.RestClient.Webservices;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.LocaleHelper;
import com.app.santabanta.Utils.NonSwipeableViewPager;
import com.app.santabanta.Utils.ResUtils;
import com.app.santabanta.Utils.SimpleDividerItemDecoration;
import com.app.santabanta.Utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.santabanta.AppController.LANGUAGE_SELECTED;
import static com.app.santabanta.Utils.GlobalConstants.INTENT_PARAMS.SHOW_JOKES_FRAGMENT;
import static com.app.santabanta.Utils.GlobalConstants.INTENT_PARAMS.SHOW_MEMES_FRAGMENT;
import static com.app.santabanta.Utils.GlobalConstants.INTENT_PARAMS.SHOW_SMS_FRAGMENT;

public class MainActivityHelper implements SearchAdapter.SearchClickListener {

    public DrawerLayout drawer;
    public SharedPreferences pref;
    private String languageToLoad = "en";
    private MainActivity mActivity;
    private TabLayout mTabLayout;
    private NavigationView navigationView;
    private NonSwipeableViewPager mViewPager;
    private SectionsPagerAdapter mSectionPagerAdapter;
    private Webservices mInterface_method = AppController.getRetroInstance().create(Webservices.class);
    private ImageView ivMenu;
    private ImageView iv_language;
    private ImageView ivSearch;
    private ImageView ivLogo;
    private RelativeLayout rlSearch;
    private ImageView ivCancel;
    private SearchAdapter mAdapter;

    public MainActivityHelper(MainActivity mActivity) {
        this.mActivity = mActivity;

        pref = Utils.getSharedPref(mActivity);
        findViews();
        setupViewPager();
    }

    private void setupViewPager() {
        mSectionPagerAdapter = new SectionsPagerAdapter(mActivity.getSupportFragmentManager());
        mAdapter = new SearchAdapter(mActivity, this);
        mActivity.rvSearch.setLayoutManager(new LinearLayoutManager(mActivity));
        mActivity.rvSearch.setAdapter(mAdapter);

    }


    private void findViews() {
        ivCancel = mActivity.findViewById(R.id.iv_search);
        ivMenu = mActivity.findViewById(R.id.iv_hamburger);
        rlSearch = mActivity.findViewById(R.id.rl_search);
        ivLogo = mActivity.findViewById(R.id.ivLogo);
        ivSearch = mActivity.findViewById(R.id.ivSearch);
        iv_language = mActivity.findViewById(R.id.iv_language);
        mViewPager = mActivity.findViewById(R.id.viewPager);
        mTabLayout = mActivity.findViewById(R.id.tabLayout);
        drawer = mActivity.findViewById(R.id.drawer_layout);
        navigationView = mActivity.findViewById(R.id.navigationView);
        ImageView ivChangeMode = navigationView.findViewById(R.id.ivChangeMode);
        ivChangeMode.setOnClickListener(view -> {
//                if (pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false)) {
//                    name.setText(mActivity.getResources().getString(R.string.light));
//                } else {
//                    name.setText(mActivity.getResources().getString(R.string.dark));
//
//                }
            mActivity.switchTheme();
        });

        ivCancel.setOnClickListener(v -> {
            ivSearch.setVisibility(View.VISIBLE);
            ivLogo.setVisibility(View.VISIBLE);
            rlSearch.setVisibility(View.GONE);
        });

        ivSearch.setOnClickListener(v -> {
            ivSearch.setVisibility(View.GONE);
            ivLogo.setVisibility(View.GONE);
            rlSearch.setVisibility(View.VISIBLE);
        });


        ivMenu.setOnClickListener(view -> {
            if (drawer.isDrawerOpen(Gravity.LEFT)){
                drawer.closeDrawer(Gravity.LEFT);
            }else {
                drawer.openDrawer(Gravity.LEFT);
            }
        });

        String locale = LocaleHelper.getPersistedData(mActivity, Locale.getDefault().getLanguage());
        if (locale.equalsIgnoreCase("en")) {
            LANGUAGE_SELECTED = GlobalConstants.COMMON.ENGLISH;

            if (pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false)) {
                iv_language.setImageDrawable(ResUtils.getDrawable(R.drawable.ic_hindi_language));
            } else {
                iv_language.setImageDrawable(ResUtils.getDrawable(R.drawable.ic_hindi_language_black));
            }

        } else {
            LANGUAGE_SELECTED = GlobalConstants.COMMON.HINDI;

            if (pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false)) {
                iv_language.setImageDrawable(ResUtils.getDrawable(R.drawable.ic_english_language_new));
            } else {
                iv_language.setImageDrawable(ResUtils.getDrawable(R.drawable.ic_english_language_blackjpg));
            }

//            iv_language.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_english_language_new));
        }
        iv_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LANGUAGE_SELECTED.equalsIgnoreCase(GlobalConstants.COMMON.HINDI)) {
                    languageToLoad = "en";
                    iv_language.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_hindi_language));
                } else {
                    languageToLoad = "hi";
                    iv_language.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.ic_english_language_new));

                }

                LocaleHelper.setLocale(mActivity, languageToLoad);
                mActivity.finish();
                mActivity.startActivity(mActivity.getIntent().putExtra("change",true));
            }
        });
        initDrawer();
    }


    private void initDrawer() {

        TextView name = navigationView.findViewById(R.id.name);
        ImageView ivChangeMode = navigationView.findViewById(R.id.ivChangeMode);
        RecyclerView recyclerViewItems = navigationView.findViewById(R.id.recyclerViewItems);

//        name.setText(iSelectedThemeLight() ? ResUtils.getString(R.string.light) : ResUtils.getString(R.string.dark));
        name.setText(ResUtils.getString(R.string.dark));

        getHomeCategories(response -> {
            recyclerViewItems.setLayoutManager(new LinearLayoutManager(mActivity));
            recyclerViewItems.setAdapter(new SideMenuAdapter(mActivity, response.getData(), new DrawerMenuClickListener() {
                @Override
                public void onSmsClicked(String slugName, String slugId, String category) {
                    Utils.showLog("onSmsClicked", slugName + " " + slugId + " " + category);
//                    Toast.makeText(mActivity,"slug" + " " + slugName ,Toast.LENGTH_SHORT).show();
                    openSmsFragment(slugName, category);
                }

                @Override
                public void onJokesClicked(String slug, String slugId) {
                    openJokesFragment(slug);
                }

                @Override
                public void onMemesClicked(String slug, String slugId) {
                    openMemesFragment(slug);
                }
            }));
//            recyclerViewItems.addItemDecoration(new SimpleDividerItemDecoration(mActivity));
        });

    }

    public void openMemesFragment(String slug) {
        drawer.closeDrawer(Gravity.LEFT);
        mActivity.navFragment.getViewPager().setCurrentItem(3);
        Events.MemesEvent memesEvent= new Events.MemesEvent(slug);
        GlobalBus.getBus().post(memesEvent);
//        mActivity.sendBroadcast(new Intent().setAction(SHOW_MEMES_FRAGMENT).putExtra(GlobalConstants.INTENT_PARAMS.MEME_SLUG, slug));
    }

    public void openJokesFragment(String slug) {
        drawer.closeDrawer(Gravity.LEFT);
        mActivity.navFragment.getViewPager().setCurrentItem(2);
        Events.JokesEvent jokesEvent= new Events.JokesEvent(slug);
        GlobalBus.getBus().post(jokesEvent);
//        mActivity.sendBroadcast(new Intent().setAction(SHOW_JOKES_FRAGMENT).putExtra(GlobalConstants.INTENT_PARAMS.JOKE_SLUG, slug));
    }

    public void openSmsFragment(String slug, String category) {
        drawer.closeDrawer(Gravity.LEFT);
        mActivity.navFragment.getViewPager().setCurrentItem(1);
        Events.SMSEvent onFileSelected = new Events.SMSEvent(slug,category);
        GlobalBus.getBus().post(onFileSelected);
//        mActivity.sendBroadcast(new Intent().setAction(SHOW_SMS_FRAGMENT).putExtra(GlobalConstants.INTENT_PARAMS.SMS_CATEGORY, category).putExtra(GlobalConstants.INTENT_PARAMS.SMS_SLUG, slug));
    }

    public boolean iSelectedThemeLight() {
        return pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false);
    }

    public void searchText(String query){
        mActivity.rvSearch.setVisibility(View.GONE);
        mActivity.pbSearch.setVisibility(View.VISIBLE);
        mActivity.tvNoDataFoundSearch.setText(ResUtils.getString(R.string.loading));
        mActivity.tvNoDataFoundSearch.setVisibility(View.VISIBLE);
        Call<SearchResponse> call = mInterface_method.search(query);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()){
                    mActivity.pbSearch.setVisibility(View.GONE);
                    if (response.body().getHits().getHits().size()>0){
                        mActivity.rvSearch.setVisibility(View.VISIBLE);
                        mActivity.tvNoDataFoundSearch.setVisibility(View.GONE);
                        mAdapter.setItems(response.body().getHits().getHits());
                    }else {
                        mActivity.tvNoDataFoundSearch.setText(ResUtils.getString(R.string.no_data_found));
                        mActivity.rvSearch.setVisibility(View.GONE);
                        mActivity.tvNoDataFoundSearch.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                mActivity.pbSearch.setVisibility(View.GONE);
                mActivity.tvNoDataFoundSearch.setText(ResUtils.getString(R.string.no_data_found));
            }
        });
    }


    private void getHomeCategories(CategoriesCallback categoriesCallback) {
        Call<NavMenuResponse> call = mInterface_method.getNavList(LANGUAGE_SELECTED);
        call.enqueue(new Callback<NavMenuResponse>() {
            @Override
            public void onResponse(Call<NavMenuResponse> call, Response<NavMenuResponse> response) {
                if (response.isSuccessful())
                    categoriesCallback.onCategoriesFetched(response.body());
            }

            @Override
            public void onFailure(Call<NavMenuResponse> call, Throwable t) {
                Utils.showLog("onFailure", "onFailure");
            }
        });
    }

    @Override
    public void onSearchClicked(SearchResponse.Hits.Hit.Source model) {
        mActivity.flSearch.setVisibility(View.GONE);
        mActivity.container.setVisibility(View.VISIBLE);
        mActivity.etSearch.setText("");
        switch (model.getType()){
            case "sms":
                openSmsFragment(model.getSlug(),"V eg");
                break;

            case "jokes":
                openJokesFragment(model.getSlug());
                break;

            case "memes":
                openMemesFragment(model.getSlug());
                break;
        }
    }
}
