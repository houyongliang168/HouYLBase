package com.yongliang.downloadmodule.download;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.download.core.DownloadService;
import com.download.core.callback.DownloadManager;
import com.download.core.domain.DownloadInfo;
import com.download.encapsulation.DownLoadObject;
import com.utils.file.AlbumUtil;
import com.utils.log.MyLog;
import com.utils.log.MyToast;
import com.utils.optimize.PhoneBuildStatus;
import com.widget.dialog.fragment_dialog.TDialog;
import com.widget.dialog.fragment_dialog.base.BindViewHolder;
import com.widget.dialog.fragment_dialog.listener.OnViewClickListener;
import com.yongliang.downloadmodule.R;

import org.json.JSONException;

import java.io.File;

import static com.download.core.domain.DownloadInfo.STATUS_COMPLETED;


/**
 * 加密的sdk
 */
public class H5DownloadFunction  {

    private String TAG = H5DownloadFunction.class.getSimpleName ();
    public boolean isBegin = false;//处理 多次调用 ，保证下载只能单个下载
    private String mFilePath;
    private static String VideoType;
    private static  int scene=0;

    //    public H5DownloadFunction() {
//    }
    private H5DownloadFunction() {
    }

    public static H5DownloadFunction getInstance(String Type, int Scene) {
        VideoType=Type;
        scene=Scene;
        return Builder.INSTANCE;
    }

    private static class Builder {
        private static final H5DownloadFunction INSTANCE = new H5DownloadFunction ();
    }

    /**
     * 开始处理
     *
     *
     * @param activity 需要展示的activity
     */
    public void processingFunction(String seat, String ispause, String url, String type, final FragmentActivity activity) throws JSONException {
        if (isBegin) {
            return;
        }
        if (activity == null || activity.isFinishing ()) {
            return;
        }


//        //位置 默认为0  下载到目录里面  1 为下载到相册里面
//        String seat = jsonObject.optString ("seat");
//        //是否支持暂停 默认0     0不暂停，1为可暂停
//        String ispause = jsonObject.optString ("ispause");
//        //下载的网络地址
//        String url = jsonObject.optString ("url");
//        //文件类型 默认 jpg   ,如果传 视频单独处理
//        String type = jsonObject.optString ("type");


        if (TextUtils.isEmpty (seat)) {
            seat = 0 + "";
        }
        if (TextUtils.isEmpty (ispause)) {
            ispause = 0 + "";
        }
        final DownloadCashInfo downloadInfo = new DownloadCashInfo ();
        downloadInfo.setCallBackName ("");
        downloadInfo.setUrl (url);
        downloadInfo.setSeat (seat);
        downloadInfo.setIspause (ispause);
        downloadInfo.setType (TextUtils.isEmpty (type) ? "" : type);

        // 权限处理逻辑
//        DangerousPermissionHelper.requestPermission (activity, new OnDangerousPermissionListener () {
//            @Override
//            public void onGranted(int requestCode) {
//                processingDownload (downloadInfo, activity);
//            }
//
//            @Override
//            public void onDenied(List<String> permissionNames) {
//
//            }
//        }, 20000, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        processingDownload (downloadInfo, activity);
    }


    /**
     * 权限处理
     *
     * @param cashInfo 传递 的基本数据
     * @param activity 需要展示的activity
     */
    public void processingDownload(final DownloadCashInfo cashInfo, final FragmentActivity activity) {
        //前面 已经做了非空判断
        DownLoadObject object = DownLoadObject.getInstance ();
        object.setCanDownloadListener (new DownLoadObject.CanDownloadListener () {
            @Override
            public void onSuccess(long size) {
                //默认下载存储位置 默认为0    0下载到目录里面
                if ("0".equals (cashInfo.getSeat ())) {
                    mFilePath = getFileDirectory (activity);//初始化 FILe 路径
                } else {
                    //获取相册的路径
                    mFilePath =getDCIMPath();
                }
                MyLog.wtf (TAG, "mFilePath :" + mFilePath);
                String url = cashInfo.getUrl ();
                String[] urlSplit = url.split ("/");
//                String fileName = System.currentTimeMillis ()+urlSplit[urlSplit.length - 1];
                String fileName = urlSplit[urlSplit.length - 1];
                File outputFile = new File(mFilePath, fileName);
                //开始下载
                DownloadInfo info = new DownloadInfo.Builder ()
                        .setUrl (cashInfo.getUrl ())
                        .setPath (outputFile.getAbsolutePath ())
                        .build ();
                //  info .setId (cashInfo.getUrl ()+System.currentTimeMillis ()+"");

                //如果之前有数据，则删除该数
                DownloadManager downloadManager = DownloadService.getDownloadManager (activity);
                DownloadInfo oldDownloadInfo = downloadManager.getDownloadById (cashInfo.getUrl ());
                if (oldDownloadInfo != null) {
                    if (STATUS_COMPLETED == oldDownloadInfo.getStatus ()) {
                        File file = new File(oldDownloadInfo.getPath ());
                        if (file.exists () && file.isFile ()) {

                            MyToast.showShort ("已下载到手机相册中");
                            return;
                        }
                    }
                    downloadManager.remove (oldDownloadInfo);
                }
                info.setImgUrl ("");
                info.setTitle ("");
                info.setDescription ("");
                info.setIsBanner (0);
                info.setType (DownloadInfo.TYPE_NORMOL);

                downloadManager.download (info);
                if (activity != null && !activity.isFinishing ()) {
                    showProDialog (size, info, activity, cashInfo, downloadManager);
                }

            }

            @Override
            public void onFailed() {

            }
        });
        object.isAmpleSpace (cashInfo.getUrl ());

    }


