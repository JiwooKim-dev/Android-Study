package com.example.lab7g_glide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    ImageView ivScone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivScone = findViewById(R.id.iv_scone);
        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoadSconeImage();
            }
        });
    }

    private void startLoadSconeImage() {

        String url = "https://cdn.sallysbakingaddiction.com/wp-content/uploads/2019/04/scones.jpg";
        Glide.with(this).load(url).into(ivScone);
    }
}
