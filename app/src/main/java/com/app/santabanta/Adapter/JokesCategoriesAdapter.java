package com.app.santabanta.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.Modals.FeaturedCategory;
import com.app.santabanta.Modals.JokesFeaturedCategory;
import com.app.santabanta.R;
import com.app.santabanta.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class JokesCategoriesAdapter extends RecyclerView.Adapter<JokesCategoriesAdapter.ViewHolder>{

    private List<JokesFeaturedCategory> mList;
    private Activity mActivity;
    private JokesCategoryClickListener mClickListener;

    public JokesCategoriesAdapter(List<JokesFeaturedCategory> mList, Activity mActivity,JokesCategoryClickListener mClickListener) {
        this.mList = mList;
        this.mClickListener = mClickListener;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_row_item, parent, false);
        return new JokesCategoriesAdapter.ViewHolder(v);
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

        @BindView(R.id.rlFeaturedItem)
        RelativeLayout rlFeaturedItem;
        @BindView(R.id.ivRoot)
        CircleImageView ivRoot;
        @BindView(R.id.ivSubCategory)
        CircleImageView ivSubCategory;
        @BindView(R.id.tv_sub_cat_name)
        TextView tvSubcatName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bindData(JokesFeaturedCategory model){
            tvSubcatName.setText(model.getName());
            Utils.loadGlideImage(mActivity, ivSubCategory,model.getIcon());
            itemView.setOnClickListener(view -> mClickListener.onItemClicked(model));
        }

    }

    public interface JokesCategoryClickListener{
        void onItemClicked(JokesFeaturedCategory model);
    }
}
