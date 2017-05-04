package com.example.githubusersapp;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.text.DateFormat;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class AppSingleton {
    private static final AppSingleton ourInstance = new AppSingleton();
    private GitApiService gitApiService;

    public static AppSingleton getInstance() {
        return ourInstance;
    }

    private AppSingleton() {
        Gson gson = new GsonBuilder().setDateFormat(DateFormat.FULL, DateFormat.FULL).create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("OkHttp", message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request request = chain.request();
                                HttpUrl url = request.url().newBuilder().addQueryParameter("access_token", "94b03abf4d63c51cec82c722218ad0ef8116d2b5").build();
                                request = request.newBuilder().url(url).build();
                                return chain.proceed(request);
                            }
                        })
                        .addInterceptor(logging)
                        .build())
                .build();
         gitApiService = retrofit.create(GitApiService.class);
    }
    public GitApiService getGitApiService() {
        return gitApiService;
    }
}
