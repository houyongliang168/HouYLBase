package com.yongliang.lib_web.web.util;


import com.utils.log.MyLog;

/**
 * Created by REN SHI QIAN on 2018/11/1.
 */
public class H5TitleUtil {
    /**
     * html的title是否是一个链接
     *  解决android6.0的webview获取h5标题的bug
     * @return
     */
    public static boolean titleIsUrl(String url, String title) {
        MyLog.wtf("H5标题显示onReceivedTitle：", "title=" + title);
        MyLog.wtf("H5标题显示onReceivedTitle：", "url=" + url);
        String mUrl = "";
        if(url.startsWith("http://")){
            mUrl = url.replace("http://","");
        }else if(url.startsWith("https://")){
            mUrl = url.replace("https://","");
        }
        MyLog.wtf("H5标题显示onReceivedTitle：", "mUrl=" + mUrl);
        String mTitle = EncodeUtil.encodeUrlOnlyEncodeChinese(title);
        MyLog.wtf("H5标题显示onReceivedTitle：", "mTitle=" + mTitle);
        if(mTitle.startsWith("http://")){
            mTitle = mTitle.replace("http://","");
        }else if(url.startsWith("https://")){
            mTitle = mTitle.replace("https://","");
        }
        MyLog.wtf("H5标题显示onReceivedTitle：", "mTitle=" + mTitle);

        if(mUrl.equals(mTitle)){
            return true;
        }else{
            return false;
        }
    }
}
