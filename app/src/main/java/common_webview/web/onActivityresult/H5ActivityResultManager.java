package common_webview.web.onActivityresult;

import android.content.Intent;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * created by houyl
 * on  7:45 PM
 */
public class H5ActivityResultManager {
    private static H5ActivityResultManager instance;
    private List<OnH5ActivityResult> list = new CopyOnWriteArrayList();

    public static synchronized H5ActivityResultManager getInstance() {
        if (instance == null) {
            instance = new H5ActivityResultManager();
        }

        return instance;
    }

    private H5ActivityResultManager() {
    }

    public void put(OnH5ActivityResult onH5ActivityResult) {
        this.list.add(onH5ActivityResult);
    }

    public void remove(OnH5ActivityResult onH5ActivityResult) {
        this.list.remove(onH5ActivityResult);
    }

    public void sendResult(int requestCode, int resultCode, Intent dat) {
        if (!this.list.isEmpty()) {
            Iterator var4 = this.list.iterator();

            while(var4.hasNext()) {
                ((OnH5ActivityResult)var4.next()).onGetResult(requestCode, resultCode, dat);
            }

        }
    }
}
