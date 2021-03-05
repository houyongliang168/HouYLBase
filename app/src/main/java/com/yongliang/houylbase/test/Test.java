package com.yongliang.houylbase.test;

/**
 * created by houyl
 * on \ 2:51 PM
 */
public class Test {
    public static void main(String[] args) {
        long y = n(5);
        System.out.println(y);
    }
    public static long n(int num) {
        long x = 0;
        for(int i = 0;i<10;i++) {
            x = x+ i;
            return x + 5;
        }
        return 20;
    }
}
