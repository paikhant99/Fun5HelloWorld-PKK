package com.devteam5.fun5helloworld.network.responses;

import com.devteam5.fun5helloworld.data.vos.NewsVO;

import java.util.List;

/**
 * Created by paikhantko on 6/16/18.
 */

public class GetNewsResponse {
    private int code;
    private String message;
    private String apiVersion;
    private List<NewsVO> mmNews;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public List<NewsVO> getMmNews() {
        return mmNews;
    }

    public boolean isResponseOK(){
        return code==200 && mmNews!=null; //expression returns directly boolean
    }
}
