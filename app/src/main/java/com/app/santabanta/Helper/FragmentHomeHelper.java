package com.app.santabanta.Helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.santabanta.Adapter.HomeCategoriesAdapter;
import com.app.santabanta.Adapter.HomeItemAdapter;
import com.app.santabanta.AppController;
import com.app.santabanta.Fragment.FragmentHome;
import com.app.santabanta.Modals.AddFavouriteRequest;
import com.app.santabanta.Modals.Favourite;
import com.app.santabanta.Modals.FeaturedCategory;
import com.app.santabanta.Modals.HomeDetailList;
import com.app.santabanta.Modals.HomeDetailsModel;
import com.app.santabanta.RestClient.Webservices;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
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

                    if (response.body().getData()!=null && response.body().getData().size()>0){
                        mAdapter = new HomeItemAdapter(mActivity, response.body().getData(),FragmentHomeHelper.this);
                        mAdapter.setHasStableIds(true);
                        mLinearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
                        fragmentHome.recyclerHome.setLayoutManager(mLinearLayoutManager);
                        fragmentHome.recyclerHome.setMediaObjects(response.body().getData());
                        fragmentHome.recyclerHome.setAdapter(mAdapter);
                        fragmentHome.recyclerHome.setNestedScrollingEnabled(false);
                        fragmentHome.recyclerHome.setHasFixedSize(false);

                        fragmentHome.recyclerHome.setOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);
                            }

                            @Override
                            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                                super.onScrolled(recyclerView, dx, dy);
                                fragmentHome.swipeRefreshLayout.setEnabled(mLinearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
                            }
                        });
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
                        fragmentHome.recyclerHome.setVisibility(View.VISIBLE);
                        fragmentHome.tvNoDataFound.setVisibility(View.GONE);
//                        Utils.fixRecyclerScroll(fragmentHome.recyclerHome, fragmentHome.swipeRefreshLayout, mLinearLayoutManager);
                    }else {
                        fragmentHome.recyclerHome.setVisibility(View.GONE);
                        fragmentHome.tvNoDataFound.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onFailure(Call<HomeDetailsModel> call, Throwable t) {

                if (fragmentHome.swipeRefreshLayout != null && fragmentHome.swipeRefreshLayout.isRefreshing())
                    fragmentHome.swipeRefreshLayout.setRefreshing(false);

                fragmentHome.recyclerHome.setVisibility(View.GONE);
                fragmentHome.tvNoDataFound.setVisibility(View.VISIBLE);

                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
    }




    public void addToFav(HomeDetailList obj, int position, String type, CheckBox cbLike, Dialog progressBar) {

        AddFavouriteRequest addFavouriteRequest = new AddFavouriteRequest();
        addFavouriteRequest.setDeviceId(Utils.getMyDeviceId(mActivity));
        addFavouriteRequest.setType(type);
        addFavouriteRequest.setItemId(obj.getId().intValue());


        Call<ResponseBody> call = null;

        call = mInterface_method.saveFavouriteJoke(addFavouriteRequest);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.has("status")) {
                            String status = jsonObject.getString("status");

                            if (status.equals("success")) {
                                progressBar.dismiss();
                                cbLike.setClickable(true);
                                     obj.setFavCount(obj.getFavCount()+1);
                                setFavItemToModel(position, addFavouriteRequest, obj, jsonObject.getInt("fav_id"));
                                Toast.makeText(mActivity, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure", "onFailure");
            }
        });




    }

    private void setFavItemToModel(int position, AddFavouriteRequest addFavouriteRequest, HomeDetailList obj, int fav_id) {
        Favourite homeFavouriteList = new Favourite();
        homeFavouriteList.setDeviceId(addFavouriteRequest.getDeviceId());
        homeFavouriteList.setItemId(String.valueOf(addFavouriteRequest.getItemId()));
        homeFavouriteList.setId(fav_id);

        ArrayList<HomeDetailList> pagedLists = null;
        pagedLists = mAdapter.getCurrentList();
        if (obj.getFavourites() != null && obj.getFavourites().size() != 0) {
            obj.getFavourites().add(0, homeFavouriteList);
        } else {
            List<Favourite> favouriteModelList = new ArrayList<>();
            favouriteModelList.add(0, homeFavouriteList);
            obj.setFavourites(favouriteModelList);
        }
        pagedLists.get(position).setFavourites(obj.getFavourites());
        mAdapter.updateList(pagedLists); //paging method

    }

    public void removeFromFav(HomeDetailList obj, int id, int position, CheckBox cbLike, Dialog progressBar) {


        Call<ResponseBody> call = null;

        call = mInterface_method.removeJokeFromFav(id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.has("status")) {
                            String status = jsonObject.getString("status");
                            if (status.equals("success")) {
                                cbLike.setClickable(true);
                                progressBar.dismiss();
                                removeFavItemFromModel(position, obj);
                                Toast.makeText(mActivity, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure", "onFailure");
            }
        });


    }

    private void removeFavItemFromModel(int position, HomeDetailList obj) {
        ArrayList<HomeDetailList> pagedLists = null;
        pagedLists = mAdapter.getCurrentList();

        pagedLists.get(position).setFavourites(null);
        obj.setFavCount(obj.getFavCount() - 1);
        mAdapter.updateList(pagedLists); //paging method

    }

}
