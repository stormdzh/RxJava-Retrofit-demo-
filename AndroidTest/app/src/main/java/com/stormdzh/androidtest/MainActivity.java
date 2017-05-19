package com.stormdzh.androidtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.stormdzh.androidtest.model.DateModel;
import com.stormdzh.androidtest.request.RequestServes;
import com.stormdzh.androidtest.request.RequestServes2;
import com.stormdzh.androidtest.request.RequestServes3;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        requestNet1();
//        requestNet2();
        requestNet3();
    }

    Retrofit retrofit = new Retrofit.Builder()
            //http://m.test.com/test?id=1&token=123 例如这样的url：这里 基类的地址为： http://m.test.com
            .baseUrl("http://m.test.com")
            //增加返回值为String的支持
            .addConverterFactory(ScalarsConverterFactory.create())
            //增加返回值为Gson的支持(以实体类返回)
            .addConverterFactory(GsonConverterFactory.create())
            //增加返回值为Oservable<T>的支持
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    private void requestNet1() {
        RequestServes requestSerives = retrofit.create(RequestServes.class);//这里采用的是Java的动态代理模式
        Call<String> call = requestSerives.getString("1", "123");//传入我们请求的键值对的值

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("===", "return:" + response.body().toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("===", "失败");
            }
        });
    }

    private void requestNet2() {
        RequestServes2 requestSerives = retrofit.create(RequestServes2.class);//这里采用的是Java的动态代理模式
        Call<DateModel> call = requestSerives.getData("1", "123");//传入我们请求的键值对的值

        call.enqueue(new Callback<DateModel>() {
            @Override
            public void onResponse(Call<DateModel> call, Response<DateModel> response) {
                DateModel re = response.body();
                Log.e("===", "response--->" + re.res.zlist.get(0).name);
            }

            @Override
            public void onFailure(Call<DateModel> call, Throwable t) {
                Log.e("===", "failure");
            }
        });
    }

    private void requestNet3() {
        RequestServes3 requestSerives = retrofit.create(RequestServes3.class);//这里采用的是Java的动态代理模式
        Observable<DateModel> observable = requestSerives.getRx("1", "123");//传入我们请求的键值对的值

        observable.subscribeOn(Schedulers.io())
                //让订阅者在主线程
//                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DateModel>() {
                    @Override
                    public void onCompleted() {
                        Log.e("===", "onCompleted--->");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("===", "onError--->");
                    }

                    @Override
                    public void onNext(DateModel dateModel) {
                        Log.e("===", "onNext--->" + dateModel.res.zlist.get(0).name);
                    }
                });
    }
}
