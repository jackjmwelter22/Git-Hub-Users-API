package com.example.githubusersapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class GitAdapter extends RecyclerView.Adapter<GitViewHolder> {
    private List<DataObject> mDataList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;

    public GitAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }
    @Override
    public GitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_user, parent, false);
        final GitViewHolder viewHolder = new GitViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(GitViewHolder holder, int position) {
        final DataObject dataObject = mDataList.get(position);
        Picasso.with(mContext).load(dataObject.getAvatarUrl()).into(holder.imageView);
        holder.usr.setText("User: " + dataObject.getLogin());
        holder.id.setText("id: " + Integer.toString(dataObject.getId()));

        holder.itemView.findViewById(R.id.linearLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(mContext,DetailActivity.class);
                intent.putExtra("login",dataObject.getLogin());
                //mContext.startActivity(intent);

                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(this, holder.imageView, mContext.getString(R.string.activity_image_trans));
                mContext.startActivity(intent, options.toBundle());
            }
        });
    }
    @Override
    public int getItemCount() {
        return (mDataList == null) ? 0 : mDataList.size();
    }
    public void setDataList(List<DataObject> DataList) {
        this.mDataList.addAll(DataList);
        notifyDataSetChanged();
    }
    public int getLastItemId() {
        return mDataList.get(mDataList.size()-1).getId();
    }
}
