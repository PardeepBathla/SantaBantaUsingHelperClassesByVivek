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
import com.app.santabanta.Modals.memesModel.MemesFeaturedCategory;
import com.app.santabanta.R;
import com.app.santabanta.Utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MemesCategoryAdapter extends RecyclerView.Adapter<MemesCategoryAdapter.ViewHolder> {

    private Activity mActivity;
    private ArrayList<MemesFeaturedCategory> featuredCategories;
    private MemeCategoryClickListener mClickListener;

    public MemesCategoryAdapter(Activity mActivity, ArrayList<MemesFeaturedCategory> featuredCategories, MemeCategoryClickListener mClickListener) {
        this.mActivity = mActivity;
        this.featuredCategories = featuredCategories;
        this.mClickListener = mClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_row_item, parent, false);
        return new MemesCategoryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(featuredCategories.get(position));
    }

    @Override
    public int getItemCount() {
        return featuredCategories.size();
    }

    public interface MemeCategoryClickListener {
        void onClicked(MemesFeaturedCategory model);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

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

        void bindData(MemesFeaturedCategory model){
            tvSubcatName.setText(model.getName());
            Utils.loadGlideImage(mActivity, ivSubCategory,model.getIcon());
            itemView.setOnClickListener(view -> mClickListener.onClicked(model));
        }
    }
}
