package com.example.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import uk.co.senab.photoview.PhotoViewAttacher;

public class FullScreenImageActivity extends AppCompatActivity {

    private int i = 0;
    private ImageView FullScreen;
    PhotoViewAttacher pAttacher;
    private ProgressBar load_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        FullScreen = findViewById(R.id.fullScreenImageView);
        load_image = findViewById(R.id.loader_image);
        Intent callingActivity = getIntent();


        if(callingActivity != null){
            load_image.setVisibility(View.VISIBLE);
            String imageUri = callingActivity.getStringExtra("photo");
            if(imageUri != null && FullScreen!= null){
               Picasso.get()
                        .load(imageUri)

                        .into(FullScreen, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                FullScreenImageActivity.this.load_image.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError(Exception e) {
                                Toast.makeText(FullScreenImageActivity.this.getApplicationContext(),"Error Loading Image",Toast.LENGTH_SHORT).show();
                                FullScreenImageActivity.this.load_image.setVisibility(View.GONE);
                            }
                        });
            }
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pAttacher = new PhotoViewAttacher(FullScreen);
        pAttacher.update();
        //FullScreen.setOnTouchListener(this);
        //load_image.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("permission state","Permission is granted");
                return true;
            } else {

                Log.v("permission not grnted","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("permision auto granted","Permission is granted");
            return true;
        }
    }

    public void Download(View view) {

        i++;

        if (this.isStoragePermissionGranted() ){
            FullScreen.invalidate();
            BitmapDrawable drawable = (BitmapDrawable) FullScreen.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            Log.d("External Storage State", Environment.getExternalStorageState());

            String uniqueString = UUID.randomUUID().toString();

            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/"+uniqueString+".jpg");
            Log.d("Image Path", file.getAbsolutePath());
            try {
                Log.d("","before creating file");
                file.createNewFile();
                Log.d("","after creating file");
                FileOutputStream ostream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                ostream.flush();
                ostream.close();
            } catch (IOException e) {
                Log.e("IOException", e.getLocalizedMessage());
            }
        }
    }

}

