package com.utils.num;


import java.text.DecimalFormat;

/**
 * Created by zcc on 2019/3/28.
 */

public class NumUtils {
    public static String toPercent(Double num){
        DecimalFormat df = new DecimalFormat("0.00%");
        return df.format(num);
    }
}
