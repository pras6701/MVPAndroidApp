package com.mvp.app.data.network;

import android.content.Context;

import com.mvp.app.BuildConfig;
import com.mvp.app.data.network.adapter.ImprovedDateTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static final String BASE_ENDPOINT = BuildConfig.BASE_URL;

    public static IApiHelper makeApiService(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new UnauthorisedInterceptor(context))
                .addInterceptor(logging)
                .build();

        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new ImprovedDateTypeAdapter()).create();
               // .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_ENDPOINT)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(IApiHelper.class);
    }

}

class Util {
    // Build API authorization string from a given access token.
    public static String buildAuthorization(String accessToken) {
        return "Bearer " + accessToken;
    }
}
