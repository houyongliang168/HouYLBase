package com.yongliang.houylbase.utils;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;

import com.yongliang.houylPage.section.base.ISectionLifecycleObserver;

import mvvm.SingleLiveEvent;

public class BlankViewModel extends ViewModel implements ISectionLifecycleObserver {
    public SingleLiveEvent<Integer> event=new SingleLiveEvent<>();

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void onCreate() {
        event.postValue(1);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {
        event.setValue(1);
    }

    @Override
    public void onPause() {

    }


}