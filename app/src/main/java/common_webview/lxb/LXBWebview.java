package common_webview.lxb;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * created by houyl
 * on  5:25 PM
 */
public class LXBWebview  extends WebView {
    public LXBWebview(@NonNull Context context) {
        super(context);
    }

    public LXBWebview(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LXBWebview(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LXBWebview(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


}
