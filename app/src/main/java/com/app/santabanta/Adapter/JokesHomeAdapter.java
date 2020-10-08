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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.app.santabanta.Events.Events;
import com.app.santabanta.Events.GlobalBus;
import com.app.santabanta.Fragment.FragmentJokes;
import com.app.santabanta.Helper.FragmentJokesHelper;
import com.app.santabanta.Modals.HomeDetailList;
import com.app.santabanta.Modals.JokesDetailModel;
import com.app.santabanta.Modals.JokesFavouriteModel;
import com.app.santabanta.Modals.SmsDetailModel;
import com.app.santabanta.R;
import com.app.santabanta.Utils.CheckPermissions;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.Utils;
import com.app.santabanta.Utils.GlobalConstants;
import com.app.santabanta.Utils.ShareableIntents;
import com.app.santabanta.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JokesHomeAdapter extends RecyclerView.Adapter<JokesHomeAdapter.ViewHolder> {

    private final ShareableIntents shareableIntents;
    public SharedPreferences pref;
    private List<JokesDetailModel> mData;
    private Activity mActivity;
    private boolean isSharelayoutVisible = false;
    private FragmentJokesHelper fragmentJokesHelper;
    private FragmentJokes fragmentJokes;

    public JokesHomeAdapter(Activity mActivity, FragmentJokesHelper fragmentJokesHelper, FragmentJokes fragmentJokes) {
        this.mData = new ArrayList<>();
        this.mActivity = mActivity;
        pref = Utils.getSharedPref(mActivity);
        shareableIntents = new ShareableIntents(mActivity);
        this.fragmentJokesHelper = fragmentJokesHelper;
        this.fragmentJokes = fragmentJokes;
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
        holder.bindData(mData.get(position), position);
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
//                        Events.JokesEvent jokesEvent= new Events.JokesEvent(slug);
//                        GlobalBus.getBus().post(jokesEvent);
                        fragmentJokes.enterSubCategoryJoke(true, slug);
//                        mActivity.sendBroadcast(new Intent().setAction(GlobalConstants.INTENT_PARAMS.NAVIGATE_FROM_HOME)
//                                .putExtra(GlobalConstants.INTENT_PARAMS.NAVIGATE_TYPE,"jokes").putExtra(GlobalConstants.INTENT_PARAMS.NAVIGATE_SLUG,slug));
                    }
                });
            }
        }
    }

    public ArrayList<JokesDetailModel> getCurrentList() {
        return (ArrayList<JokesDetailModel>) mData;
    }

    public void updateList(ArrayList<JokesDetailModel> pagedLists) {
        mData = pagedLists;
        notifyDataSetChanged();
    }

    public void addAll(List<JokesDetailModel> list) {
        for (JokesDetailModel mc : list) {
            add(mc);
        }
    }

    public void add(JokesDetailModel item) {
        this.mData.add(item);
        //notifyItemInserted(mData.size() - 1);
        notifyDataSetChanged();
    }

    public void resetList() {
        if (this.mData != null) {
            this.mData = new ArrayList<>();
            notifyDataSetChanged();
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

        int TAB_WHITE = 0;
        int TAB_OFF_WHITE = 0;
        int TAB_BROWN = 0;
        int TAB_PURPLE = 0;
        @BindView(R.id.tv_like_count)
        TextView tv_like_count;
        @BindView(R.id.cardview)
        CardView cardview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            TAB_WHITE = mActivity.getResources().getColor(R.color.light_gray);
            TAB_OFF_WHITE = mActivity.getResources().getColor(R.color.off_white);
            TAB_BROWN = mActivity.getResources().getColor(R.color.brown);
            TAB_PURPLE = mActivity.getResources().getColor(R.color.purple);
        }

        void bindData(JokesDetailModel model, int position) {


            if (pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false)) {
                if (getAdapterPosition() % 2 == 0) {
                    cardview.setCardBackgroundColor(TAB_WHITE);
                } else {
                    cardview.setCardBackgroundColor(TAB_OFF_WHITE);
                }
            } else {
                if (getAdapterPosition() % 2 == 0) {
                    cardview.setCardBackgroundColor(TAB_BROWN);
                } else {
                    cardview.setCardBackgroundColor(TAB_PURPLE);
                }
            }


           /* if (model.getFav_count() == 0) {
                tv_like_count.setVisibility(View.GONE);
            } else {
                tv_like_count.setVisibility(View.GONE);
                tv_like_count.setText(String.valueOf(model.getFav_count()));
            }
*/

            if (model.getmFavourite() != null && model.getmFavourite().size() != 0) {
                for (JokesFavouriteModel favouriteModel : model.getmFavourite()) {
                    if (favouriteModel.getDeviceId().equals(Utils.getMyDeviceId(mActivity))) {
                        cb_like.setChecked(true);
                        break;

                    } else {
                        cb_like.setChecked(false);
                    }
                }
            } else {
                cb_like.setChecked(false);
            }


            tv_title.setText(Html.fromHtml(model.getTitle()));
            tvContent.setText(Html.fromHtml(model.getContent()));
            setBreadCrumbs(model, llbreadcrumbs);
            jokesItemListener(model, position);


        }

        private void jokesItemListener(JokesDetailModel obj, int position) {

            socialSharing(obj, iv_whatsapp, iv_facebook, iv_twitter, iv_instagram, iv_pintrest, iv_snapchat);


            tvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    boolean is_checked = cb_like.isChecked();
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




                    if (obj.getFav_count() != -1 && obj.getFav_count() != 0) {
                        tv_fav_count.setVisibility(View.GONE);
                        tv_fav_count.setText(String.valueOf(obj.getFav_count()));
                    } else {
                        tv_fav_count.setVisibility(View.GONE);
                    }


                    ImageView iv_whatsapp = dialog.findViewById(R.id.iv_whatsapp);
                    ImageView iv_facebook = dialog.findViewById(R.id.iv_facebook);
                    ImageView iv_twitter = dialog.findViewById(R.id.iv_twitter);
                    ImageView iv_instagram = dialog.findViewById(R.id.iv_instagram);
                    ImageView iv_pintrest = dialog.findViewById(R.id.iv_pintrest);
                    ImageView iv_snapchat = dialog.findViewById(R.id.iv_snapchat);

                    tv_fav_count.setText(String.valueOf(obj.getFav_count()));
                    iv_close.setOnClickListener(v -> dialog.dismiss());
                    CheckBox cb_like = dialog.findViewById(R.id.cb_like);
                    cb_like.setChecked(is_checked);


                    tv_content.setText(Html.fromHtml(obj.getContent().replaceAll("<br/><br/>", "")));
                    tv_title.setText(Html.fromHtml(obj.getTitle()));

                    if (obj.getCategories() != null && obj.getCategories().size() != 0) {
                        tv_categories.setText(Html.fromHtml(obj.getCategories().get(0).getName()));
                    }


                    ll_share_joke.setOnClickListener(v -> {
                        if (isSharelayoutVisible) {
                            DialogshareLayoutGone(ll_share_joke, ll_share_options_joke);
                        } else {
                            ll_share_joke.setBackgroundDrawable(mActivity.getDrawable(R.drawable.bottom_round_corner_bg));
                            int padding = (int) mActivity.getResources().getDimension(R.dimen._10sdp);
                            ll_share_joke.setPadding(padding, padding, padding, padding);
                            ll_share_options_joke.setVisibility(View.VISIBLE);
                            isSharelayoutVisible = true;
                        }
                    });
                    socialSharing(obj, iv_whatsapp, iv_facebook, iv_twitter, iv_instagram, iv_pintrest, iv_snapchat);
                    content_scroll.setOnClickListener(v -> shareLayoutGone());
                    iv_close.setOnClickListener(v -> dialog.dismiss());


                    cb_like.setOnCheckedChangeListener((buttonView, isChecked) -> {


                        if (isChecked) {
                            if (!tv_fav_count.getText().toString().equals("")) {
                                tv_fav_count.setVisibility(View.GONE);
                                tv_fav_count.setText(String.valueOf(Integer.parseInt(tv_fav_count.getText().toString()) + 1));
                            } else {
                                tv_fav_count.setVisibility(View.GONE);
                                tv_fav_count.setText("0");
                            }
                        } else {
                            if (!tv_fav_count.getText().equals("0")) {
                                tv_fav_count.setVisibility(View.GONE);
                                tv_fav_count.setText(String.valueOf(Integer.parseInt(tv_fav_count.getText().toString()) - 1));
                            }
                        }
                        progress_bar.setVisibility(View.VISIBLE);
                        cb_like.setClickable(false);

                    });

                    dialog.show();
                }
            });

            cb_like.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (buttonView.isPressed()) {
                    Dialog dialog = Utils.getProgressDialog(mActivity);
                    dialog.show();
                    cb_like.setClickable(false);
                    onFavCheckChanged(isChecked, obj, position, cb_like, dialog);
                }
                //FUNCTIONALITY NOT IN SCOPE FOR THE TIME BEING
                //                    SmsRepository.getInstance().InsertFavourite(obj,fragmentSms);
            });


            ll_share_home.setOnClickListener(v -> {

                Utils.vibrate(mActivity);

                if (isSharelayoutVisible) {
                    shareLayoutGone();
                } else {
                    ll_share_home.setBackgroundDrawable(mActivity.getDrawable(pref.getBoolean(GlobalConstants.COMMON.THEME_MODE_LIGHT, false) ? R.drawable.share_round_corner_bg_light : R.drawable.bottom_round_corner_bg));
                    int padding = (int) mActivity.getResources().getDimension(R.dimen._10sdp);
                    ll_share_home.setPadding(padding, padding, padding, padding);
                    ll_share_options_home.setVisibility(View.VISIBLE);
                    isSharelayoutVisible = true;
                }
            });
        }


        private void DialogshareLayoutGone(LinearLayout ll_share_joke, LinearLayout ll_share_options_joke) {
            isSharelayoutVisible = false;
            ll_share_joke.setBackground(null);
            ll_share_options_joke.setVisibility(View.GONE);
        }


        public void shareLayoutGone() {

            isSharelayoutVisible = false;
            ll_share_home.setBackground(null);
            ll_share_options_home.setVisibility(View.GONE);
        }



        private void socialSharing(JokesDetailModel obj, ImageView iv_whatsapp, ImageView iv_facebook, ImageView iv_twitter, ImageView iv_instagram, ImageView iv_pintrest, ImageView iv_snapchat) {
            iv_whatsapp.setOnClickListener(v -> {
                shareLayoutGone();
                Utils.vibrate(mActivity);
                shareableIntents.shareOnWhatsapp(obj.getContent());

            });
            iv_facebook.setOnClickListener(v -> {
                shareLayoutGone();
                Utils.vibrate(mActivity);
                shareableIntents.shareOnFbMesenger(obj.getContent());

            });
            iv_twitter.setOnClickListener(v -> {
                shareableIntents.shareOnTwitter(v, obj.getContent());
                Utils.vibrate(mActivity);
                shareLayoutGone();
            });
            iv_instagram.setOnClickListener(v -> {
                shareableIntents.shareOnInstagram(obj.getContent());
                Utils.vibrate(mActivity);
                shareLayoutGone();
            });
            iv_pintrest.setOnClickListener(v -> {
                shareableIntents.shareOnPintrest(v, obj.getContent());
                Utils.vibrate(mActivity);
                shareLayoutGone();
            });
            iv_snapchat.setOnClickListener(v -> {
                shareableIntents.shareOnSnapChat(obj.getContent());
                Utils.vibrate(mActivity);
                shareLayoutGone();
            });
        }

        private void onFavCheckChanged(boolean isChecked, JokesDetailModel obj, int position, CheckBox cbLike, Dialog progress_bar) {
            if (isChecked) {
                fragmentJokesHelper.addJokeTOFav(obj, position, progress_bar, cbLike);
            } else {
                if (obj.getmFavourite() != null) {
                    for (JokesFavouriteModel favouriteModel : obj.getmFavourite()) {
                        if (favouriteModel.getDeviceId().equals(Utils.getMyDeviceId(mActivity))) {
                            fragmentJokesHelper.removeFromFav(obj, position, favouriteModel.getId(), progress_bar, cbLike);
                            break;

                        }
                    }
                }

            }
        }

    }


}
