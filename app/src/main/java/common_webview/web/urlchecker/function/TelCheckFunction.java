package common_webview.web.urlchecker.function;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;

import common_webview.web.urlchecker.BaseUrlChecker;
import common_webview.web.util.SystemUtils;


/**
 * created by houyl
 * on  1:08 PM
 */
public class TelCheckFunction extends BaseUrlChecker {
    public TelCheckFunction() {
        setPriority (10);
    }

    public boolean shouldInterruptUrlOverride(WebView webView, String url) {
        if (url.startsWith ("tel")) {
            Uri uri = Uri.parse (url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            webView.getContext ().startActivity (intent);
            return true;
        } else if (url.endsWith (".pdf") || url.contains ("preservation/oml/downLoadAppendix")) {
            SystemUtils.openBrowser (webView.getContext (), url);
            return true;
        } else if (url.startsWith ("weixin://wap/pay?")) {
            Intent intent = new Intent();
            intent.setAction (Intent.ACTION_VIEW);
            intent.setData (Uri.parse (url));
            webView.getContext ().startActivity (intent);
            return true;
        }
        return false;
    }

}
