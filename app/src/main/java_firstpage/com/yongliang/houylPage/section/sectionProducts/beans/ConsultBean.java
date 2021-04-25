package com.yongliang.houylPage.section.sectionProducts.beans;



import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * created by houyl
 * on  2:47 PM
 */
public class ConsultBean  {
    public static final int NONE = -1;
    public static final int CONSULT_ONE = 0;
    public static final int CONSULT_TWO = 1;
    public static final int PRODUCT_ONE = 2;
    public static final int PRODUCT_TWO = 3;

    //用 @IntDef "包住" 常量；
    // @Retention 定义策略
    // 声明构造器
    @IntDef({NONE, CONSULT_ONE, CONSULT_TWO, PRODUCT_ONE, PRODUCT_TWO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ReflashStatus {
    }
    //定义属性
    private int types = NONE;

    public void setTypes(@ReflashStatus int types) {
        this.types = types;
    }
    @ReflashStatus
    public int getTypes() {
        return types;
    }
}
