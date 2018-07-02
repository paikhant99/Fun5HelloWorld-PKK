package com.devteam5.fun5helloworld.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devteam5.fun5helloworld.R;
import com.devteam5.fun5helloworld.data.vos.NewsVO;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by paikhantko on 6/30/18.
 */

public class NewsBriefViewHolder extends BaseNewsViewHolder {

    @BindView(R.id.tv_publication_title)
    TextView tvPublicationTitle;

    @BindView(R.id.tv_news_brief)
    TextView tvNewsBrief;

    @BindView(R.id.iv_news_brief)
    ImageView ivNewsHero;

    private NewsVO mNews;


    public NewsBriefViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void bindData(NewsVO newsVO){
        mNews=newsVO;

        tvPublicationTitle.setText(mNews.getPublication().getTitle());
        tvNewsBrief.setText(mNews.getBrief());

        if(!mNews.getImages().isEmpty()){
            Glide.with(ivNewsHero.getContext())
                    .load(mNews.getImages().get(0))
                    .into(ivNewsHero);
        }else{
            ivNewsHero.setVisibility(View.GONE);
        }


    }
}
