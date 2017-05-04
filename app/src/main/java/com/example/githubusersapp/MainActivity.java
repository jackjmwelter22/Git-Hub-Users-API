package com.example.githubusersapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private GitAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GitAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        getData(0);

        mRecyclerView.addOnScrollListener(new EndlessScrollListener(mLayoutManager){
            @Override
            public void onLoadMore() {
                getData(mAdapter.getLastItemId());
            }
        });
    }
    public void getData(int idCount) {
        Call<List<DataObject>> call = AppSingleton.getInstance().getGitApiService().GitUsers(idCount);

        call.enqueue(new Callback<List<DataObject>>() {
            @Override
            public void onResponse(Call<List<DataObject>> dataObjects, Response<List<DataObject>> response) {
                mAdapter.setDataList(response.body());
            }
            @Override
            public void onFailure(Call<List<DataObject>> dataObjects, Throwable t) {
            }
        });
    }
}
