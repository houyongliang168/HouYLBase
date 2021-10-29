package com.yongliang.lib_web.web.js;


import com.yongliang.lib_web.web.WebCommonFragment;

/**
 * created by houyl
 * on  5:35 PM
 */
public interface H5Plugin {
//    void onInitialize(H5CoreNode var1);
//
//    void onPrepare(H5EventFilter var1);


//
//    boolean interceptEvent(H5Event var1, WebCommonFragment fragment);

    boolean handleEvent(H5Event h5Event, WebCommonFragment fragment);
    boolean handleAsynEvent(H5Event h5Event, WebCommonFragment fragment);

    void onRelease();
    void onReusme();
    void onEnd();

    String getCallBackName();
    //    void onPrepare(H5EventFilter var1);

    /**
     * created by houyl
     * on  4:50 PM
     */
    interface IJsInterface {

    }
}
