package com.yongliang.houylbase.utils;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yongliang.houylPage.section.sectionProducts.titleSectionProduct.TitleSectionProduct;
import com.yongliang.houylbase.R;

public class BlankFragment extends Fragment {

    private BlankViewModel mViewModel;

    public static BlankFragment newInstance() {
        return new BlankFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.blank_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BlankViewModel.class);
        mViewModel. event.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.d("hyl", "adfadfadf");
            }
        });
        // TODO: Use the ViewModel
        if(getView()!=null){
            LinearLayout ll_fg_contain= getView().findViewById(R.id.ll_fg_contain);
            TitleSectionProduct titleSectionProduct=   new TitleSectionProduct(this,null);
//            ll_fg_contain.a
                    titleSectionProduct.addView(ll_fg_contain);
        }

    }

}