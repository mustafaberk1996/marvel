package com.app.marvel;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        String rootUrl = Constants.ROOT_API_URL;

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();


        if (retrofit == null) {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT)
                rootUrl = rootUrl.replace("https", "http");
            retrofit = new Retrofit.Builder()
                    .baseUrl(rootUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
            return retrofit;
        }
        return retrofit;
    }
}
