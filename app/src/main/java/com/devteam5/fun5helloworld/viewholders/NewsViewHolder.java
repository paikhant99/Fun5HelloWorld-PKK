package com.devteam5.fun5helloworld.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.devteam5.fun5helloworld.R;
import com.devteam5.fun5helloworld.data.vos.NewsVO;
import com.devteam5.fun5helloworld.delegates.NewsDelegate;

import org.mmtextview.components.MMTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pai Khant Ko on 5/27/2018.
 */

public class NewsViewHolder extends BaseNewsViewHolder {

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

    @Override
    public void bindData(NewsVO news) {
        super.bindData(news);
        mNews = news;
        tvNewsBrief.setText(mNews.getBrief());
        Glide.with(ivPublication.getContext())
                .load(mNews.getPublication().getLogo())
                .apply(RequestOptions
                        .circleCropTransform()
                        .placeholder(R.drawable.placeholder_img)
                        .error(R.drawable.error_img))
                .into(ivPublication);
        tvPublicationTitle.setText(mNews.getPublication().getTitle());
        tvPostedDate.setText(tvPostedDate.getContext()
                .getResources().getString(R.string.format_posted_date,news.getPostedDate()));

        if (!news.getImages().isEmpty()) {// null safety check included
            Glide.with(ivNewsHero.getContext())
                    .load(news.getImages().get(0))
                    .into(ivNewsHero);
        } else {
            ivNewsHero.setVisibility(View.GONE);
        }
    }
}
