package com.app.santabanta.Helper;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.santabanta.Adapter.JokesCategoriesAdapter;
import com.app.santabanta.Adapter.JokesHomeAdapter;
import com.app.santabanta.AppController;
import com.app.santabanta.Fragment.FragmentJokes;
import com.app.santabanta.Modals.JokesDataModel;
import com.app.santabanta.Modals.JokesFeaturedCategory;
import com.app.santabanta.RestClient.Webservices;
import com.app.santabanta.Utils.Utils;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentJokesHelper {

    private static final int PAGE_START = 1;

    private Activity mActivity;
    private Webservices mInterface_method = AppController.getRetroInstance().create(Webservices.class);
    private FragmentJokes fragmentJokes;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 10;
    private int currentPage = PAGE_START;

    public FragmentJokesHelper(Activity mActivity, FragmentJokes fragmentJokes) {
        this.mActivity = mActivity;
        this.fragmentJokes = fragmentJokes;
        initViews();
    }

    private void initViews() {

        fragmentJokes.swipeRefreshJokes.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJokes("English",fragmentJokes.slugName);
            }
        });

        //Todo Implement dynamic language
        getJokes("English",fragmentJokes.slugName);
    }

    private void getJokes(String language, String slug) {
        Call<JokesDataModel> call;
        if (fragmentJokes.IS_SUB_CAT)
            call = mInterface_method.getJokesListNew(slug,1);
        else
            call = mInterface_method.getJokesList(language,slug,1);
        Dialog dialog = Utils.getProgressDialog(mActivity);
        dialog.show();
        call.enqueue(new Callback<JokesDataModel>() {
            @Override
            public void onResponse(Call<JokesDataModel> call, Response<JokesDataModel> response) {
                if (dialog.isShowing())
                    dialog.dismiss();

                if (fragmentJokes.swipeRefreshJokes != null && fragmentJokes.swipeRefreshJokes.isRefreshing())
                    fragmentJokes.swipeRefreshJokes.setRefreshing(false);

                if (response.isSuccessful()){
                    fragmentJokes.rvSubCategoryJokes.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL,false));
                    fragmentJokes.rvSubCategoryJokes.setAdapter(new JokesCategoriesAdapter(response.body().getFeaturedCategories(), mActivity, new JokesCategoriesAdapter.JokesCategoryClickListener() {
                        @Override
                        public void onItemClicked(JokesFeaturedCategory model) {
                            fragmentJokes.enterSubCategoryJoke(true,model.getSlug());
                        }
                    }));

                    fragmentJokes.recyclerJokes.setLayoutManager(new LinearLayoutManager(mActivity));
                    fragmentJokes.recyclerJokes.setAdapter(new JokesHomeAdapter(response.body().getData(),mActivity));
                }

            }

            @Override
            public void onFailure(Call<JokesDataModel> call, Throwable t) {
                if (dialog.isShowing())
                    dialog.dismiss();

                if (fragmentJokes.swipeRefreshJokes != null && fragmentJokes.swipeRefreshJokes.isRefreshing())
                    fragmentJokes.swipeRefreshJokes.setRefreshing(false);
            }
        });
    }
}
