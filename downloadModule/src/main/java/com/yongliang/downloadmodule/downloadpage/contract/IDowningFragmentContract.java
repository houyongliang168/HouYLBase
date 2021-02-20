package com.yongliang.downloadmodule.downloadpage.contract;


import mvp.CoreBasePresenter;
import mvp.ICoreBaseControl;

/**
 * 详情页面接口
 * Created by houyongliang on 2017/11/2.
 */

public interface IDowningFragmentContract {
    interface IDowningFragmentView extends ICoreBaseControl.ICoreBaseView {




    }

    interface IDowningFragmentModel extends ICoreBaseControl.ICoreBaseModel {


    }

    abstract class IDowningFragmentPresenter extends CoreBasePresenter<IDowningFragmentModel, IDowningFragmentView> {
        /*网络请求具体数据*/
        abstract void httpRequestData(int page, int size);


    }
}
