package common_weight.recy.demo1.http;


import com.txx.app.main.section.bean.CommonBean;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 网络API接口类

 */
public interface SectionService {


   @GET
    Observable<CommonBean> commonFunctions(
           @Url String url
   );

}
