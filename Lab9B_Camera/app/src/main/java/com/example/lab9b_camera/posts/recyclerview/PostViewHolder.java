package com.example.lab9b_camera.posts.recyclerview;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab9b_camera.R;

public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public CheckBox cbLike;
    public ImageView ivImg, ivLike, ivShare;
    public TextView tvLikeCount, tvUserName, tvPostText;
    private PostAdapter adapter;

    public PostViewHolder(@NonNull View itemView, PostAdapter postAdapter) {
        super(itemView);

        ivImg = itemView.findViewById(R.id.iv_img);
        cbLike = itemView.findViewById(R.id.cb_like);
        ivShare = itemView.findViewById(R.id.iv_share);

        tvLikeCount = itemView.findViewById(R.id.tv_likeCount);
        tvUserName = itemView.findViewById(R.id.tv_userName);
        tvPostText = itemView.findViewById(R.id.tv_postText);

        cbLike.setOnClickListener(this);
        ivShare.setOnClickListener(this);

        adapter = postAdapter;
    }

    @Override
    public void onClick(View v) {

        int position = getAdapterPosition();

        switch(v.getId()){

            case (R.id.cb_like):
                adapter.onLikeClicked(position);
                break;

            case(R.id.iv_share):
                break;
        }
    }
}
