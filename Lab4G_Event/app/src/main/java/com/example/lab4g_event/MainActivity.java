package com.example.lab4g_event;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.iv_like).setOnClickListener(this);
        findViewById(R.id.iv_share).setOnClickListener(this);

        findViewById(R.id.iv_photo).setOnTouchListener(this);
        findViewById(R.id.iv_photo).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case(R.id.iv_like):

                Toast.makeText(MainActivity.this, "좋아요", Toast.LENGTH_SHORT).show();
                break;

            case(R.id.iv_share):

                Toast.makeText(MainActivity.this, "공유하기", Toast.LENGTH_SHORT).show();
                break;

            case(R.id.iv_photo):

                Toast.makeText(MainActivity.this, "사진", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    // onTouch가 상위 onClick이 하위
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch(event.getAction()){

            case(MotionEvent.ACTION_DOWN):
                Toast.makeText(MainActivity.this, "사진down터치", Toast.LENGTH_SHORT).show();
                break;
            case(MotionEvent.ACTION_UP):
                Toast.makeText(MainActivity.this, "사진up터치", Toast.LENGTH_SHORT).show();
                break;
        }

      ///  Toast.makeText(MainActivity.this, "사진터치", Toast.LENGTH_SHORT).show();
        return false;
    }
}
