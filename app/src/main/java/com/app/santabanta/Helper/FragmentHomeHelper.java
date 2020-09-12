package com.app.santabanta.Helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.santabanta.Adapter.HomeCategoriesAdapter;
import com.app.santabanta.Adapter.HomeItemAdapter;
import com.app.santabanta.AppController;
import com.app.santabanta.Fragment.FragmentHome;
import com.app.santabanta.Modals.FeaturedCategory;
import com.app.santabanta.Modals.HomeDetailsModel;
import com.app.santabanta.RestClient.Webservices;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHomeHelper {

    private static final int PAGE_START = 1;
    private Activity mActivity;
    private Webservices mInterface_method = AppController.getRetroInstance().create(Webservices.class);
    private FragmentHome fragmentHome;
    private HomeItemAdapter mAdapter;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 10;
    private int currentPage = PAGE_START;
    private LinearLayoutManager mLinearLayoutManager;

    public FragmentHomeHelper(Activity mActivity, FragmentHome fragmentHome) {
        this.mActivity = mActivity;
        this.fragmentHome = fragmentHome;
        initViews();
    }

    private void initViews() {

        fragmentHome.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHomeData(AppController.LANGUAGE_SELECTED);
            }
        });
        getHomeData(AppController.LANGUAGE_SELECTED);
    }

    private void getHomeData(String language) {
        Dialog progressDialog = Utils.getProgressDialog(mActivity);
        progressDialog.show();

        //// TODO: 9/12/20  make language dynamic
        Call<HomeDetailsModel> call = mInterface_method.getHomeList(language, currentPage);
        call.enqueue(new Callback<HomeDetailsModel>() {
            @Override
            public void onResponse(Call<HomeDetailsModel> call, Response<HomeDetailsModel> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

                if (fragmentHome.swipeRefreshLayout != null && fragmentHome.swipeRefreshLayout.isRefreshing())
                    fragmentHome.swipeRefreshLayout.setRefreshing(false);

                if (response.isSuccessful()) {
                    fragmentHome.rvSubCategory.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false));
                    fragmentHome.rvSubCategory.setAdapter(new HomeCategoriesAdapter(response.body().getFeaturedCategories(), mActivity, new HomeCategoriesAdapter.HomeCategoryClickListener() {
                        @Override
                        public void onItemClicked(FeaturedCategory model) {
                            switch (model.getType()) {
                                case "sms":
                                    mActivity.sendBroadcast(new Intent().setAction(GlobalConstants.INTENT_PARAMS.NAVIGATE_FROM_HOME)
                                            .putExtra(GlobalConstants.INTENT_PARAMS.NAVIGATE_TYPE,"sms").putExtra(GlobalConstants.INTENT_PARAMS.NAVIGATE_SLUG,model.getSlug()));
                                    break;

                                case "jokes":
                                    mActivity.sendBroadcast(new Intent().setAction(GlobalConstants.INTENT_PARAMS.NAVIGATE_FROM_HOME)
                                            .putExtra(GlobalConstants.INTENT_PARAMS.NAVIGATE_TYPE,"jokes").putExtra(GlobalConstants.INTENT_PARAMS.NAVIGATE_SLUG,model.getSlug()));
                                    break;

                                case "memes":
                                    mActivity.sendBroadcast(new Intent().setAction(GlobalConstants.INTENT_PARAMS.NAVIGATE_FROM_HOME)
                                            .putExtra(GlobalConstants.INTENT_PARAMS.NAVIGATE_TYPE,"memes").putExtra(GlobalConstants.INTENT_PARAMS.NAVIGATE_SLUG,model.getSlug()));
                                    break;
                            }
                        }
                    }));

                    mAdapter = new HomeItemAdapter(mActivity, response.body().getData());
                    mAdapter.setHasStableIds(true);
                    mLinearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
                    fragmentHome.recyclerHome.setLayoutManager(mLinearLayoutManager);
                    fragmentHome.recyclerHome.setMediaObjects(response.body().getData());
                    fragmentHome.recyclerHome.setAdapter(mAdapter);
                    fragmentHome.recyclerHome.setNestedScrollingEnabled(false);
                    fragmentHome.recyclerHome.setHasFixedSize(false);

                    //TODO uncomment when pagination values comes in api response
//
//                    if (currentPage < TOTAL_PAGES) {
//                        if (mFeedAdapter != null)
//                            mFeedAdapter.addLoadingFooter();
//                    } else isLastPage = true;
//
//                    mRvFeed.addOnScrollListener(new PaginationScrollListener(mLinearLayoutManager) {
//                        @Override
//                        protected void loadMoreItems() {
//                            isLoading = true;
//                            currentPage += 1;
//                            new Handler().postDelayed(HomeFragmentHelper.this::loadNextPage, 1000);
//                        }
//
//                        @Override
//                        public int getTotalPageCount() {
//                            return TOTAL_PAGES;
//                        }
//
//                        @Override
//                        public boolean isLastPage() {
//                            return isLastPage;
//                        }
//
//                        @Override
//                        public boolean isLoading() {
//                            return isLoading;
//                        }
//
//                        @Override
//                        public void onScrolled() {
//
//                        }
//                    });

                }
            }

            @Override
            public void onFailure(Call<HomeDetailsModel> call, Throwable t) {

                if (fragmentHome.swipeRefreshLayout != null && fragmentHome.swipeRefreshLayout.isRefreshing())
                    fragmentHome.swipeRefreshLayout.setRefreshing(false);

                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
    }
}
