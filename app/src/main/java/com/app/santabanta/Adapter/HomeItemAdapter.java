package com.app.santabanta.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.Callbacks.BitmapLoadedCallback;
import com.app.santabanta.Events.Events;
import com.app.santabanta.Events.GlobalBus;
import com.app.santabanta.Helper.FragmentHomeHelper;
import com.app.santabanta.Modals.Favourite;
import com.app.santabanta.Modals.HomeDetailList;
import com.app.santabanta.R;
import com.app.santabanta.Utils.AspectRatioImageView;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int JOKE_POST = 0;
    private static final int SMS_POST = 1;
    private static final int MEMES_VIDEO_POST = 2;
    private static final int MEMES_IMAGE_POST = 3;
    private static final int LOADING = 4;
    public Activity mActivity;
    FragmentHomeHelper fragmentHomeHelper;
    private List<HomeDetailList> mList = new ArrayList<>();
    private boolean isLoadingAdded = false;
    private SharedPreferences pref;

    public HomeItemAdapter(Activity mActivity, FragmentHomeHelper fragmentHomeHelper) {
        this.mActivity = mActivity;
        pref = Utils.getSharedPref(mActivity);
        this.fragmentHomeHelper = fragmentHomeHelper;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case SMS_POST:
                viewHolder = new SmsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_sms_item, parent, false));
                viewHolder.setIsRecyclable(false);
                break;

            case JOKE_POST:
                viewHolder = new JokesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_jokes_item, parent, false));
                viewHolder.setIsRecyclable(false);
                break;

            case MEMES_IMAGE_POST:
                viewHolder = new MemesImageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_memes_image_item, parent, false));
                viewHolder.setIsRecyclable(false);
                break;

            case MEMES_VIDEO_POST:
                viewHolder = new MemesVideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_memes_video_item, parent, false));
                viewHolder.setIsRecyclable(false);
                break;

            case LOADING:
                viewHolder = new LoadingVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress, parent, false));
                viewHolder.setIsRecyclable(false);
                break;
        }

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getType() != null) {
            String type = mList.get(position).getType();
            if (type.equalsIgnoreCase("jokes"))
                return JOKE_POST;
            else if (type.equalsIgnoreCase("sms"))
                return SMS_POST;
            else if (type.equalsIgnoreCase("memes")) {
                if (mList.get(position).getImage().endsWith(".mp4")) {
                    return MEMES_VIDEO_POST;
                } else
                    return MEMES_IMAGE_POST;
            }
        }
        return 0;

    }

    public void addAll(List<HomeDetailList> list) {
        for (HomeDetailList mc : list) {
            add(mc);
        }
    }

    public void setNewList(List<HomeDetailList> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void add(HomeDetailList item) {
        this.mList.add(item);
        notifyItemInserted(mList.size() - 1);

    }


    public void remove(HomeDetailList datum) {
        int position = mList.indexOf(datum);
        if (position > -1) {
            mList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void resetList(){
        this.mList = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SmsViewHolder) {
            ((SmsViewHolder) holder).bindData(mList.get(position), position);
        } else if (holder instanceof JokesHolder) {
            ((JokesHolder) holder).bindData(mList.get(position), position);
        } else if (holder instanceof MemesImageHolder) {
            ((MemesImageHolder) holder).bindData(mList.get(position), position);
        } else if (holder instanceof MemesVideoViewHolder) {
            ((MemesVideoViewHolder) holder).bindData(mList.get(position), position);
        } else if (holder instanceof LoadingVH) {

        }
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        HomeDetailList model = new HomeDetailList();
        add(model);
    }

    private HomeDetailList getItem(int position) {
        return mList.get(position);
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = mList.size() - 1;
        HomeDetailList item = getItem(position);

        if (item != null) {
            mList.remove(position);
            notifyItemRemoved(position);
        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public ArrayList<HomeDetailList> getCurrentList() {
        return (ArrayList<HomeDetailList>) mList;
    }

    public void updateList(ArrayList<HomeDetailList> pagedLists) {
        mList = pagedLists;
        notifyDataSetChanged();
    }

    private void setBreadCrumbs(HomeDetailList obj, LinearLayout llbreadcrumbs, String type) {
        if (obj.getBreadcrumbs() != null) {
            TextView[] textView = new TextView[obj.getBreadcrumbs().size()];

            if (llbreadcrumbs.getChildCount() > 0)
                llbreadcrumbs.removeAllViews();


            for (int i = 0; i < obj.getBreadcrumbs().size(); i++) {
                textView[i] = new TextView(mActivity);

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

                String slug = obj.getBreadcrumbs().get(i).getLink();
                textView[i].setOnClickListener(v -> {
                    switch (type) {
                        case "sms":
//                                Events.SMSEvent onFileSelected = new Events.SMSEvent(slug, "Veg");
//                                GlobalBus.getBus().post(onFileSelected);
                            mActivity.sendBroadcast(new Intent().setAction(GlobalConstants.INTENT_PARAMS.NAVIGATE_FROM_HOME)
                                    .putExtra(GlobalConstants.INTENT_PARAMS.NAVIGATE_TYPE, "sms").putExtra(GlobalConstants.INTENT_PARAMS.NAVIGATE_SLUG, slug));
                            break;

                        case "jokes":
//                                Events.JokesEvent jokesEvent = new Events.JokesEvent(slug);
//                                GlobalBus.getBus().post(jokesEvent);
                            mActivity.sendBroadcast(new Intent().setAction(GlobalConstants.INTENT_PARAMS.NAVIGATE_FROM_HOME)
                                    .putExtra(GlobalConstants.INTENT_PARAMS.NAVIGATE_TYPE, "jokes").putExtra(GlobalConstants.INTENT_PARAMS.NAVIGATE_SLUG, slug));
                            break;

                        case "memes":
//                                Events.MemesEvent memesEvent = new Events.MemesEvent(slug);
//                                GlobalBus.getBus().post(memesEvent);
                            mActivity.sendBroadcast(new Intent().setAction(GlobalConstants.INTENT_PARAMS.NAVIGATE_FROM_HOME)
                                    .putExtra(GlobalConstants.INTENT_PARAMS.NAVIGATE_TYPE, "memes").putExtra(GlobalConstants.INTENT_PARAMS.NAVIGATE_SLUG, slug));
                            break;
                    }
                });
            }
        }
    }

    class MemesImageHolder extends RecyclerView.ViewHolder implements BitmapLoadedCallback {
        private final ShareableIntents shareableIntents;
        @BindView(R.id.llbreadcrumbs)
        LinearLayout llbreadcrumbs;
        @BindView(R.id.ivMeme)
        AspectRatioImageView ivMeme;
        @BindView(R.id.ll_like_dislike)
        LinearLayout ll_like_dislikel;
        @BindView(R.id.cb_like)
        CheckBox cb_like;
        @BindView(R.id.ll_share_home)
        LinearLayout ll_share_home;
        @BindView(R.id.tv_share_count)
        TextView tv_share_count;
        @BindView(R.id.share)
        ImageView share;
        @BindView(R.id.tv_categories)
        TextView tv_categories;
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
        HomeDetailList imageTOBeShared;
        private boolean isSharelayoutVisible = false;


        public MemesImageHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            shareableIntents = new ShareableIntents(mActivity);

        }


        void bindData(HomeDetailList model, int position) {
            Utils.loadGlideImage(mActivity, ivMeme, model.getImage());
            setBreadCrumbs(model, llbreadcrumbs, "memes");

            imageTOBeShared = model;


            ivMeme.setOnLongClickListener(v -> {
                Utils.showImageSaveDialog(mActivity,ivMeme);
                return true;
            });

            if (model.getFavourites() != null && model.getFavourites().size() != 0) {
                for (Favourite favouriteModel : model.getFavourites()) {
                    if (favouriteModel.getDeviceId().equals(Utils.getMyDeviceId(mActivity))) {
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
            MemesImageItemListeners(model, position);


        }

        private void MemesImageItemListeners(HomeDetailList obj, int position) {

            iv_whatsapp.setOnClickListener(v -> {
                shareLayoutGone();
                Utils.vibrate(mActivity);
                if (CheckPermissions.isStoragePermissionGranted(mActivity)) {
                    BitmapConversion(obj, GlobalConstants.COMMON.WHATSAPP);
                }
            });
            iv_facebook.setOnClickListener(v -> {
                shareLayoutGone();
                Utils.vibrate(mActivity);
                shareableIntents.shareOnFbMesenger(imageTOBeShared.getImage());

            });
            iv_twitter.setOnClickListener(v -> {
                shareableIntents.shareOnTwitter(v, imageTOBeShared.getImage());
                shareLayoutGone();
                Utils.vibrate(mActivity);
            });
            iv_instagram.setOnClickListener(v -> {
                shareableIntents.shareOnInstagram(imageTOBeShared.getImage());
                shareLayoutGone();
                Utils.vibrate(mActivity);
            });
            iv_pintrest.setOnClickListener(v -> {
                shareableIntents.shareOnPintrest(v, imageTOBeShared.getImage());
                shareLayoutGone();
                Utils.vibrate(mActivity);
            });
            iv_snapchat.setOnClickListener(v -> {
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
            cb_like.setOnCheckedChangeListener((buttonView, isChecked) -> {

                Utils.vibrate(mActivity);

                Dialog dialog = Utils.getProgressDialog(mActivity);
                dialog.show();
                cb_like.setClickable(false);
                if (isChecked) {
                    fragmentHomeHelper.addToFav(obj, position, "sms", cb_like, dialog);
                } else {
                    for (Favourite favouriteModel : obj.getFavourites()) {
                        if (favouriteModel.getDeviceId().equals(Utils.getMyDeviceId(mActivity))) {
                            fragmentHomeHelper.removeFromFav(obj, favouriteModel.getId(), position, cb_like, dialog);
                            break;

                        }
                    }

                }
                //FUNCTIONALITY NOT IN SCOPE FOR THE TIME BEING
//                    SmsRepository.getInstance().InsertFavourite(obj,fragmentSms);
            });
            ll_share_home.setOnClickListener(v -> {
                Utils.vibrate(mActivity);
                if (isSharelayoutVisible) {
                    shareLayoutGone();
                } else {
                    if (pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false)) {
                        ll_share_home.setBackgroundDrawable(mActivity.getDrawable(R.drawable.share_round_corner_bg_light));
                        int padding = (int) mActivity.getResources().getDimension(R.dimen._10sdp);
                        ll_share_home.setPadding(padding, padding, padding, padding);
                        ll_share_options_home.setVisibility(View.VISIBLE);
                        isSharelayoutVisible = true;
                    } else {
                        ll_share_home.setBackgroundDrawable(mActivity.getDrawable(R.drawable.bottom_round_corner_bg));
                        int padding = (int) mActivity.getResources().getDimension(R.dimen._10sdp);
                        ll_share_home.setPadding(padding, padding, padding, padding);
                        ll_share_options_home.setVisibility(View.VISIBLE);
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
            ll_share_home.setBackground(null);
            ll_share_options_home.setVisibility(View.GONE);
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

    public class MemesVideoViewHolder extends RecyclerView.ViewHolder implements BitmapLoadedCallback {
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
//        @BindView(R.id.tv_like_count)
//        TextView tv_like_count;
        HomeDetailList imageTOBeShared;
        private boolean isSharelayoutVisible = false;


        public MemesVideoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

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

    class JokesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.llbreadcrumbs)
        LinearLayout llbreadcrumbs;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.rl_fav_and_share)
        RelativeLayout rl_fav_and_share;
        @BindView(R.id.ll_like_dislike)
        LinearLayout ll_like_dislike;
        @BindView(R.id.cb_like)
        CheckBox cb_like;
        @BindView(R.id.ll_share_home)
        LinearLayout ll_share_home;
        @BindView(R.id.tv_share_count)
        TextView tv_share_count;
        @BindView(R.id.share)
        ImageView share;
        @BindView(R.id.tv_categories)
        TextView tv_categories;
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
        int TAB_WHITE = 0;
        int TAB_OFF_WHITE = 0;
        int TAB_BROWN = 0;
        int TAB_PURPLE = 0;
        @BindView(R.id.cardview)
        CardView cardView;
        @BindView(R.id.tv_like_count)
        TextView tv_like_count;

        private ShareableIntents shareableIntents;
        private boolean isSharelayoutVisible = false;
        private boolean isDialogSharelayoutVisible = false;

        public JokesHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            shareableIntents = new ShareableIntents(mActivity);
            TAB_WHITE = mActivity.getResources().getColor(R.color.light_gray);
            TAB_OFF_WHITE = mActivity.getResources().getColor(R.color.off_white);
            TAB_BROWN = mActivity.getResources().getColor(R.color.brown);
            TAB_PURPLE = mActivity.getResources().getColor(R.color.purple);
        }

        void bindData(HomeDetailList model, int position) {


            if (pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false)) {
                if (getAdapterPosition() % 2 == 0) {
                    cardView.setCardBackgroundColor(TAB_WHITE);
                } else {
                    cardView.setCardBackgroundColor(TAB_OFF_WHITE);
                }
            } else {
                if (getAdapterPosition() % 2 == 0) {
                    cardView.setCardBackgroundColor(TAB_BROWN);
                } else {
                    cardView.setCardBackgroundColor(TAB_PURPLE);
                }
            }


            if (model.getFavCount() != null && model.getFavCount() == 0) {
                tv_like_count.setVisibility(View.GONE);
            } else {
                tv_like_count.setVisibility(View.GONE);
                tv_like_count.setText(String.valueOf(model.getFavCount()));
            }


            if (pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false)) {
                if (getAdapterPosition() % 2 == 0) {
                    cardView.setCardBackgroundColor(TAB_WHITE);
                } else {
                    cardView.setCardBackgroundColor(TAB_OFF_WHITE);
                }
            } else {
                if (getAdapterPosition() % 2 == 0) {
                    cardView.setCardBackgroundColor(TAB_BROWN);
                } else {
                    cardView.setCardBackgroundColor(TAB_PURPLE);
                }
            }


            tv_title.setText(Html.fromHtml(model.getTitle()));
            tvContent.setText(Html.fromHtml(model.getContent()));
            setBreadCrumbs(model, llbreadcrumbs, "jokes");
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


            if (model.getFavourites() != null && model.getFavourites().size() != 0) {
                for (Favourite favouriteModel : model.getFavourites()) {
                    if (favouriteModel.getDeviceId().equals(Utils.getMyDeviceId(mActivity))) {
                        cb_like.setChecked(true);
                        break;

                    } else {
                        cb_like.setChecked(false);
                    }
                }
            } else {
                cb_like.setChecked(false);
            }

            jokesItemListener(model, position);

        }

        private void jokesItemListener(HomeDetailList obj, int position) {

            iv_whatsapp.setOnClickListener(v -> {
                shareLayoutGone();
                Utils.vibrate(mActivity);
                shareableIntents.shareOnWhatsapp(obj.getContent());

            });
            iv_facebook.setOnClickListener(v -> {
                shareLayoutGone();
                Utils.vibrate(mActivity);
                shareableIntents.shareOnFbMesenger(obj.getContent());

            });
            iv_twitter.setOnClickListener(v -> {
                shareableIntents.shareOnTwitter(v, obj.getContent());
                Utils.vibrate(mActivity);
                shareLayoutGone();
            });
            iv_instagram.setOnClickListener(v -> {
                shareableIntents.shareOnInstagram(obj.getContent());
                Utils.vibrate(mActivity);
                shareLayoutGone();
            });
            iv_pintrest.setOnClickListener(v -> {
                shareableIntents.shareOnPintrest(v, obj.getContent());
                Utils.vibrate(mActivity);
                shareLayoutGone();
            });
            iv_snapchat.setOnClickListener(v -> {
                shareableIntents.shareOnSnapChat(obj.getContent());
                Utils.vibrate(mActivity);
                shareLayoutGone();
            });

            tvContent.setOnClickListener(v -> {
                shareLayoutGone();
                fullScreenDialog(obj, position, cb_like.isChecked());
            });

            cb_like.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (buttonView.isPressed()) {

                    Dialog dialog = Utils.getProgressDialog(mActivity);
                    dialog.show();

                    cb_like.setClickable(false);
                    onFavCheckChanged(isChecked, obj, position, cb_like, dialog);
                }
                //FUNCTIONALITY NOT IN SCOPE FOR THE TIME BEING
            });


            ll_share_home.setOnClickListener(v -> {

                Utils.vibrate(mActivity);

                if (isSharelayoutVisible) {
                    shareLayoutGone();
                } else {
                    if (pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false)) {
                        ll_share_home.setBackgroundDrawable(mActivity.getDrawable(R.drawable.share_round_corner_bg_light));
                        int padding = (int) mActivity.getResources().getDimension(R.dimen._10sdp);
                        ll_share_home.setPadding(padding, padding, padding, padding);
                        ll_share_options_home.setVisibility(View.VISIBLE);
                        isSharelayoutVisible = true;
                    } else {
                        ll_share_home.setBackgroundDrawable(mActivity.getDrawable(R.drawable.bottom_round_corner_bg));
                        int padding = (int) mActivity.getResources().getDimension(R.dimen._10sdp);
                        ll_share_home.setPadding(padding, padding, padding, padding);
                        ll_share_options_home.setVisibility(View.VISIBLE);
                        isSharelayoutVisible = true;
                    }

                }
            });
        }

        private void shareLayoutGone() {

            isSharelayoutVisible = false;
            ll_share_home.setBackground(null);
            ll_share_options_home.setVisibility(View.GONE);
        }

        public void fullScreenDialog(HomeDetailList model, int position, boolean ischecked) {
            Dialog dialog = new Dialog(mActivity, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.full_screen_joke_dialog);
            TextView tv_content = dialog.findViewById(R.id.tvContent);
            LinearLayout ll_share_joke = dialog.findViewById(R.id.ll_share_joke);
            LinearLayout ll_share_options_joke = dialog.findViewById(R.id.ll_share_options_joke);
            TextView tv_title = dialog.findViewById(R.id.tv_title);
            TextView tv_categories = dialog.findViewById(R.id.tv_categories);
            ImageView iv_close = dialog.findViewById(R.id.iv_close);
            TextView tv_fav_count = dialog.findViewById(R.id.tv_fav_count);
            ProgressBar progress_bar = dialog.findViewById(R.id.progress_bar);
            ScrollView content_scroll = dialog.findViewById(R.id.content_scroll);

            if (model.getFavCount() != null && !model.getFavCount().equals(0)) {
                tv_fav_count.setVisibility(View.GONE);
                tv_fav_count.setText(String.valueOf(model.getFavCount()));
            } else {
                tv_fav_count.setVisibility(View.GONE);
            }

            ll_share_joke.setOnClickListener(v -> {
                if (isDialogSharelayoutVisible) {
                    DialogshareLayoutGone(ll_share_joke, ll_share_options_joke);
                } else {
                    ll_share_joke.setBackgroundDrawable(mActivity.getDrawable(R.drawable.bottom_round_corner_bg));
                    int padding = (int) mActivity.getResources().getDimension(R.dimen._10sdp);
                    ll_share_joke.setPadding(padding, padding, padding, padding);
                    ll_share_options_joke.setVisibility(View.VISIBLE);
                    isDialogSharelayoutVisible = true;
                }
            });
            content_scroll.setOnClickListener(v -> DialogshareLayoutGone(ll_share_joke, ll_share_options_joke));
            iv_close.setOnClickListener(v -> dialog.dismiss());
            CheckBox cb_like = dialog.findViewById(R.id.cb_like);
            cb_like.setChecked(ischecked);

            tv_content.setText(Html.fromHtml(model.getContent().replaceAll("<br/><br/>", "")));
            if (model.getTitle() != null && !model.getTitle().equals(""))
                tv_title.setText(Html.fromHtml(model.getTitle()));
            if (model.getCategories() != null && model.getCategories().size() != 0) {
                tv_categories.setText(Html.fromHtml(model.getCategories().get(0).getName()));
            }

            cb_like.setOnCheckedChangeListener((buttonView, isChecked) -> {

                Utils.vibrate(mActivity);

                Dialog dialog1 = Utils.getProgressDialog(mActivity);
                progress_bar.setVisibility(View.VISIBLE);
                cb_like.setClickable(false);
                onFavCheckChanged(isChecked, model, position, cb_like, dialog1);
                //FUNCTIONALITY NOT IN SCOPE FOR THE TIME BEING
//                    SmsRepository.getInstance().InsertFavourite(obj,fragmentSms);
            });
            dialog.show();
        }

        private void DialogshareLayoutGone(LinearLayout ll_share_joke, LinearLayout ll_share_options_joke) {
            isDialogSharelayoutVisible = false;
            ll_share_joke.setBackground(null);
            ll_share_options_joke.setVisibility(View.GONE);
        }

        private void onFavCheckChanged(boolean isChecked, HomeDetailList obj, int position, CheckBox cbLike, Dialog progress_bar) {
            if (isChecked) {
                fragmentHomeHelper.addToFav(obj, position, "jokes", cbLike, progress_bar);
            } else {
                if (obj.getFavourites() != null) {
                    for (Favourite favouriteModel : obj.getFavourites()) {
                        if (favouriteModel.getDeviceId().equals(Utils.getMyDeviceId(mActivity))) {
                            fragmentHomeHelper.removeFromFav(obj, favouriteModel.getId(), position, cbLike, progress_bar);
                            break;

                        }
                    }
                }

            }
        }


    }

    class SmsViewHolder extends RecyclerView.ViewHolder implements BitmapLoadedCallback {

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
//        @BindView(R.id.tv_like_count)
//        TextView tv_like_count;
        ShareableIntents shareableIntents;
        HomeDetailList imageTOBeShared;
        private boolean isSharelayoutVisible = false;

        public SmsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            shareableIntents = new ShareableIntents(mActivity);
        }


        void bindData(HomeDetailList model, int position) {

            ivMeme.setOnLongClickListener(v -> {
                Utils.showImageSaveDialog(mActivity,ivMeme);
                return true;
            });

//            if (model.getFavCount() == 0) {
//                tv_like_count.setVisibility(View.GONE);
//            } else {
//                tv_like_count.setVisibility(View.GONE);
//                tv_like_count.setText(String.valueOf(model.getFavCount()));
//            }

            imageTOBeShared = model;
            Utils.loadGlideImage(mActivity, ivMeme, model.getImage());
            setBreadCrumbs(model, llbreadcrumbs, "sms");
            if (model.getFavourites() != null && model.getFavourites().size() != 0) {
                for (Favourite favouriteModel : model.getFavourites()) {
                    if (favouriteModel.getDeviceId().equals(Utils.getMyDeviceId(mActivity))) {
                        cb_like.setChecked(true);
                        break;

                    } else {
                        cb_like.setChecked(false);
                    }
                }
            } else {
                cb_like.setChecked(false);
            }

            smsItemListeners(model, position);


        }


        private void smsItemListeners(HomeDetailList obj, int position) {

            iv_whatsapp.setOnClickListener(v -> {
                shareLayoutGone();
                Utils.vibrate(mActivity);
                if (CheckPermissions.isStoragePermissionGranted(mActivity)) {
                    BitmapConversion(obj, GlobalConstants.COMMON.WHATSAPP);
                }
            });
            iv_facebook.setOnClickListener(v -> {
                shareLayoutGone();
                Utils.vibrate(mActivity);
                shareableIntents.shareOnFbMesenger(imageTOBeShared.getImage());

            });
            iv_twitter.setOnClickListener(v -> {
                shareableIntents.shareOnTwitter(v, imageTOBeShared.getImage());
                shareLayoutGone();
                Utils.vibrate(mActivity);
            });
            iv_instagram.setOnClickListener(v -> {
                shareableIntents.shareOnInstagram(imageTOBeShared.getImage());
                shareLayoutGone();
                Utils.vibrate(mActivity);
            });
            iv_pintrest.setOnClickListener(v -> {
                shareableIntents.shareOnPintrest(v, imageTOBeShared.getImage());
                shareLayoutGone();
                Utils.vibrate(mActivity);
            });
            iv_snapchat.setOnClickListener(v -> {
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
            cb_like.setOnCheckedChangeListener((buttonView, isChecked) -> {

                Utils.vibrate(mActivity);

                Dialog dialog = Utils.getProgressDialog(mActivity);
                dialog.show();
                cb_like.setClickable(false);
                if (isChecked) {
                    fragmentHomeHelper.addToFav(obj, position, "sms", cb_like, dialog);
                } else {
                    for (Favourite favouriteModel : obj.getFavourites()) {
                        if (favouriteModel.getDeviceId().equals(Utils.getMyDeviceId(mActivity))) {
                            fragmentHomeHelper.removeFromFav(obj, favouriteModel.getId(), position, cb_like, dialog);
                            break;

                        }
                    }

                }
                //FUNCTIONALITY NOT IN SCOPE FOR THE TIME BEING
//                    SmsRepository.getInstance().InsertFavourite(obj,fragmentSms);
            });
//
//            BubbleLayout bubbleLayout = (BubbleLayout) LayoutInflater.from(mActivity).inflate(R.layout.share_bubble_layout, null);
//            PopupWindow popupWindow = BubblePopupHelper.create(mActivity, bubbleLayout);
//            final Random random = new Random();
//            ll_share_home.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Utils.vibrate(mActivity);
//                    int[] location = new int[2];
//                    view.getLocationInWindow(location);
////                    if (random.nextBoolean()) {
////                        bubbleLayout.setArrowDirection(ArrowDirection.TOP);
////                    } else {
//                        bubbleLayout.setArrowDirection(ArrowDirection.BOTTOM);
////                    }
//                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0], view.getHeight() + location[1]);
//                }
//            });



            ll_share_home.setOnClickListener(v -> {
                Utils.vibrate(mActivity);
                if (isSharelayoutVisible) {
                    shareLayoutGone();
                } else {
                    if (pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false)) {
                        ll_share_home.setBackgroundDrawable(mActivity.getDrawable(R.drawable.share_round_corner_bg_light));
//                        int padding = (int) mActivity.getResources().getDimension(R.dimen._10sdp);
//                        ll_share_home.setPadding(padding, padding, padding, padding);
                        ll_share_options_home.setVisibility(View.VISIBLE);
                        isSharelayoutVisible = true;
                    } else {
                        ll_share_home.setBackgroundDrawable(mActivity.getDrawable(R.drawable.bottom_round_corner_bg));
//                        int padding = (int) mActivity.getResources().getDimension(R.dimen._10sdp);
//                        ll_share_home.setPadding(padding, padding, padding, padding);
                        ll_share_options_home.setVisibility(View.VISIBLE);
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
            ll_share_home.setBackground(null);
            ll_share_options_home.setVisibility(View.GONE);
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

    class LoadingVH extends RecyclerView.ViewHolder {

        LoadingVH(View itemView) {
            super(itemView);
        }
    }
}

