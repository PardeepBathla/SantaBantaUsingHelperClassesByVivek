package com.app.santabanta.Helper;

import android.app.Activity;
import android.app.Dialog;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.santabanta.Adapter.SmsCategoriesAdapter;
import com.app.santabanta.Adapter.SmsHomeAdapter;
import com.app.santabanta.AppController;
import com.app.santabanta.Fragment.FragmentSms;
import com.app.santabanta.Modals.AddFavouriteRequest;
import com.app.santabanta.Modals.SmsDetailModel;
import com.app.santabanta.Modals.SmsFavouriteModel;
import com.app.santabanta.Modals.SmsFeaturedCategory;
import com.app.santabanta.Modals.SmsResponseModel;
import com.app.santabanta.R;
import com.app.santabanta.RestClient.Webservices;
import com.app.santabanta.Utils.PaginationScrollListener;
import com.app.santabanta.Utils.ResUtils;
import com.app.santabanta.Utils.ShareableIntents;
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

public class FragmentSmsHelper {

    private static final int PAGE_START = 1;
    SmsHomeAdapter smsHomeAdapter;
    private Activity mActivity;
    private Webservices mInterface_method = AppController.getRetroInstance().create(Webservices.class);
    private FragmentSms fragmentSms;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 10;
    private LinearLayoutManager mLinearLayoutManager;
    private LinearLayoutManager mSubListLayoutManager;
    private int currentPage = PAGE_START;
    private int pageCount, total_pages, next_page;
    private boolean isLoading = false;

    public FragmentSmsHelper(Activity mActivity, FragmentSms fragmentSms) {
        this.mActivity = mActivity;
        this.fragmentSms = fragmentSms;

        initViews();
    }

