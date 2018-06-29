package com.devteam5.fun5helloworld.delegates;

import com.devteam5.fun5helloworld.data.vos.NewsVO;

/**
 * Created by Pai Khant Ko on 6/3/2018.
 */

public interface NewsDelegate {

    void onTapNews(NewsVO news);
    void onTapFavorite(NewsVO news);
    void onTapComments(NewsVO news);
    void onTapSendTo(NewsVO news);
    void onTapStatistics(NewsVO news);

}
