package com.yongliang.houylbase.utils.tabs2;

import android.view.ViewGroup;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class TabelFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private String[] titleList;


    public TabelFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] titleList) {
        super (fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;

    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get (position);
    }

    @Override
    public int getCount() {
        return fragmentList.size ();
    }


    //重写这个方法，将设置每个Tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        if (titleList == null || titleList[position] == null) {
            return "";
        }
        return titleList[position];

    }

    /* viewpager+fragment来回滑动fragment重新加载的简单解决办法*/
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }

}
