package com.utopia.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.utopia.permission.annotation.NeedsPermission;
import com.utopia.permission.annotation.OnNeverAskAgain;
import com.utopia.permission.annotation.OnPermissionDenied;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private final static int WRITE_EXTERNAL_STORAGE_CODE = 0X0001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @NeedsPermission(value = Manifest.permission.WRITE_EXTERNAL_STORAGE,requestCode = WRITE_EXTERNAL_STORAGE_CODE)
    public void getAllSdcardFiles(View view) {
        Log.e("test","我读写了SD卡");
    }

    /**
     * 也可以是无参方法，建议使用此回调
     */
    @OnPermissionDenied
    public void onPermissionDenied(int requestCode){
        if (WRITE_EXTERNAL_STORAGE_CODE == requestCode) {
            Log.e("test", "Manifest.permission.WRITE_EXTERNAL_STORAGE 权限被拒绝了");
        }
    }

    @OnNeverAskAgain
    public void onNeverAskAgain(int requestCode){
        Log.e("test","不要在询问了:"+requestCode);
    }
}