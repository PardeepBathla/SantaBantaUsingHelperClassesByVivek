package com.app.santabanta.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.santabanta.Adapter.HomeCategoriesAdapter;
import com.app.santabanta.Adapter.MemesCategoryAdapter;
import com.app.santabanta.Adapter.MemesItemAdapter;
import com.app.santabanta.AppController;
import com.app.santabanta.Callbacks.MemesCallback;
import com.app.santabanta.Fragment.FragmentMemes;
import com.app.santabanta.Modals.AddFavouriteRequest;
import com.app.santabanta.Modals.memesModel.MemesDetailModel;
import com.app.santabanta.Modals.memesModel.MemesFavouriteModel;
import com.app.santabanta.Modals.memesModel.MemesFeaturedCategory;
import com.app.santabanta.Modals.memesModel.MemesResposeModel;
import com.app.santabanta.R;
import com.app.santabanta.RestClient.Webservices;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.MemesExoPlayerRecyclerView;
import com.app.santabanta.Utils.PaginationScrollListener;
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

public class FragmentMemesHelper {
    public SharedPreferences pref;
    private MemesItemAdapter memesItemAdapter;
    private HomeCategoriesAdapter mHomeCategoriesAdapter;
    private RelativeLayout smsRootLayout;
    private TextView tvNoDataFound;
    private View view;
    private ArrayList<MemesDetailModel> memesList = new ArrayList<>();
    private Activity context;
    private FragmentMemes mFragment;
    private Webservices mInterface_method = AppController.getRetroInstance().create(Webservices.class);
    private SwipeRefreshLayout swipeContainer;
    private LinearLayoutManager mLinearLayoutManager;
    private MemesExoPlayerRecyclerView recyclerMemes;
    private RecyclerView rvSubCategory;
    private LinearLayoutManager mSubListLayoutManager;
    private ImageView ivPrevious;
    private ImageView ivNext;
    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private int pageCount, total_pages, next_page;
    private boolean isLoading = false;

    public FragmentMemesHelper(Activity context, FragmentMemes mFragment, View view) {
        this.mFragment = mFragment;
        this.view = view;
        this.context = context;
        pref = Utils.getSharedPref(mFragment.getActivity());
        findViews();
        getApiData();
        setAdapter();
    }

    private void setAdapter() {

        mLinearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerMemes.setLayoutManager(mLinearLayoutManager);

        memesItemAdapter = new MemesItemAdapter(mFragment.getActivity(), mFragment, null, this);
        memesItemAdapter.setHasStableIds(true);
        recyclerMemes.setAdapter(memesItemAdapter);
        recyclerMemes.setNestedScrollingEnabled(false);
        recyclerMemes.setHasFixedSize(false);
    }

    private void loadNextPage() {
        if (isLoading) {
            if (currentPage <= total_pages) {
                getMemesData(new MemesCallback() {
                    @Override
                    public void onMemesFetched(MemesResposeModel body) {
                        isLoading = false;

                    }
                });
            }
        }
    }

