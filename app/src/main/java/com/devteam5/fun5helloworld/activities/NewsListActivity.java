package com.devteam5.fun5helloworld.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.devteam5.fun5helloworld.R;
import com.devteam5.fun5helloworld.adapters.NewsAdapter;
import com.devteam5.fun5helloworld.data.models.NewsModel;
import com.devteam5.fun5helloworld.data.vos.NewsVO;
import com.devteam5.fun5helloworld.delegates.NewsDelegate;
import com.devteam5.fun5helloworld.events.ApiErrorEvent;
import com.devteam5.fun5helloworld.events.SuccessForceRefreshGetNewsEvent;
import com.devteam5.fun5helloworld.events.SuccessGetNewsEvent;
import com.devteam5.fun5helloworld.viewpods.EmptyViewPod;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pai Khant Ko on 5/27/2018.
 */

public class NewsListActivity extends BaseActivity implements NewsDelegate{

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_news)
    RecyclerView rvNews;

    @BindView(R.id.vp_empty)
    EmptyViewPod vpEmpty;

    private NewsAdapter mNewsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        ButterKnife.bind(this,this);

        setSupportActionBar(toolbar);

        mNewsAdapter = new NewsAdapter(this);
        rvNews.setAdapter(mNewsAdapter);
        rvNews.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false));
        rvNews.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private boolean isListEndReached=false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("OnScrollListener : ","onScrollStateChanged - "+newState);

                if(newState==RecyclerView.SCROLL_STATE_IDLE
                        && ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition()==recyclerView.getAdapter().getItemCount()-1
                        && !isListEndReached){
                    isListEndReached=true;
                    NewsModel.getObjInstance().loadNewsList();
                }
            }

            @Override
            public void onScrolled(RecyclerView rv, int dx, int dy) {
                super.onScrolled(rv, dx, dy);
                Log.d("OnScrollListener : ","onScrolled -x: "+dx+" -y : "+dy);

                int visibleItemCount=rv.getLayoutManager().getChildCount();
                int totalItemCount=rv.getLayoutManager().getItemCount();
                int pastVisibleItemCount=((LinearLayoutManager)rv.getLayoutManager()).findFirstVisibleItemPosition();

                if(visibleItemCount+pastVisibleItemCount<totalItemCount){
                    isListEndReached=false;
                }

            }
        });

        vpEmpty.setVisibility(View.GONE);

        NewsModel.getObjInstance().loadNewsList();
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NewsModel.getObjInstance().forceRefreshNewsList();
            }
        });

        vpEmpty.setEmptyData(R.drawable.empty_data_placeholder,getString(R.string.empty_msg));


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onTapNews(NewsVO news) {
        Intent intent=new Intent(getApplicationContext(),NewsDetailsActivity.class);
        intent.putExtra("newsId",news.getNewsId());
        startActivity(intent);
    }

    @Override
    public void onTapFavorite(NewsVO news) {

    }

    @Override
    public void onTapComments(NewsVO news) {

    }

    @Override
    public void onTapSendTo(NewsVO news) {

    }

    @Override
    public void onTapStatistics(NewsVO news) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN) // catch from eventBus in mainThread
    public void onSuccessGetNews(SuccessGetNewsEvent event){
        mNewsAdapter.appendNewsList(event.getNewsList());
        swipeRefreshLayout.setRefreshing(false);
        Log.d("onSuccessGetNews","onSuccessGetNews"+event.getNewsList());
    }

    @Subscribe(threadMode = ThreadMode.MAIN) // catch from eventBus in mainThread
    public void onSuccessForceRefreshGetNews(SuccessForceRefreshGetNewsEvent event){
        mNewsAdapter.appendNewsList(event.getNewsList());
        swipeRefreshLayout.setRefreshing(false);
        Log.d("onSuccessGetNews","onSuccessGetNews"+event.getNewsList());
    }

    @Subscribe(threadMode = ThreadMode.MAIN) // catch from eventBus in mainThread
    public void onFailGetNews(ApiErrorEvent event){
        vpEmpty.setVisibility(View.VISIBLE);
        Snackbar.make(swipeRefreshLayout,event.getMessage(),Snackbar.LENGTH_INDEFINITE).show();
        swipeRefreshLayout.setRefreshing(false);
    }
}
