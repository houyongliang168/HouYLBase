package mvp;

import android.app.Dialog;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

/**
 *
 */
public class BaseActivity extends FragmentActivity {
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        todo 基本数据展示待处理
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
    }

    @Override
    public void finish() {
        super.finish();
       ActivityStack.getInstance().finishActivity(this);
    }

}
