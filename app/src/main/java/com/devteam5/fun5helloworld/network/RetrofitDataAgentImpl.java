package com.devteam5.fun5helloworld.network;

import com.devteam5.fun5helloworld.events.ApiErrorEvent;
import com.devteam5.fun5helloworld.events.SuccessForceRefreshGetNewsEvent;
import com.devteam5.fun5helloworld.events.SuccessGetNewsEvent;
import com.devteam5.fun5helloworld.network.responses.GetNewsResponse;
import com.devteam5.fun5helloworld.utils.MMNewsConstants;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by paikhantko on 6/17/18.
 */

public class RetrofitDataAgentImpl implements NewsDataAgent {

    private static RetrofitDataAgentImpl sObjInstance;

    private NewsApi mTheApi;

    private RetrofitDataAgentImpl(){
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(MMNewsConstants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        mTheApi=retrofit.create(NewsApi.class);

    }

    public static RetrofitDataAgentImpl getInstance(){
        if(sObjInstance==null){
            sObjInstance=new RetrofitDataAgentImpl();
        }
        return sObjInstance;
    }
    @Override
    public void loadNewsList(int page, String accessToken, final boolean isForceRefresh) {
        Call<GetNewsResponse> loadNewsCall=mTheApi.loadNewsList(accessToken,page);
        loadNewsCall.enqueue(new Callback<GetNewsResponse>() {// anonymous inner class
            @Override
            public void onResponse(Call<GetNewsResponse> call, Response<GetNewsResponse> response) {
                GetNewsResponse newsResponse=response.body();
                if(newsResponse !=null && newsResponse.isResponseOK()){
                    if(isForceRefresh){
                        SuccessForceRefreshGetNewsEvent event=new SuccessForceRefreshGetNewsEvent(newsResponse.getMmNews());
                        EventBus.getDefault().post(event);
                    }else{
                        SuccessGetNewsEvent event=new SuccessGetNewsEvent(newsResponse.getMmNews());
                        EventBus.getDefault().post(event);
                    }
                }else{
                    if(newsResponse==null){
                        ApiErrorEvent errorEvent=new ApiErrorEvent("Empty response in network call.");
                        EventBus.getDefault().post(errorEvent);
                    }else{
                        ApiErrorEvent errorEvent=new ApiErrorEvent(newsResponse.getMessage());
                        EventBus.getDefault().post(errorEvent);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetNewsResponse> call, Throwable t) {// Server down or api crashes
                ApiErrorEvent errorEvent=new ApiErrorEvent(t.getMessage());
                EventBus.getDefault().post(errorEvent);
            }
        });
    }
}
