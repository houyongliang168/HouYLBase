package common_webview.web.urlchecker;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * created by houyl
 * on  4:43 PM
 */


public class UrlCheckSdkManager {
    private List<BaseUrlChecker> urlCheckers;

    public UrlCheckSdkManager() {
    }


    public void removeUrlChecker(BaseUrlChecker iUrlChecker) {
        if (urlCheckers != null) {
            urlCheckers.remove (iUrlChecker);
        }
    }

    public void addUrlChecker(BaseUrlChecker iUrlChecker) {
        if (urlCheckers == null) {
            urlCheckers = new CopyOnWriteArrayList();
        }

        if (!urlCheckers.contains (iUrlChecker)) {
            urlCheckers.add (iUrlChecker);
        }
//        参考优先级处理
//        orderby ();
    }

    //    排序
    public void orderby() {
        if (urlCheckers == null) {
            return;
        }
        Collections.sort (urlCheckers, new Comparator<BaseUrlChecker>() {
            @Override
            public int compare(BaseUrlChecker o1, BaseUrlChecker o2) {
                return o1.getPriority () - o2.getPriority ();
            }
        });

    }

    public List<BaseUrlChecker> getUrlCheckers() {
        if (urlCheckers == null) {
            urlCheckers = new CopyOnWriteArrayList();
        }
        return urlCheckers;
    }

}

