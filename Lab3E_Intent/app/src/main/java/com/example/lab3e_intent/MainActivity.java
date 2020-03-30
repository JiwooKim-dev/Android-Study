package com.example.lab3e_intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // res.layout.activity_main.xml

        /* 버튼 연결 */
        Button button = (Button) findViewById(R.id.btn_call);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        /* 명시적 Intent */
        Intent intent = new Intent(MainActivity.this, CallActivity.class);
        intent.putExtra("intent-message","졸려졸려");
        startActivity(intent);

        /* Toast 메세지 출력 */
        // Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show();
        // Toast.makeText(MainActivity.this, "Click!", Toast.LENGTH_SHORT).show();
    }
}
