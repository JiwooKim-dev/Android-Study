package com.example.lab9b_camera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

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
