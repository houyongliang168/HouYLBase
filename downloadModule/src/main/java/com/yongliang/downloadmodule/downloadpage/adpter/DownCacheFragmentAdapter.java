package com.yongliang.downloadmodule.downloadpage.adpter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by houyongliang on 2017/11/7.
 */

public class DownCacheFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private List<String> listTitle;

    public DownCacheFragmentAdapter(FragmentManager fm, List<Fragment> list, List<String> listTitle) {
        super(fm);
        this.list = list;
        this.listTitle = listTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }


    //重写这个方法，将设置每个Tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle.get(position);
    }
    /* viewpager+fragment来回滑动fragment重新加载的简单解决办法*/
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }

}
