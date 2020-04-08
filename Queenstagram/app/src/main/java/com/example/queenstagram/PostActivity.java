package com.example.queenstagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class PostActivity extends AppCompatActivity {

    ImageView ivPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ivPost = findViewById(R.id.iv_post);
        showImage();
    }

    private void showImage() {

        Intent intent = getIntent();
        Bitmap imgBitmap = (Bitmap) intent.getExtras().get("photo");
        ivPost.setImageBitmap(imgBitmap);
    }
}
