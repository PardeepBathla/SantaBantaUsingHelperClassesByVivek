package com.app.santabanta.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.Modals.JokesDetailModel;
import com.app.santabanta.R;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JokesHomeAdapter extends RecyclerView.Adapter<JokesHomeAdapter.ViewHolder> {

    public SharedPreferences pref;
    private List<JokesDetailModel> mData;
    private Activity mActivity;

    public JokesHomeAdapter(List<JokesDetailModel> mData, Activity mActivity) {
        this.mData = mData;
        this.mActivity = mActivity;
        pref = Utils.getSharedPref(mActivity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_jokes_item, parent, false);
        return new JokesHomeAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setBreadCrumbs(JokesDetailModel obj, LinearLayout llbreadcrumbs) {
        if (obj.getBreadcrumbs() != null && obj.getBreadcrumbs().size() > 0) {
            TextView[] textView = new TextView[obj.getBreadcrumbs().size()];

            if (llbreadcrumbs.getChildCount() > 0)
                llbreadcrumbs.removeAllViews();


            for (int i = 0; i < obj.getBreadcrumbs().size(); i++) {
                textView[i] = new TextView(mActivity);

                if (i == 0) {
                    textView[i].setText(obj.getBreadcrumbs().get(i).getLabel());

                } else {
                    textView[i].setText(" > " + obj.getBreadcrumbs().get(i).getLabel());

                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT

                );
                llbreadcrumbs.setOrientation(LinearLayout.HORIZONTAL);
                params.setMargins(3, 3, 3, 3);
                textView[i].setLayoutParams(params);
                llbreadcrumbs.addView(textView[i]);

                int finalI = i;
                String slug = obj.getBreadcrumbs().get(i).getLink();
                textView[i].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        mActivity.sendBroadcast(new Intent().setAction(GlobalConstants.INTENT_PARAMS.NAVIGATE_FROM_HOME)
                                .putExtra(GlobalConstants.INTENT_PARAMS.NAVIGATE_TYPE,"jokes").putExtra(GlobalConstants.INTENT_PARAMS.NAVIGATE_SLUG,slug));
                    }
                });
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.llbreadcrumbs)
        LinearLayout llbreadcrumbs;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.rl_fav_and_share)
        RelativeLayout rl_fav_and_share;
        @BindView(R.id.ll_like_dislike)
        LinearLayout ll_like_dislike;
        @BindView(R.id.cb_like)
        CheckBox cb_like;
        @BindView(R.id.ll_share_home)
        LinearLayout ll_share_home;
        @BindView(R.id.tv_share_count)
        TextView tv_share_count;
        @BindView(R.id.share)
        ImageView share;
        @BindView(R.id.tv_categories)
        TextView tv_categories;
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
        @BindView(R.id.cardView)
        CardView cardView;

        int TAB_WHITE = 0;
        int TAB_OFF_WHITE = 0;
        int TAB_BROWN = 0;
        int TAB_PURPLE = 0;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            TAB_WHITE = mActivity.getResources().getColor(R.color.light_gray);
            TAB_OFF_WHITE = mActivity.getResources().getColor(R.color.off_white);
            TAB_BROWN = mActivity.getResources().getColor(R.color.brown);
            TAB_PURPLE = mActivity.getResources().getColor(R.color.purple);

        }

        void bindData(JokesDetailModel model) {

            if (pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false)) {
                if (getAdapterPosition() % 2 == 0) {
                    cardView.setCardBackgroundColor(TAB_WHITE);
                } else {
                    cardView.setCardBackgroundColor(TAB_OFF_WHITE);
                }
            } else {
                if (getAdapterPosition() % 2 == 0) {
                    cardView.setCardBackgroundColor(TAB_BROWN);
                } else {
                    cardView.setCardBackgroundColor(TAB_PURPLE);
                }
            }


            tv_title.setText(model.getTitle());
            tvContent.setText(model.getContent());
            setBreadCrumbs(model, llbreadcrumbs);
            ll_share_home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ll_share_options_home.getVisibility() == View.VISIBLE) {
                        ll_share_options_home.setVisibility(View.GONE);
                    } else {
                        ll_share_options_home.setVisibility(View.VISIBLE);
                    }
                }
            });

            tvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Dialog dialog = new Dialog(mActivity, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.full_screen_joke_dialog);

                    TextView tv_content = dialog.findViewById(R.id.tvContent);
                    LinearLayout ll_share_joke = dialog.findViewById(R.id.ll_share_joke);
                    LinearLayout ll_share_options_joke = dialog.findViewById(R.id.ll_share_options_joke);
                    TextView tv_title = dialog.findViewById(R.id.tv_title);
                    TextView tv_categories = dialog.findViewById(R.id.tv_categories);
                    ImageView iv_close = dialog.findViewById(R.id.iv_close);
                    TextView tv_fav_count = dialog.findViewById(R.id.tv_fav_count);
                    ProgressBar progress_bar = dialog.findViewById(R.id.progress_bar);
                    ScrollView content_scroll = dialog.findViewById(R.id.content_scroll);

                    tv_fav_count.setText(String.valueOf(model.getFav_count()));
                    iv_close.setOnClickListener(v -> dialog.dismiss());
                    CheckBox cb_like = dialog.findViewById(R.id.cb_like);
                    tv_content.setText(Html.fromHtml(model.getContent().replaceAll("<br/><br/>", "")));
                    tv_title.setText(Html.fromHtml(model.getTitle()));

                    if (model.getCategories() != null && model.getCategories().size() != 0) {
                        tv_categories.setText(Html.fromHtml(model.getCategories().get(0).getName()));
                    }

                    cb_like.setOnCheckedChangeListener((buttonView, isChecked) -> {


                        if (isChecked) {
                            if (!tv_fav_count.getText().toString().equals("")) {
                                tv_fav_count.setText(String.valueOf(Integer.parseInt(tv_fav_count.getText().toString()) + 1));
                            } else {
                                tv_fav_count.setText("0");
                            }
                        } else {
                            if (!tv_fav_count.getText().equals("0")) {
                                tv_fav_count.setText(String.valueOf(Integer.parseInt(tv_fav_count.getText().toString()) - 1));
                            }
                        }
                        progress_bar.setVisibility(View.VISIBLE);
                        cb_like.setClickable(false);

                    });

                    dialog.show();
                }
            });
        }
    }
}
