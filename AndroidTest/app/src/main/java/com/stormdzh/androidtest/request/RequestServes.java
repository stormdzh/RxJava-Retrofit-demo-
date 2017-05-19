package com.stormdzh.androidtest.request;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/5/19.
 */
public interface RequestServes {
    //http://m.test.com/test?id=1&token=123 例如这样的url：这里 基类的地址为： /test?
    @POST("/test?")
    Call<String> getString(@Query("id") String id,
                           @Query("token") String token);
}