package com.utopia.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.utopia.permission.annotation.NeedsPermission;
import com.utopia.permission.annotation.OnNeverAskAgain;
import com.utopia.permission.annotation.OnPermissionDenied;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @NeedsPermission(value = Manifest.permission.WRITE_EXTERNAL_STORAGE,requestCode = 0)
    public void getAllSdcardFiles(View view) {
        File rootFile = Environment.getStorageDirectory();
        Log.e("test","rootFile:"+rootFile.getAbsolutePath());

        File[] listFiles = rootFile.listFiles();
        Log.e("test","listFiles:"+listFiles.length);
    }

    @OnPermissionDenied
    public void onPermissionDenied(){
        Log.e("test","权限被拒绝了");
    }

    @OnPermissionDenied
    public void onPermissionDenied(int requestCode){
        Log.e("test","权限被拒绝了:"+requestCode);
    }

    @OnNeverAskAgain
    public void onNeverAskAgain(int requestCode){
        Log.e("test","不要在询问了:"+requestCode);
    }

    @OnNeverAskAgain
    public void onNeverAskAgain(){
        Log.e("test","不要在询问了");
    }
    
}