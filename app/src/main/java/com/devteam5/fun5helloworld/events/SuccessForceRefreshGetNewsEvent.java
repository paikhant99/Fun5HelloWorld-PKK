package com.devteam5.fun5helloworld.events;

import com.devteam5.fun5helloworld.data.vos.NewsVO;

import java.util.List;

/**
 * Created by paikhantko on 6/24/18.
 */

public class SuccessForceRefreshGetNewsEvent extends SuccessGetNewsEvent{

    private List<NewsVO> mNewsList;


    public SuccessForceRefreshGetNewsEvent(List<NewsVO> newsList) {
        super(newsList);
    }
}
