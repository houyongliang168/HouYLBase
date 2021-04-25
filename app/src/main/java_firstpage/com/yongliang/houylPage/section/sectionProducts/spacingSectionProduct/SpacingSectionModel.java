package com.yongliang.houylPage.section.sectionProducts.spacingSectionProduct;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.yongliang.houylPage.section.baseView.BaseProductModel;

import mvvm.BaseModel;

/**
 * created by houyl
 * on  10:06 AM
 */
public class SpacingSectionModel extends BaseProductModel {


    public SpacingSectionModel(@NonNull Application application) {
        super (application);
    }

    public SpacingSectionModel(@NonNull Application application, BaseModel model) {
        super (application, model);
    }
//    获取请求数据
    @Override
    public void onCreate() {



    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void loadData(Bundle mBundle) {
        super.loadData(mBundle);
    }
}
