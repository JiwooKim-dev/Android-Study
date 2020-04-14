package com.example.queenstagram.uuid;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import java.util.UUID;

public class UserUUID {

    private static final String PREF_UUID = "PREF_UUID";
    private static final String PREF_UUID_KEY = "PREF_UUID_KEY";

    @Nullable
    public static String getUserUUID(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(PREF_UUID, Context.MODE_PRIVATE);
        String user_id = sharedPreferences.getString(PREF_UUID_KEY, null);

        if (user_id == null) {
            String uuidStr = UUID.randomUUID().toString();
            user_id = uuidStr.substring(0, 8);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(PREF_UUID_KEY, user_id);
            editor.commit();
        }

        return user_id;
    }

}