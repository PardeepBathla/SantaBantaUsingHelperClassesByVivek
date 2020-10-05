package com.app.santabanta.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.Callbacks.BitmapLoadedCallback;
import com.app.santabanta.Helper.FragmentHomeHelper;
import com.app.santabanta.Modals.Favourite;
import com.app.santabanta.Modals.HomeDetailList;
import com.app.santabanta.R;
import com.app.santabanta.Utils.CheckPermissions;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.LoadImageBitmap;
import com.app.santabanta.Utils.ShareableIntents;
import com.app.santabanta.Utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemeVideoPlayerHolder extends RecyclerView.ViewHolder implements BitmapLoadedCallback {
    private final ShareableIntents shareableIntents;
    @BindView(R.id.ivMediaCoverImage)
    public ImageView ivMediaCoverImage;
    @BindView(R.id.ivVolumeControl)
    public ImageView ivVolumeControl;
    @BindView(R.id.mediaContainer)
    public FrameLayout mediaContainer;
    @BindView(R.id.pbBuffering)
    public ProgressBar pbBuffering;
    public RequestManager requestManager;
    @BindView(R.id.llbreadcrumbs)
    LinearLayout llbreadcrumbs;
    @BindView(R.id.ll_share_home)
    LinearLayout llShareMemes;
    @BindView(R.id.ll_share_options_home)
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

    private SharedPreferences pref;
    HomeDetailList imageTOBeShared;
    private boolean isSharelayoutVisible = false;
    private Activity mActivity;
    FragmentHomeHelper fragmentHomeHelper;


    public MemeVideoPlayerHolder(@NonNull View itemView,Activity mActivity,FragmentHomeHelper fragmentHomeHelper) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mActivity = mActivity;
        pref = Utils.getSharedPref(mActivity);
        this.fragmentHomeHelper = fragmentHomeHelper;
        requestManager = Glide.with(mActivity).setDefaultRequestOptions(new RequestOptions());
        shareableIntents = new ShareableIntents(mActivity);
    }

    void bindData(HomeDetailList model, int position) {
        this.itemView.setTag(this);
        imageTOBeShared = model;
        MemesImageItemListeners(model, position);
    }

    private void MemesImageItemListeners(HomeDetailList obj, int position) {

        ivWhatsapp.setOnClickListener(v -> {
            shareLayoutGone();
            Utils.vibrate(mActivity);
            if (CheckPermissions.isStoragePermissionGranted(mActivity)) {
                BitmapConversion(obj, GlobalConstants.COMMON.WHATSAPP);
            }
        });
        ivFacebook.setOnClickListener(v -> {
            shareLayoutGone();
            Utils.vibrate(mActivity);
            shareableIntents.shareOnFbMesenger(imageTOBeShared.getImage());

        });
        ivTwitter.setOnClickListener(v -> {
            shareableIntents.shareOnTwitter(v, imageTOBeShared.getImage());
            shareLayoutGone();
            Utils.vibrate(mActivity);
        });
        ivInstagram.setOnClickListener(v -> {
            shareableIntents.shareOnInstagram(imageTOBeShared.getImage());
            shareLayoutGone();
            Utils.vibrate(mActivity);
        });
        ivPintrest.setOnClickListener(v -> {
            shareableIntents.shareOnPintrest(v, imageTOBeShared.getImage());
            shareLayoutGone();
            Utils.vibrate(mActivity);
        });
        ivSnapchat.setOnClickListener(v -> {
            shareableIntents.shareOnSnapChat(imageTOBeShared.getImage());
            shareLayoutGone();
            Utils.vibrate(mActivity);
        });
       /* binding.ivSms.setOnClickListener(v -> {
            shareLayoutGone();

        });*/
       /* binding.rlSms.setOnClickListener(v -> {
            shareLayoutGone();
        });
*/
        cbLike.setOnCheckedChangeListener((buttonView, isChecked) -> {

            Utils.vibrate(mActivity);

            Dialog dialog = Utils.getProgressDialog(mActivity);
            dialog.show();
            cbLike.setClickable(false);
            if (isChecked) {
                fragmentHomeHelper.addToFav(obj, position, "sms", cbLike, dialog);
            } else {
                for (Favourite favouriteModel : obj.getFavourites()) {
                    if (favouriteModel.getDeviceId().equals(Utils.getMyDeviceId(mActivity))) {
                        fragmentHomeHelper.removeFromFav(obj, favouriteModel.getId(), position, cbLike, dialog);
                        break;

                    }
                }

            }
            //FUNCTIONALITY NOT IN SCOPE FOR THE TIME BEING
//                    SmsRepository.getInstance().InsertFavourite(obj,fragmentSms);
        });
        llShareMemes.setOnClickListener(v -> {
            Utils.vibrate(mActivity);
            if (isSharelayoutVisible) {
                shareLayoutGone();
            } else {
                if (pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false)) {
                    llShareMemes.setBackgroundDrawable(mActivity.getDrawable(R.drawable.share_round_corner_bg_light));
                    int padding = (int) mActivity.getResources().getDimension(R.dimen._10sdp);
                    llShareMemes.setPadding(padding, padding, padding, padding);
                    llShareOptionsSms.setVisibility(View.VISIBLE);
                    isSharelayoutVisible = true;
                } else {
                    llShareMemes.setBackgroundDrawable(mActivity.getDrawable(R.drawable.bottom_round_corner_bg));
                    int padding = (int) mActivity.getResources().getDimension(R.dimen._10sdp);
                    llShareMemes.setPadding(padding, padding, padding, padding);
                    llShareOptionsSms.setVisibility(View.VISIBLE);
                    isSharelayoutVisible = true;
                }

            }
        });

    }


    private void BitmapConversion(HomeDetailList obj, String platform) {
        shareLayoutGone();
        try {
            URL url = new URL(obj.getImage());
            new LoadImageBitmap(mActivity, this::onBitmapLoaded, platform).execute(url);

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void shareLayoutGone() {

        isSharelayoutVisible = false;
        llShareMemes.setBackground(null);
        llShareOptionsSms.setVisibility(View.GONE);
    }


    @Override
    public void onBitmapLoaded(Bitmap bitmap, String platform) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(mActivity.getContentResolver(), bitmap, "SantaBantaShare", null);
        Uri imageUri = Uri.parse(path);

        switch (platform) {
            case GlobalConstants.COMMON.WHATSAPP:
                shareableIntents.shareOnWhatsapp(imageUri);
                break;
            /*    case Constants.COMMON.FACEBOOK :
                shareableIntents.shareOnWhatsapp(imageUri);
                break;*/
            case GlobalConstants.COMMON.TWITTER:
//                shareableIntents.shareOnTwi1tter(imageUri);
                break;
               /* case Constants.COMMON.INSTAGRAM :
                shareableIntents.shareOnWhatsapp(imageUri);
                break;
                case Constants.COMMON.PINTREST :
                shareableIntents.shareOnWhatsapp(imageUri);
                break;
                case Constants.COMMON.SNAPCHAT :
                shareableIntents.shareOnWhatsapp(imageUri);
                break;*/
        }
    }

}