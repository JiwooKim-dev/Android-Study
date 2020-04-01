package com.example.lab5g_sharedpreference;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    // default = -1
    // user = 1
    public static final String SHAREDPREF_FIRST_USER_KEY = "NEW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvHello = findViewById(R.id.tv_hello);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        int firstUser = sharedPref.getInt(SHAREDPREF_FIRST_USER_KEY, -1);

        if (firstUser == 1){
            // 기존 유저
            tvHello.setText(R.string.hello_user);
        }
        else if (firstUser == -1){
            // 신규 유저
            tvHello.setText(R.string.hello_new_user);
            saveNewUser();
        }

        // int defaultValue = getResources().getInteger(R.string.saved_high_score_default);
        // long highScore = sharedPref.getInt(getString(R.string.saved_high_score), defaultValue);
        }

    private void saveNewUser() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(SHAREDPREF_FIRST_USER_KEY, 1);
        editor.commit();
    }
}

