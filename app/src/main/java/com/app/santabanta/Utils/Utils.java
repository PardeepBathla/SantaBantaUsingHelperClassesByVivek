package com.app.santabanta.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.fevziomurtekin.customprogress.Type;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Utils {

    public static void showImageSaveDialog(Activity mActivity,ImageView imageView) {
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.dialog_common, null);
        final Dialog dialog = new Dialog(mActivity);
        dialog.setContentView(view1);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        TextView tvMessage = dialog.findViewById(R.id.txt_dia);
        Button btnYes = dialog.findViewById(R.id.btn_yes);
        Button btnNo = dialog.findViewById(R.id.btn_no);

        tvMessage.setText(ResUtils.getString(R.string.save_image));
        btnYes.setText(ResUtils.getString(R.string.yes));
        btnNo.setText(ResUtils.getString(R.string.no));

        btnYes.setOnClickListener(v1 -> {
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            saveImage(bitmap);
            dialog.dismiss();
        });

        btnNo.setOnClickListener(v12 -> dialog.dismiss());

        dialog.show();
    }

    public static void saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/santa_banta");
        myDir.mkdirs();
        Random generator = new Random();

        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            // sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
            //     Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
// Tell the media scanner about the new file so that it is
// immediately available to the user.
        MediaScannerConnection.scanFile(AppController.getInstance(), new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
        Toast.makeText(AppController.getInstance(), ResUtils.getString(R.string.image_saved), Toast.LENGTH_SHORT).show();
    }


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
                    transaction.replace(containerViewId, fragment).commit();
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
