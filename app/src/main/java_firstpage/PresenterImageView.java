
import android.content.Context;

import com.yongliang.houylPage.SWImageView;

/**
 * created by houyl
 * on  10:06 AM
 */
public class PresenterImageView extends BasePresenter {
    public PresenterImageView(Context mContext){
        this.mContext=mContext;
        swImageView=new SWImageView(mContext);
    }
    public SWImageView swImageView;
    public Context mContext;

    public void getString(){
//        异步获取请求
    }

}
