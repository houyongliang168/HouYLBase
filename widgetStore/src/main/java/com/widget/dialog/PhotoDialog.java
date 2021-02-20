package com.widget.dialog;


import android.app.Activity;
import android.view.Gravity;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.widget.dialog.fragment_dialog.TDialog;
import com.widget.dialog.fragment_dialog.base.BindViewHolder;
import com.widget.dialog.fragment_dialog.listener.OnViewClickListener;
import com.yongliang.widgetstore.R;

/**
 * Created by 41113 on 2018/9/30.
 */

public class PhotoDialog extends TDialog {

    private final int CAMERA = 1000;//调用相机
    private final int IMAGES = 2000;//打开相册

    public PhotoDialog() {
        super();
    }

    public void showDialog(FragmentManager fragmentManager, final Activity context, final ChooseListener chooseListener) {
        this.chooseListener = chooseListener;
        new Builder(fragmentManager)
                .setLayoutRes(R.layout.dialog_photo_change)
                .setScreenWidthAspect(context, 1.0f)
                .setGravity(Gravity.BOTTOM)
                .setDialogAnimationRes(R.style.CodeAnim)
                .addOnClickListener(R.id.tv_camera, R.id.tv_change_photo)
                .setOnViewClickListener(new OnViewClickListener() {
                    @Override
                    public void onViewClick(BindViewHolder viewHolder, View view, final TDialog tDialog) {
                        int id = view.getId();
                        if (id == R.id.tv_camera) {//                                DangerousPermissionHelper.requestPermission (context, new OnDangerousPermissionListener() {
//                                    @Override
//                                    public void onGranted(int requestCode) {
                            if (chooseListener != null) {
                                chooseListener.chooseCamera();
                            }
                            tDialog.dismiss();
//                                    }
//
//                                    @Override
//                                    public void onDenied(List<String> permissionNames) {
//                                        tDialog.dismiss ();
//                                        MyToast.showShort ("拍照录像被禁止，请在设置中开启拍照权限");
//                                    }
//                                }, CAMERA, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE);
                        } else if (id == R.id.tv_change_photo) {//
//                                DangerousPermissionHelper.requestPermission (context, new OnDangerousPermissionListener () {
//                                    @Override
//                                    public void onGranted(int requestCode) {
                            if (chooseListener != null) {
                                chooseListener.chooseImages();
                            }
                            tDialog.dismiss();
//                                    }
//
//                                    @Override
//                                    public void onDenied(List<String> permissionNames) {
//                                        MyToast.showShort ("相册被禁止，请在设置中开启相册权限");
//                                        tDialog.dismiss ();
//                                    }
//                                }, IMAGES, Permission.Group.STORAGE);
                        }
                    }
                })
                .create()
                .show();
    }

    private ChooseListener chooseListener;

    public interface ChooseListener {

        void chooseCamera();

        void chooseImages();
    }
}
//示例代码
// photoDialog=new PhotoDialog ();
//         photoDialog.showDialog(getSupportFragmentManager(), EventDetailsActivity.this, new PhotoDialog.ChooseListener() {
//@Override
//public void chooseCamera() {
//        String time = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
//        File dir = new File(tmpDir);
//        if(!dir.exists()){
//        dir.mkdirs();
//        }
//
//        picPath = tmpDir + time + ".jpg";
//        File file = new File(picPath);
//        Uri uri = Uri.fromFile(file);
//        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent1.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        startActivityForResult(intent1, CAMERA);
//
//        }
//
//@Override
//public void chooseImages() {
//        Intent intent2 = new Intent(Intent.ACTION_PICK, null);
//        intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//        startActivityForResult(intent2, IMAGES);
//        }
//        });

