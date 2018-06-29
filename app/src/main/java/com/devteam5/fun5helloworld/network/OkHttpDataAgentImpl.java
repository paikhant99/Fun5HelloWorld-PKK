package com.devteam5.fun5helloworld.network;

import java.util.concurrent.TimeUnit;
import android.os.AsyncTask;
import android.util.Log;

import com.devteam5.fun5helloworld.events.ApiErrorEvent;
import com.devteam5.fun5helloworld.events.SuccessGetNewsEvent;
import com.devteam5.fun5helloworld.network.responses.GetNewsResponse;
import com.devteam5.fun5helloworld.utils.MMNewsConstants;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by paikhantko on 6/17/18.
 */

public class OkHttpDataAgentImpl implements NewsDataAgent {

    private static OkHttpDataAgentImpl mObjInstance;
    private OkHttpClient okHttpClient;

    private OkHttpDataAgentImpl() {
        okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpDataAgentImpl getmObjInstance() {
        if(mObjInstance==null){
            mObjInstance=new OkHttpDataAgentImpl();
        }
        return mObjInstance;
    }

    @Override
    public void loadNewsList(final int page, final String accessToken,boolean isForceRefresh) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                RequestBody formBody=new FormBody.Builder()
                        .add(MMNewsConstants.PARAM_ACCESS_TOKEN,accessToken)
                        .add(MMNewsConstants.PARAM_PAGE,String.valueOf(page))
                        .build();

                Request request=new Request.Builder()
                        .url(MMNewsConstants.API_BASE_URL+MMNewsConstants.GET_NEWS)
                        .post(formBody)
                        .build();

                try {
                    Response response = okHttpClient.newCall(request).execute();

                    if(response.isSuccessful()){
                        String responseString=response.toString();
                        return responseString;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String responseString) {
                super.onPostExecute(responseString);
                Gson gson = new Gson();
                GetNewsResponse getNewsResponse = gson.fromJson(responseString, GetNewsResponse.class);
                Log.d("OnPostExecute", "News List Size : " + getNewsResponse.getMmNews().size());

                if (getNewsResponse.isResponseOK()) {
                    SuccessGetNewsEvent event = new SuccessGetNewsEvent(getNewsResponse.getMmNews());
                    EventBus.getDefault().post(event);
                } else {
                    ApiErrorEvent errorEvent = new ApiErrorEvent(getNewsResponse.getMessage());
                    EventBus.getDefault().post(errorEvent);
                }
            }
        }.execute();
    }
}
