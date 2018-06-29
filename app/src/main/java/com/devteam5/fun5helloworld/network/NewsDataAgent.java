package com.devteam5.fun5helloworld.network;

/**
 * Created by paikhantko on 6/10/18.
 */

public interface NewsDataAgent {
    void loadNewsList(int page,String accessToken,boolean isForceRefresh);
}
