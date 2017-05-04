package com.example.githubusersapp;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private String login;
    private static Context mDetailContext;
    private ImageView imageView2;
    private TextView loginTextView;
    private TextView nameTextView;
    private TextView bioTextView;
    private TextView companyTextView;
    private TextView followersTextView;
    private TextView createdDateTextView;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        pb = (ProgressBar) findViewById(R.id.pbLoading);
        pb.setVisibility(ProgressBar.VISIBLE);

        login = getIntent().getStringExtra("login");

        imageView2 = (ImageView) findViewById(R.id.photoImageView2);
        loginTextView = (TextView) findViewById(R.id.loginTextView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        bioTextView = (TextView) findViewById(R.id.bioTextView);
        companyTextView = (TextView) findViewById(R.id.companyTextView);
        followersTextView = (TextView) findViewById(R.id.followersTextView);
        createdDateTextView = (TextView) findViewById(R.id.createdDateTextView);

       final Call<DataObject> call = AppSingleton.getInstance().getGitApiService().GitDetails(login);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                call.enqueue(new Callback<DataObject>() {
                    @Override
                    public void onResponse(Call<DataObject> dataObject, Response<DataObject> response) {
                        Picasso.with(mDetailContext).load(response.body().getAvatarUrl()).into(imageView2);
                        loginTextView.setText("User: " + response.body().getLogin());
                        nameTextView.setText("Name: " + response.body().getName());
                        bioTextView.setText("Bio: " + response.body().getBio());
                        companyTextView.setText("Company: " + response.body().getCompany());
                        followersTextView.setText("# Followers: " + String.valueOf(response.body().getFollowers()));
                        String pattern = "MM/dd/yyyy";
                        SimpleDateFormat format = new SimpleDateFormat(pattern);
                        createdDateTextView.setText("Created Date: " + format.format(response.body().getCreatedAt()));
                        pb.setVisibility(ProgressBar.INVISIBLE);
                    }
                    @Override
                    public void onFailure(Call<DataObject> dataObjects, Throwable t) {
                    }
                });
            }
        },0); //Simulating a delay in the data request
    }
}
