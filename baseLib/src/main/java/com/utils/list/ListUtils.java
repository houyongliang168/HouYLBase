package com.utils.list;

import java.util.HashSet;
import java.util.List;

/**
 * Created by zcc on 2018/4/20.
 */

public class ListUtils {
    /**
     * list集合转成String字符串
     *
     * @param list
     * @return
     */
    public static String listToString(List<String> list) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String item : list) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(item);
        }
        return result.toString();
    }
    /**
     * 删除重复的数据
     *
     * @param list
     * @return
     */
    public static List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }
}
