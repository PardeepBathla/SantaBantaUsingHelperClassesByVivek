package com.app.santabanta.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.Modals.HomeDetailList;
import com.app.santabanta.R;
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

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MemesImageHolder extends RecyclerView.ViewHolder{

        public MemesImageHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bindData(HomeDetailList model){

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

        void onBind(RequestManager requestManager){

        }
    }


    class JokesHolder extends RecyclerView.ViewHolder{

        public JokesHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bindData(HomeDetailList model){

        }
    }

    class SmsViewHolder extends RecyclerView.ViewHolder{

        public SmsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bindData(HomeDetailList model){

        }
    }

    class LoadingVH extends RecyclerView.ViewHolder {

        LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
