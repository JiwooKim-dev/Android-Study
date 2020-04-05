package com.example.lab9b_camera.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab9b_camera.posts.recyclerview.PostAdapter;
import com.example.lab9b_camera.posts.PostItem;
import com.example.lab9b_camera.R;

import java.util.ArrayList;

public class TimelineFragment extends Fragment {

    public TimelineFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // BaseView인 timeline activity랑 연결
        View baseView =  inflater.inflate(R.layout.fragment_timeline, container, false);
        RecyclerView rvList = baseView.findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setAdapter(new PostAdapter(getActivity(), getSamplePosts()));

        baseView.findViewById(R.id.fab_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null){   // intent에 걸린 액티비티 확인
                    startActivityForResult(cameraIntent, 1000);
                }
            }
        });

        return baseView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 1000){
            Bundle extras = data.getExtras();
            Log.d("onActivityResult", "카메라 성공");
        }
    }

    private ArrayList getSamplePosts() {

        ArrayList<PostItem> listItem = new ArrayList<>();

        String urls[] = {"https://i.pinimg.com/564x/2e/e7/e2/2ee7e28761891c8a2ecdd89937b6c6fe.jpg",
                "https://i.pinimg.com/originals/b6/5e/db/b65edb7ea15664be3e5796fa1932d70b.jpg",
                "https://a.wattpad.com/cover/168071750-352-k485887.jpg",
                "https://www.morrisonhotelgallery.com/images/medium/Queen-Bo-Rap_copyrightMRock.jpg",
                "https://lh3.googleusercontent.com/proxy/zCEz7EOzR2iWZlQvhaBFu5X7wopuzVz1tLPvFkdaMpFvxVqua4YtWdjLMuSAlP_n0LTl-lGGFt4Wye0wtDpa1Sfn9bTYCLpd0i42n9s93TjhdiGcFoqLwcUMDjqZjye9OVZS-XIMQ7xvuEr9k2QL_41ZtguCIwag",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQKBHPsvNIh1zZl_VTAErCk_nb_izEYcGxB0_NbieQAt9GYTTIT&usqp=CAU",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSDlOW9E77grFVAkxaFVwmp_AmT2mv2-_dYm37tObBAbvPsOxbE&usqp=CAU",
                "https://upload.wikimedia.org/wikipedia/commons/9/9d/Bass_player_queen.jpg",
                "https://cdn2.tstatic.net/makassar/foto/bank/images/band-queen.jpg",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTF8JueHwIToqMgBDZppd5xktAAMsN19AYrMcESKTc_dQXHoPyv&usqp=CAU"};

        for (int i=0 ; i<10 ; i++){

            PostItem postItem = new PostItem(true, i*10,"user"+(i+1), urls[i],"This is item "+(i+1));
            listItem.add(i, postItem);
        }

        return listItem;
    }   /* 샘플 포스트 데이터 생성 */
}