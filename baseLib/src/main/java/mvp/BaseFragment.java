package mvp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


/**
 * Created by renshiqian on 2018/10/30.
 */

public class BaseFragment extends Fragment {
    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 显示progressDialog
     */
    public boolean showProgressDialog() {
        if (dialog != null && !getActivity().isFinishing()) {
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
    }
}
