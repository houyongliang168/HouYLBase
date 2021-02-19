package com.utils.optimize;

import android.os.Build;

public class PhoneBuildStatus {


    public static boolean isVivo() {
        String MARK = Build.MANUFACTURER.toLowerCase ();
        if (MARK.contains ("vivo")) {
            return true;
        }
        return false;
    }

    public static boolean isLowO() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return true;
        }
        return false;
    }
}
