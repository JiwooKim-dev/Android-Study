package com.example.queenstagram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.queenstagram.api.Api;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PostActivity extends AppCompatActivity {

    ImageView ivPost;
    EditText etText, etUploader;
    Uri photoUri;
    Bundle extras;

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

        etUploader = findViewById(R.id.et_uploader);
        etText = findViewById(R.id.et_text);
        findViewById(R.id.btn_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFB(etUploader.getText().toString(), etText.getText().toString(), photoUri.toString());
                Log.d("post", "포스팅 완료");
            }
        });
    }

    private void showImage() throws IOException {

        extras = getIntent().getBundleExtra("extras");
        photoUri = Uri.parse(extras.getString("photoUriString"));
        Bitmap originalImgBitmap = getBitmapFromUri(photoUri);
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

    private void uploadFB(String uploaderString, String textString, String uriString){

        Api.Post newPost = new Api.Post(uploaderString, textString, uriString, new Date());
        newPost.setImageUrl("https://i.pinimg.com/originals/0b/56/af/0b56af0c2ff8a777e75bc651e0c969cb.jpg");    // 임시 이미지 링크
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").document(makeDocName()).set(newPost);
    }

    private String makeDocName() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_hhmm");
        Date date = new Date();
        String strDate = simpleDateFormat.format(date);
        return "post_"+strDate;
    }

}