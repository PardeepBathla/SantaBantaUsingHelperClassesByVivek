package com.app.santabanta.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.view.Window;
import android.widget.ImageView;

import com.app.santabanta.AppController;
import com.app.santabanta.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class Utils {

    public static SharedPreferences getSharedPref(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static Dialog getProgressDialog(Activity mActivity) {
        Dialog progressDialog ;
        if (mActivity == null)
            progressDialog = new Dialog(AppController.getInstance());
        else
            progressDialog = new Dialog(mActivity);
        progressDialog.setCancelable(true);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return progressDialog;
    }

    public static void loadGlideImage(Activity mActivity, ImageView profileImageCircleImageView, String url) {

        if (!mActivity.isDestroyed()) {
            Glide.with(mActivity)
                    .load(url)
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .error(R.mipmap.ic_launcher)
                    .priority(Priority.HIGH)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(profileImageCircleImageView);
        }
    }
}
