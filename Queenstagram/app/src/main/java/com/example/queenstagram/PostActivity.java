package com.example.queenstagram;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.queenstagram.api.Api;
import com.example.queenstagram.uuid.UserUUID;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Date;

public class PostActivity extends AppCompatActivity {

    ImageView ivPost;
    EditText etText;

    Bundle extras;
    Uri photoUri;
    Bitmap originalImgBitmap;

    Api.Post newPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        ivPost = findViewById(R.id.iv_post);
        try {
            showImage();
        } catch (IOException e) {
            Toast.makeText(this, "이미지 로딩 오류", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        etText = findViewById(R.id.et_text);
        findViewById(R.id.btn_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload(etText.getText().toString());
            }
        });
    }

    private void showImage() throws IOException {

        extras = getIntent().getBundleExtra("extras");
        photoUri = Uri.parse(extras.getString("photoUriString"));
        originalImgBitmap = getBitmapFromUri(photoUri);
        ivPost.setImageBitmap(originalImgBitmap);
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {

        // 이미지 추출
        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, opts);

        int width = opts.outWidth;
        int height = opts.outHeight;

        float sampleRatio = getSampleRatio(width, height);

        opts.inJustDecodeBounds = false;
        opts.inSampleSize = (int) sampleRatio;

        Bitmap resizedBitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, opts);

        // 이미지 회전

        ExifInterface exif = null;

        try {
            exif = new ExifInterface(fileDescriptor);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int exifOrientation;
        int exifDegree;

        if (exif != null) {
            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            exifDegree = exifOrientationToDegrees(exifOrientation);
        } else {
            exifDegree = 0;
        }

        parcelFileDescriptor.close();

        return rotate(resizedBitmap, exifDegree);
    }   /* Uri 주소를 Bmp 파일로 변환하여 리턴 */

    private float getSampleRatio(int width, int height) {

        final int targetWidth = 1280;
        final int targetHeight = 1280;

        float ratio;

        if (width > height) {
            // Landscape
            if (width > targetWidth) {
                ratio = (float) width / (float) targetWidth;
            } else ratio = 1f;
        } else {
            // Portrait
            if (height > targetHeight) {
                ratio = (float) height / (float) targetHeight;
            } else ratio = 1f;
        }

        return Math.round(ratio);
    }   /* 축소 비율 리턴 */

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }   /* 상수를 받아 각도로 변환 */

    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }   /* 비트맵을 각도대로 회전시켜 결과를 반환 */

    private void upload(String textString){

        newPost = new Api.Post("-1", UserUUID.getUserUUID(this), textString, new Date());   // timestamp?
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").add(newPost)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String docID = documentReference.getId();   // 1. 문서 생성 후 ID 발급
                uploadImageToStorage(docID);                // 2. Storage에 이미지 업로드 후 URI 발급
                                                            // 2-1. Cloud FB의 Post에 URI 등록
            }
        });
    }   /* Firebase 업로드 */

    private void uploadImageToStorage(final String docID) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        final StorageReference imageRef = storageRef.child(UserUUID.getUserUUID(this) + "/" + docID);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        originalImgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        final UploadTask uploadTask = imageRef.putBytes(data);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Log.d("Storage", "이미지 업로드 성공");
                    Uri downloadUri = task.getResult();
                    updatePostUriInCloud(docID, downloadUri);
                } else {
                    Log.d("Storage", "이미지 업로드 실패");
                }
            }
        });
    }   /* 촬영된 이미지를 Firebase Storage에 업로드 */

    private void updatePostUriInCloud(String docID, Uri downloadUri) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").document(docID)
                .update("imageUrl", downloadUri.toString())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Cloud FB", "포스트 URI 업데이트 성공");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Cloud FB", "포스트 URI 업데이트 실패");
                    }
                });
    }   /* 저장된 이미지의 URL을 Post에 업데이트 */

    /** onActivityResult() 구현 **/
}