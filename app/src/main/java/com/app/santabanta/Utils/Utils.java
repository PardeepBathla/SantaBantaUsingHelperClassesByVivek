package com.app.santabanta.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.app.santabanta.Activites.MainActivity;
import com.app.santabanta.AppController;
import com.app.santabanta.R;
import com.app.santabanta.base.BaseFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class Utils {

    public static ProgressDialog progressDialog;

    public static SharedPreferences getSharedPref(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void showLog(String tag, String msg) {
        if (msg != null)
            Log.e(tag, msg);
    }

    public static void ShowToast(Context context,String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public static String getMyDeviceId(Context activity) {

        return Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
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
                    .placeholder(R.drawable.ic_santa_banta_logo)
                    .into(profileImageCircleImageView);
        }
    }

    public static void switchFragment(FragmentTransaction transaction, BaseFragment fragment, int containerViewId) {

//        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    transaction.addToBackStack(null);
                    transaction.add(containerViewId, fragment).commit();
                } catch (Exception e) {
                    e.printStackTrace();
//                    showToast("Error!");
                }
            }
        });

    }

    public static void fixRecyclerScroll(RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout, LinearLayoutManager linearLayoutManager){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {

                int firstPos=linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                if (firstPos>0)
                {
                    swipeRefreshLayout.setEnabled(false);
                }
                else {
                    swipeRefreshLayout.setEnabled(true);
                }
            }
        });
    }

    public static void vibrate(Context mCtx) {

        Vibrator v = (Vibrator) ((MainActivity)mCtx).getSystemService();
// Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(100);
        }
    }

    public static void showProgressDialog(Context context){

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }
    public static void _dismissProgressDialog() {

        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


}
