package com.yongliang.houylPage.section.sectionProducts.spacingSectionProduct;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.yongliang.houylPage.section.baseView.FragmentBaseSectionProduct;
import com.yongliang.houylbase.R;
import com.yongliang.houylbase.databinding.ItemTitleProductBinding;


/**
 * created by houyl
 * on  10:06 AM
 * 垂直间距的控制器
 */
public class SpacingSectionProduct extends FragmentBaseSectionProduct<ItemTitleProductBinding, SpacingSectionModel> {

    private View v_vertical;

    public SpacingSectionProduct(Fragment context, Bundle mBundle) {
        super (context, mBundle);

    }

    @Override
    public int getlayoutId() {
        return R.layout.product_spacing_vertical;
    }



    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public void setAdapter(View view) {
        v_vertical = view.findViewById(R.id.v_vertical);

    }



}
