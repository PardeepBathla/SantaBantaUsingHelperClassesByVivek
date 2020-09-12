package com.app.santabanta.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.santabanta.Adapter.HomeCategoriesAdapter;
import com.app.santabanta.Adapter.HomeItemAdapter;
import com.app.santabanta.Adapter.MemesItemAdapter;
import com.app.santabanta.AppController;
import com.app.santabanta.Callbacks.CategoriesCallback;
import com.app.santabanta.Callbacks.MemesCallback;
import com.app.santabanta.Fragment.FragmentMemes;
import com.app.santabanta.Modals.AddFavouriteRequest;
import com.app.santabanta.Modals.FeaturedCategory;
import com.app.santabanta.Modals.NavMenuResponse;
import com.app.santabanta.Modals.memesModel.MemesDetailModel;
import com.app.santabanta.Modals.memesModel.MemesFavouriteModel;
import com.app.santabanta.Modals.memesModel.MemesResposeModel;
import com.app.santabanta.R;
import com.app.santabanta.RestClient.Webservices;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.MemesExoPlayerRecyclerView;
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
    private FragmentMemes mFragment;
    MemesItemAdapter memesItemAdapter;
    HomeCategoriesAdapter mHomeCategoriesAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout smsRootLayout;
    TextView tvNo_data_found;
    ProgressBar progressBar;
    View view;
    ArrayList<MemesDetailModel> memesList = new ArrayList<>();
    Context context;
    private Webservices mInterface_method = AppController.getRetroInstance().create(Webservices.class);
    private SwipeRefreshLayout swipeContainer;
    private LinearLayoutManager mLinearLayoutManager;
    private MemesExoPlayerRecyclerView recyclerMemes;

    public FragmentMemesHelper(Context context, FragmentMemes mFragment, View view) {
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

        memesItemAdapter = new MemesItemAdapter(mFragment.getActivity(),mFragment,progressBar,memesList);
        memesItemAdapter.setHasStableIds(true);
    }

    private void getApiData() {

        progressBar.setVisibility(View.VISIBLE);
        getMemesData(new MemesCallback() {
            @Override
            public void onMemesFetched(MemesResposeModel response) {


                recyclerMemes.setMediaObjects(response.getData());
                recyclerMemes.setAdapter(memesItemAdapter);
                recyclerMemes.setNestedScrollingEnabled(false);
                recyclerMemes.setHasFixedSize(false);

                progressBar.setVisibility(View.VISIBLE);
                memesItemAdapter.updateList((ArrayList<MemesDetailModel>) response.getData());





            }

        });
    }

    private void findViews() {

        progressBar =view.findViewById(R.id.progress_bar);
        swipeContainer =view.findViewById(R.id.swipeContainer);
        recyclerMemes =view.findViewById(R.id.recyclerMemes);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getMemesData(new MemesCallback() {
                    @Override
                    public void onMemesFetched(MemesResposeModel body) {
                        memesItemAdapter.updateList((ArrayList<MemesDetailModel>) body.getData());
                    }
                });
            }
        });
    }

    private void getMemesData(MemesCallback memesCallback){
        Call<MemesResposeModel> call  = null;
        if (FragmentMemes.IS_FROM_MENU) {
            call = mInterface_method.getMemesNewList(FragmentMemes.subcat_slug_name, 1);
        }else {
            call = mInterface_method.getMemesList(GlobalConstants.COMMON.LANGUAGE_SELECTED, 1);
        }
        call.enqueue(new Callback<MemesResposeModel>() {
            @Override
            public void onResponse(Call<MemesResposeModel> call, Response<MemesResposeModel> response) {
                if (response.isSuccessful())
                    memesCallback.onMemesFetched(response.body());

            }

            @Override
            public void onFailure(Call<MemesResposeModel> call, Throwable t) {
                Log.e("onFailure","onFailure");
            }
        });
    }


    /*TODO */
    /*public void addJokeToFav(MemesDetailModel obj, int position, CheckBox cbLike, ProgressBar progressBar) {

        AddFavouriteRequest addFavouriteRequest = new AddFavouriteRequest();
        addFavouriteRequest.setDeviceId(Utils.getMyDeviceId(context));
        addFavouriteRequest.setType("memes");
        addFavouriteRequest.setItemId(obj.getId().intValue());

        mViewModel.postFavMeme(addFavouriteRequest).observe(context, new Observer<ResponseBody>() {
            @Override
            public void onChanged(ResponseBody responseBody) {
                if (responseBody != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
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


        });

    }*/

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


}
