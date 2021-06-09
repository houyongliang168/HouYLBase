package com.txx.app.main.section.factory

import android.os.Bundle
import androidx.fragment.app.Fragment
import common_weight.recy.demo1.baseView.FragmentBaseSectionProduct

/**
 * created by houyl
 * on  9:18 AM
 */
interface FragmentSectionBaseFactory {
     fun <T : FragmentBaseSectionProduct<*, *>?> createProduct(clz: Class<T>, context: Fragment, bundle: Bundle): T
}