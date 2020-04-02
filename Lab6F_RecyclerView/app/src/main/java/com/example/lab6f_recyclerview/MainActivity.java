package com.example.lab6f_recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lab6f_recyclerview.model.PostItem;
import com.example.lab6f_recyclerview.recyclerview.PostAdapter;

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

        RecyclerView rvList = findViewById(R.id.rv_list);
        PostAdapter adapter = new PostAdapter(this, listItem);
        rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvList.setAdapter(adapter);
    }
}
