package com.example.queenstagram.posts.recyclerview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.queenstagram.R;
import com.example.queenstagram.api.Api;
import com.example.queenstagram.posts.PostItem;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private Context context;
    private ArrayList<Api.Post> postItems;

    public PostAdapter(Context context, ArrayList<Api.Post> postItems) {
        this.context = context;
        this.postItems = postItems;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        /* baseView의 view holder 생성 */
        View baseView = View.inflate(context, R.layout.post_item, null);
        PostViewHolder postViewHolder = new PostViewHolder(baseView, this);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        Api.Post item = postItems.get(position);

        holder.tvUserName.setText(item.getUploader());
        holder.tvPostText.setText(item.getText());
        holder.tvLikeCount.setText(String.valueOf(item.getLikesCount()));
        Glide.with(context).load(item.getImageUrl()).centerCrop().into(holder.ivImg);
    }

    @Override
    public int getItemCount() {
        return postItems.size();
    }

    public void onLikeClicked(int position) {

        Api.Post item = postItems.get(position);
        Toast.makeText(context, "좋아요", Toast.LENGTH_SHORT).show();
    }
}
