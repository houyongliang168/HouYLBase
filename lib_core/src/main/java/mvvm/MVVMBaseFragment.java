package mvvm;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.utils.log.MyToast;
import com.widget.dialog.MyDialog;
import com.yongliang.baselib.R;


/**
 * Created by renshiqian on 2018/10/30.
 */

public class MVVMBaseFragment extends Fragment {
    private Dialog dialog;
    private Activity activity;
    private MyDialog meDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = this.getActivity();
        createProgressDialog();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 显示progressDialog
     */
    public boolean showProgressDialog() {
        if (dialog != null && !activity.isFinishing()) {
            dialog.show();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 关闭progressDialog
     */
    public void dismissProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 关闭dialog后，可以使用
     */
    public void nullProgressDialog() {
        dialog = null;
    }

    public void createProgressDialog() {
        View layout = View.inflate(activity, R.layout.dialog_loading_bg2, null);
        dialog = new Dialog(activity, R.style.dialog);
        dialog.setCancelable(true);
        dialog.setContentView(layout);
    }

//    private void createProgressDialog2() {
//        View layout = View.inflate(activity, R.layout.dialog_loading_bg2, null);
//        dialog = new Dialog(activity, R.style.dialog);
//        dialog.setCancelable(true);
//        dialog.setContentView(layout);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();

    }
    public void showError(String msg) {
        MyToast.showShort(msg);
    }

    public void showErrorDialog(String msg) {
        if (meDialog == null) {
            meDialog = new MyDialog(getActivity());
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

    public  boolean isBackPressed(){
        return true;
    }
}
