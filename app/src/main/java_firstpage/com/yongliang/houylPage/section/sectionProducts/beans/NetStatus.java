package com.yongliang.houylPage.section.sectionProducts.beans;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * created by houyl
 * on  4:04 PM
 */
public class NetStatus {
    //用 @IntDef "包住" 常量；
    // @Retention 定义策略
    // 声明构造器
    @IntDef({NO_KIND,SUCCESS, FAIL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ReflashKind {
    }


    //定义属性
    private int kind = NO_KIND;
    public static final int NO_KIND = 99;
    public static final int SUCCESS = 100;
    public static final int FAIL = 101;
    @ReflashKind
    public int getKind() {
        return kind;
    }

    public void setKind(@ReflashKind int kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "NetStatus{" +
                "kind=" + kind +
                '}';
    }
}
