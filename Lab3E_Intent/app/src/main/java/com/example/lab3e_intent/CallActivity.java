package com.example.lab3e_intent;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        String msg = getIntent().getStringExtra("intent-message");
        Toast.makeText(CallActivity.this, msg, Toast.LENGTH_SHORT).show();

    }

}
