package com.widget.loadmanager.core;


import com.widget.loadmanager.callback.Callback;

/**
 * Description:TODO
 * Create Time:2017/9/4 8:58
 * Author:KingJA
 * Email:kingjavip@gmail.com
 * 类型转换
 */
public interface Convertor<T> {
   Class<?extends Callback> map(T t);
}
