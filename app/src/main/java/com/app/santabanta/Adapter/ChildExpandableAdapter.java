package com.app.santabanta.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.Modals.NavMenuResponse;
import com.app.santabanta.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChildExpandableAdapter extends RecyclerView.Adapter<ChildExpandableAdapter.ViewHolder>{

    Context context;
    ArrayList<NavMenuResponse.NavMenuDetail.NavMenuDetailChildInfo.NavMenuDetailChildSubInfo> children;
    String name;

    public ChildExpandableAdapter(Context context, ArrayList<NavMenuResponse.NavMenuDetail.NavMenuDetailChildInfo.NavMenuDetailChildSubInfo> children, String name) {
        this.context = context;
        this.children = children;
        this.name = name;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_item_to_expand, parent, false);
        return new ChildExpandableAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(children.get(position));
    }

    @Override
    public int getItemCount() {
        return children.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_cat)
        ImageView iv_cat;
        @BindView(R.id.text)
        TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bindData(NavMenuResponse.NavMenuDetail.NavMenuDetailChildInfo.NavMenuDetailChildSubInfo model){
            text.setText(model.getName());
        }
    }
}
