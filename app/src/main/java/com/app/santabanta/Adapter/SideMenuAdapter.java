package com.app.santabanta.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.Callbacks.DrawerMenuClickListener;
import com.app.santabanta.Modals.NavMenuResponse;
import com.app.santabanta.R;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.ResUtils;
import com.app.santabanta.Utils.Utils;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SideMenuAdapter extends RecyclerView.Adapter<SideMenuAdapter.ViewHolder>{

    private Activity context;
    private ArrayList<NavMenuResponse.NavMenuDetail> list ;
    private int currentSelectedItem = 999;
    private DrawerMenuClickListener menuClickListener;
    public SharedPreferences pref;

    public SideMenuAdapter(Activity context, ArrayList<NavMenuResponse.NavMenuDetail> list,DrawerMenuClickListener menuClickListener) {
        this.context = context;
        this.list = list;
        this.menuClickListener = menuClickListener;
        pref = Utils.getSharedPref(context);
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
        @BindView(R.id.parent)
        RelativeLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bindData(NavMenuResponse.NavMenuDetail model){


            name.setText(Html.fromHtml(model.getName()));
            if (pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false)) {
                if (getAdapterPosition() == 0) {
                    parent.setBackgroundColor(Color.parseColor("#62D1E5"));
                } else if (getAdapterPosition() == 1) {
                    parent.setBackgroundColor(Color.parseColor("#80B1C2"));
                } else if (getAdapterPosition() == 2) {
                    parent.setBackgroundColor(Color.parseColor("#66CBFF"));
                }else if (getAdapterPosition() == 3) {
                    parent.setBackgroundColor(Color.parseColor("#14AAF6"));
                }

            } else {
                if (getAdapterPosition() == 0) {
                    parent.setBackgroundColor(Color.parseColor("#ACE2EC"));
                } else if (getAdapterPosition() == 1) {
                    parent.setBackgroundColor(Color.parseColor("#7BD7EC"));
                } else if (getAdapterPosition() == 2) {
                    parent.setBackgroundColor(Color.parseColor("#ACDFFA"));
                }else if (getAdapterPosition() == 3) {
                    parent.setBackgroundColor(Color.parseColor("#BBE0F3"));
                }
            }

            if (model.getIcon() !=null && model.getIcon().length()>0)
                Utils.loadGlideImage(context,iv_item_logo,model.getIcon());

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

            if (pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false)) {
                if (currentSelectedItem == getAdapterPosition()) {
                    viewMoreBtn.setImageDrawable(ResUtils.getDrawable(R.drawable.ic_minus));
                    layoutExpand.setVisibility(View.VISIBLE);

                } else {
                    viewMoreBtn.setImageDrawable(ResUtils.getDrawable(R.drawable.ic_icon_feather_plus));
                    layoutExpand.setVisibility(View.GONE);
                }

            } else {
                if (currentSelectedItem == getAdapterPosition()) {
                    viewMoreBtn.setImageDrawable(ResUtils.getDrawable(R.drawable.ic_minus_black));
                    layoutExpand.setVisibility(View.VISIBLE);

                } else {
                    viewMoreBtn.setImageDrawable(ResUtils.getDrawable(R.drawable.ic_icon_feater_plus_black));
                    layoutExpand.setVisibility(View.GONE);
                }
            }

//            if (currentSelectedItem == getAdapterPosition()){
//                viewMoreBtn.setImageDrawable(ResUtils.getDrawable(R.drawable.ic_minus));
//                layoutExpand.setVisibility(View.VISIBLE);
//            }else {
//                viewMoreBtn.setImageDrawable(ResUtils.getDrawable(R.drawable.ic_icon_feather_plus));
//                layoutExpand.setVisibility(View.GONE);
//            }
        }
    }
}
