package com.app.santabanta.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

public class LoadImageBitmap extends AsyncTask<URL, String, Bitmap> {
    Context context;
    Bitmap bitmap;
    BitmapLoadedCallback bitmapLoadedCallback;
    String platform;


    public LoadImageBitmap(Context context, BitmapLoadedCallback bitmapLoadedCallback, String platform) {
        this.bitmapLoadedCallback = bitmapLoadedCallback;
        this.context = context;
        this.platform = platform;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Utils.showProgressDialog(context);
    }

    @Override
    protected Bitmap doInBackground(URL... strings) {
        try {
            bitmap = BitmapFactory.decodeStream(strings[0].openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        bitmapLoadedCallback.onBitmapLoaded(bitmap,platform);
        Utils._dismissProgressDialog();
    }
}
