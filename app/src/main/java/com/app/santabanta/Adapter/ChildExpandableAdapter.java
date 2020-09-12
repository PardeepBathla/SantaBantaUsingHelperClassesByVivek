package com.app.santabanta.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.Callbacks.DrawerMenuClickListener;
import com.app.santabanta.Modals.NavMenuResponse;
import com.app.santabanta.R;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChildExpandableAdapter extends RecyclerView.Adapter<ChildExpandableAdapter.ViewHolder>{

    private Activity context;
    private ArrayList<NavMenuResponse.NavMenuDetail.NavMenuDetailChildInfo.NavMenuDetailChildSubInfo> children;
    private String name;
    private DrawerMenuClickListener menuClickListener;
    private int parentPosition = 0;
    public SharedPreferences pref;

    public ChildExpandableAdapter(Activity context, ArrayList<NavMenuResponse.NavMenuDetail.NavMenuDetailChildInfo.NavMenuDetailChildSubInfo> children
            , String name,DrawerMenuClickListener menuClickListener,int parentPosition) {
        this.context = context;
        this.children = children;
        this.name = name;
        this.menuClickListener = menuClickListener;
        this.parentPosition = parentPosition;
        pref = Utils.getSharedPref(context);
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
        @BindView(R.id.rl_root)
        RelativeLayout rlRoot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bindData(NavMenuResponse.NavMenuDetail.NavMenuDetailChildInfo.NavMenuDetailChildSubInfo model){

            if (pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false)) {
                rlRoot.setBackgroundColor(Color.parseColor("#DAD8D9"));
            } else {
                rlRoot.setBackgroundColor(Color.parseColor("#DAD8D9"));
            }
            text.setText(model.getName());
            Utils.loadGlideImage(context,iv_cat,model.getIcon());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    menuClickListener.onSmsClicked(model.getSlug(), String.valueOf(model.getId()),parentPosition == 0 ? "Veg" : "");
                }
            });
        }
    }
}
