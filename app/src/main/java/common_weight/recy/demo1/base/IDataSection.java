package common_weight.recy.demo1.base;

import android.os.Bundle;

/**
 * created by houyl
 * on  1:18 PM
 * 数据通用接口
 */
public interface IDataSection {

    //    绑定数据 源
    public abstract void bindData(Bundle mBundle);

    //  刷新数据
    public void onReflesh();

    /*加载数据 网络请求数据 mBundle 传递的数据可能为空 */
    public abstract  void loadData(Bundle mBundle);

}
