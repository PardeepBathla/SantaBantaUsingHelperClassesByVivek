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
import com.app.santabanta.Modals.SmsFeaturedCategory;
import com.app.santabanta.R;
import com.app.santabanta.Utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SmsCategoriesAdapter extends RecyclerView.Adapter<SmsCategoriesAdapter.ViewHolder>{

    private ArrayList<SmsFeaturedCategory> mList;
    private Activity mActivity;
    private SmsCategoriesAdapter.HomeCategoryClickListener mClickListener;

    public SmsCategoriesAdapter(ArrayList<SmsFeaturedCategory> mList, Activity mActivity, SmsCategoriesAdapter.HomeCategoryClickListener mClickListener) {
        this.mList = mList;
        this.mActivity = mActivity;
        this.mClickListener = mClickListener;
    }

    @NonNull
    @Override
    public SmsCategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_row_item, parent, false);
        return new SmsCategoriesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SmsCategoriesAdapter.ViewHolder holder, int position) {
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

        void bindData(SmsFeaturedCategory model){
            tvSubcatName.setText(model.getName());
            Utils.loadGlideImage(mActivity, ivSubCategory,model.getIcon());
            itemView.setOnClickListener(view -> mClickListener.onItemClicked(model));
        }
    }

    public interface HomeCategoryClickListener{
        void onItemClicked(SmsFeaturedCategory model);
    }
}
