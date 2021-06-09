package common_network;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 网络API接口类

 */
public interface ApiService {
    /**
     * 更多功能
     */
    @GET("isales/moreFunctions")
    Observable<MoreFunctionsBean> moreFunctions(
            @Query("token") String token
    );




}
