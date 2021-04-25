package com.yongliang.houylPage.section.sectionProducts.titleSectionProduct;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.utils.log.MyLog;
import com.utils.log.MyToast;
import com.yongliang.houylPage.section.baseView.FragmentBaseSectionProduct;
import com.yongliang.houylPage.section.sectionProducts.beans.EnumNetStatus;
import com.yongliang.houylbase.R;
import com.yongliang.houylbase.databinding.ItemTitleProductBinding;


/**
 * created by houyl
 * on  10:06 AM
 */
public class TitleSectionProduct  extends FragmentBaseSectionProduct<ItemTitleProductBinding,TitleSectionModel> {

    public TitleSectionProduct(Fragment context, Bundle mBundle) {
        super (context, mBundle);

    }

    @Override
    public int getlayoutId() {
        return R.layout.item_title_product;
    }



    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void initViewObservable() {
        if(viewModel!=null){

            viewModel.requestNetStatus.observe(this, new Observer<EnumNetStatus>() {
                @Override
                public void onChanged(EnumNetStatus enumNetStatus) {
                    MyLog.wtf("hyl"," 接收到信息了");
                    switch (enumNetStatus){

                        case SUCCESS:
                            MyToast.showLong(" 接收到信息了.....requestNetStatus..1");
                            MyLog.wtf("hyl"," 接收到信息了....requestNetStatus..1");
                            break;

                        case FAIL:
                            MyToast.showLong(" 接收到信息了.....requestNetStatus..2..EnumNetStatus");
                            MyLog.wtf("hyl"," 接收到信息了....requestNetStatus..2");
                            MyLog.wtf("hyl"," 接收到信息了....requestNetStatus..EnumNetStatus2");
                            break;
                        default:
                            MyToast.showLong(" 接收到信息  失败了...default.");
                            MyLog.wtf("hyl"," 接收到信息   失败了....requestNetStatus....default");
                            break;
                    }

                }
            });
//            viewModel.requestNetStatus.observe(this, new Observer<Integer>() {
//                @Override
//                public void onChanged(Integer enumNetStatus) {
//                    MyLog.wtf("hyl"," 接收到信息了");
//                    switch (enumNetStatus){
//
//                        case 1:
//                            MyToast.showLong(" 接收到信息了.....requestNetStatus..1");
//                            MyLog.wtf("hyl"," 接收到信息了....requestNetStatus..1");
//                            break;
//
//                        case 2:
//                            MyToast.showLong(" 接收到信息了.....requestNetStatus..2");
//                            MyLog.wtf("hyl"," 接收到信息了....requestNetStatus..2");
//                            break;
//                        default:
//                            MyToast.showLong(" 接收到信息  失败了...default.");
//                            MyLog.wtf("hyl"," 接收到信息   失败了....requestNetStatus....default");
//                            break;
//                    }
//
//                }
//            });
        }
    }

    @Override
    public void setAdapter(View view) {
        view.findViewById(R.id.tv_title_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.wtf("hyl"," 点击了事件");
                if(viewModel!=null){

//                    viewModel.requestNetStatus.setValue(2);
                    viewModel.requestNetStatus.setValue(EnumNetStatus.FAIL);
                    MyLog.wtf("hyl"," 点击了事件 2");
                }
            }
        }); ;

    }



}
