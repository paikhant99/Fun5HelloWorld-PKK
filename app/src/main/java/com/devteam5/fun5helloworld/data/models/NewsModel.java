package com.devteam5.fun5helloworld.data.models;

import com.devteam5.fun5helloworld.data.vos.NewsVO;
import com.devteam5.fun5helloworld.events.SuccessForceRefreshGetNewsEvent;
import com.devteam5.fun5helloworld.events.SuccessGetNewsEvent;
import com.devteam5.fun5helloworld.network.HttpUrlConnectionDataAgentImpl;
import com.devteam5.fun5helloworld.network.NewsDataAgent;
import com.devteam5.fun5helloworld.network.OkHttpDataAgentImpl;
import com.devteam5.fun5helloworld.network.RetrofitDataAgentImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by paikhantko on 6/10/18.
 */

public class NewsModel {

    private static NewsModel objInstance;

    private NewsDataAgent mDataAgent;

    private static final String DUMMY_ACCESS_TOKEN = "b002c7e1a528b7cb460933fc2875e916";

    private Map<String, NewsVO> mNewsMap;

    private int mPage;

    private NewsModel() {
//        mDataAgent = HttpUrlConnectionDataAgentImpl.getObjInstance();
//        mDataAgent= OkHttpDataAgentImpl.getmObjInstance();
        mDataAgent = RetrofitDataAgentImpl.getInstance();

        mPage=1;
        mNewsMap=new HashMap<>();
        EventBus.getDefault().register(this);
    }

    public static NewsModel getObjInstance() {
        if (objInstance == null) {
            objInstance = new NewsModel();
        }
        return objInstance;
    }

    public void forceRefreshNewsList(){
        mPage=1;
        mDataAgent.loadNewsList(mPage, DUMMY_ACCESS_TOKEN,true);
    }

    public void loadNewsList() {
        mDataAgent.loadNewsList(mPage, DUMMY_ACCESS_TOKEN,false);
    }

    public NewsVO getNewsById(String newsId){
        return mNewsMap.get(newsId);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onSuccessGetNews(SuccessGetNewsEvent event){
        setDataIntoRespository(event.getNewsList());
        mPage++;
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onSuccessForceRefreshGetNews(SuccessForceRefreshGetNewsEvent event){
        setDataIntoRespository(event.getNewsList());
    }

    private void setDataIntoRespository(List<NewsVO> newsList){
        for (NewsVO news:newsList){
            mNewsMap.put(news.getNewsId(),news);
        }
    }

}
