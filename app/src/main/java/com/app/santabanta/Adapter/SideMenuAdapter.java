package com.app.santabanta.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.Callbacks.DrawerMenuClickListener;
import com.app.santabanta.Modals.NavMenuResponse;
import com.app.santabanta.R;
import com.app.santabanta.Utils.ResUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SideMenuAdapter extends RecyclerView.Adapter<SideMenuAdapter.ViewHolder>{

    private Activity context;
    private ArrayList<NavMenuResponse.NavMenuDetail> list ;
    private int currentSelectedItem = 999;
    private DrawerMenuClickListener menuClickListener;

    public SideMenuAdapter(Activity context, ArrayList<NavMenuResponse.NavMenuDetail> list,DrawerMenuClickListener menuClickListener) {
        this.context = context;
        this.list = list;
        this.menuClickListener = menuClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.side_menu_nav_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
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

        void bindData(NavMenuResponse.NavMenuDetail model){
            name.setText(model.getName());
            recycler.setLayoutManager(new LinearLayoutManager(context));
            recycler.setAdapter(new ExpandableViewAdapter(context,model.getInfo(),menuClickListener,getAdapterPosition()));
            viewMoreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (layoutExpand.getVisibility() == View.VISIBLE){
                        currentSelectedItem = 999;
                    }else {
                        currentSelectedItem = getAdapterPosition();
                    }
                    notifyDataSetChanged();
                }
            });

            if (currentSelectedItem == getAdapterPosition()){
                viewMoreBtn.setImageDrawable(ResUtils.getDrawable(R.drawable.ic_minus));
                layoutExpand.setVisibility(View.VISIBLE);
            }else {
                viewMoreBtn.setImageDrawable(ResUtils.getDrawable(R.drawable.ic_icon_feather_plus));
                layoutExpand.setVisibility(View.GONE);
            }
        }
    }
}
