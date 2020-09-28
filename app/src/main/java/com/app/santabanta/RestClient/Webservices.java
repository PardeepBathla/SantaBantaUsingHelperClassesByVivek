package com.app.santabanta.RestClient;

import com.app.santabanta.Modals.AddFavouriteRequest;
import com.app.santabanta.Modals.HomeDetailsModel;
import com.app.santabanta.Modals.JokesDataModel;
import com.app.santabanta.Modals.NavMenuResponse;
import com.app.santabanta.Modals.SearchResponse;
import com.app.santabanta.Modals.SmsResponseModel;
import com.app.santabanta.Modals.memesModel.MemesResposeModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Webservices {

    @GET("newsms/{slug}")
    Call<SmsResponseModel> getCategorySms(@Path(value = "slug", encoded = true) String slug, @Query("page") int page_num);

    @GET("categories/all/{language}")
    Call<NavMenuResponse> getNavList(@Path("language") String lang) ;

    @GET("home/{language}")
    Call<HomeDetailsModel> getHomeList(@Path("language") String lang, @Query("next_page") int page);

    @POST("favourites/save")
    Call<ResponseBody> saveFavouriteJoke(@Body AddFavouriteRequest addFavouriteRequest);

    @GET("favourites/delete/{id}")
    Call<ResponseBody> removeJokeFromFav(@Path("id") int id);

    @GET("sms/latest/{language}")
    Call<SmsResponseModel> getSmsList(@Path("language") String lang, @Query(value = "slug1", encoded = true) String slug, @Query("page") int page_num, @Query("selected_category") String selected_category);

    @GET("newjokes/{slug}")
    Call<JokesDataModel> getJokesListNew(@Path(value = "slug", encoded =true) String slug, @Query("page") int page_num);

    @GET("jokes/latest/{language}")
    Call<JokesDataModel> getJokesList(@Path("language") String lang, @Query(value = "name", encoded = true) String slugname, @Query("page") int page_num);

    @GET("newmemes/{slug}")
    Call<MemesResposeModel> getMemesNewList(@Path(value = "slug", encoded =true) String slug, @Query("page") int page_num);

    @GET("memes/latest/{language}")
    Call<MemesResposeModel> getMemesList(@Path("language") String lang, @Query("page") int page_num);

    @GET("search")
    Call<SearchResponse> search(@Query(value = "q", encoded =true) String query);

}
