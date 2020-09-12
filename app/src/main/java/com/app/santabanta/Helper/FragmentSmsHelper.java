package com.app.santabanta.Helper;

import android.app.Activity;
import android.app.Dialog;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.Adapter.HomeCategoriesAdapter;
import com.app.santabanta.Adapter.SmsCategoriesAdapter;
import com.app.santabanta.Adapter.SmsHomeAdapter;
import com.app.santabanta.AppController;
import com.app.santabanta.Fragment.FragmentHome;
import com.app.santabanta.Fragment.FragmentSms;
import com.app.santabanta.Modals.FeaturedCategory;
import com.app.santabanta.Modals.SmsFeaturedCategory;
import com.app.santabanta.Modals.SmsResponseModel;
import com.app.santabanta.RestClient.Webservices;
import com.app.santabanta.Utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSmsHelper {

    private static final int PAGE_START = 1;

    private Activity mActivity;
    private Webservices mInterface_method = AppController.getRetroInstance().create(Webservices.class);
    private FragmentSms fragmentSms;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 10;
    private int currentPage = PAGE_START;
    private LinearLayoutManager mLinearLayoutManager;


    public FragmentSmsHelper(Activity mActivity, FragmentSms fragmentSms) {
        this.mActivity = mActivity;
        this.fragmentSms = fragmentSms;
        initViews();
    }

    private void initViews() {

        Call<SmsResponseModel> call = mInterface_method.getSmsList("English","",1,"");
        Dialog progressDialog = Utils.getProgressDialog(mActivity);
        progressDialog.show();
        call.enqueue(new Callback<SmsResponseModel>() {
            @Override
            public void onResponse(Call<SmsResponseModel> call, Response<SmsResponseModel> response) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

                if (response.isSuccessful() && response.body() != null) {

                    fragmentSms.rvSubCategorySms.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL,false));
                    fragmentSms.rvSubCategorySms.setAdapter(new SmsCategoriesAdapter(response.body().getFeaturedCategories(), mActivity, new SmsCategoriesAdapter.HomeCategoryClickListener() {
                        @Override
                        public void onItemClicked(SmsFeaturedCategory model) {

                        }
                    }));


                    fragmentSms.recyclerSms.setLayoutManager(new LinearLayoutManager(mActivity));
                    fragmentSms.recyclerSms.setAdapter(new SmsHomeAdapter(response.body().getData(), mActivity));
                }
            }

            @Override
            public void onFailure(Call<SmsResponseModel> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
    }
}
