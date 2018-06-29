package com.devteam5.fun5helloworld.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.devteam5.fun5helloworld.R;
import com.devteam5.fun5helloworld.data.vos.NewsVO;
import com.devteam5.fun5helloworld.delegates.NewsDelegate;
import com.devteam5.fun5helloworld.utils.GlideApp;

import org.mmtextview.components.MMTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pai Khant Ko on 5/27/2018.
 */

public class NewsViewHolder extends RecyclerView.ViewHolder {

    private NewsDelegate mNewsDelegate;
    private NewsVO mNews;
    @BindView(R.id.tv_news_brief)
    MMTextView tvNewsBrief;

    @BindView(R.id.iv_publication)
    ImageView ivPublication;

    @BindView(R.id.tv_publication_title)
    TextView tvPublicationTitle;

    @BindView(R.id.tv_posted_date)
    TextView tvPostedDate;

    @BindView(R.id.iv_news_hero)
    ImageView ivNewsHero;


    public NewsViewHolder(View itemView, NewsDelegate newsDelegate) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        mNewsDelegate = newsDelegate;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewsDelegate.onTapNews(mNews);
            }
        });
    }

    public void setNewsData(NewsVO newsVO) {
        mNews = newsVO;
        tvNewsBrief.setText(mNews.getBrief());
        GlideApp.with(ivPublication.getContext())
                .load(mNews.getPublication().getLogo())
                .apply(RequestOptions
                        .circleCropTransform()
                        .placeholder(R.drawable.placeholder_img)
                        .error(R.drawable.error_img))
                .into(ivPublication);
        tvPublicationTitle.setText(mNews.getPublication().getTitle());
        tvPostedDate.setText(tvPostedDate.getContext()
                .getResources().getString(R.string.format_posted_date,newsVO.getPostedDate()));

        if (!newsVO.getImages().isEmpty()) {// null safety check included
            GlideApp.with(ivNewsHero.getContext())
                    .load(newsVO.getImages().get(0))
                    .into(ivNewsHero);
        } else {
            ivNewsHero.setVisibility(View.GONE);
        }
    }
}
