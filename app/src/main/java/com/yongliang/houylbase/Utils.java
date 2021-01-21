package com.yongliang.houylbase;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * created by houyl
 * on  4:13 PM
 */
public class Utils {
    // 设置透明状态栏
    public static void  setOwnTranslucent(@NonNull AppCompatActivity activity){
        Window window =activity .getWindow ();
        View decorView = window.getDecorView();
        //>=21
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);//使內容進入導航欄
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//// 透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//// 透明导航栏

            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.setNavigationBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(Color.TRANSPARENT);
            //  21> sdk_int>=19
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);// 透明状态栏
            // setColor(window, 0x80000000, true);
        }
        ActionBar actionBar = activity.getSupportActionBar ();
        if(actionBar!=null){
            actionBar.hide();
        }


    }
}
