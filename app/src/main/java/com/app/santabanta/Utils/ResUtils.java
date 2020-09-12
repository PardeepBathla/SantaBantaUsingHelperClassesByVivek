package com.app.santabanta.Utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.app.santabanta.AppController;

public class ResUtils {


    private static Resources getResources() {
        return AppController.getInstance().getResources();
    }

    public static int getColor(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return getResources().getColor(id, AppController.getInstance().getTheme());
        else
            return getResources().getColor(id);
    }

    public static String getString(int id) {
        return (getResources().getString(id));
    }

    public static Drawable getDrawable(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            return getResources().getDrawable(id, AppController.getInstance().getTheme());
        else
            return getResources().getDrawable(id);
    }

    public static int getDimens(int id) {
        return (Math.round((getResources().getDimension(id))));
    }

}
