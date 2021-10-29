package com.yongliang.lib_web.web.function;

import android.content.Intent;

import com.yongliang.lib_web.web.WebCommonFragment;
import com.yongliang.lib_web.web.js.H5BasePlugin;
import com.yongliang.lib_web.web.js.H5Event;
import com.yongliang.lib_web.web.onActivityresult.H5ActivityResultManager;
import com.yongliang.lib_web.web.onActivityresult.OnH5ActivityResult;


/**
 * created by houyl
 * on  6:51 PM
 */
public class H5ScanByNative extends H5BasePlugin implements OnH5ActivityResult {
   public H5ScanByNative(){

    }
    @Override
    public boolean handleEvent(H5Event h5Event, WebCommonFragment fragment) {
//        处理后
//        调用
        if(fragment!=null){
//            fragment.getH5CallBackCommon (callbackNaME,);
        }
        H5ActivityResultManager.getInstance ().put (this);
        return false;
    }

    @Override
    public void onRelease() {

    }

    @Override
    public String getCallBackName() {
        return null;
    }


    @Override
    public void onGetResult(int requestCode, int resultCode, Intent intent) {
//       ...
        H5ActivityResultManager.getInstance ().remove (this);
    }
}
