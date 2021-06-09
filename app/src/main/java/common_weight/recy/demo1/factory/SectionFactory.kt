package com.txx.app.main.section.factory

import android.os.Bundle
import androidx.fragment.app.Fragment
import common_weight.recy.demo1.baseView.FragmentBaseSectionProduct
import java.lang.reflect.InvocationTargetException

/**
 * created by houyl
 * on  9:18 AM
 */
object SectionFactory : FragmentSectionBaseFactory {
    override fun <T : FragmentBaseSectionProduct<*, *>?> createProduct(clz: Class<T>, fragment: Fragment, bundle: Bundle): T {
        var p: FragmentBaseSectionProduct<*, *>? = null
        try {
            val clazz = Class.forName(clz.name)
            // 获取到指定的构造方法
            val constructor1 = clazz.getConstructor(*arrayOf(Fragment::class.java, Bundle::class.java))
            // 通过获取到的构造方法创建对象
            p = constructor1.newInstance(fragment, bundle) as FragmentBaseSectionProduct<*, *>
            //            System.out.println(obj1.toString());

            // 也可以获取无参构造方法，但是比前面一种难
//            Object obj2 = clazz.getConstructor(new Class[]{}).newInstance(new Object[]{});
//            obj2 = clazz.getConstructor().newInstance();
//            System.out.println(obj2);
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return p as T
    }
}