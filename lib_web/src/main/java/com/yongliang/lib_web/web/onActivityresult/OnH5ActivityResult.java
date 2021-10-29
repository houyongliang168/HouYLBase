package com.yongliang.lib_web.web.onActivityresult;

import android.content.Intent;

/**
 * created by houyl
 * on  6:22 PM
 */
public interface OnH5ActivityResult {
    void onGetResult(int requestCode, int resultCode, Intent intent);

//    String getTag();
}