    private void initViews() {

        mLinearLayoutManager = new LinearLayoutManager(mActivity);
        fragmentSms.recyclerSms.setLayoutManager(mLinearLayoutManager);

        smsHomeAdapter = new SmsHomeAdapter(FragmentSmsHelper.this, mActivity,fragmentSms);
        fragmentSms.recyclerSms.setAdapter(smsHomeAdapter);


        fragmentSms.btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkAvailable()){
                    getSms(GlobalConstants.COMMON.LANGUAGE_SELECTED, "", "");
                }else {
                    Toast.makeText(mActivity,mActivity.getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
        fragmentSms.swipeRefreshSms.setOnRefreshListener(() -> {
            if (Utils.isNetworkAvailable()){
                currentPage = PAGE_START;
                fragmentSms.recyclerSms.setVisibility(View.VISIBLE);
                fragmentSms.tvNoDataFound.setVisibility(View.GONE);
                fragmentSms.btnTryAgain.setVisibility(View.GONE);
                if (smsHomeAdapter!=null)
                    smsHomeAdapter.resetList();
                getSms(GlobalConstants.COMMON.LANGUAGE_SELECTED, "", "");
            }else {
                fragmentSms.swipeRefreshSms.setRefreshing(false);
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
            }
        });

        if (Utils.isNetworkAvailable()) {
            fragmentSms.btnTryAgain.setVisibility(View.GONE);
            fragmentSms.tvNoDataFound.setVisibility(View.GONE);
            getSms(AppController.LANGUAGE_SELECTED, "", "");
        }else {
            fragmentSms.btnTryAgain.setVisibility(View.VISIBLE);
            fragmentSms.tvNoDataFound.setVisibility(View.VISIBLE);
            fragmentSms.tvNoDataFound.setText(mActivity.getResources().getString(R.string.internet_error));
            fragmentSms.recyclerSms.setVisibility(View.GONE);
        }


    }
    private void loadNextPage() {
        if (isLoading) {
            if (currentPage <= total_pages) {
                getSms(GlobalConstants.COMMON.LANGUAGE_SELECTED, fragmentSms.subCatSlug,fragmentSms.selectedCategory);
                isLoading = false;
            }
        }
    }
    private void getSms(String language, String slug, String selectedCategory) {
        Call<SmsResponseModel> call = mInterface_method.getSmsList(AppController.LANGUAGE_SELECTED, fragmentSms.subCatSlug, currentPage, fragmentSms.selectedCategory);

        Dialog progressDialog = Utils.getProgressDialog(mActivity);
        progressDialog.show();
        call.enqueue(new Callback<SmsResponseModel>() {
            @Override
            public void onResponse(Call<SmsResponseModel> call, Response<SmsResponseModel> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

                if (fragmentSms.swipeRefreshSms != null && fragmentSms.swipeRefreshSms.isRefreshing())
                    fragmentSms.swipeRefreshSms.setRefreshing(false);

                if (response.isSuccessful() && response.body() != null) {

                    mSubListLayoutManager = new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false);
                    fragmentSms.rvSubCategorySms.setLayoutManager(mSubListLayoutManager);
                    fragmentSms.rvSubCategorySms.setAdapter(new SmsCategoriesAdapter(response.body().getFeaturedCategories(), mActivity, new SmsCategoriesAdapter.HomeCategoryClickListener() {
                        @Override
                        public void onItemClicked(SmsFeaturedCategory model) {
                            fragmentSms.enterSubCategorySms(true,model.getSlug());
                        }
                    }));

                    total_pages = (int)response.body().getTotal() / response.body().getPerPage() ;
                    currentPage =response.body().getCurrentPage();
                    currentPage = currentPage+1;

                    if (response.body().getData() != null && response.body().getData().size() > 0){

                        fragmentSms.recyclerSms.setVisibility(View.VISIBLE);
                        fragmentSms.tvNoDataFound.setVisibility(View.GONE);
                        smsHomeAdapter.addAll(response.body().getData());

                        fragmentSms.recyclerSms.addOnScrollListener(new PaginationScrollListener(mLinearLayoutManager) {
                            @Override
                            protected void loadMoreItems() {
                                isLoading = true;
                                new Handler().postDelayed(() -> loadNextPage(), 1000);
                            }

                            @Override
                            public int getTotalPageCount() {
                                return total_pages;
                            }

                            @Override
                            public boolean isLastPage() {
                                return false;
                            }

                            @Override
                            public boolean isLoading() {
                                return false;
                            }


                            @Override
                            public void onScrolled() {

                            }
                        });

                        fragmentSms.ivNext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                fragmentSms.rvSubCategorySms.getLayoutManager().scrollToPosition(mSubListLayoutManager.findLastVisibleItemPosition() + 1);
                            }
                        });

                        fragmentSms.ivPrevious.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                fragmentSms.rvSubCategorySms.getLayoutManager().scrollToPosition(mSubListLayoutManager.findFirstVisibleItemPosition() - 1);
                            }
                        });
                    }else {
                        fragmentSms.recyclerSms.setVisibility(View.GONE);
                        fragmentSms.tvNoDataFound.setVisibility(View.VISIBLE);
                        fragmentSms.tvNoDataFound.setText(mActivity.getResources().getString(R.string.no_data_found));
                    }

                }else{
                    fragmentSms.recyclerSms.setVisibility(View.GONE);
                    fragmentSms.tvNoDataFound.setVisibility(View.VISIBLE);
                    fragmentSms.tvNoDataFound.setText(mActivity.getResources().getString(R.string.no_data_found));
                }
            }

            @Override
            public void onFailure(Call<SmsResponseModel> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

                if (fragmentSms.swipeRefreshSms != null && fragmentSms.swipeRefreshSms.isRefreshing())
                    fragmentSms.swipeRefreshSms.setRefreshing(false);

                fragmentSms.recyclerSms.setVisibility(View.GONE);
                fragmentSms.tvNoDataFound.setVisibility(View.VISIBLE);
                fragmentSms.tvNoDataFound.setText(mActivity.getResources().getString(R.string.no_data_found));
            }
        });
    }


    public void addSmsToFav(SmsDetailModel obj, int position, Dialog progressBar, CheckBox cbLike) {

        AddFavouriteRequest addFavouriteRequest = new AddFavouriteRequest();
        addFavouriteRequest.setDeviceId(Utils.getMyDeviceId(mActivity));
        addFavouriteRequest.setType("sms");
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
                                obj.setFav_count(obj.getFav_count() + 1);
                                setFavItemToModel(position, addFavouriteRequest, obj, jsonObject.getInt("fav_id"));

                            }
                        }

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utils.showLog("onFailure", "onFailure");
            }
        });




    }


    private void setFavItemToModel(int position, AddFavouriteRequest addFavouriteRequest, SmsDetailModel obj, int fav_id) {
        SmsFavouriteModel smsFavouriteModel = new SmsFavouriteModel();
        smsFavouriteModel.setDeviceId(addFavouriteRequest.getDeviceId());
        smsFavouriteModel.setItemId(String.valueOf(addFavouriteRequest.getItemId()));
        smsFavouriteModel.setId(fav_id);

        ArrayList<SmsDetailModel> pagedLists = null;
        pagedLists = smsHomeAdapter.getCurrentList();
        if (obj.getmFavourite() != null && obj.getmFavourite().size() != 0) {
            obj.getmFavourite().add(0, smsFavouriteModel);
        } else {
            List<SmsFavouriteModel> favouriteModelList = new ArrayList<>();
            favouriteModelList.add(0, smsFavouriteModel);
            obj.setmFavourite(favouriteModelList);
        }
        pagedLists.get(position).setmFavourite(obj.getmFavourite());
        smsHomeAdapter.updateList(pagedLists); //paging method

    }

    public void removeFromFav(SmsDetailModel obj, int position, int id, Dialog progressBar, CheckBox cbLike) {

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
                                progressBar.dismiss();
                                cbLike.setClickable(true);

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

            }
        });


    }

    private void removeFavItemFromModel(int position, SmsDetailModel obj) {
        ArrayList<SmsDetailModel> pagedLists = null;
        pagedLists = smsHomeAdapter.getCurrentList();

        pagedLists.get(position).setmFavourite(null);
        obj.setFav_count(obj.getFav_count() - 1);
        smsHomeAdapter.updateList(pagedLists);

    }

}
