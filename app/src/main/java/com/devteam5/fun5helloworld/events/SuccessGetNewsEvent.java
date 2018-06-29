package com.devteam5.fun5helloworld.events;

import com.devteam5.fun5helloworld.data.vos.NewsVO;

import java.util.List;

/**
 * Created by paikhantko on 6/16/18.
 */

public class SuccessGetNewsEvent {
    private List<NewsVO> newsList;

    public SuccessGetNewsEvent(List<NewsVO> newsList) {
        this.newsList = newsList;
    }

    public List<NewsVO> getNewsList() {
        return newsList;
    }
}