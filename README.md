## AndPermission

此库是基于AspectJ AOP框架开发的Andorid动态权限申请框架，使用前请先了解Android M动态权限相关说明.

注意: To use this library your minSdkVersion must be >= 14 （Android 4.0）.

## Annotations
|Annotation|Description|
|---|---|
|`@NeedsPermission`|方法注解，标识此方法运行需要一个或多个运行时权限|
|`@OnPermissionDenied`|方法注解，标识用户未授予权限时回调时执行此方法|
|`@OnNeverAskAgain`|方法注解，标识用户动态授权时勾选“不再询问”选项时则回调该方法|


## 依赖配置
It only supports androidx, add dependencies in your gradle:

```groovy
//需要在项目根目录下build.gradle中添加以下依赖
allprojects {
    dependencies {
        ...
        classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.10'
    }
}

//需要在使用到本库的模块添加以下依赖,module或app下,build.gradle中添加
dependencies {
    ...
    implementation 'com.utopia:permission:1.0.0'
}


//需要在application，build.gradle中添加插件
plugins {   
    ...    
    id 'android-aspectjx'
}
```

## 使用示例
````java
public class MainActivity extends AppCompatActivity {    
    private final static int WRITE_EXTERNAL_STORAGE_CODE = 0X0001;        
    
    @Override    
    protected void onCreate(Bundle savedInstanceState) {       
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_main);   
    }    
    
    @NeedsPermission(value = Manifest.permission.WRITE_EXTERNAL_STORAGE,requestCode =WRITE_EXTERNAL_STORAGE_CODE)    
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
````

## 开源协议
```text
Copyright 2021 freeutopia.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
