package com.example.githubusersapp;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitApiService {
    @GET("users")
    Call<List<DataObject>> GitUsers(@Query("since") int since);
    @GET("users/{user}")
    Call<DataObject> GitDetails(@Path("user") String user);
}
