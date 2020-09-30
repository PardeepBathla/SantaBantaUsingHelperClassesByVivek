package com.app.santabanta.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.Activites.MainActivity;
import com.app.santabanta.AppController;
import com.app.santabanta.Callbacks.DrawerMenuClickListener;
import com.app.santabanta.Modals.NavMenuResponse;
import com.app.santabanta.R;
import com.app.santabanta.Utils.CheckPermissions;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.ResUtils;
import com.app.santabanta.Utils.SimpleDividerItemDecoration;
import com.app.santabanta.Utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ExpandableViewAdapter extends RecyclerView.Adapter<ExpandableViewAdapter.ViewHolder> {

    public SharedPreferences pref;
    private Activity context;
    private ArrayList<NavMenuResponse.NavMenuDetail.NavMenuDetailChildInfo> navMenuChildDetails;
    private DrawerMenuClickListener menuClickListener;
    private int parentPosition;
    private int currentPos = 999;

    public ExpandableViewAdapter(Activity context, ArrayList<NavMenuResponse.NavMenuDetail.NavMenuDetailChildInfo> navMenuChildDetails, DrawerMenuClickListener menuClickListener, int parentPosition) {
        this.menuClickListener = menuClickListener;
        this.context = context;
        this.navMenuChildDetails = navMenuChildDetails;
        this.parentPosition = parentPosition;
        pref = Utils.getSharedPref(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expandable_view, parent, false);
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

    class ViewHolder extends RecyclerView.ViewHolder {

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
            ButterKnife.bind(this, itemView);
        }

        void bindData(NavMenuResponse.NavMenuDetail.NavMenuDetailChildInfo model) {

            if (model.getIcon() != null && model.getIcon().length() > 0)
                Utils.loadGlideImage(context, iv_item_logo, model.getIcon());

            if (currentPos == getAdapterPosition()){
                layoutExpand.setVisibility(View.VISIBLE);
                if (pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT,false)){
                    viewMoreBtn.setImageDrawable(ResUtils.getDrawable(R.drawable.ic_minus));
                }else {
                    viewMoreBtn.setImageDrawable(ResUtils.getDrawable(R.drawable.ic_minus_black_new));
                }

            }else {
                layoutExpand.setVisibility(View.GONE);
                if (pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT,false)){
                    viewMoreBtn.setImageDrawable(ResUtils.getDrawable(R.drawable.ic_icon_feather_plus));
                }else {
                    viewMoreBtn.setImageDrawable(ResUtils.getDrawable(R.drawable.ic_icon_feater_plus_black));
                }
            }

            if (pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false)) {
                parent.setBackgroundColor(Color.parseColor("#B2B0B1"));
                if (layoutExpand.getVisibility() == View.VISIBLE)
                    viewMoreBtn.setImageDrawable(ResUtils.getDrawable(R.drawable.ic_minus));
                else
                    viewMoreBtn.setImageDrawable(ResUtils.getDrawable(R.drawable.ic_icon_feather_plus));

            } else {
                parent.setBackgroundColor(Color.parseColor("#B2B0B1"));
                if (layoutExpand.getVisibility() == View.VISIBLE)
                    viewMoreBtn.setImageDrawable(ResUtils.getDrawable(R.drawable.ic_minus_black_new));
                else
                    viewMoreBtn.setImageDrawable(ResUtils.getDrawable(R.drawable.ic_icon_feater_plus_black));

            }


            name.setText(model.getName());
            recycler.setLayoutManager(new LinearLayoutManager(context));
            recycler.setAdapter(new ChildExpandableAdapter(context, model.getInfo(), model.getName(), menuClickListener, getAdapterPosition(),parentPosition));
            recycler.addItemDecoration(new SimpleDividerItemDecoration(context,40));
            viewMoreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (currentPos == getAdapterPosition()){
                        currentPos = 999;
                        notifyDataSetChanged();
                    }else {
                        if (getAdapterPosition() == 1){

                            if (parentPosition == 0 || parentPosition == 1){
                                if (AppController.SHOW_ADULT_DIALOG){
                                    LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
                                    View view1 = inflater.inflate(R.layout.dialog_18_plus, null);
                                    final Dialog dialog = new Dialog(context);
                                    dialog.setContentView(view1);
                                    dialog.setCancelable(true);
                                    dialog.setCanceledOnTouchOutside(true);
                                    Button btn_yes = dialog.findViewById(R.id.btn_yes);
                                    Button btn_no = dialog.findViewById(R.id.btn_no);
                                    btn_yes.setOnClickListener(view2 -> {
                                        AppController.SHOW_ADULT_DIALOG = false;
                                        currentPos = getAdapterPosition();
                                        notifyDataSetChanged();
                                        dialog.dismiss();

                                    });
                                    btn_no.setOnClickListener(view12 -> dialog.dismiss());
                                    dialog.show();
                                }else {
                                    currentPos = getAdapterPosition();
                                    notifyDataSetChanged();
                                }
                            }else {
                                currentPos = getAdapterPosition();
                                notifyDataSetChanged();
                            }



                        }else {
                            currentPos = getAdapterPosition();
                            notifyDataSetChanged();
                        }

                    }
                }
            });


            viewMoreBtn.setVisibility(model.getInfo().size() > 0 ? View.VISIBLE : View.GONE);

            itemView.setOnClickListener(view -> {
                // for jokes
                if (model.getInfo()!=null && model.getInfo().size()>0){
                    viewMoreBtn.performClick();
                }else {

                    if (parentPosition == 1)
                        menuClickListener.onJokesClicked(model.getSlug(), model.getId());
                    else // for memes
                        if (parentPosition == 2)
                            menuClickListener.onMemesClicked(model.getSlug(), model.getId());
                }

            });
        }
    }

}
