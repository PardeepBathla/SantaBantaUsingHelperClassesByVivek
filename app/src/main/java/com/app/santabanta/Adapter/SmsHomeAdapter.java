package com.app.santabanta.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.Activites.MainActivity;
import com.app.santabanta.Callbacks.BitmapLoadedCallback;
import com.app.santabanta.Events.Events;
import com.app.santabanta.Events.GlobalBus;
import com.app.santabanta.Fragment.FragmentSms;
import com.app.santabanta.Helper.FragmentSmsHelper;
import com.app.santabanta.Modals.SmsDetailModel;
import com.app.santabanta.Modals.SmsFavouriteModel;
import com.app.santabanta.R;
import com.app.santabanta.Utils.AspectRatioImageView;
import com.app.santabanta.Utils.CheckPermissions;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.LoadImageBitmap;
import com.app.santabanta.Utils.ShareableIntents;
import com.app.santabanta.Utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SmsHomeAdapter extends RecyclerView.Adapter<SmsHomeAdapter.ViewHolder> implements BitmapLoadedCallback {

    private final ShareableIntents shareableIntents;
    private final SharedPreferences pref;
    FragmentSmsHelper fragmentSmsHelper;
    private List<SmsDetailModel> mList;
    private Activity mActivity;
    private boolean isSharelayoutVisible = false;
    private boolean isLoadingAdded = false;
    private FragmentSms fragmentSms;

    public SmsHomeAdapter(FragmentSmsHelper fragmentSmsHelper,Activity mActivity,FragmentSms fragmentSms) {
        mList = new ArrayList<>();
        this.mActivity = mActivity;
        this.fragmentSmsHelper = fragmentSmsHelper;
        shareableIntents = new ShareableIntents(mActivity);
        pref = Utils.getSharedPref(mActivity);
        this.fragmentSms = fragmentSms;
    }

    private SmsDetailModel getItem(int position) {
        return mList.get(position);
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mList.size() - 1;
        SmsDetailModel item = getItem(position);

        if (item != null) {
            mList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        SmsDetailModel newsFeedResponse = new SmsDetailModel();
        add(newsFeedResponse);
    }

    public void add(SmsDetailModel datu) {
        this.mList.add(datu);
        notifyItemInserted(mList.size() - 1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_sms_item, parent, false);
        return new SmsHomeAdapter.ViewHolder(v);
    }

    public void addAll(List<SmsDetailModel> mcList) {
        for (SmsDetailModel mc : mcList) {
            add(mc);
        }
    }

    public void remove(SmsDetailModel datum) {
        int position = mList.indexOf(datum);
        if (position > -1) {
            mList.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private void setBreadCrumbs(SmsDetailModel obj, LinearLayout llbreadcrumbs) {
        if (obj.getBreadcrumbs() !=null){
            TextView[] textView = new TextView[obj.getBreadcrumbs().size()];

            if(llbreadcrumbs.getChildCount()>0)
                llbreadcrumbs.removeAllViews();


            for (int i = 0; i < obj.getBreadcrumbs().size(); i++){
                textView[i] = new TextView(mActivity);

                if (i==0){
                    textView[i].setText(obj.getBreadcrumbs().get(i).getLabel());

                }else {
                    textView[i].setText(" > "+obj.getBreadcrumbs().get(i).getLabel());

                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT

                );
                llbreadcrumbs.setOrientation(LinearLayout.HORIZONTAL);
                params.setMargins(3,3,3,3);
                textView[i].setLayoutParams(params);
                llbreadcrumbs.addView(textView[i]);
                String slug = obj.getBreadcrumbs().get(i).getLink();
                textView[i].setOnClickListener(v -> {
//                    Events.SMSEvent onFileSelected = new Events.SMSEvent(slug,"Veg");
//                    GlobalBus.getBus().post(onFileSelected);
                    fragmentSms.enterSubCategorySms(true, slug, "Veg");
//                    mActivity.sendBroadcast(new Intent().setAction(GlobalConstants.INTENT_PARAMS.NAVIGATE_FROM_HOME)
//                            .putExtra(GlobalConstants.INTENT_PARAMS.NAVIGATE_TYPE, "sms").putExtra(GlobalConstants.INTENT_PARAMS.NAVIGATE_SLUG, slug));
                });
            }
        }
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

    public ArrayList<SmsDetailModel> getCurrentList() {
        return (ArrayList<SmsDetailModel>) mList;
    }

    public void updateList(ArrayList<SmsDetailModel> pagedLists) {
        mList = pagedLists;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivMeme)
        AspectRatioImageView ivMeme;
        @BindView(R.id.cb_like)
        CheckBox cb_like;
        @BindView(R.id.tv_share_count)
        TextView tvShareCount;
        @BindView(R.id.share)
        ImageView share;
        @BindView(R.id.tv_categories)
        TextView tvCategories;
        @BindView(R.id.ll_share_options_home)
        LinearLayout ll_share_options_home;
        @BindView(R.id.iv_whatsapp)
        ImageView iv_whatsapp;
        @BindView(R.id.iv_facebook)
        ImageView iv_facebook;
        @BindView(R.id.iv_twitter)
        ImageView iv_twitter;
        @BindView(R.id.iv_instagram)
        ImageView iv_instagram;
        @BindView(R.id.iv_pintrest)
        ImageView iv_pintrest;
        @BindView(R.id.iv_snapchat)
        ImageView iv_snapchat;
        @BindView(R.id.llbreadcrumbs)
        LinearLayout llbreadcrumbs;
        @BindView(R.id.ll_share_home)
        LinearLayout ll_share_home;
        @BindView(R.id.rl_sms)
        RelativeLayout rl_sms;
        @BindView(R.id.tv_like_count)
        TextView tv_like_count;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindData(SmsDetailModel model, int position) {
            Log.e("sms_url",model.getImage());
           /* if (model.getFav_count() == 0) {
                tv_like_count.setVisibility(View.GONE);
            } else {
                tv_like_count.setVisibility(View.GONE);
                tv_like_count.setText(String.valueOf(model.getFav_count()));
            }*/

            Utils.loadGlideImage(mActivity, ivMeme, model.getImage());
            smsItemListeners(model, position);
            setBreadCrumbs(model, llbreadcrumbs);
            if (model.getmFavourite() != null && model.getmFavourite().size() != 0) {
                for (SmsFavouriteModel favouriteModel : model.getmFavourite()) {
                    if (favouriteModel.getDeviceId().equals(Utils.getMyDeviceId(mActivity.getApplicationContext()))) {
                        cb_like.setChecked(true);
                        break;

                    } else {
                        cb_like.setChecked(false);
                    }
                }
            } else {
                cb_like.setChecked(false);
            }


            ll_share_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ll_share_options_home.getVisibility() == View.VISIBLE) {
                        ll_share_options_home.setVisibility(View.GONE);
                    } else {
                        ll_share_options_home.setVisibility(View.VISIBLE);
                    }
                }
            });
        }



        private void BitmapConversion(SmsDetailModel obj, String platform) {
            shareLayoutGone();
            try {
                URL url = new URL(obj.getImage());
                new LoadImageBitmap(mActivity,SmsHomeAdapter.this::onBitmapLoaded, platform).execute(url);

            } catch (IOException e) {
                System.out.println(e);
            }
        }

        private void shareLayoutGone() {
            isSharelayoutVisible = false;
            ll_share_home.setBackground(null);
               ll_share_options_home.setVisibility(View.GONE);
        }


           private void smsItemListeners(SmsDetailModel smsTOBeShared, int position) {

               iv_whatsapp.setOnClickListener(v -> {
                shareLayoutGone();
                Utils.vibrate(mActivity);
                if (CheckPermissions.isStoragePermissionGranted(mActivity)) {
                    BitmapConversion(smsTOBeShared, GlobalConstants.COMMON.WHATSAPP);
                }
            });
            iv_facebook.setOnClickListener(v -> {
                shareLayoutGone();
                Utils.vibrate(mActivity);
                shareableIntents.shareOnFbMesenger(smsTOBeShared.getImage());

               });
            iv_twitter.setOnClickListener(v -> {
                shareableIntents.shareOnTwitter(v, smsTOBeShared.getImage());
                shareLayoutGone();
                Utils.vibrate(mActivity);
            });
            iv_instagram.setOnClickListener(v -> {
                shareableIntents.shareOnInstagram(smsTOBeShared.getImage());
                shareLayoutGone();
                Utils.vibrate(mActivity);
            });
            iv_pintrest.setOnClickListener(v -> {
                shareableIntents.shareOnPintrest(v, smsTOBeShared.getImage());
                shareLayoutGone();
                Utils.vibrate(mActivity);
            });
            iv_snapchat.setOnClickListener(v -> {
                shareableIntents.shareOnSnapChat(smsTOBeShared.getImage());
                shareLayoutGone();
                Utils.vibrate(mActivity);
            });
            ivMeme.setOnClickListener(v -> {
                shareLayoutGone();

            });
            rl_sms.setOnClickListener(v -> {
                shareLayoutGone();
            });


            ll_share_home.setOnClickListener(v -> {
                Utils.vibrate(mActivity);
                if (isSharelayoutVisible) {
                    shareLayoutGone();
                } else {
                    ll_share_home.setBackgroundDrawable(mActivity.getDrawable(pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false) ? R.drawable.share_round_corner_bg_light : R.drawable.bottom_round_corner_bg));

                    int padding = (int) mActivity.getResources().getDimension(R.dimen._10sdp);
                    ll_share_home.setPadding(padding, padding, padding, padding);
                    ll_share_options_home.setVisibility(View.VISIBLE);
                       isSharelayoutVisible = true;
                }
            });

               cb_like.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (buttonView.isPressed()) {
                    Utils.vibrate(mActivity);

                    Dialog dialog = Utils.getProgressDialog(mActivity);
                    dialog.show();
                    cb_like.setClickable(false);
                    if (isChecked) {
                        fragmentSmsHelper.addSmsToFav(smsTOBeShared, position,dialog, cb_like);
                    } else {
                        if (smsTOBeShared.getmFavourite() != null) {
                            for (SmsFavouriteModel favouriteModel : smsTOBeShared.getmFavourite()) {
                                if (favouriteModel.getDeviceId().equals(Utils.getMyDeviceId(mActivity))) {
                                    fragmentSmsHelper.removeFromFav(smsTOBeShared, position, favouriteModel.getId(), dialog, cb_like);
                                    break;

                                }
                            }
                        }
                    }
                }
            });

        }

    }



}
