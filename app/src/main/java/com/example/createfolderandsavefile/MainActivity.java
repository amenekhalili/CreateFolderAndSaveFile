package com.example.createfolderandsavefile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "tag";
    Button mButtonPic;
    Uri photoUri;
    private static int REQUEST_IMAGE_CAPTURE = 5;


    public static final int REQ_CAMERA = 1;
    private static String [] PERMISSIONS_CAMERA = {
            Manifest.permission.CAMERA

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonPic = findViewById(R.id.btn_pic);
        checkStoragePermissions();
        mButtonPic.setOnClickListener(v -> takePhoto());

        //Create Folder in external Storage in android 11 //
    }


    private void takePhoto() {


        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            photoFile = createImageFile();

            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this
                        , "com.example.cam_ex.FileProvider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }


    }


    private File createImageFile() {

        return new File(Objects.requireNonNull(createFolder()).getPath(), "abc.jpeg");
    }

    public File createFolder() {
        //File myFolder = new File(Environment.getExternalStorageDirectory(), "faraKhalili");
        File myFolder = new File(getExternalFilesDir(null), "faraKhalili4");
        if (!myFolder.exists()) {
            if (myFolder.mkdirs()) {
                return myFolder;
            } else {
                return null;
            }
        }
        return myFolder;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            String l = photoUri.toString();
            Log.d(TAG, "onActivityResult: " + l);
        }
    }


    private void checkStoragePermissions(){
        int permission = ActivityCompat.checkSelfPermission
                (this,Manifest.permission.CAMERA) ;

        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                                this, PERMISSIONS_CAMERA, REQ_CAMERA
                        );
        }

    }
}