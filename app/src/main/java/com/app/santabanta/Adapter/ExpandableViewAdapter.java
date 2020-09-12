package com.app.santabanta.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.Modals.NavMenuResponse;
import com.app.santabanta.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpandableViewAdapter extends RecyclerView.Adapter<ExpandableViewAdapter.ViewHolder>{

    public SharedPreferences pref;
    Context context;
    ArrayList<NavMenuResponse.NavMenuDetail.NavMenuDetailChildInfo> navMenuChildDetails;

    public ExpandableViewAdapter(Context context, ArrayList<NavMenuResponse.NavMenuDetail.NavMenuDetailChildInfo> navMenuChildDetails) {

        this.context = context;
        this.navMenuChildDetails = navMenuChildDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.side_menu_nav_item, parent, false);
        return new ExpandableViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(navMenuChildDetails.get(position));
    }

    @Override
    public int getItemCount() {
        return navMenuChildDetails.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_item_logo)
        ImageView iv_item_logo;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.viewMoreBtn)
        ImageView viewMoreBtn;
        @BindView(R.id.recycler)
        RecyclerView recycler;
        @BindView(R.id.layoutExpand)
        LinearLayout layoutExpand;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bindData(NavMenuResponse.NavMenuDetail.NavMenuDetailChildInfo model){
            name.setText(model.getName());
            recycler.setLayoutManager(new LinearLayoutManager(context));
            recycler.setAdapter(new ChildExpandableAdapter(context,model.getInfo(),model.getName()));
            viewMoreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layoutExpand.setVisibility(layoutExpand.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                }
            });
        }
    }

}
