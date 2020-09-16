package com.app.santabanta.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.Callbacks.BitmapLoadedCallback;
import com.app.santabanta.Events.Events;
import com.app.santabanta.Events.GlobalBus;
import com.app.santabanta.Fragment.FragmentMemes;
import com.app.santabanta.Helper.FragmentMemesHelper;
import com.app.santabanta.Modals.SmsDetailModel;
import com.app.santabanta.Modals.memesModel.MemesDetailModel;
import com.app.santabanta.Modals.memesModel.MemesFavouriteModel;
import com.app.santabanta.R;
import com.app.santabanta.Utils.AspectRatioImageView;
import com.app.santabanta.Utils.CheckPermissions;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.LoadImageBitmap;
import com.app.santabanta.Utils.ShareableIntents;
import com.app.santabanta.Utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MemesItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements BitmapLoadedCallback {

    private static final int VIDEO = 1;
    private static final int IMAGE = 0;
    private static Activity mCtx;
    private final ShareableIntents shareableIntents;
    ArrayList<MemesDetailModel> memesData;
    private FragmentMemes memesFragment;
    private MemesDetailModel memeTOBeShared;
    private boolean isSharelayoutVisible = false;
    private boolean isDialogSharelayoutVisible = false;
    private SharedPreferences pref;
    private FragmentMemesHelper fragmentMemesHelper;
    private FragmentMemes fragmentMemes;

    public MemesItemAdapter(Activity mCtx, FragmentMemes memesFragment, ProgressBar progressBar, FragmentMemesHelper fragmentMemesHelper,FragmentMemes fragmentMemes) {
        this.memesFragment = memesFragment;
        MemesItemAdapter.mCtx = mCtx;
        shareableIntents = new ShareableIntents(mCtx);
        pref = Utils.getSharedPref(mCtx);
        this.memesData = new ArrayList<>();
        this.fragmentMemesHelper = fragmentMemesHelper;
        this.fragmentMemes = fragmentMemes;
        setHasStableIds(true);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIDEO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memes_video_item, parent, false);
                viewHolder = new PlayerViewHolder(mCtx, view, memesFragment,fragmentMemesHelper);
                viewHolder.setIsRecyclable(false);

                break;

            case IMAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memes_image_item, parent, false);

                viewHolder = new MemesItemAdapter.ImageViewHolder(view);
                viewHolder.setIsRecyclable(false);
                break;

        }
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof PlayerViewHolder) {
            ((PlayerViewHolder) holder).onBind(memesData.get(position), Glide.with(mCtx).setDefaultRequestOptions(new RequestOptions()), position);
            holder.setIsRecyclable(false);
        } else if (holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).bindData(memesData.get(position), position);
            holder.setIsRecyclable(false);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (memesData.get(position).getMemeType().equals("video"))
            return VIDEO;
        else
            return IMAGE;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, String platform) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(mCtx.getContentResolver(), bitmap, "SantaBantaShare", null);
        Uri imageUri = Uri.parse(path);

        switch (platform) {
            case GlobalConstants.COMMON.WHATSAPP:
                shareableIntents.shareOnWhatsapp(imageUri);
                break;
            /*    case Constants.COMMON.FACEBOOK :
                shareableIntents.shareOnWhatsapp(imageUri);
                break;*/
            case GlobalConstants.COMMON.TWITTER:
//              shareableIntents.shareOnTwi1tter(imageUri);
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

    public void updateList(ArrayList<MemesDetailModel> data) {
        memesData = data;
        notifyDataSetChanged();
    }

    public ArrayList<MemesDetailModel> getCurrentList() {
        return memesData;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return memesData == null ? 0 : memesData.size();
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

    public void add(MemesDetailModel data) {
        this.memesData.add(data);
       // notifyItemInserted(memesData.size() - 1);
        notifyDataSetChanged();
    }

    public void addAll(List<MemesDetailModel> mcList) {
        for (MemesDetailModel mc : mcList) {
            add(mc);
        }
    }

    public void clearList(){
        this.memesData = new ArrayList<>();
        notifyDataSetChanged();
    }
    private void setMemeImage(String url, AspectRatioImageView ivMeme) {
        Utils.loadGlideImage(mCtx, ivMeme, url);
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements BitmapLoadedCallback{

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
        @BindView(R.id.ivMeme)
        AspectRatioImageView ivMeme;
        @BindView(R.id.progress_bar)
        ProgressBar progressBar;
        @BindView(R.id.tv_like_count)
        TextView tv_like_count;

        View memesImageItemBinding;


        ImageViewHolder(View memesImageItemBinding) {
            super(memesImageItemBinding);
            this.memesImageItemBinding = memesImageItemBinding;

            ButterKnife.bind(this, memesImageItemBinding);

        }

        void bindData(MemesDetailModel obj, int position) {

            if (obj.getFavCount() == 0) {
                tv_like_count.setVisibility(View.GONE);
            } else {
                tv_like_count.setVisibility(View.GONE);
                tv_like_count.setText(String.valueOf(obj.getFavCount()));
            }//

            setMemeImage(obj.getImage(), ivMeme);

            memeTOBeShared = obj;
            setBreadCrumbs(memesImageItemBinding, obj, position);
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

            memesItemListener(obj, position,ivMeme);
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

                    String slug = obj.getBreadcrumbs().get(i).getLink();
                    textView[i].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
//                            Events.MemesEvent memesEvent= new Events.MemesEvent(slug);
//                            GlobalBus.getBus().post(memesEvent);
                            fragmentMemes.enterSubCategoryMemes(true, slug);
//                            mCtx.sendBroadcast(new Intent().setAction(GlobalConstants.INTENT_PARAMS.NAVIGATE_FROM_HOME)
//                                    .putExtra(GlobalConstants.INTENT_PARAMS.NAVIGATE_TYPE,"memes").putExtra(GlobalConstants.INTENT_PARAMS.NAVIGATE_SLUG,slug));
                        }
                    });
                }
            }
        }


        private void BitmapConversion(MemesDetailModel obj, String platform) {
            shareLayoutGone();
            try {
                URL url = new URL(obj.getImage());
                new LoadImageBitmap(mCtx, MemesItemAdapter.this::onBitmapLoaded, platform).execute(url);

            } catch (IOException e) {
                System.out.println(e);
            }
        }

        public Uri getLocalBitmapUri(ImageView imageView) {
            // Extract Bitmap from ImageView drawable
            Drawable drawable = imageView.getDrawable();
            Bitmap bmp = null;
            if (drawable instanceof BitmapDrawable){
                bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            } else {
                return null;
            }
            // Store image to default external storage directory
            Uri bmpUri = null;
            try {
                File file =  new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
                file.getParentFile().mkdirs();
                FileOutputStream out = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
                out.close();
                bmpUri = Uri.fromFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmpUri;
        }

        private void memesItemListener(MemesDetailModel obj, int position,ImageView ivMeme) {

            ivWhatsapp.setOnClickListener(v -> {
                shareLayoutGone();
                Utils.vibrate(mCtx);

                if (CheckPermissions.isStoragePermissionGranted(mCtx)) {
                    BitmapConversion(obj, GlobalConstants.COMMON.WHATSAPP);
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
          /*  memesImageItemBinding.ivMeme.setOnClickListener(v -> {
                shareLayoutGone();
                fullScreenDialog(obj, position, memesImageItemBinding.cbLike.isChecked());
            });
*/
            cbLike.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (buttonView.isPressed()) {
                    progressBar.setVisibility(View.VISIBLE);
                    cbLike.setClickable(false);
                    /*TODO like dislike share*/
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

        @Override
        public void onBitmapLoaded(Bitmap bitmap, String platform) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(mCtx.getContentResolver(), bitmap, "SantaBantaShare", null);
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

}