    private void getApiData() {

//        progressBar.setVisibility(View.VISIBLE);
        getMemesData(new MemesCallback() {
            @Override
            public void onMemesFetched(MemesResposeModel response) {


                if (response.getData() != null && response.getData().size()>0){
                    if (swipeContainer.isRefreshing()) {
                        swipeContainer.setRefreshing(false);
                    }

                    total_pages = response.getTotal();
                    currentPage =response.getCurrentPage();
                    currentPage = currentPage+1;

                    recyclerMemes.setMediaObjects(response.getData());
                    recyclerMemes.addOnScrollListener(new PaginationScrollListener(mLinearLayoutManager) {
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



//                    progressBar.setVisibility(View.VISIBLE);
                    memesItemAdapter.addAll((ArrayList<MemesDetailModel>) response.getData());

                    recyclerMemes.setVisibility(View.VISIBLE);
                    tvNoDataFound.setVisibility(View.GONE);
                    recyclerMemes.setOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                        }

                        @Override
                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                                super.onScrolled(recyclerView, dx, dy);
                            swipeContainer.setEnabled(mLinearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
                        }
                    });
                }else {
                    recyclerMemes.setVisibility(View.GONE);
                    tvNoDataFound.setVisibility(View.VISIBLE);
                }


            }

        });
    }

    private void findViews() {

        ivNext = view.findViewById(R.id.ivNext);
        ivPrevious = view.findViewById(R.id.ivPrevious);
        rvSubCategory = view.findViewById(R.id.rvSubCategory);
        tvNoDataFound = view.findViewById(R.id.tvNoDataFound);
//        progressBar = view.findViewById(R.id.progress_bar);
        swipeContainer = view.findViewById(R.id.swipeContainer);
        recyclerMemes = view.findViewById(R.id.recyclerMemes);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                mFragment.IS_FROM_MENU = false;
//                mFragment.subcat_slug_name = "";
                currentPage = PAGE_START;
                getApiData();

            }
        });
    }

    private void getMemesData(MemesCallback memesCallback) {
        Call<MemesResposeModel> call = null;
        if (mFragment.IS_FROM_MENU) {
            call = mInterface_method.getMemesNewList(mFragment.subcat_slug_name, currentPage);
        } else {
            call = mInterface_method.getMemesList(GlobalConstants.COMMON.LANGUAGE_SELECTED, currentPage);
        }
        call.enqueue(new Callback<MemesResposeModel>() {
            @Override
            public void onResponse(Call<MemesResposeModel> call, Response<MemesResposeModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        memesCallback.onMemesFetched(response.body());

                        mSubListLayoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
                        rvSubCategory.setLayoutManager(mSubListLayoutManager);
                        ivNext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rvSubCategory.getLayoutManager().scrollToPosition(mSubListLayoutManager.findLastVisibleItemPosition() + 1);
                            }
                        });

                        ivPrevious.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rvSubCategory.getLayoutManager().scrollToPosition(mSubListLayoutManager.findFirstVisibleItemPosition() - 1);
                            }
                        });
                        rvSubCategory.setAdapter(new MemesCategoryAdapter(context, response.body().getFeaturedCategories(), new MemesCategoryAdapter.MemeCategoryClickListener() {
                            @Override
                            public void onClicked(MemesFeaturedCategory model) {
                                mFragment.enterSubCategoryMemes(true,model.getSlug());
                            }
                        }));
                    }
                }

            }

            @Override
            public void onFailure(Call<MemesResposeModel> call, Throwable t) {
                Log.e("onFailure", "onFailure");
            }
        });
    }


    /*TODO */
    public void addJokeToFav(MemesDetailModel obj, int position, CheckBox cbLike, ProgressBar progressBar) {

        AddFavouriteRequest addFavouriteRequest = new AddFavouriteRequest();
        addFavouriteRequest.setDeviceId(Utils.getMyDeviceId(context));
        addFavouriteRequest.setType("memes");
        addFavouriteRequest.setItemId(obj.getId().intValue());


        Call<ResponseBody> call = null;

        call = mInterface_method.saveFavouriteJoke(addFavouriteRequest);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    if (response != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.has("status")) {
                                String status = jsonObject.getString("status");

                                if (status.equals("success")) {
                                    progressBar.setVisibility(View.GONE);
                                    cbLike.setClickable(true);
                                    obj.setFavCount(obj.getFavCount() + 1);
                                    setFavItemToModel(position, addFavouriteRequest, obj, jsonObject.getInt("fav_id"));
                                    Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure", "onFailure");
            }
        });


    }

    private void setFavItemToModel(int position, AddFavouriteRequest addFavouriteRequest, MemesDetailModel obj, int fav_id) {
        MemesFavouriteModel memesFavouriteModel = new MemesFavouriteModel();
        memesFavouriteModel.setDeviceId(addFavouriteRequest.getDeviceId());
        memesFavouriteModel.setItemId(String.valueOf(addFavouriteRequest.getItemId()));
        memesFavouriteModel.setId(fav_id);

        ArrayList<MemesDetailModel> pagedLists = null;
        pagedLists = memesItemAdapter.getCurrentList();
        if (obj.getFavourites() != null && obj.getFavourites().size() != 0) {
            obj.getFavourites().add(0, memesFavouriteModel);
        } else {
            List<MemesFavouriteModel> favouriteModelList = new ArrayList<>();
            favouriteModelList.add(0, memesFavouriteModel);
            obj.setFavourites(favouriteModelList);
        }
        pagedLists.get(position).setFavourites(obj.getFavourites());
        memesItemAdapter.updateList(pagedLists); //paging method

    }


    public void removeFromFav(MemesDetailModel obj, int id, int position, CheckBox cbLike, ProgressBar progressBar) {

        Call<ResponseBody> call = null;

        call = mInterface_method.removeJokeFromFav(id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    if (response != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.has("status")) {
                                String status = jsonObject.getString("status");
                                if (status.equals("success")) {
                                    cbLike.setClickable(true);
                                    progressBar.setVisibility(View.GONE);
                                    removeFavItemFromModel(position, obj);
                                    Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void removeFavItemFromModel(int position, MemesDetailModel obj) {
        ArrayList<MemesDetailModel> pagedLists = null;
        pagedLists = memesItemAdapter.getCurrentList();

        pagedLists.get(position).setFavourites(null);
        obj.setFavCount(obj.getFavCount() - 1);
        memesItemAdapter.updateList(pagedLists); //paging method
        memesItemAdapter.notifyDataSetChanged();
    }


}
