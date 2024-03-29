package com.yongliang.houylbase.utils;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.yongliang.houylbase.R;
import com.yongliang.houylbase.utils.tabs2.NoScrollViewPager;
import com.yongliang.houylbase.utils.tabs2.SlidingTabLayout2;
import com.yongliang.houylbase.utils.tabs2.TabelFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlankFragment extends Fragment {

    private BlankViewModel mViewModel;
    private SlidingTabLayout2 slidingTabLayout;
    private NoScrollViewPager viewPager;
    private ArrayList<Fragment> mFragments;
    private String[] mTitlesArrays ={"新闻","娱乐","头条","八卦"};

    public static BlankFragment newInstance() {
        return new BlankFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.blank_fragment, container, false);
        slidingTabLayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.viewpager_content);
        viewPager.setNoScroll(true);
        return view;
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

        mFragments = new ArrayList<>();
//        mFragments.add( AFragment.newInstance(1));
//        mFragments.add( AFragment.newInstance(2));
//        mFragments.add( AFragment.newInstance(3));
//        mFragments.add( AFragment.newInstance(4));

        List<String> ss= Arrays.asList(mTitlesArrays);
        TabelFragmentPagerAdapter pagerAdapter = new TabelFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitlesArrays);
        viewPager.setAdapter(pagerAdapter);

        slidingTabLayout.setViewPager(viewPager, mTitlesArrays);//tab和ViewPager进行关联


//        if(getView()!=null){
//            LinearLayout ll_fg_contain= getView().findViewById(R.id.ll_fg_contain);
//            TitleSectionProduct titleSectionProduct=   new TitleSectionProduct(this,null);
////            ll_fg_contain.a
//                    titleSectionProduct.addView(ll_fg_contain);
//        }

    }

}