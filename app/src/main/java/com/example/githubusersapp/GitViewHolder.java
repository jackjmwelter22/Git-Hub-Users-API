package com.example.githubusersapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GitViewHolder extends RecyclerView.ViewHolder{
    public ImageView imageView;
    public TextView usr;
    public TextView id;

    public GitViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.photoImageView);
        usr = (TextView) itemView.findViewById(R.id.usrTextView);
        id = (TextView) itemView.findViewById(R.id.idTextView);
    }
}
