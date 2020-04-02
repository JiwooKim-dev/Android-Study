package com.example.lab6f_recyclerview.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab6f_recyclerview.R;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public ImageView ivImg, ivLike, ivShare;
    public TextView tvLikeCount, tvUserName, tvPostText;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);

        ivImg = itemView.findViewById(R.id.iv_img);
        ivLike = itemView.findViewById(R.id.iv_like);
        ivShare = itemView.findViewById(R.id.iv_share);

        tvLikeCount = itemView.findViewById(R.id.tv_likeCount);
        tvUserName = itemView.findViewById(R.id.tv_userName);
        tvPostText = itemView.findViewById(R.id.tv_postText);
    }
}
