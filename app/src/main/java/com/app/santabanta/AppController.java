package com.app.santabanta;

import android.app.Application;
import android.os.SystemClock;

import com.app.santabanta.RestClient.TLSSocketFactory;
import com.app.santabanta.Utils.GlobalConstants;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppController extends Application {

    private static AppController mInstance;
    public static boolean SHOW_ADULT_DIALOG = true;
    private static OkHttpClient okHttpClient = null;
    public static  String LANGUAGE_SELECTED = "language_selected";

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public static Retrofit getRetroInstance() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(1);

        Interceptor interceptor = chain -> {
            SystemClock.sleep(700);
            return chain.proceed(chain.request());
        };
        TLSSocketFactory tlsSocketFactory= null;
           okHttpClient = null;
        try {
            tlsSocketFactory = new TLSSocketFactory();

            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.MINUTES)
                    .readTimeout(10, TimeUnit.MINUTES)
                    .writeTimeout(10, TimeUnit.MINUTES)
//                    .addInterceptor(logging)
//                    .addNetworkInterceptor(interceptor)
                    .dispatcher(dispatcher)
                    .sslSocketFactory(tlsSocketFactory, tlsSocketFactory.getTrustManager())
                    .build();


        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return new Retrofit.Builder()
                .baseUrl(GlobalConstants.API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static synchronized OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
