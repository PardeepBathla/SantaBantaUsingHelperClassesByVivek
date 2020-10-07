package com.app.santabanta.Utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.app.santabanta.AppController;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import static com.app.santabanta.Utils.Utils.isPackageInstalled;

public class ShareableIntents<T> {

    private Activity mCtx;

    public ShareableIntents(Activity mCtx) {
        this.mCtx = mCtx;
    }

    public static void filterByPackageName(Context context, Intent intent, String prefix) {
        List<ResolveInfo> matches = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith(prefix)) {
                intent.setPackage(info.activityInfo.packageName);
                return;
            }
        }
    }

    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.wtf("", "UTF-8 should always be supported", e);
            return "";
        }
    }

    public void shareOnFbMesenger(String image) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, image);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.facebook.orca");
//        sendIntent.setPackage("com.facebook.lite");
        try {
            mCtx.startActivity(sendIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mCtx, "Please Install Facebook Messenger", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareOnSnapChat(String image) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
//        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        shareIntent.putExtra(Intent.EXTRA_STREAM,uri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, image);
        shareIntent.setPackage("com.snapchat.android");
        try {
            mCtx.startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mCtx, "Please Install Snapchat", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareOnPintrest(View v, String smsTOBeShared) {

//        String shareUrl = "https://stackoverflow.com/questions/27388056/";
//        String mediaUrl = "http://cdn.sstatic.net/stackexchange/img/logos/so/so-logo.png";
//        String description = "Pinterest sharing using Android intents";
//        String url = String.format(
//                "https://www.pinterest.com/pin/create/button/?description=%s",
////                "https://www.pinterest.com/pin/create/button/?url=%s&media=%s&description=%s",
//                 urlEncode(shareUrl), urlEncode(mediaUrl), urlEncode(description));
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        intent.setDataAndType(null,"text/plain");
//        filterByPackageName(mCtx, intent, "com.pinterest.android1");
//        try {
//            mCtx.startActivity(intent);
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(mCtx, "Please Install Pinterest", Toast.LENGTH_SHORT).show();
//        }

        PackageManager pm = AppController.getInstance().getPackageManager();
        boolean isInstalled = isPackageInstalled("com.pinterest", pm);
        if (isInstalled) {

            String shareUrl = "https://santabantareact.techpss.com/";
            String description = "";
            String url = String.format(
                    "https://www.pinterest.com/pin/create/button/?url=%s&media=%s",
                    urlEncode(shareUrl), urlEncode(smsTOBeShared));
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            filterByPackageName(mCtx, intent, "com.pinterest");
            mCtx.startActivity(intent);

//            Intent intent = new Intent(Intent.ACTION_SEND);
//            intent.setPackage("com.pinterest");
//            intent.setType("*/*");
//            intent.putExtra(Intent.EXTRA_TEXT, smsTOBeShared);
//            mCtx.startActivity(intent);
//
//            Intent sharePintrestIntent = new Intent(Intent.ACTION_SEND);
//            sharePintrestIntent.setPackage("com.pinterest");
////            sharePintrestIntent.putExtra(Intent.EXTRA_STREAM, uri);
//            sharePintrestIntent.putExtra("com.pinterest.EXTRA_DESCRIPTION", smsTOBeShared);
//            sharePintrestIntent.setType("*/*");
//            mCtx.startActivityForResult(sharePintrestIntent, 45);

        } else {
            Toast.makeText(mCtx, "Please install pinterest", Toast.LENGTH_SHORT).show();
        }


    }

    public void shareOnInstagram(String image) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
//        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        shareIntent.putExtra(Intent.EXTRA_STREAM,uri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, image);
        shareIntent.setPackage("com.instagram.android");
        try {
            mCtx.startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mCtx, "Please Install Instagram", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareOnTwitter(View v, String image) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, image);
        PackageManager pm = v.getContext().getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);

        boolean resolved = false;
        for (final ResolveInfo app : resolveInfos) {
            if (app.activityInfo.packageName.startsWith("com.twitter.android")) {
                resolved = true;
                shareIntent.setClassName(app.activityInfo.packageName, app.activityInfo.name);
                v.getContext().startActivity(shareIntent);
                break;
            }
        }
        if (!resolved) {
            Toast.makeText(mCtx, "Please Install Twitter", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareOnTwitter(View v, Uri image) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_STREAM, image);
        PackageManager pm = v.getContext().getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);

        boolean resolved = false;
        for (final ResolveInfo app : resolveInfos) {
            if (app.activityInfo.packageName.startsWith("com.twitter.android")) {
                resolved = true;
                shareIntent.setClassName(app.activityInfo.packageName, app.activityInfo.name);
                v.getContext().startActivity(shareIntent);
                break;
            }
        }
        if (!resolved) {
            Toast.makeText(mCtx, "Please Install Twitter", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareOnWhatsapp(Uri image) {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
//        whatsappIntent.putExtra(Intent.EXTRA_TEXT, image);
        whatsappIntent.putExtra(Intent.EXTRA_STREAM, image);
        try {
            mCtx.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Utils.ShowToast(mCtx, "Whatsapp has not been installed.");
        }
    }

    public void shareOnWhatsapp(String image) {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, image);
//        whatsappIntent.putExtra(android.content.Intent.EXTRA_STREAM, image);
        try {
            mCtx.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Utils.ShowToast(mCtx, "Whatsapp has not been installed.");
        }
    }

    private void shareOnFacebook(View v) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "http://www.google.fr/");
//        intent.setPackage("com.facebook");
//        mCtx.startActivity(intent);
        PackageManager pm = v.getContext().getPackageManager();
        List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
        for (final ResolveInfo app : activityList) {
            if ((app.activityInfo.name).contains("facebook")) {
                final ActivityInfo activity = app.activityInfo;
                final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                shareIntent.setComponent(name);
                v.getContext().startActivity(shareIntent);
                break;
            }
        }

    }
}