    public void showProDialog(long size, final DownloadInfo info, final FragmentActivity activity, final DownloadCashInfo cashInfo, final DownloadManager downloadManager) {
        isBegin = true;
        String ispause = cashInfo.getIspause();
        int type = 0;
        if ("1".equals(ispause)) {
            type = 1;
        }

        final ProgressDialog tDialog = new ProgressDialog.Builder(activity.getSupportFragmentManager())
                .setLayoutRes(R.layout.dialog_download_file)//语音
                .setTag("AProgressDialog")
                .setGravity(Gravity.CENTER)
                .addOnClickListener(R.id.img_close)
                .setOnViewClickListener(new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, TDialog tDialog) {
                        if (view.getId() == R.id.img_close) {
                            if (tDialog != null) {
                                if (downloadManager != null && info != null) {
                                    downloadManager.pause(info);
                                }
                                isBegin = false;
                                tDialog.dismiss();
                            }
                        }
                    }
                })
                .setCancelableOutside(false)
                .create()
                .show(size, type, info);
        MyLog.wtf(TAG, "ProgressDialog :" + tDialog.toString());

        tDialog.setInfoLisener(new ProgressDialog.InfoLisener() {
            @Override
            public void setInfoLisener(String infos) {
                if (tDialog != null) {
                    if ("1".equals(cashInfo.getSeat())) {
                        if ("mp4".equals(cashInfo.getType())) {
                            AlbumUtil.insertVideo(info.getPath(), activity);
                        } else {
                            AlbumUtil.insertAlbumMedia(info.getPath(), activity);
                        }
                        AlbumUtil.updateAlbumMedia(new File(info.getPath()), activity);
                        MyLog.wtf(TAG, "AlbumUtil updateAlbumMedia");
                    }
                    isBegin = false;
                    tDialog.dismiss();
                    MyToast.showShort ("已下载到手机相册中");


                }
            }

            @Override
            public void setFaileLisener(String info) {
                if (tDialog != null) {

                    tDialog.dismiss();
                }


            }

        });
    }
    /* 获取文件下载 参数相关信息*/
    private String getFileDirectory(Context context) {
        /* 获取文件夹*/
        String filePath;
        if (!Environment.getExternalStorageState ().equals (Environment.MEDIA_MOUNTED)) {//无sd 卡
            //无SD 卡  存储在应用下面
            filePath = context.getFilesDir () + File.separator + "houyongliang" + File.separatorChar;
        } else {//有sd 卡
            filePath = Environment.getExternalStorageDirectory ().getAbsolutePath () + File.separatorChar + "houyongliang" + File.separatorChar;
        }
        File folder = new File(filePath);
        if (!folder.exists ()) {
            folder.mkdirs ();
            MyLog.wtf (TAG, "File 不存在");
        } else {
            MyLog.wtf (TAG, "File 存在");
        }
        return filePath;
    }

    //获取相册的路径
    public String getDCIMPath() {
        if(PhoneBuildStatus.isVivo ()&& PhoneBuildStatus.isLowO ()){
            String mFilePath = Environment.getExternalStorageDirectory ().getAbsolutePath () + File.separator + "相机" + File.separator;
            return mFilePath;
        }
        String mFilePath = Environment.getExternalStoragePublicDirectory (Environment.DIRECTORY_DCIM).getAbsolutePath () + File.separator + "Camera" + File.separator;


        return mFilePath;
    }

}
