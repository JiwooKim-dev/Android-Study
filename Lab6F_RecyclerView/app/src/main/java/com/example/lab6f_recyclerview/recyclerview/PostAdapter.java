package com.example.lab6f_recyclerview.recyclerview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab6f_recyclerview.R;
import com.example.lab6f_recyclerview.model.PostItem;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    /* 생성자에서 MainActivity 받아오기 */
    private Context context;
    private ArrayList<PostItem> postItems;

    public PostAdapter(Context context, ArrayList<PostItem> listItem) {
        this.context = context;
        postItems = listItem;
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

        PostItem item = postItems.get(position);
        holder.tvUserName.setText(item.getUserName());
        holder.tvPostText.setText(item.getPostText());
        holder.tvLikeCount.setText(String.valueOf(item.getPostLikeCount()));
    }

    @Override
    public int getItemCount() {
        return postItems.size();
    }

    public void onLikeClicked(int position) {

        PostItem item = postItems.get(position);
        Toast.makeText(context, position + " : " +item.getPostLikeCount(), Toast.LENGTH_SHORT).show();
    }
}
