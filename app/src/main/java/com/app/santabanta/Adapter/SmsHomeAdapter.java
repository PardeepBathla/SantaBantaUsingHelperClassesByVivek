package com.app.santabanta.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.Modals.HomeDetailList;
import com.app.santabanta.Modals.SmsDetailModel;
import com.app.santabanta.R;
import com.app.santabanta.Utils.AspectRatioImageView;
import com.app.santabanta.Utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SmsHomeAdapter extends RecyclerView.Adapter<SmsHomeAdapter.ViewHolder>{

    private List<SmsDetailModel> mList;
    private Activity mActivity;

    public SmsHomeAdapter(List<SmsDetailModel> mList, Activity mActivity) {
        this.mList = mList;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_sms_item, parent, false);
        return new SmsHomeAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bindData(SmsDetailModel model){
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

    private void setBreadCrumbs(SmsDetailModel obj, LinearLayout llbreadcrumbs) {
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
