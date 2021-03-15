package com.utopia.permission;

import android.content.Context;
import com.utopia.permission.annotation.NeedsPermission;
import com.utopia.permission.annotation.OnNeverAskAgain;
import com.utopia.permission.annotation.OnPermissionDenied;
import com.utopia.permission.fragment.PermissionFragment;
import com.utopia.permission.utils.FragmentUtils;
import com.utopia.permission.utils.PermissionCallback;
import com.utopia.permission.utils.ReflectionUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import androidx.lifecycle.LifecycleOwner;

@Aspect
public class Permissions {
    //execution(<@注解？> <修饰符?> <返回值类型> <类型声明?>.<方法名>(参数列表) <异常列表>？)
    @Around("execution(@com.utopia.permission.annotation.NeedsPermission * *(..)) && @annotation(needsPermission)")
    public void onProcessMethod(ProceedingJoinPoint joinPoint, NeedsPermission needsPermission) throws Throwable {
        LifecycleOwner lifecycleOwner = (LifecycleOwner) joinPoint.getThis();
        if (lifecycleOwner != null) {
            String[] permissions = needsPermission.value();
            int requestCode = needsPermission.requestCode();

            PermissionFragment permissionFragment = FragmentUtils.getPermissionFragment(lifecycleOwner,"PermissionFragment");
            // 使用PermissionFragment代理申请权限
            permissionFragment.request(permissions, requestCode, new PermissionCallback() {
                @Override
                public void granted(int requestCode) {//授权
                    FragmentUtils.hiddenPermissionFragment(lifecycleOwner,permissionFragment);
                    try {
                        joinPoint.proceed();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }

                @Override
                public void denied(Context context,int requestCode)  {//拒绝
                    FragmentUtils.hiddenPermissionFragment(lifecycleOwner,permissionFragment);
                    ReflectionUtils.invoke(context, OnPermissionDenied.class, requestCode);
                }

                @Override
                public void neverAsk(Context context,int requestCode) {//不再提醒
                    FragmentUtils.hiddenPermissionFragment(lifecycleOwner,permissionFragment);
                    ReflectionUtils.invoke(context, OnNeverAskAgain.class, requestCode);
                }

                @Override
                public void cancel(Context context,int requestCode) {//取消
                    FragmentUtils.hiddenPermissionFragment(lifecycleOwner,permissionFragment);
                }
            });
        }
    }


}
