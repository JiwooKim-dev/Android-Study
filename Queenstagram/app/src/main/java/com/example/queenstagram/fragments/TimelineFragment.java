package com.example.queenstagram.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.queenstagram.PostActivity;
import com.example.queenstagram.api.Api;
import com.example.queenstagram.posts.PostItem;
import com.example.queenstagram.posts.recyclerview.PostAdapter;
import com.example.queenstagram.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TimelineFragment extends Fragment {

    static final int REQUEST_PERMISSIONS = 1;
    static final int REQUEST_CAMERA_ACTIVITY = 2;

    ArrayList<Api.Post> arrayList;
    PostAdapter postAdapter;

    public TimelineFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        arrayList = new ArrayList<>();
        postAdapter = new PostAdapter(getActivity(), arrayList);
        fetchPostsFromFB();

        View baseView =  inflater.inflate(R.layout.fragment_timeline, container, false);
        RecyclerView rvList = baseView.findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setAdapter(postAdapter);

        /* 카메라 실행 전 권한 체크 */
        baseView.findViewById(R.id.fab_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        + ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);

                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    startCameraActivity();
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA}, REQUEST_PERMISSIONS);
                }
            }
        });

        return baseView;
    }

    private void fetchPostsFromFB(){

        final String TAG = "CloudFirebase";

        // Access a Cloud Firestore instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                arrayList.add(document.toObject(Api.Post.class));
                            }
                            postAdapter.notifyDataSetChanged();
                        } else {
                            Log.w(TAG, "Error fetching Cloud FB", task.getException());
                        }
                    }
                });
    }


    /* 카메라 액티비티 */
    private void startCameraActivity() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {   // intent에 걸린 액티비티 확인
            startActivityForResult(cameraIntent, 2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0){
            startCameraActivity();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Main (TimelineFrag) -> Camera -> Main -> Post
        if (resultCode == Activity.RESULT_OK && requestCode == 2){

            Intent startIntent = new Intent(getActivity(), PostActivity.class);
            Bitmap imgBitmap = (Bitmap) data.getExtras().get("data");
            startIntent.putExtra("photo", imgBitmap); // 액티비티 호출하면서 촬영된 이미지 전송
            startActivity(startIntent);
        }
    }

}
