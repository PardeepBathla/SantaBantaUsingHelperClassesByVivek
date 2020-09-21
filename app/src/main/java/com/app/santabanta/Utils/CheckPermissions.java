package com.app.santabanta.Utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.app.santabanta.Activites.MainActivity;

public class CheckPermissions {

    public static final int REQUEST_CODE = 11;

    public static boolean isStoragePermissionGranted(Context mCtx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mCtx.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Utils.showLog("Permissions","Permission is granted");
                return true;
            } else {
                ActivityCompat.requestPermissions(((MainActivity)mCtx), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE)
;
                Utils.showLog("Permissions","Permission is revoked");
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Utils.showLog("Permissions","Permission is granted");
            return true;
        }


    }
}
