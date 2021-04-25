package com.yongliang.houylPage.section.factory;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.yongliang.houylPage.section.base.BaseSection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * created by houyl
 * on  9:18 AM
 */
public class SectionFactory extends FragmentSectionBaseFactory {


    public SectionFactory() {
    }

    @Override
    public <T extends BaseSection> T createProduct(Class<T> clz, Fragment fragment, Bundle bundle) {
        BaseSection p = null;
        try {
            Class clazz = Class.forName (clz.getName ());
            // 获取到指定的构造方法
            Constructor constructor1 = clazz.getConstructor (new Class[]{Fragment.class, Bundle.class});
            // 通过获取到的构造方法创建对象
            p = (BaseSection) constructor1.newInstance (fragment, bundle);
//            System.out.println(obj1.toString());

            // 也可以获取无参构造方法，但是比前面一种难
//            Object obj2 = clazz.getConstructor(new Class[]{}).newInstance(new Object[]{});
//            obj2 = clazz.getConstructor().newInstance();
//            System.out.println(obj2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace ();
        } catch (NoSuchMethodException e) {
            e.printStackTrace ();
        } catch (SecurityException e) {
            e.printStackTrace ();
        } catch (InstantiationException e) {
            e.printStackTrace ();
        } catch (IllegalAccessException e) {
            e.printStackTrace ();
        } catch (IllegalArgumentException e) {
            e.printStackTrace ();
        } catch (InvocationTargetException e) {
            e.printStackTrace ();
        }
        return (T) p;
    }

}
