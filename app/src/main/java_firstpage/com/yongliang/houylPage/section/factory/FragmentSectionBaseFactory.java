package com.yongliang.houylPage.section.factory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.yongliang.houylPage.section.base.BaseSection;


/**
 * created by houyl
 * on  9:18 AM
 */
public abstract class FragmentSectionBaseFactory {

   public abstract <T extends BaseSection> T createProduct(Class<T> clz, Fragment context, Bundle bundle);
}
