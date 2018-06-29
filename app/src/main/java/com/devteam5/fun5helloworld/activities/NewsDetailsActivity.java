package com.devteam5.fun5helloworld.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devteam5.fun5helloworld.R;
import com.devteam5.fun5helloworld.data.models.NewsModel;
import com.devteam5.fun5helloworld.data.vos.NewsVO;
import com.devteam5.fun5helloworld.viewpods.EmptyViewPod;

import org.mmtextview.components.MMTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pai Khant Ko on 6/3/2018.
 */

public class NewsDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_news_details)
    TextView tvNewsDetails;

    @BindView(R.id.vp_empty)
    EmptyViewPod vpEmpty;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this,this);

        Toolbar toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);

        String newsId = getIntent().getStringExtra("newsId");
        Log.d("NewsDetailsActivity", "newsId : " + newsId);

        NewsVO news= NewsModel.getObjInstance().getNewsById(newsId);
        if(news!=null){
            coordinatorLayout.setVisibility(View.VISIBLE);
            bindData(news);
        }else {
            vpEmpty.setVisibility(View.VISIBLE);
            coordinatorLayout.setVisibility(View.GONE);
        }


    }

    private void bindData(NewsVO newsVO){
        tvNewsDetails.setText(newsVO.getDetails());
    }
}
