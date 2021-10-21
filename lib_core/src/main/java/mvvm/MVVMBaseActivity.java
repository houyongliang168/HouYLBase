package mvvm;

import android.app.Dialog;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.utils.log.MyToast;
import com.widget.dialog.MyDialog;
import com.yongliang.baselib.R;

import mvp.ActivityStack;


/**
 * Created by renshiqian on 2018/10/30.
 */
public class MVVMBaseActivity extends RxAppCompatActivity {

    private Dialog dialog;

    private MyDialog myDialog;
    private MyDialog meDialog;
    private DialogListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityStack.getInstance().addActivity(this);
        createView();
        myDialog = new MyDialog(this);

    }




    /**
     * 不依赖系统字体大小变化
     *
     * @return
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }


    public void dismissProgressDialog() {
        if (dialog != null && !isFinishing()) {
            dialog.dismiss();
        }
    }

    private void createView() {
        View layout = View.inflate(this, R.layout.dialog_loading_bg2, null);
        dialog = new Dialog(this, R.style.dialog);
        dialog.setCancelable(true);
        dialog.setContentView(layout);
    }


    public void showProgressDialog() {
        if (dialog != null && !isFinishing()) {
            dialog.show();
        }
    }

    public void showProgressDialog(boolean cancelable) {
        if (dialog != null && !isFinishing()) {
            dialog.setCancelable(false);
            dialog.show();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
        ActivityStack.getInstance().finishOwnerActivity(this);
    }

    public void showError(String msg) {
        MyToast.showShort(msg);
    }

    public void showErrorDialog(String msg) {
        if (meDialog == null) {
            meDialog = new MyDialog(this);
            return;
        }
        meDialog.showDialog(R.layout.dialog_verify);
        TextView tv_msg = (TextView) meDialog.findViewById(R.id.tv_warn_msg);
        tv_msg.setText(msg);
        meDialog.findViewById(R.id.tv_warn_true).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meDialog.dismiss();
            }
        });
    }

//    public void showDialogYesOrNo(String msg, final boolean isFinish, final DialogListener listener) {
//        this.listener=listener;
//        if (meDialog == null) {
//            meDialog = new MyDialog(this);
//        }
//        meDialog.showDialog(R.layout.basemodule_dialog_sure_and_cancel_type_one);
//        TextView tv_msg = (TextView) meDialog.findViewById(R.id.tv_content);
//        tv_msg.setText(msg);
//        meDialog.findViewById(R.id.dialog_exit_true).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isFinish) {
//                    finish();
//                }
//                if(listener!=null){
//                    listener.onClickListenerYes();
//                }
//                meDialog.dismiss();
//            }
//        });
//        meDialog.findViewById(R.id.dialog_exit_false).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(listener!=null){
//                    listener.onClickListenerNo();
//                }
//                meDialog.dismiss();
//            }
//        });
//    }
}
