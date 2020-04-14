package com.example.queenstagram.posts.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.queenstagram.R;
import com.example.queenstagram.api.Api;
import com.example.queenstagram.posts.PostItem;
import com.example.queenstagram.uuid.UserUUID;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private Context context;
    private ArrayList<Api.Post> postItems;
    private PostViewHolder postViewHolder;

    public PostAdapter(Context context, ArrayList<Api.Post> postItems) {
        this.context = context;
        this.postItems = postItems;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        /* baseView의 view holder 생성 */
        View baseView = View.inflate(context, R.layout.post_item, null);
        postViewHolder = new PostViewHolder(baseView, this);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        Api.Post item = postItems.get(position);

        holder.tvUserName.setText(item.getUploader());
        holder.tvPostText.setText(item.getText());
        holder.tvLikeCount.setText(String.valueOf(item.getLikesCount()));
        holder.cbLike.setChecked(item.isUserLiked());
        Glide.with(context).load(item.getImageUrl()).centerCrop().into(holder.ivImg);
    }

    @Override
    public int getItemCount() {
        return postItems.size();
    }

    public void onLikeClicked(int position) {

        Api.Post post = postItems.get(position);

        // Firestore에 반영
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference postRef = db.document("posts/" + post.getId());   // getId : document ID
        CollectionReference likesRef = postRef.collection("likes");

        if (post.isUserLiked()){
            // 좋아요 취소
            DocumentReference userLikeRef = likesRef.document(post.getLikeId());
            userLikeRef.delete()
                    .addOnCompleteListener(task -> {
                        Toast.makeText(context, "좋아요 취소", Toast.LENGTH_SHORT).show();
                    });
        }
        else {
            // 좋아요 추가
            Map<String, Object> likeMap = new HashMap<>();
            likeMap.put("created_at", new Date());
            likeMap.put("userName", UserUUID.getUserUUID((Activity) context));
            likesRef.add(likeMap)
                    .addOnCompleteListener(task -> {
                        Toast.makeText(context, "좋아요", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}
