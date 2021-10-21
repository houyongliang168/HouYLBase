package com.yongliang.downloadmodule.download;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.download.core.domain.DownloadInfo;
import com.download.encapsulation.CommonDownloadListener;
import com.view.progressview.CircleProgressView;
import com.widget.dialog.fragment_dialog.TDialog;
import com.widget.dialog.fragment_dialog.base.TController;
import com.widget.dialog.fragment_dialog.listener.OnBindViewListener;
import com.widget.dialog.fragment_dialog.listener.OnViewClickListener;
import com.yongliang.downloadmodule.R;


public class ProgressDialog extends TDialog {
    private int type = 0;
    private long size = 0;
    private DownloadInfo downloadInfo;
    private ImageView img_close;
    private CircleProgressView progress_download;
    private TextView tv_status, tv_size;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
    }

    public ProgressDialog() {
        super ();

    }

    @Override
    public ProgressDialog show() {
        Log.d (TAG, "show");
        FragmentTransaction ft = tController.getFragmentManager ().beginTransaction ();
        ft.add (this, tController.getTag ());
        ft.commitAllowingStateLoss ();
        return this;

    }

    public ProgressDialog show(long size, int type, final DownloadInfo downloadInfo) {
        this.type = type;
        this.size = size;
        this.downloadInfo = downloadInfo;

        FragmentTransaction ft = tController.getFragmentManager ().beginTransaction ();
        ft.add (this, tController.getTag ());
        ft.commitAllowingStateLoss ();
        return this;
    }


    @Override
    protected void bindView(View view) {
        super.bindView (view);
        img_close = view.findViewById (R.id.img_close);
        progress_download = view.findViewById (R.id.progress_download);
        tv_size = view.findViewById (R.id.tv_size);
        tv_status = view.findViewById (R.id.tv_status);

        if (1 == type && img_close != null) {
            img_close.setVisibility (View.VISIBLE);
        } else if (img_close != null) {
            img_close.setVisibility (View.GONE);
        }

        if (tv_size != null) {
            float countLen = size / 1048576;
            if (countLen < 1) {
                countLen = size / 1024;
                tv_size.setText ("文件大小 : " + countLen + "KB");
            } else {
                tv_size.setText ("文件大小 : " + countLen + "MB");
            }
        }
        if (downloadInfo != null) {
            downloadInfo.setDownloadListener (new CommonDownloadListener () {

                @Override
                public void onRefresh() {
                    refresh (downloadInfo);
                }
            });
        }
    }

    public static class Builder {

        TController.TParams params;

        public Builder(FragmentManager fragmentManager) {
            params = new TController.TParams ();
            params.mFragmentManager = fragmentManager;
        }

        /**
         * 传入弹窗xmL布局文件
         *
         * @param layoutRes
         * @return
         */
        public Builder setLayoutRes(@LayoutRes int layoutRes) {
            params.mLayoutRes = layoutRes;
            return this;
        }

        /**
         * 直接传入控件
         *
         * @param view
         * @return
         */
        public Builder setDialogView(View view) {
            params.mDialogView = view;
            return this;
        }

        /**
         * 设置弹窗宽度(单位:像素)
         *
         * @param widthPx
         * @return
         */
        public Builder setWidth(int widthPx) {
            params.mWidth = widthPx;
            return this;
        }

        /**
         * 设置弹窗高度(px)
         *
         * @param heightPx
         * @return
         */
        public Builder setHeight(int heightPx) {
            params.mHeight = heightPx;
            return this;
        }

        /**
         * 设置弹窗宽度是屏幕宽度的比例 0 -1
         */
        public Builder setScreenWidthAspect(Context context, float widthAspect) {
            params.mWidth = (int) (getScreenWidth (context) * widthAspect);
            return this;
        }

        /**
         * 设置弹窗高度是屏幕高度的比例 0 -1
         */
        public Builder setScreenHeightAspect(Context context, float heightAspect) {
            params.mHeight = (int) (getScreenHeight (context) * heightAspect);
            return this;
        }

        /**
         * 设置弹窗在屏幕中显示的位置
         *
         * @param gravity
         * @return
         */
        public Builder setGravity(int gravity) {
            params.mGravity = gravity;
            return this;
        }

        /**
         * 设置弹窗在弹窗区域外是否可以取消
         *
         * @param cancel
         * @return
         */
        public Builder setCancelableOutside(boolean cancel) {
            params.mIsCancelableOutside = cancel;
            return this;
        }

        /**
         * 弹窗dismiss时监听回调方法
         *
         * @param dismissListener
         * @return
         */
        public Builder setOnDismissListener(DialogInterface.OnDismissListener dismissListener) {
            params.mOnDismissListener = dismissListener;
            return this;
        }

        /**
         * 设置弹窗背景透明度(0-1f)
         *
         * @param dim
         * @return
         */
        public Builder setDimAmount(float dim) {
            params.mDimAmount = dim;
            return this;
        }

        public Builder setTag(String tag) {
            params.mTag = tag;
            return this;
        }

        /**
         * 通过回调拿到弹窗布局控件对象
         *
         * @param listener
         * @return
         */
        public Builder setOnBindViewListener(OnBindViewListener listener) {
            params.bindViewListener = listener;
            return this;
        }

        /**
         * 添加弹窗控件的点击事件
         *
         * @param ids 传入需要点击的控件id
         * @return
         */
        public Builder addOnClickListener(int... ids) {
            params.ids = ids;
            return this;
        }

        /**
         * 弹窗控件点击回调
         *
         * @param listener
         * @return
         */
        public Builder setOnViewClickListener(OnViewClickListener listener) {
            params.mOnViewClickListener = listener;
            return this;
        }

        /**
         * 设置弹窗动画
         *
         * @param animationRes
         * @return
         */
        public Builder setDialogAnimationRes(int animationRes) {
            params.mDialogAnimationRes = animationRes;
            return this;
        }

        /**
         * 真正创建TDialog对象实例
         *
         * @return
         */
        public ProgressDialog create() {
            ProgressDialog dialog = new ProgressDialog ();
            Log.d (TAG, "create");
            //将数据从Buidler的DjParams中传递到DjDialog中
            params.apply (dialog.tController);
            dialog.setCancelable (false);
            return dialog;
        }

    }


    public void refresh(DownloadInfo data) {
        //刷新方法
        if (data == null || progress_download == null || tv_size == null || tv_status == null) {
//            MyLog.wtf (TAG, "refresh(DownloadInfo data) NULL");
            return;
        }
        switch (data.getStatus ()) {
            case DownloadInfo.STATUS_NONE:
                break;
            case DownloadInfo.STATUS_WAIT:
                tv_status.setText ("等待下载中...");
                break;
            case DownloadInfo.STATUS_PAUSED:
                tv_status.setText ("下载暂停...");
                break;
            case DownloadInfo.STATUS_ERROR:
                tv_status.setText ("下载失败...");
                if (infoLisener != null) {
                    infoLisener.setFaileLisener ("下载失败..");
                }
                break;
            case DownloadInfo.STATUS_DOWNLOADING:
                tv_status.setText ("正在下载...");
                long countLenF = data.getSize ();
                long readLenF = data.getProgress ();
                progress_download.setmCurrent ((int) (readLenF * 100 / countLenF));
                break;
            case DownloadInfo.STATUS_PREPARE_DOWNLOAD:
                tv_status.setText ("连接中...");

                break;
            case DownloadInfo.STATUS_COMPLETED:
                tv_status.setText ("下载完成");
                if (infoLisener != null) {
                    infoLisener.setInfoLisener ("下载完成");
                }

                break;
            case DownloadInfo.STATUS_REMOVED:

                break;

        }
    }

    //    对外传递信息
    public InfoLisener infoLisener;

    public interface InfoLisener {
        void setInfoLisener(String info);
        void setFaileLisener(String info);
    }

    public void setInfoLisener(InfoLisener infoLisener) {
        this.infoLisener = infoLisener;
    }


}
