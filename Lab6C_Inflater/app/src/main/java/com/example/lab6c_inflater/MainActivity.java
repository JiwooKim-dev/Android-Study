package com.example.lab6c_inflater;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lab6c_inflater.model.PostItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 포스트 데이터 입력 */
        ArrayList<PostItem> listItem = new ArrayList<>();

        for (int i=0 ; i<30 ; i++){

            PostItem postItem = new PostItem(true, i*10,"user"+(i+1),
                    "https://images-na.ssl-images-amazon.com/images/I/51aq7G0ttnL._AC_SY606_.jpg",
                    "This is item "+(i+1));
            listItem.add(i, postItem);
        }

        /* 포스트 화면에 출력 */

        LinearLayout ll_scrollParent = findViewById(R.id.ll_scroll);

        for (PostItem postItem : listItem){

            View v = View.inflate(this, R.layout.post_item, null);
            ImageView ivItem = v.findViewById(R.id.iv_item);
            TextView tvUserName = v.findViewById(R.id.tv_userName);
            TextView tvPostLikeCount = v.findViewById(R.id.tv_postLikeCount);
            TextView tvPostText = v.findViewById(R.id.tv_postText);

            tvUserName.setText(postItem.getUserName());
            // tvPostLikeCount.setText(postItem.getPostLikeCount());
            tvPostText.setText(postItem.getPostText());
            // ivItem.setImageURI(postItem.getPostImgUrl());

            ll_scrollParent.addView(v);
        }


    }
}
