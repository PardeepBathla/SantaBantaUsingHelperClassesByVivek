package com.app.santabanta.Helper;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.santabanta.Adapter.JokesCategoriesAdapter;
import com.app.santabanta.Adapter.JokesHomeAdapter;
import com.app.santabanta.AppController;
import com.app.santabanta.Fragment.FragmentJokes;
import com.app.santabanta.Modals.AddFavouriteRequest;
import com.app.santabanta.Modals.JokesDataModel;
import com.app.santabanta.Modals.JokesDetailModel;
import com.app.santabanta.Modals.JokesFavouriteModel;
import com.app.santabanta.Modals.JokesFeaturedCategory;
import com.app.santabanta.RestClient.Webservices;
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


public class FragmentJokesHelper {

    private static final int PAGE_START = 1;
    JokesHomeAdapter mJokesHomeAdapter;
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
//                fragmentJokes.IS_SUB_CAT = false;
//                fragmentJokes.slugName = "";
                getJokes(AppController.LANGUAGE_SELECTED, fragmentJokes.slugName);
            }
        });

        getJokes(AppController.LANGUAGE_SELECTED, fragmentJokes.slugName);
    }

    private void getJokes(String language, String slug) {
        Call<JokesDataModel> call;
        if (fragmentJokes.IS_SUB_CAT)
            call = mInterface_method.getJokesListNew(slug, 1);
        else
            call = mInterface_method.getJokesList(language, slug, 1);
        Dialog dialog = Utils.getProgressDialog(mActivity);
        dialog.show();
        call.enqueue(new Callback<JokesDataModel>() {
            @Override
            public void onResponse(Call<JokesDataModel> call, Response<JokesDataModel> response) {
                try {
                    if (dialog.isShowing())
                        dialog.dismiss();

                    if (fragmentJokes.swipeRefreshJokes != null && fragmentJokes.swipeRefreshJokes.isRefreshing())
                        fragmentJokes.swipeRefreshJokes.setRefreshing(false);

                    if (response.isSuccessful()) {
                        fragmentJokes.rvSubCategoryJokes.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false));
                        fragmentJokes.rvSubCategoryJokes.setAdapter(new JokesCategoriesAdapter(response.body().getFeaturedCategories(), mActivity, new JokesCategoriesAdapter.JokesCategoryClickListener() {
                            @Override
                            public void onItemClicked(JokesFeaturedCategory model) {
                                fragmentJokes.enterSubCategoryJoke(true, model.getSlug());
                            }
                        }));

                        if (response.body().getData() != null && response.body().getData().size() > 0) {
                            fragmentJokes.recyclerJokes.setLayoutManager(new LinearLayoutManager(mActivity));
                            fragmentJokes.recyclerJokes.setAdapter(new JokesHomeAdapter(response.body().getData(), mActivity,FragmentJokesHelper.this));
                            fragmentJokes.recyclerJokes.setVisibility(View.VISIBLE);
                            fragmentJokes.tvNoDataFound.setVisibility(View.GONE);
                        } else {
                            fragmentJokes.recyclerJokes.setVisibility(View.GONE);
                            fragmentJokes.tvNoDataFound.setVisibility(View.VISIBLE);


                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    fragmentJokes.recyclerJokes.setVisibility(View.GONE);
                    fragmentJokes.tvNoDataFound.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<JokesDataModel> call, Throwable t) {
                try {
                    if (dialog.isShowing())
                        dialog.dismiss();

                    if (fragmentJokes.swipeRefreshJokes != null && fragmentJokes.swipeRefreshJokes.isRefreshing())
                        fragmentJokes.swipeRefreshJokes.setRefreshing(false);

                    fragmentJokes.recyclerJokes.setVisibility(View.GONE);
                    fragmentJokes.tvNoDataFound.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void addJokeTOFav(JokesDetailModel obj, int position, Dialog progressBar, CheckBox cbLike) {

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


    private void setFavItemToModel(int position, AddFavouriteRequest addFavouriteRequest, JokesDetailModel obj, int fav_id) {
        JokesFavouriteModel jokesFavouriteModel = new JokesFavouriteModel();
        jokesFavouriteModel.setDeviceId(addFavouriteRequest.getDeviceId());
        jokesFavouriteModel.setItemId(String.valueOf(addFavouriteRequest.getItemId()));
        jokesFavouriteModel.setId(fav_id);

        ArrayList<JokesDetailModel> pagedLists = null;
        pagedLists = mJokesHomeAdapter.getCurrentList();
        if (obj.getmFavourite() != null && obj.getmFavourite().size() != 0) {
            obj.getmFavourite().add(0, jokesFavouriteModel);
        } else {
            List<JokesFavouriteModel> favouriteModelList = new ArrayList<>();
            favouriteModelList.add(0, jokesFavouriteModel);
            obj.setmFavourite(favouriteModelList);
        }
        pagedLists.get(position).setmFavourite(obj.getmFavourite());
        mJokesHomeAdapter.updateList(pagedLists); //paging method

    }

    public void removeFromFav(JokesDetailModel obj, int position, int id, Dialog progressBar, CheckBox cbLike) {

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

    private void removeFavItemFromModel(int position, JokesDetailModel obj) {
        ArrayList<JokesDetailModel> pagedLists = null;
        pagedLists = mJokesHomeAdapter.getCurrentList();

        pagedLists.get(position).setmFavourite(null);
        obj.setFav_count(obj.getFav_count() - 1);
        mJokesHomeAdapter.updateList(pagedLists);

    }
}
