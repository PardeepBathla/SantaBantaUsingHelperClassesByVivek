package com.app.santabanta.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.WallpaperColors;
import android.content.Intent;
import android.media.Image;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.Modals.HomeDetailList;
import com.app.santabanta.R;
import com.app.santabanta.Utils.AspectRatioImageView;
import com.app.santabanta.Utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int JOKE_POST = 0;
    private static final int SMS_POST = 1;
    private static final int MEMES_VIDEO_POST = 2;
    private static final int MEMES_IMAGE_POST = 3;
    private static final int LOADING = 4;
    private Activity mActivity;
    private List<HomeDetailList> mList;
    private boolean isLoadingAdded = false;

    public HomeItemAdapter(Activity mActivity, List<HomeDetailList> mList) {
        this.mActivity = mActivity;
        this.mList = mList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType){
            case SMS_POST:
                viewHolder = new SmsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_sms_item,parent,false));
                viewHolder.setIsRecyclable(false);
                break;

            case JOKE_POST:
                viewHolder = new JokesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_jokes_item,parent,false));
                viewHolder.setIsRecyclable(false);
                break;

            case MEMES_IMAGE_POST:
                viewHolder = new MemesImageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_memes_image_item,parent,false));
                viewHolder.setIsRecyclable(false);
                break;

            case MEMES_VIDEO_POST:
                viewHolder = new MemesVideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_memes_video_item,parent,false));
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
        String type = mList.get(position).getType();
        if (type.equalsIgnoreCase("jokes"))
            return JOKE_POST;
        else if (type.equalsIgnoreCase("sms"))
            return SMS_POST;
        else if (type.equalsIgnoreCase("memes")){
            if (mList.get(position).getImage().endsWith(".mp4")){
                return MEMES_VIDEO_POST;
            }else
                return MEMES_IMAGE_POST;
        }
        return 0;

    }


    public void setNewList(List<HomeDetailList> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void add(HomeDetailList item){
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

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SmsViewHolder) {
            ((SmsViewHolder)holder).bindData(mList.get(position));
        }else if (holder instanceof JokesHolder){
            ((JokesHolder)holder).bindData(mList.get(position));
        }else if (holder instanceof MemesImageHolder){
            ((MemesImageHolder)holder).bindData(mList.get(position));
        }else if (holder instanceof MemesVideoViewHolder){
            ((MemesVideoViewHolder)holder).bindData(mList.get(position));
        }else if (holder instanceof LoadingVH){

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

    class MemesImageHolder extends RecyclerView.ViewHolder{
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

        public MemesImageHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bindData(HomeDetailList model){
            Utils.loadGlideImage(mActivity,ivMeme, model.getImage());
            setBreadCrumbs(model, llbreadcrumbs);
            ll_share_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ll_share_options_home.getVisibility() == View.VISIBLE){
                        ll_share_options_home.setVisibility(View.GONE);
                    }else {
                        ll_share_options_home.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    public class MemesVideoViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.ivMediaCoverImage)
        public ImageView ivMediaCoverImage;
        @BindView(R.id.progressBar)
        public ProgressBar progressBar;
        @BindView(R.id.ivVolumeControl)
        public ImageView ivVolumeControl;
        @BindView(R.id.mediaContainer)
        public FrameLayout mediaContainer;
        @BindView(R.id.pbBuffering)
        public ProgressBar pbBuffering;
        public RequestManager requestManager;

        public MemesVideoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            Glide.with(mActivity)
                    .setDefaultRequestOptions(new RequestOptions());
        }

        void bindData(HomeDetailList model){

        }
    }


    class JokesHolder extends RecyclerView.ViewHolder{

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

        public JokesHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bindData(HomeDetailList model){
            tv_title.setText(model.getTitle());
            tvContent.setText(model.getContent());
            setBreadCrumbs(model, llbreadcrumbs);
            ll_share_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ll_share_options_home.getVisibility() == View.VISIBLE){
                        ll_share_options_home.setVisibility(View.GONE);
                    }else {
                        ll_share_options_home.setVisibility(View.VISIBLE);
                    }
                }
            });

          tvContent.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

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

                  tv_fav_count.setText(String.valueOf(model.getFavCount()));
                  iv_close.setOnClickListener(v -> dialog.dismiss());
                  CheckBox cb_like = dialog.findViewById(R.id.cb_like);
                  tv_content.setText(Html.fromHtml(model.getContent().replaceAll("<br/><br/>","")));
                  tv_title.setText(Html.fromHtml(model.getTitle()));

                  if (model.getCategories() != null && model.getCategories().size() != 0) {
                      tv_categories.setText(Html.fromHtml(model.getCategories().get(0).getName()));
                  }

                  cb_like.setOnCheckedChangeListener((buttonView, isChecked) -> {


                      if (isChecked){
                          if (!tv_fav_count.getText().toString().equals("")) {
                              tv_fav_count.setText(String.valueOf(Integer.parseInt(tv_fav_count.getText().toString())+1));
                          }else {
                              tv_fav_count.setText("0");
                          }
                      }else {
                          if (!tv_fav_count.getText().equals("0")) {
                              tv_fav_count.setText(String.valueOf(Integer.parseInt(tv_fav_count.getText().toString())-1));
                          }
                      }
                      progress_bar.setVisibility(View.VISIBLE);
                      cb_like.setClickable(false);

                  });

                  dialog.show();
              }
          });
        }

    }


    class SmsViewHolder extends RecyclerView.ViewHolder{

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

        public SmsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }



        void bindData(HomeDetailList model){
            Utils.loadGlideImage(mActivity,ivMeme,model.getImage());
            setBreadCrumbs(model, llbreadcrumbs);
            ll_share_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ll_share_options_home.getVisibility() == View.VISIBLE){
                        ll_share_options_home.setVisibility(View.GONE);
                    }else {
                        ll_share_options_home.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    class LoadingVH extends RecyclerView.ViewHolder {

        LoadingVH(View itemView) {
            super(itemView);
        }
    }

    private void setBreadCrumbs(HomeDetailList obj, LinearLayout llbreadcrumbs) {
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

            int finalI = i;
            textView[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                }
            });
        }
    }
}
