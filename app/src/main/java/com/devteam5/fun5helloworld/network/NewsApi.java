package com.devteam5.fun5helloworld.network;

import com.devteam5.fun5helloworld.network.responses.GetNewsResponse;
import com.devteam5.fun5helloworld.utils.MMNewsConstants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by paikhantko on 6/17/18.
 */

public interface NewsApi {

    @FormUrlEncoded
    @POST(MMNewsConstants.GET_NEWS)
    Call<GetNewsResponse> loadNewsList(
            @Field(MMNewsConstants.PARAM_ACCESS_TOKEN) String accessToken,
            @Field(MMNewsConstants.PARAM_PAGE) int page);
}
