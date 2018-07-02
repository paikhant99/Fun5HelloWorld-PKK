package com.devteam5.fun5helloworld.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.devteam5.fun5helloworld.data.vos.NewsVO;

/**
 * Created by paikhantko on 6/30/18.
 */

public abstract class BaseNewsViewHolder extends RecyclerView.ViewHolder {
    public BaseNewsViewHolder(View itemView) {
        super(itemView);
    }

    public void bindData(NewsVO news){

    }
}
