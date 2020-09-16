package com.app.santabanta.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.AppController;
import com.app.santabanta.Callbacks.MemesCallback;
import com.app.santabanta.Events.Events;
import com.app.santabanta.Events.GlobalBus;
import com.app.santabanta.Fragment.FragmentMemes;
import com.app.santabanta.Helper.FragmentMemesHelper;
import com.app.santabanta.Modals.AddFavouriteRequest;
import com.app.santabanta.Modals.memesModel.MemesDetailModel;
import com.app.santabanta.Modals.memesModel.MemesFavouriteModel;
import com.app.santabanta.Modals.memesModel.MemesResposeModel;
import com.app.santabanta.R;
import com.app.santabanta.RestClient.Webservices;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.ShareableIntents;
import com.app.santabanta.Utils.Utils;
import com.bumptech.glide.RequestManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PlayerViewHolder extends RecyclerView.ViewHolder {

    private static Context mCtx;
    private final SharedPreferences pref;
    private final ShareableIntents shareableIntents;
    public RequestManager requestManager;
    @BindView(R.id.llbreadcrumbs)
    LinearLayout llbreadcrumbs;
    @BindView(R.id.ll_share_memes)
    LinearLayout llShareMemes;
    @BindView(R.id.ll_share_options_sms)
    LinearLayout llShareOptionsSms;
    @BindView(R.id.cb_like)
    CheckBox cbLike;
    @BindView(R.id.iv_facebook)
    ImageView ivFacebook;
    @BindView(R.id.iv_whatsapp)
    ImageView ivWhatsapp;
    @BindView(R.id.iv_twitter)
    ImageView ivTwitter;
    @BindView(R.id.iv_snapchat)
    ImageView ivSnapchat;
    @BindView(R.id.iv_pintrest)
    ImageView ivPintrest;
    @BindView(R.id.iv_instagram)
    ImageView ivInstagram;
    @BindView(R.id.progressBar)
    public ProgressBar progressBar;

    @BindView(R.id.ivMediaCoverImage)
    public ImageView ivMediaCoverImage;

    @BindView(R.id.ivVolumeControl)
    public ImageView ivVolumeControl;

    @BindView(R.id.pbBuffering)
    public ProgressBar pbBuffering;

    @BindView(R.id.mediaContainer)
    public FrameLayout mediaContainer;

    @BindView(R.id.tv_like_count)
    public TextView tv_like_count;



    MemesDetailModel currentList;
    View parent;
    View memesVideoItemBinding;
    private FragmentMemes memesFragment;
    private boolean isSharelayoutVisible = false;
    private boolean isDialogSharelayoutVisible = false;
    private MemesDetailModel memeTOBeShared;
    private Webservices mInterface_method = AppController.getRetroInstance().create(Webservices.class);
    FragmentMemesHelper fragmentMemesHelper;

    public PlayerViewHolder(Context mCtx, View memesVideoItemBinding, FragmentMemes memesFragment, FragmentMemesHelper fragmentMemesHelper) {
        super(memesVideoItemBinding);
        this.memesVideoItemBinding = memesVideoItemBinding;
        this.memesFragment = memesFragment;
        parent = memesVideoItemBinding;
        PlayerViewHolder.mCtx = mCtx;
        pref = Utils.getSharedPref(mCtx);
        this.fragmentMemesHelper = fragmentMemesHelper;
        ButterKnife.bind(this, memesVideoItemBinding);
        shareableIntents = new ShareableIntents(mCtx);


    }

    private void setBreadCrumbs(View binding, MemesDetailModel obj, int position) {
        if (obj.getBreadcrumbs() != null) {
            TextView[] textView = new TextView[obj.getBreadcrumbs().size()];

            if (llbreadcrumbs.getChildCount() > 0)
                llbreadcrumbs.removeAllViews();


            for (int i = 0; i < obj.getBreadcrumbs().size(); i++) {
                textView[i] = new TextView(mCtx);

                if (i == 0) {
                    textView[i].setText(obj.getBreadcrumbs().get(i).getLabel());

                } else {
                    textView[i].setText(" > " + obj.getBreadcrumbs().get(i).getLabel());

                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT

                );
                llbreadcrumbs.setOrientation(LinearLayout.HORIZONTAL);
                params.setMargins(3, 3, 3, 3);
                textView[i].setLayoutParams(params);
                llbreadcrumbs.addView(textView[i]);

                int finalI = i;
                textView[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
//                        Intent intent = new Intent();
//                        intent.setAction(GlobalConstants.COMMON.SHOW_SMS);
//                        intent.putExtra("id", obj.getCategories().get(0).getCategoryId());
//                        intent.putExtra("slug", obj.getBreadcrumbs().get(finalI).getLink());
//                        mCtx.sendBroadcast(intent);
                        Events.MemesEvent memesEvent= new Events.MemesEvent(obj.getBreadcrumbs().get(finalI).getLink());
                        GlobalBus.getBus().post(memesEvent);
                    }
                });
            }
        }
    }

    public void onBind(MemesDetailModel obj, RequestManager requestManager, int position) {
        this.requestManager = requestManager;
        this.parent.setTag(this);



        if (obj.getFavCount()==0) {
            tv_like_count.setVisibility(View.GONE);
        }else {
            tv_like_count.setVisibility(View.VISIBLE);
            tv_like_count.setText(String.valueOf(obj.getFavCount()));
        }


        memeTOBeShared = obj;
        setBreadCrumbs(memesVideoItemBinding, obj, position);
        if (obj.getFavourites() != null && obj.getFavourites().size() != 0) {
            for (MemesFavouriteModel favouriteModel : obj.getFavourites()) {
                if (favouriteModel.getDeviceId().equals(Utils.getMyDeviceId(mCtx))) {
                    cbLike.setChecked(true);
                    break;

                } else {
                    cbLike.setChecked(false);
                }
            }
        } else {
            cbLike.setChecked(false);
        }

        memesItemListener(obj, position);


    }

    private void memesItemListener(MemesDetailModel obj, int position) {

        ivWhatsapp.setOnClickListener(v -> {
            shareLayoutGone();
            Utils.vibrate(mCtx);
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, obj.getContent() + "\n" + obj.getImage());
            try {
                mCtx.startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Utils.ShowToast(mCtx, "Whatsapp has not been installed.");
            }

        });
        ivFacebook.setOnClickListener(v -> {
            shareLayoutGone();
            Utils.vibrate(mCtx);
            shareableIntents.shareOnFbMesenger(obj.getContent());

        });
        ivTwitter.setOnClickListener(v -> {
            shareableIntents.shareOnTwitter(v, obj.getContent());
            Utils.vibrate(mCtx);
            shareLayoutGone();
        });
        ivInstagram.setOnClickListener(v -> {
            shareableIntents.shareOnInstagram(obj.getContent());
            Utils.vibrate(mCtx);
            shareLayoutGone();
        });
        ivPintrest.setOnClickListener(v -> {
            shareableIntents.shareOnPintrest(v, obj.getContent());
            Utils.vibrate(mCtx);
            shareLayoutGone();
        });
        ivSnapchat.setOnClickListener(v -> {
            shareableIntents.shareOnSnapChat(obj.getContent());
            Utils.vibrate(mCtx);
            shareLayoutGone();
        });

      /*  memesVideoItemBinding.ivMediaCoverImage.setOnClickListener(v -> {
            shareLayoutGone();
            fullScreenDialog(mCtx,obj, position, memesVideoItemBinding.cbLike.isChecked());
        });*/
        cbLike.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) {
                progressBar.setVisibility(View.VISIBLE);
                cbLike.setClickable(false);
                /*TODO Like Dislike and share*/
                onFavCheckChanged(isChecked, obj, position, cbLike, progressBar);
            }
            //FUNCTIONALITY NOT IN SCOPE FOR THE TIME BEING
            //                    SmsRepository.getInstance().InsertFavourite(obj,fragmentSms);
        });


        llShareMemes.setOnClickListener(v -> {

            Utils.vibrate(mCtx);

            if (isSharelayoutVisible) {
                shareLayoutGone();
            } else {
                llShareMemes.setBackgroundDrawable(mCtx.getDrawable(pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false) ? R.drawable.share_round_corner_bg_light : R.drawable.bottom_round_corner_bg));
                int padding = (int) mCtx.getResources().getDimension(R.dimen._10sdp);
                llShareMemes.setPadding(padding, padding, padding, padding);
                llShareOptionsSms.setVisibility(View.VISIBLE);
                isSharelayoutVisible = true;
            }
        });
    }

    private void shareLayoutGone() {

        isSharelayoutVisible = false;
        llShareMemes.setBackground(null);
        llShareOptionsSms.setVisibility(View.GONE);
    }


    private void DialogshareLayoutGone(LinearLayout ll_share_joke, LinearLayout ll_share_options_joke) {
        isDialogSharelayoutVisible = false;
        ll_share_joke.setBackground(null);
        ll_share_options_joke.setVisibility(View.GONE);
    }

    private void onFavCheckChanged(boolean isChecked, MemesDetailModel obj, int position, CheckBox cbLike, ProgressBar progress_bar) {
        if (isChecked) {
            fragmentMemesHelper.addJokeToFav(obj, position, cbLike, progress_bar);
        } else {
            if (obj.getFavourites() != null) {
                for (MemesFavouriteModel favouriteModel : obj.getFavourites()) {
                    if (favouriteModel.getDeviceId().equals(Utils.getMyDeviceId(mCtx))) {
                        fragmentMemesHelper.removeFromFav(obj, favouriteModel.getId(), position, cbLike, progress_bar);
                        break;

                    }
                }
            }

        }
    }



}
