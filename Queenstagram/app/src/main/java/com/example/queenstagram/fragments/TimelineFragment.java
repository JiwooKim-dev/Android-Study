package com.example.queenstagram.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.queenstagram.PostActivity;
import com.example.queenstagram.api.Api;
import com.example.queenstagram.posts.recyclerview.PostAdapter;
import com.example.queenstagram.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TimelineFragment extends Fragment {

    static final int REQUEST_PERMISSIONS = 1;
    static final int REQUEST_CAMERA_ACTIVITY = 2;

    FirebaseFirestore db;
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
        db = FirebaseFirestore.getInstance();

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

    File tempFile;
    Bundle extras;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSIONS && grantResults.length > 0){
            startCameraActivity();
        }
    }

    private void startCameraActivity() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            tempFile = null;
            try {
                tempFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(getContext(), "이미지 생성 오류", Toast.LENGTH_SHORT).show();
                ex.printStackTrace();
            }

            if (tempFile != null) {
                Uri photoUri = FileProvider.getUriForFile(getContext(),
                        "com.example.queenstagram.provider", tempFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                extras = new Bundle();
                extras.putString("photoUriString", photoUri.toString());
                cameraIntent.putExtra("extras", extras);
                startActivityForResult(cameraIntent, REQUEST_CAMERA_ACTIVITY);
            }
        }
    }

    private File createImageFile() throws IOException {

        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                makeImageFileName(),  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        return image;
    }

    private String makeImageFileName() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss");
        Date date = new Date();
        String strDate = simpleDateFormat.format(date);
        return strDate;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Main (TimelineFrag) -> Camera -> Main -> Post
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CAMERA_ACTIVITY){

            Intent startIntent = new Intent(getActivity(), PostActivity.class);
            startIntent.putExtra("extras", extras); // 액티비티 호출하면서 촬영된 이미지 전송
            startActivity(startIntent);
        }
    }

}
